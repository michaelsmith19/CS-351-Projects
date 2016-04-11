package problems;

public class SumSquareDifference {

	/**
	 * @param args
	 */
	public static int sumOfSquares() {
		int sum = 0;
		
		for (int x = 1; x <= 100; x++){
			sum += x*x;
		}
		
		return sum;
	}
	
	public static int squareOfSum() {
		int square = 0;
		int sum = 0;
		for (int x = 1; x<=100; x++){
			sum += x;
		}
		square = sum * sum;
		
		return square;
	}
	
	public static void main(String[] args) {
		int sum = sumOfSquares();
		int square = squareOfSum();
		
		if (square > sum)
			System.out.println(square - sum);
		else
			System.out.println(sum - square);

	}

}
