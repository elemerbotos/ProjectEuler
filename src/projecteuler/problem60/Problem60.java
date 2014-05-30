package projecteuler.problem60;

import java.io.IOException;
import java.math.BigInteger;
import java.util.ArrayList;

public class Problem60 {
	
	private ArrayList<BigInteger> primes;
	private BigInteger boundaryForLookForPrimes;
	private final static int MAX = 120000;
	
	public Problem60(){
		primes = new ArrayList<BigInteger>();
		
		boundaryForLookForPrimes = new BigInteger("30000") ;
		
		primes.add(new BigInteger("3"));
		primes.add(new BigInteger("7"));
		primes.add(new BigInteger("109"));
		primes.add(new BigInteger("673"));
		System.out.println("Computing primes");
		lookForPrimes();
		System.out.println("Primes computed");
	}
	
	public static void main(String[] args) {
		Problem60 primeSets = new Problem60();
		primeSets.runNotFixedPrimes();
	}
	
	public int runNotFixedPrimes() {
		BigInteger[] selectedPrimes = new BigInteger[5];
		selectedPrimes[0] = primes.get(0);
		selectedPrimes[1] = primes.get(1);
		selectedPrimes[2] = primes.get(2);
		selectedPrimes[3] = primes.get(3);
		for(int a = 0 ; a < MAX-4 ; ++a) {
			boolean gotIt = true;
			selectedPrimes[0] = primes.get(a);
			for(int b = a+1 ; b < MAX-3 && b != a && gotIt; ++b) {
				selectedPrimes[1] = primes.get(b);
				gotIt &= check(selectedPrimes[0], selectedPrimes[1]);
				for(int c = b+1 ; c < MAX-2 && c != b && c != a && gotIt; ++c) {
					selectedPrimes[2] = primes.get(c);
					gotIt &= check(selectedPrimes[0], selectedPrimes[2]);
					gotIt &= check(selectedPrimes[1], selectedPrimes[2]);
					System.out.println(selectedPrimes[0].toString() + " " + selectedPrimes[1].toString() + " " 
							+ selectedPrimes[2].toString());
					for(int d = c + 1 ; d < MAX-1 && d != c && d != b && d != a && gotIt; ++d) {
						selectedPrimes[3] = primes.get(d);
						gotIt &= check(selectedPrimes[0], selectedPrimes[3]);
						gotIt &= check(selectedPrimes[1], selectedPrimes[3]);
						gotIt &= check(selectedPrimes[2], selectedPrimes[3]);
						System.out.println(selectedPrimes[0].toString() + " " + selectedPrimes[1].toString() + " " 
											+ selectedPrimes[2].toString() + " " + selectedPrimes[3].toString());
						for(int e = d + 1 ; e < MAX && e != d && e != c && e != b && e != a && gotIt; ++e) {
							//System.out.println(e);
							selectedPrimes[4] = primes.get(e);
							if(checkSet(selectedPrimes)) {
								System.out.println("megvan");
							}
						}
					}
				}
			}
		}
		return -1;
	}
	
	private boolean check(BigInteger bigInteger, BigInteger bigInteger2) {
		return isPrime(new BigInteger(bigInteger.toString() + bigInteger2.toString())) && 
				isPrime(new BigInteger(bigInteger2.toString() + bigInteger.toString()));
	}

	public int run() {
		BigInteger[] selectedPrimes = new BigInteger[5];
		selectedPrimes[0] = primes.get(0);
		selectedPrimes[1] = primes.get(1);
		selectedPrimes[2] = primes.get(2);
		selectedPrimes[3] = primes.get(3);
		
		boolean found = false;
		for(int i = 4 ; !found ; ++i) {
			if(i >= primes.size()) {
				boundaryForLookForPrimes = boundaryForLookForPrimes.add(new BigInteger("10000"));
				lookForPrimes();
				System.out.println(boundaryForLookForPrimes.toString());
			}
			selectedPrimes[4] = primes.get(i);
			if(checkSet(selectedPrimes)) {
				System.out.println("MEGVAN");
				return 12;
			}
		}
		return -1;
	}

