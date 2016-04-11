package testRandom;

import java.util.Random;

public class TestingRand {

	/**
	 * @param args
	 */
	public static void main(String[] args) {

		Random rand = new Random();
		rand.setSeed(3847509);

		for (int i = 0; i < 20; i++) {
			rand.nextInt(100 - 2);
			int ran = rand.nextInt(50 - 2);
			System.out.println(ran);
		}

	}

}
