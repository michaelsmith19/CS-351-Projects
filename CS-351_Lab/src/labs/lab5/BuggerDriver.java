package labs.lab5;

import labs.lab5.Bugger;
import labs.lab5.BuggerImpl;

/**
 * This is the driver class for the Bugger lab. It takes input from the command
 * line or run configurations and fills the String[] args parameter in the main
 * method.
 * 
 * @author BKey
 * 
 */
public class BuggerDriver {

	public static void main(String[] args) {
		Bugger bugger = new BuggerImpl(args);

		System.out.println("The words are:");
		for (int i = 0; i < args.length; i++) {
			System.out.println(args[i]);
		}
		System.out
				.println("\nThe average word length (excluding non-letter characters) is: "
						+ bugger.calculateAverageWordLength());
	}

}