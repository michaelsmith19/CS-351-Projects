package labs.lab5;

import java.io.IOException;
import java.io.Reader;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;

/**
 * The implementation of a {@link Bugger} interface. As defined, this
 * implementation contains bugs that aren't necessarily obvious by visual
 * inspection. Instead, the code must be traversed through using the Eclipse IDE
 * debugger and breakpoints.
 * 
 * @author BKey
 */
public class BuggerImpl implements Bugger {
	private List<Integer> wordLengthList;
	private List<String> wordsList;
	int numWords;

	/**
	 * The default constructor for a {@code BuggerImpl}.
	 */
	public BuggerImpl(String[] words) {
		buildListOfWords(words);
	}

	/**
	 * Based on the String[] of words passed into the constructor of this
	 * {@code BuggerImpl}, fill in the list of words and their lengths.
	 * 
	 * @param words
	 */
	private void buildListOfWords(String[] words) {
		String onlyLettersVersion = "";

		if (words.length > 1) {
			// initialize lists
			this.wordsList = new ArrayList<String>();
			this.wordLengthList = new ArrayList<Integer>(10);

			/*
			 * QUESTION #1: How many elements does a new ArrayList<Integer>()
			 * allocate space for (in the default case)? What are they filled with?
			 */
		}

		for (int i = 1; i < words.length; i++) {
			try {
				onlyLettersVersion = removeAllNonLetters(words[i]);

				wordsList.add(onlyLettersVersion);
			} catch (IOException e) {
				// issue with reading the arguments
				System.out
						.println("There was an issue when reading the arguments.");
				e.printStackTrace();
			}

			numWords++;

			storeAssociatedUniqueWordLength(wordsList.get(wordsList
					.indexOf(onlyLettersVersion)));
		}

		/*
		 * QUESTION #2 (input 2): What is the mod count of the wordsList at this
		 * point?
		 */
	}

	/**
	 * Store the length of the word passed in for later use.
	 * 
	 * @param word
	 *            the {@String} word to get the length of and store
	 */
	private void storeAssociatedUniqueWordLength(String word) {
		int wordLength = word.length();

		if (!wordLengthList.contains(wordLength))
			wordLengthList.add(wordLength);
	}

	@Override
	public double calculateAverageWordLength() {
		int lengthAllWords = 0;

		for (int i = 0; i < numWords; i++) {
			lengthAllWords += wordLengthList.get(i);
		}

		// avg = length of all words/total number of words
		return (double) (lengthAllWords / numWords);
	}

	@Override
	public String removeAllNonLetters(String word) throws IOException {
		int ch;
		StringBuilder stringBuilder = new StringBuilder();
		Reader reader = new StringReader(word);

		while ((ch = reader.read()) != -1) {
			if (Character.isLetter(ch)) {
				stringBuilder.append((char) ch);
			}
		}

		return stringBuilder.toString();
	}
}
