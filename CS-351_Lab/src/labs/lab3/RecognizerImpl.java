package labs.lab3;

import java.io.IOException;
import java.io.Reader;
import java.io.StringReader;

public class RecognizerImpl implements Recognizer {
    private int ch;
	
	@Override
	public boolean parseBal(Reader reader) throws IOException {
		ch = reader.read();
		
		if      (ch == '.') return true;
		else if (ch == '(') return parseSingle(reader);
		else if (ch == '[') return parseDouble(reader);
		else if (ch == '{') return parseTriple(reader);
		else return false;
	}

	@Override
	public boolean parseSingle(Reader reader) throws IOException {
	
		return parseBal(reader) && ((ch = reader.read()) == ')'); 
	}

	@Override
	public boolean parseDouble(Reader reader) throws IOException {

		return parseBal(reader) && parseBal(reader) && ((ch = reader.read()) == ']'); 
	}

	@Override
	public boolean parseTriple(Reader reader) throws IOException {
	
		return parseBal(reader) && parseBal(reader) && parseBal(reader) && ((ch = reader.read()) == '}'); 
	}

	/**
	 * This method should be the one to print to the console using
	 * {@code System.out.println()} that the input is either recognized or
	 * un-recognizable.
	 * 
	 * @param args
	 */
	public static void main(String[] args) {

		String validInput1 = ".";
		String invalidInput1 = "([.])";
		String validInput2 = "((((((.))))))";
		String invalidInput2 = "[.[.].]";
		String validInput3 = "{[.(.)].(.)}";
		String invalidInput3 = "[.)";
		String[] potentialInputs = { validInput1, invalidInput1, validInput2,
				invalidInput2, validInput3, invalidInput3 };

		Recognizer recognizer = new RecognizerImpl();

		try {
			for (int i = 0; i < 6; i++) {
				if (recognizer.parseBal(new StringReader(potentialInputs[i]))) {
					// must have recognized it!
					System.out.println("Recognizer recognized "
							+ potentialInputs[i] + " as valid.");
				} else {
					// invalid, boo.
					System.out.println("Recognizer did NOT recognize "
							+ potentialInputs[i] + " as valid.");
				}
			}
		} catch (IOException e) {
			System.out.println("IO Exception. Recognizer = Dead.");
			e.printStackTrace();
		}
	}

}
