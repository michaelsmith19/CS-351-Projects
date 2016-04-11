package problems;

public class SumOfPrimes {

	/**
	 * @param args
	 */
	static int counter = 4;
	public static boolean isPrime (double num) {
		boolean isPrime = true;
		
		for (int i = 3; i <= Math.sqrt(num); i+=2){
			if (num % i == 0 && num != i){
				//System.out.println(num + " is not prime");
				isPrime = false;
			}
		}
		
		//System.out.println(num + " is prime");
		return isPrime;
	}
	
	public static double sumPrimes () {
		double sum = 17;
		
		
		for (int i = 11; i < 2000000; i+=2){
			if (isPrime(i)){
				
				sum += i;
				//System.out.println(i + " was added to sum " + sum);
				counter++;
			}
		}
		
		return sum;
	}
	
	
	public static void main(String[] args) {
		double answer = sumPrimes();
		System.out.println(answer + " there were " + counter);
	}

}
