package problems;

public class PythagoreanTriplet {

	/**
	 * @param args
	 */
	
	static int a = 0;
	static int b = 0;
	static int c = 0;
	public static void findTriplet (){
		
		for(int i = 0; i < 500; i++){
			for(int j = 0; j < 500; j++){
				for(int k = 0; k < 500; k++){
					if((Math.pow(i, 2) +Math.pow(j, 2) ==Math.pow(k, 2)) && (i+j+k)==1000){
						a = i;
						b = j;
						c = k;	
					}
				}
			}
		}
	}
	
	public static void main(String[] args) {
		findTriplet();
		System.out.println(a + " " + b + " " + c);
		System.out.println(a*b*c);
	}

}
