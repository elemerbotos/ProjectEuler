package projecteuler.problem54;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class Problem54 {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		int player1 = 0;
		int player2 = 0;

		Game pokerGame = new Game();
		Path path = Paths.get("d:\\ProjectEuler\\poker.txt");
		List<String> lines;
		try {
			lines = Files.readAllLines(path, Charset.defaultCharset());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			System.out.println("Could not open the file!!!");
			return;
		}

		for (String line : lines) {
			Card[] cardsPlayer1 = new Card[5];
			Card[] cardsPlayer2 = new Card[5];
			String[] cards = line.split(" ");
			//System.out.println("PLAYER BEGIN");
			for (int i = 0; i < 5; ++i) {
				cardsPlayer1[i] = new Card();
				cardsPlayer1[i].color = getColor(cards[i].charAt(1));
				cardsPlayer1[i].number = getNumber(cards[i].charAt(0));
				//System.out.print(cardsPlayer1[i].number + "-"
				//		+ cardsPlayer1[i].color + " ");
			}
			//System.out.println("");
			for (int i = 0; i < 5; ++i) {
				cardsPlayer2[i] = new Card();
				cardsPlayer2[i].color = getColor(cards[i + 5].charAt(1));
				cardsPlayer2[i].number = getNumber(cards[i + 5].charAt(0));
				//System.out.print(cardsPlayer2[i].number + "-"
				//		+ cardsPlayer2[i].color + " ");
			}
			//System.out.println("\nPLAYER END");
			byte res = pokerGame.new_cards(cardsPlayer1, cardsPlayer2);
			if (res == -1) {
				System.out.println("Could not decide! ");
			}
			if (res == 1) {
				++player1;
			} else if (res == 2) {
				++player2;
			}

		}

		System.out.println("player1 won: " + player1 + "matches");
		System.out.println("player2 won: " + player2 + "matches");
	}

	private static Byte getNumber(char charAt) {
		switch (charAt) {
		case 'T':
			return 10;
		case 'J':
			return 11;
		case 'Q':
			return 12;
		case 'K':
			return 13;
		case 'A':
			return 14;
		}
		if (charAt > '0' && charAt < ':') {
			return (byte) (charAt - 48);
		}
		throw new IllegalArgumentException("Could not decide number of card!");
	}

	private static Byte getColor(char charAt) {
		switch (charAt) {
		case 'S':
			return 1;
		case 'C':
			return 2;
		case 'H':
			return 3;
		case 'D':
			return 4;
		}
		throw new IllegalArgumentException("Could not decide color!");
	}
}

class Game {
	public Byte[] cardsA;
	public Byte[] cardsB;
	public boolean sameColorA;
	public boolean sameColorB;
	public int value;

	static final byte PLAYER_1 = 1;
	static final byte PLAYER_2 = 2;

	byte fh_duo;
	byte fh_trio;
	byte fhWinner;
	byte flWinner;
	byte tp_first;
	byte tp_second;
	byte tpWinner;
	byte tkWinner;
	byte tk;
	byte op;
	byte opWinner;

	public Game() {
		cardsA = new Byte[5];
		cardsB = new Byte[5];
		sameColorA = true;
		value = 0;
		set_to_zero();
	}

	private void set_to_zero() {
		fh_duo = 0;
		fh_trio = 0;
		tp_first = 0;
		tp_second = 0;
		tkWinner = 0;
		op = 0;
		fhWinner = 0;
		flWinner = 0;
		tk = 0;
		opWinner = 0;
		tpWinner = 0;
	}

