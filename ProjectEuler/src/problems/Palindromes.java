package problems;

public class Palindromes {

	
	public static int isPalindrome (int x,int y){
		int product = x * y;
		
		int temp = product;
		int rev = 0;
		
		while (product > 0) {
			int dig = product % 10;
			rev = rev * 10 + dig;
			product = product/10;			
		}
		
		if (temp == rev){
			return temp;
		}
		return -1;
	}
	
 public static void main (String[] Args){
	 	int control = -1;
	 	int currentGreatest = 0;
		for(int y = 999;y > 800; y --){
			for (int x = 999;x > 800; x --){
				control = isPalindrome (x,y);
				if (control != -1){
					break;
				}
			}
			if (control > currentGreatest)
				currentGreatest = control;
       }
		
		System.out.println(currentGreatest);
	}
 }
	

