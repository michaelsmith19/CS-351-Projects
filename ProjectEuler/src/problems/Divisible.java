package problems;

public class Divisible {

	/**
	 * @param args
	 */
	
	public static boolean isDivisible (int x){
		
		if(x%11==0 && x%12==0 && x%13==0 && x%14==0 && x%15==0 && x%16==0 && x%17==0 &&
				x%18==0 && x%19==0 && x%20==0  )
			return true;
		return false;
	}
	
	public static void main(String[] args) {
		int start = 5000;
		
		while (start > 0){
			
			if (isDivisible (start)){
				System.out.println(start);
				return;
			}else 
				start+=20;
		}
	}

}