	public byte new_cards(Card[] newCardsA, Card[] newCardsB) {
		set_to_zero();
		sameColorA = true;
		value = 0;
		int color = newCardsA[0].color;

		for (int i = 0; i < newCardsA.length; ++i) {
			cardsA[i] = newCardsA[i].number;
			sameColorA &= newCardsA[i].color == color;
		}

		sameColorB = true;
		value = 0;
		color = newCardsB[0].color;

		for (int i = 0; i < newCardsB.length; ++i) {
			cardsB[i] = newCardsB[i].number;
			sameColorB &= newCardsB[i].color == color;
		}

		// sortArray();
		Arrays.sort(cardsA, new Comparator<Byte>() {

			@Override
			public int compare(Byte o1, Byte o2) {

				return o2 - o1;
			}

		});
		Arrays.sort(cardsB, Collections.reverseOrder());

		int a = computeResult(PLAYER_1);
		int b = computeResult(PLAYER_2);

		if (a == 9 || b == 9) {
			System.out.println("Royal Flush");
		}
		
		if(sameColorA) {
			for (int i = 0; i < 5; ++i) {
				System.out.print(newCardsA[i].number + " ");
			}
			System.out.println("");
		}
		
		if(sameColorB) {
			for (int i = 0; i < 5; ++i) {
				System.out.print(newCardsB[i].number + " ");
			}
			System.out.println("");
		}

		if (b > a)
			return PLAYER_2;
		if (a > b)
			return PLAYER_1;

		// if a == b
		try {
			switch (a) {
			case 8:
				return (cardsA[0] > cardsB[0] ? PLAYER_1 : PLAYER_2);
			case 7:
				return ((cardsA[0] == cardsB[0]) ? (cardsA[4] > cardsB[4] ? PLAYER_1
						: PLAYER_2)
						: (cardsA[0] > cardsB[0] ? PLAYER_1 : PLAYER_2));
			case 6:
				return fhWinner;
			case 5:
				return flWinner;
			case 4:
				return (cardsA[0] > cardsB[0] ? PLAYER_1 : PLAYER_2);
			case 3:
				return tkWinner;
			case 2:
				return tpWinner;
			case 1:
				return opWinner;
			case 0:
				byte winner = PLAYER_2;
				decideFromMax(PLAYER_1, winner);
				return winner;
			}
		} catch (Exception ex) {
			System.out.println("Exception thrown with msg: " + ex.getMessage());
		}
		return -1;
	}

	private int computeResult(byte player) {
		Byte[] cardsInHand = player == 1 ? cardsA : cardsB;
		Boolean sameColor = player == 1 ? sameColorA : sameColorB; 
		if (sameColor) {
			System.out.println("Same color " + player);
			if (cardsInHand[4] == 10) {
				return 9;
			}
			if (cardsInHand[0] - cardsInHand[4] == 4) {
				return 8;
			}
		} else {
			if (cardsInHand[0] == cardsInHand[3]
					|| cardsInHand[1] == cardsInHand[4]) {
				return 7;
			}
			if (cardsInHand[0] == cardsInHand[2]
					&& cardsInHand[3] == cardsInHand[4]) {
				decideTrio(cardsInHand, 0);
				return 6;
			}
			if (cardsInHand[2] == cardsInHand[4]
					&& cardsInHand[0] == cardsInHand[1]) {
				decideTrio(cardsInHand, 4);
				return 6;
			}
		}
		if (sameColor) {
			if (flWinner != 0) {
				decideFromMax(player, flWinner);
				return 5;
			} else {
				flWinner = player;
			}
			return 5;
		}
		if (cardsInHand[0] - cardsInHand[4] == 4) {
			return 4;
		}
		if (cardsInHand[0] == cardsInHand[2]
				|| cardsInHand[1] == cardsInHand[3]
				|| cardsInHand[2] == cardsInHand[4]) {
			if (tkWinner != 0) {
				final byte TK_TMP = (byte) ((cardsInHand[0] == cardsInHand[2]) ? cardsInHand[0]
						: cardsInHand[2]);
				if (TK_TMP > tk) {
					tkWinner = player;
					return 3;
				}
				if (TK_TMP == tk) {
					decideFromMax(player, tkWinner);
					return 3;
				}
				if (TK_TMP < tk) {
					return 3;
				}
			} else {
				tkWinner = player;
				tk = (byte) ((cardsInHand[0] == cardsInHand[2]) ? cardsInHand[0]
						: cardsInHand[2]);
				return 3;
			}

		}
		if ((cardsInHand[0] == cardsInHand[1] && cardsInHand[2] == cardsInHand[3])
				|| (cardsInHand[1] == cardsInHand[2] && cardsInHand[3] == cardsInHand[4])
				|| (cardsInHand[0] == cardsInHand[1] && cardsInHand[3] == cardsInHand[4])) {
			if (tpWinner == 0) {
				tp_first = (byte) cardsInHand[1];
				tp_second = (byte) cardsInHand[3];
				tpWinner = player;
				return 2;
			} else {
				if (tp_first > (byte) cardsInHand[1]) {
					return 2;
				} else if (tp_first < (byte) cardsInHand[1]) {
					tpWinner = player;
					return 2;
				} else if (tp_second > (byte) cardsInHand[3]) {
					return 2;
				} else if (tp_second < (byte) cardsInHand[3]) {
					tpWinner = player;
					return 2;
				} else {
					decideFromMax(player, tpWinner);
					return 2;
				}
			}
		}
		if (cardsInHand[0] == cardsInHand[1]
				|| cardsInHand[1] == cardsInHand[2]) {
			if (opWinner == 0) {
				op = (byte) cardsInHand[1];
				return 1;
			} else {
				if (op > (byte) cardsInHand[1]) {
					return 1;
				} else if (op < (byte) cardsInHand[1]) {
					opWinner = player;
					return 1;
				} else {
					decideFromMax(player, opWinner);
					return 1;
				}
			}
		}
		if (cardsInHand[2] == cardsInHand[3]
				|| cardsInHand[3] == cardsInHand[4]) {
			if (opWinner == 0) {
				op = (byte) cardsInHand[3];
				return 1;
			} else {
				if (op > (byte) cardsInHand[3]) {
					return 1;
				} else if (op < (byte) cardsInHand[3]) {
					opWinner = player;
					return 1;
				} else {
					decideFromMax(player, opWinner);
					return 1;
				}
			}
		}

		return 0;
	}

