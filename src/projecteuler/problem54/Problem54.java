package projecteuler.problem54;

public class Problem54 {

	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}
	
	public static int whoWon()
	{
		return 0;
	}

}

class Game
{
	public int[] cards_A;
	public int[] cards_B;
	public boolean same_color_A;
	public boolean same_color_B;
	public int value;
	
	byte fk;
	byte fh_duo;
	byte fh_trio;
	byte tp_first;
	byte tp_second;
	byte tk;
	byte op;
	
	public Game()
	{
		cards_A = new int[5];
		cards_B = new int[5];
		same_color_A = true;
		value = 0;
	}
	
	private void set_to_zero()
	{
		fk=0;
		fh_duo=0;
		fh_trio=0;
		tp_first=0;
		tp_second=0;
		tk=0;
		op=0;
	}
	
	public int new_cards(card[] new_cards_A, card[] new_cards_B)
	{
		set_to_zero();
		same_color_A = true;
		value = 0;
		int color = new_cards_A[0].color;
		
		for(int i = 0; i < new_cards_A.length; ++i)
		{
			cards_A[i] = new_cards_A[i].number;
			same_color_A &= new_cards_A[i].color == color;
		}
		
		same_color_B = true;
		value = 0;
		color = new_cards_A[0].color;
		
		for(int i = 0; i < new_cards_A.length; ++i)
		{
			cards_B[i] = new_cards_B[i].number;
			same_color_B &= new_cards_B[i].color == color;
		}
		
		sort_array();
		
		int a = computeResult(cards_A);
		int b = computeResult(cards_B);
		
		if(b>a) return 2;
		if(a>b) return 1;
		
		//if a == b
		switch (a)
		{
		case 8:
			return (cards_A[0] > cards_B[0] ? 1 : 2 );
		case 7:
			//if()
		}
		return 1;
	}
	
	private int computeResult(int[] cardsInHand)
	{
		if(same_color_A)
		{
			if(cardsInHand[4] == 10) 
			{
				return 9;
			}
			if(cardsInHand[0] - cardsInHand[4] == 4) 
			{
				return 8;
			}
		}
		else
		{
			if(cardsInHand[0] == cardsInHand[3] || cardsInHand[1] == cardsInHand[4]) 
			{
				fk = (byte) cardsInHand[1];
				return 7;
			}
			if(cardsInHand[0] == cardsInHand[2] && cardsInHand[3] == cardsInHand[4]) 
			{
				if(fh_trio == 0)
				{
					fh_trio = (byte) cardsInHand[1];
					fh_duo = (byte) cardsInHand[3];
				}
				else
				{
					if(fh_trio < (byte) cardsInHand[1])
					{
						
					}
					else if(fh_trio == (byte) cardsInHand[1])
					{
						if(fh_duo < (byte) cardsInHand[3])
						{
							
						}
					}
				}
				return 6;
			}
			if(cardsInHand[2] == cardsInHand[4] && cardsInHand[0] == cardsInHand[1])
			{
				fh_duo = (byte) cardsInHand[1];
				fh_trio = (byte) cardsInHand[3];
				return 6;
			}
		}
		if(same_color_A) 
		{
			return 5;
		}
		if(cardsInHand[0] - cardsInHand[4] == 4) 
		{
			return 4;
		}
		if(cardsInHand[0] == cardsInHand[2]) 
		{
			tk = (byte) cardsInHand[0];
			return 3;
		}
		if(cardsInHand[1] == cardsInHand[3]) 
		{
			tk = (byte) cardsInHand[0];
			return 3;
		}
		if(cardsInHand[2] == cardsInHand[4]) 
		{
			tk = (byte) cardsInHand[0];
			return 3;
		}
		if((cardsInHand[0] == cardsInHand[1] && cardsInHand[2] == cardsInHand[3]) || 
				(cardsInHand[1] == cardsInHand[2] && cardsInHand[3] == cardsInHand[4]) ||
				(cardsInHand[0] == cardsInHand[1] && cardsInHand[3] == cardsInHand[4]))
		{
			tp_first = (byte) cardsInHand[1]; 
			tp_second = (byte) cardsInHand[3];
			return 2;
		}
		if(cardsInHand[0]==cardsInHand[1] || cardsInHand[1] == cardsInHand[2])
		{
			op = (byte) cardsInHand[1];
			return 1;
		}
		if(cardsInHand[2]==cardsInHand[3] || cardsInHand[3] == cardsInHand[4])
		{
			op = (byte) cardsInHand[3];
			return 1;
		}
		
		return 0;
	}
	
	public int max(int ab)
	{
		int max = 0;
		if(ab == 1)
		{
			for(int i = 0; i < 5; ++i)
			{
				if(cards_A[i]>max)
				{
					max = cards_A[i];
				}
			}
			return max;
		}
		else
		{
			for(int i = 0; i < 5; ++i)
			{
				if(cards_B[i]>max)
				{
					max = cards_B[i];
				}
			}
			return max; 
		}
	}
	
	private void sort_array()
	{
		for(int j = 0; j < 4 ; ++j)
		{
			int max_ind = 0;
			for(int i = j; i < 5 ; ++i)
			{
				if(cards_A[i] > max_ind)
				{
					max_ind = i;
				}
			}
			
			int tmp = cards_A[0];
			cards_A[0] = cards_A[max_ind];
			cards_A[max_ind] = tmp;
		}
		for(int j = 0; j < 4 ; ++j)
		{
			int max_ind = 0;
			for(int i = j; i < 5 ; ++i)
			{
				if(cards_B[i] > max_ind)
				{
					max_ind = i;
				}
			}
			
			int tmp = cards_B[0];
			cards_B[0] = cards_B[max_ind];
			cards_B[max_ind] = tmp;
		}
	}
	
	private class card
	{
		public int number;
		public int color;
		
		public card()
		{
			number = 0;
			color = 0;
		}
	}
}

