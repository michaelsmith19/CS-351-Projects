package cs361;

public class TestScript {

	public static void main(String[] args) {
		String[] items = { "hello", "myfrind", "this", "sucks" };
	
		for (int n = 0; n < items.length; n++) {
			for (int i = n; i >= 0; i--) {
				System.out.println(items[i]);
			}
		}
	}
}
