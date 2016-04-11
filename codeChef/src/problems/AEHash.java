package problems;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashSet;
import java.util.Set;
import java.util.StringTokenizer;

public class AEHash {

	public static String makeS(int a, int e) {
		StringBuilder s = new StringBuilder();
		for (int i = 0; i < a; i++) {
			s.append('A');
		}
		for (int i = 0; i < e; i++) {
			s.append('E');
		}
		return s.toString();
	}

	public static int numOf1(String s) {
		int result = 0;
		for (int i = 0; i < s.length(); i++) {
			if (s.charAt(i) == 'A')
				result++;
			if (!s.contains("A"))
				break;
		}
		return result;
	}

	public static int hash(String s) {
		int result = numOf1(s);
		if (s.length() > 1) {
			String s1 = "";
			String s2 = "";
			if (s.length() % 2 == 0) {
				s1 = s.substring(0, s.length() / 2);
				s2 = s.substring(s.length() / 2);
			} else {
				s1 = s.substring(0, (int) Math.floor(s.length() / 2));
				s2 = s.substring((int) Math.floor(s.length() / 2));
			}
			result = result + Math.max(hash(s1), hash(s2));
		}
		return result;
	}

	public static void runTest(int a, int e, int v) {
		if (a > 50 || e > 50 || v > 1000 || a < 0 || e < 0 || v < 0) {
			System.out.println(0);
			return;
		}
		int result = 0;
		String s = makeS(a, e);
		Set<String> perms = perms(s);

		for (String word : perms) {
			int hash = hash(word);
			if (hash == v)
				result++;
		}

		System.out.println(result % 1000000007);
	}

	public static Set<String> perms(String str) {
		Set<String> result = new HashSet<String>();
		if (str == null)
			return null;
		else if (str.length() == 0) {
			result.add("");
			return result;
		}
		char firstChar = str.charAt(0);
		String rem = str.substring(1);
		Set<String> words = perms(rem);
		for (String newString : words) {
			for (int i = 0; i <= newString.length(); i++) {
				result.add(CharAdd(newString, firstChar, i));
			}
		}
		return result;
	}

	public static String CharAdd(String str, char c, int j) {
		String first = str.substring(0, j);
		String last = str.substring(j);
		return first + c + last;
	}

	public static void main(String[] args) throws NumberFormatException,
			IOException {

		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		int t = Integer.parseInt(br.readLine());
		for (int testCase = 0; testCase < t; ++testCase) {
			StringTokenizer st = new StringTokenizer(br.readLine());
			int A = Integer.parseInt(st.nextToken());
			int E = Integer.parseInt(st.nextToken());
			int V = Integer.parseInt(st.nextToken());
			runTest(A, E, V);
		}
	}
}
