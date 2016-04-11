package com.putable.siteriter.msmith19;

import java.io.IOException;
import java.io.Reader;
//import java.io.StringReader;
import java.util.ArrayList;

import com.putable.siteriter.SDLParseException;

/**
 * This class will turn the input from a reader into Tokens
 * to be verified by the recognizer and then loaded into a 
 * SymbolTable.
 * @author michaelsmith
 */

public class Tokenizer {

	private ArrayList<Token> tokens = new ArrayList<Token>();


	public ArrayList<Token> getTokens () {
		return tokens;
	}
	
	private int peek(Reader reader) throws IOException {
		reader.mark(1);
		int ch = reader.read();
		reader.reset();
		return ch;
	}
	
	/**
	 * Takes a Reader as input and parses it turning all discrete elements 
	 * into Tokens to be loaded into an ArrayList and sent to a recognizer and a 
	 * loader.
	 * @param reader  The input stream that was passed in the method call.
	 * @throws IOException If the input was invalid according to SDL's grammar it will throw an exception.
	 */

	public void tokenize(Reader reader) throws IOException {
		
		int ch;
		Token token;
		
		while ((ch = peek(reader)) != -1) {	

			switch ((char) ch) {
			
			case ' ':
				reader.read();
				break;
			case '\n':
				reader.read();
				break;
			case '\r':
				reader.read();
				break;
			case ';': token = new Token();
				reader.read();
				token.setType("SEMICOLON");
				token.setValue(";");
				tokens.add(token);
				break;
			case ':': token = new Token();
				reader.read();
				token.setType("COLON");
				token.setValue(":");
				tokens.add(token);
				break;
			case '|': token = new Token();
				reader.read();
				token.setType("BAR");
				token.setValue("|");
				tokens.add(token);
				break;
			case '=': token = new Token();
				reader.read();
				token.setType("EQUAL");
				token.setValue("=");
				tokens.add(token);
				break;
			case '\'': token = new Token();
				reader.read();
				token.setType("SLITERAL");
				makeSLit(reader, token);
				tokens.add(token);
				break;
			case '\"': token = new Token();
				reader.read();
				token.setType("DLITERAL");
				makeDLit(reader, token);
				tokens.add(token);
				break;
			default: token = new Token();
				token.setType("NAME");
				makeName(reader, token);
				tokens.add(token);
				break;
			}
		}
	}
	
	private void makeSLit (Reader reader, Token token) throws IOException{
		int ch;
		String workingString = "";
		while ((ch = reader.read()) != '\''){
			if (ch == -1)
				throw new SDLParseException("SLiteral does not have a closing \'");
			workingString = workingString + (char) ch;
		}
		token.setValue(workingString);
	}
	private void makeDLit (Reader reader, Token token) throws IOException {
		int ch;
		String workingString = "";
		while ((ch = reader.read()) != '\"'){
			if (ch == -1)
				throw new SDLParseException("DLiteral does not have a closing \"");
			workingString = workingString + (char) ch;
		}
		token.setValue(workingString);
	}
	private void makeName (Reader reader, Token token) throws IOException {
		int ch = reader.read();
	    int next = peek(reader);
		String workingString = "";
		
		while(next != ' ' && next != ':' && next != ';' && next != '=') {
			
			if ( ch == -1)
				throw new SDLParseException ("The name was not finished before EOI");
			workingString = workingString + (char) ch;
			ch = reader.read();
			next = peek(reader);
						
		}
		workingString = workingString + (char) ch;
		token.setValue(workingString);
	}

//	public static void main(String[] args) throws IOException {
//		Reader in = new StringReader("Page='haha'|myself;myself = \"hello\";");
//		
//		Tokenizer tok = new Tokenizer();
//		
//		double start = System.nanoTime();
//		tok.tokenize(in);
//		double elapsedTime = System.nanoTime() - start;
//		System.out.println(elapsedTime);
//		
//		for (int i = 0; i < tok.tokens.size(); i++) {
//			System.out.println("its type is: " + tok.tokens.get(i).getType() +
//					" its value is: " + tok.tokens.get(i).getValue());
//		}
//	}
}