	private boolean checkSet(BigInteger[] selectedPrimes) {
		String numb = selectedPrimes[selectedPrimes.length-1].toString();
		for(int i = 0; i < selectedPrimes.length-1 ; ++i) {
			if(!isPrime(new BigInteger (selectedPrimes[i].toString()+numb))) {
				return false;
			}
		}
		for(int i = 0; i < selectedPrimes.length-1 ; ++i) {
			if(!isPrime(new BigInteger (numb+selectedPrimes[i].toString()))) {
				return false;
			}
		}
		return true;
	}
	
	private boolean checkAllWithAll(BigInteger[] selectedPrimes) {
		String numb = selectedPrimes[selectedPrimes.length-1].toString();
		for(int i = 0; i < selectedPrimes.length-1 ; ++i) {
			if(!isPrime(new BigInteger (selectedPrimes[i].toString()+numb))) {
				return false;
			}
		}
		for(int i = 0; i < selectedPrimes.length-1 ; ++i) {
			if(!isPrime(new BigInteger (numb+selectedPrimes[i].toString()))) {
				return false;
			}
		}
		numb = selectedPrimes[selectedPrimes.length-2].toString();
		for(int i = 0; i < selectedPrimes.length-2 ; ++i) {
			if(!isPrime(new BigInteger (selectedPrimes[i].toString()+numb))) {
				return false;
			}
		}
		for(int i = 0; i < selectedPrimes.length-2 ; ++i) {
			if(!isPrime(new BigInteger (numb+selectedPrimes[i].toString()))) {
				return false;
			}
		}
		numb = selectedPrimes[selectedPrimes.length-3].toString();
		for(int i = 0; i < selectedPrimes.length-3 ; ++i) {
			if(!isPrime(new BigInteger (selectedPrimes[i].toString()+numb))) {
				return false;
			}
		}
		for(int i = 0; i < selectedPrimes.length-3 ; ++i) {
			if(!isPrime(new BigInteger (numb+selectedPrimes[i].toString()))) {
				return false;
			}
		}
		numb = selectedPrimes[selectedPrimes.length-4].toString();
		for(int i = 0; i < selectedPrimes.length-4 ; ++i) {
			if(!isPrime(new BigInteger (selectedPrimes[i].toString()+numb))) {
				return false;
			}
		}
		for(int i = 0; i < selectedPrimes.length-4 ; ++i) {
			if(!isPrime(new BigInteger (numb+selectedPrimes[i].toString()))) {
				return false;
			}
		}
		return true;
	}

	private boolean isPrime(BigInteger num) {
		BigInteger sqr = bigIntSqrt(num);
		for (BigInteger i = new BigInteger("2"); i.compareTo(sqr) < 0; 
				i = i.add(BigInteger.ONE)) {
			//System.out.println(i.toString());
			if(num.mod(i).equals(BigInteger.ZERO)) {
				return false;
			}
		}
		return true;
	}
	
	private void lookForPrimes() {
		int counter = 0;
		BigInteger i = primes.get(primes.size()-1);
		i = i.add(BigInteger.ONE);
		for( ; i.compareTo(boundaryForLookForPrimes) <= 0 || counter <= MAX ; i = i.add(BigInteger.ONE)) {
			//System.out.println("valami");
			//if(i.mod(new BigInteger("1000")).equals(BigInteger.ZERO)) System.out.println("i:" + i);
			//System.out.println(i.toString());
			if(isPrime(i)) {
				primes.add(new BigInteger(i.toString()));
				++counter;
			}
		}
	}

	private static BigInteger bigIntSqrt(BigInteger x)
			throws IllegalArgumentException {
		if (x.compareTo(BigInteger.ZERO) < 0) {
			throw new IllegalArgumentException("Negative argument.");
		}
		// square roots of 0 and 1 are trivial and
		// y == 0 will cause a divide-by-zero exception
		if (x == BigInteger.ZERO || x == BigInteger.ONE) {
			return x;
		} // end if
		BigInteger two = BigInteger.valueOf(2L);
		BigInteger y;
		// starting with y = x / 2 avoids magnitude issues with x squared
		for (y = x.divide(two); y.compareTo(x.divide(y)) > 0; 
				y = ((x.divide(y)).add(y)).divide(two));
		return y;
	} // end bigIntSqrt
	
	

}
