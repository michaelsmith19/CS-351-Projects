package com.putable.siteriter.msmith19;

import java.io.IOException;
import java.io.Reader;
import java.util.ArrayList;

import com.putable.siteriter.SDLParseException;

/**
 * This class will turn the input from a reader into Tokens to be verified by
 * the recognizer and then loaded into a SymbolTable.
 * 
 * @author michaelsmith
 */
//XXX This class is compact and it looks like it works. Well done!
public class Tokenizer
{

	private ArrayList<Token>	tokens	= new ArrayList<Token>();

	public ArrayList<Token> getTokens()
	{
		return tokens;
	}

	/**
	 * Takes a Reader as input and parses it turning all discrete elements into
	 * Tokens to be loaded into an ArrayList and sent to a recognizer and a
	 * loader.
	 * 
	 * @param reader
	 *            The input stream that was passed in the method call.
	 * @throws IOException
	 *             If the input was invalid according to SDL's grammar it will
	 *             throw an exception.
	 */

	public void tokenize(Reader reader) throws IOException
	{
		boolean saveCharacter = false;
		int ch = reader.read();
		Token token;

		while (ch != -1)
		{
			saveCharacter = false;

			// Catch all whiteSpace and turn it into spaces for easy handling.
			// XXX Now you can switch on all whitespace. Nice!
			if (Character.isWhitespace(ch))
				ch = ' ';

			switch ((char)ch)
			{

			case ' ':
				break;
			case ';':
				token = new Token();
				token.setType("SEMICOLON");
				token.setValue(";"); 
				//XXX Lots of this token.set... going on. You should
				//    instead make a constructor for a Token. See the
				//    Token class for details.
				tokens.add(token);
				break;
			case ':':
				token = new Token();
				token.setType("COLON");
				token.setValue(":");
				tokens.add(token);
				break;
			case '|':
				token = new Token();
				token.setType("BAR");
				token.setValue("|");
				tokens.add(token);
				break;
			case '=':
				token = new Token();
				token.setType("EQUAL");
				token.setValue("=");
				tokens.add(token);
				break;
			case '\'':
				token = new Token();
				token.setType("SLITERAL");
				makeSLit(reader, token);
				tokens.add(token);
				break;
			case '\"':
				token = new Token();
				token.setType("DLITERAL");
				makeDLit(reader, token);
				tokens.add(token);
				break;
			default:
				token = new Token();
				token.setType("NAME");
				ch = makeName(reader, token, ch);
				tokens.add(token);
				// save the character that ends the name. Then parse it again
				// since it can be an operator.
				saveCharacter = true;
				//XXX I like that makeName returns the read character! Keeps you
				//    from using peek(). However, you don't need that
				//    saveCharacter variable if you use continue; here instead of break;.
				break;
			}
			if (!saveCharacter)
				ch = reader.read();
		}
	}

	/**
	 * This method takes the reader and its SLiteral token and continues to read
	 * in the characters until it finishes with a closing '. It will drop both
	 * the opening and closing single quote. It will throw an exception if it
	 * reaches EOI without finishing the literal.
	 * 
	 * @param reader
	 *            the input reader
	 * @param token
	 *            token who's value is being read in.
	 * @throws IOException
	 */

	private void makeSLit(Reader reader, Token token) throws IOException
	{
		int ch;
		String workingString = "";
		while ((ch = reader.read()) != '\'')
		{
			if (ch == -1)
				throw new SDLParseException(
						"SLiteral does not have a closing \'");
			workingString = workingString + (char)ch;
		}
		token.setValue(workingString);
	}

	/**
	 * This method takes the reader and its DLiteral token and continues to read
	 * in the characters until it finishes with a closing \". It will drop both
	 * the opening and closing double quote. It will throw an exception if it
	 * reaches EOI without finishing the literal.
	 * 
	 * @param reader
	 *            the input reader
	 * @param token
	 *            token who's value is being read in.
	 * @throws IOException
	 */

	// XXX This method is awfully similar to makeSLit. Maybe one method called
	// makeLit could have worked?
	private void makeDLit(Reader reader, Token token) throws IOException
	{
		int ch;
		String workingString = "";
		while ((ch = reader.read()) != '\"')
		{
			if (ch == -1)
				throw new SDLParseException(
						"DLiteral does not have a closing \"");
			workingString = workingString + (char)ch;
		}
		token.setValue(workingString);
	}

	/**
	 * This method constructs a name. It takes in a reader and the character
	 * that is it's start character. It will return the character that ends the
	 * name. If it does not finish the name before EOI it will throw an
	 * exception.
	 * 
	 * @param reader
	 *            the input reader
	 * @param token
	 *            the name token who's value is being read.
	 * @param character
	 *            the character that started the name.
	 * @return the character that ended the name.
	 * @throws IOException
	 */

	private int makeName(Reader reader, Token token, int character)
			throws IOException
	{
		int ch = character;

		String workingString = "";

		while (ch != ' ' && ch != ':' && ch != ';' && ch != '=' && ch != '|')
		{

			if (ch == -1)
				throw new SDLParseException(
						"The name was not finished before EOI");
			workingString = workingString + (char)ch;
			ch = reader.read();
			// Handle all possible whitespace so that it will end the name.
			if (Character.isWhitespace(ch))
				ch = ' ';
		}
		token.setValue(workingString);
		return ch;
	}
}
