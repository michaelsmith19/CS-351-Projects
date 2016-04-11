package labs.lab5;

import java.io.IOException;

/**
 * The {@code Bugger} interface defines some methods that perform some specific
 * operations. Any implementation of the {@code Bugger} interface will
 * inherently contain bugs that aren't necessarily evident by visual inspection,
 * but instead must be found using the Eclipse IDE build in debugger.
 * 
 * @author BKey
 */
public interface Bugger {

	/**
	 * Remove all non letter characters from word. That is all characters that
	 * fail {@code Character.isLetter()}.
	 * 
	 * @param word
	 *            the {@code String} that is the word to remove non-letters from
	 * @return the modified {@code String} with all non-letters removed
	 * @throws IOException
	 */
	String removeAllNonLetters(String word) throws IOException;

	/**
	 * Calculate the average word length of the words given to the
	 * {@code Bugger}. This count EXCLUDES non-letter characters.
	 * 
	 * @return the average word length
	 */
	double calculateAverageWordLength();

}