	private void decideFromMax(byte player, Object winner) {
		Byte[] cardsInHand = player == 1 ? cardsA : cardsB;
		Byte[] otherPlayer = player == 1 ? cardsB : cardsA;
		for (int i = 0; i < 5; ++i) {
			if (cardsInHand[i] > otherPlayer[i]) {
				winner = player;
				return;
			} else if (cardsInHand[i] < otherPlayer[i]) {
				winner = player == PLAYER_1 ? PLAYER_2 : PLAYER_1;
				return;
			}
		}
		throw new IllegalArgumentException("DRAWN");
	}

	private void decideTrio(Byte[] cardsInHand, int i) {
		if (fh_trio == 0) {
			fh_trio = (byte) cardsInHand[i];
			fh_duo = (byte) cardsInHand[4 - i];
		} else {
			if (fh_trio < (byte) cardsInHand[i]) {
				fhWinner = PLAYER_2;
			} else if (fh_trio > (byte) cardsInHand[i]) {
				fhWinner = PLAYER_1;
			} else {
				if (fh_duo < (byte) cardsInHand[4 - i]) {
					fhWinner = PLAYER_2;
				} else if (fh_duo > (byte) cardsInHand[4 - i]) {
					fhWinner = PLAYER_1;
				} else {
					throw new IllegalArgumentException("DRAWN");
				}
			}
		}
	}

	public int max(int ab) {
		int max = 0;
		if (ab == 1) {
			for (int i = 0; i < 5; ++i) {
				if (cardsA[i] > max) {
					max = cardsA[i];
				}
			}
			return max;
		} else {
			for (int i = 0; i < 5; ++i) {
				if (cardsB[i] > max) {
					max = cardsB[i];
				}
			}
			return max;
		}
	}

	private void sortArray() {
		for (int j = 0; j < 4; ++j) {
			int max_ind = 0;
			for (int i = j; i < 5; ++i) {
				if (cardsA[i] > max_ind) {
					max_ind = i;
				}
			}

			Byte tmp = cardsA[0];
			cardsA[0] = cardsA[max_ind];
			cardsA[max_ind] = tmp;
		}
		for (int j = 0; j < 4; ++j) {
			int max_ind = 0;
			for (int i = j; i < 5; ++i) {
				if (cardsB[i] > max_ind) {
					max_ind = i;
				}
			}

			Byte tmp = cardsB[0];
			cardsB[0] = cardsB[max_ind];
			cardsB[max_ind] = tmp;
		}
	}
}

class Card {
	public Byte number;
	public Byte color;
}
