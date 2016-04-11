package homework4;

public class LongesPalindromicSubsequence {
	private static int[][] matrix;

	public static void lcs(char[] inString, char[] rString) {
		int n = inString.length;
		for (int i = 0; i < n; i++)
			matrix[i][0] = 0;
		for (int j = 0; j < n; j++)
			matrix[0][j] = 0;
		for (int i = 1; i < n; i++)
			for (int j = 1; j < n; j++)
				if (inString[i] == rString[j])
					matrix[i][j] = matrix[i - 1][j - 1] + 1;
				else
					matrix[i][j] = Math.max(matrix[i][j - 1], matrix[i - 1][j]);
		// return matrix[n][n]
	}

	public static String createString(int[][] matrix, char[] x, char[] y,
			int i, int j) {
		if (i == 0 || j == 0)
			return "";
		else if (x[i] == y[j])
			return createString(matrix, x, y, i - 1, j - 1) + x[i];
		else if (matrix[i][j - 1] > matrix[i - 1][j])
			return createString(matrix, x, y, i, j - 1);
		else
			return createString(matrix, x, y, i - 1, j);
	}

	public static void main(String[] args) {
		String inString = "ABCDEBCA";
		String rString = new StringBuilder(inString).reverse().toString();
		int n = inString.length();
		matrix = new int[n + 1][n + 1];
		// The lcs of a string and its reverse is not always a LPS so here we
		// make it one
		char[] inChar = new char[inString.length() + 1];
		char[] rChar = new char[rString.length() + 1];
		for (int i = 0; i < inString.length(); i++) {
			inChar[i + 1] = inString.charAt(i);
			rChar[i + 1] = rString.charAt(i);
		}
		lcs(inChar, rChar);
		System.out.println("The longest palidromic subsequence is : "
				+ createString(matrix, inChar, rChar, n, n));
	}

}
