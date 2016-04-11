package com.putable.siteriter.msmith19;

import java.util.ArrayList;

import com.putable.siteriter.SDLParseException;

/**
 * This class takes an ArrayList<Token> and parses it recursively 
 * to determine if the tokens represent a valid SDL rules file. 
 * @author michaelsmith
 *
 */

public class SDLRecognizer {

	private ArrayList<Token> tokens = new ArrayList<Token>();
	
	public SDLRecognizer (ArrayList<Token> tokens) {
		this.tokens = tokens;
	}
	
	/**
	 * This will attempt to parse the input. If the given input 
	 * does not meet the requirements of the SDL language specification
	 * then it will throw an SDLParseException.
	 * 
	 * @throws SDLParseException  The exception that can be thrown, It will 
	 * include a message that states why it was thrown.
	 */
	
	public void parseTokens () throws SDLParseException {
		ArrayList<Token> rule = new ArrayList<Token>();
		boolean hasRule = false;
		
		if (tokens.size() == 0)
			throw new SDLParseException("The input was empty");
		
		for (int i = 0; i < tokens.size(); i++) {
			
			
			if (tokens.get(i).getType() == "SEMICOLON") {
				hasRule = true;
				parseRules(rule);
				rule = new ArrayList<Token>();
			}
			if (tokens.get(i).getType() != "SEMICOLON")
				rule.add(tokens.get(i));
			
		}
		
		if (tokens.get(tokens.size()-1).getType() != "SEMICOLON")
			throw new SDLParseException("There is an unclosed rule");
		
		if (!hasRule){
			throw new SDLParseException("There is not a complete rule in this file");
		}
	}
	
	private void parseRules (ArrayList<Token> rule) throws SDLParseException {
		ArrayList<Token> head = new ArrayList<Token>();
		ArrayList<Token> body = new ArrayList<Token>();
		boolean isHead = true;
		
		for (int i = 0; i < rule.size(); i++) {
			
			if (rule.get(i).getType() == "EQUAL") {
				isHead = false;
				if (head.size() >= 1)
					parseHead(head);
				else
					throw new SDLParseException("This rule has not head");
			}
			
			if (isHead)
				head.add(rule.get(i));
			else if (rule.get(i).getType() != "EQUAL")
				body.add(rule.get(i));
			
			if (i == rule.size()-1) {
				parseBody(body);
			}
				
		}
		if (isHead)
			throw new SDLParseException("There is no head to a rule");
		
	}
	
	private void parseHead (ArrayList<Token> head) throws SDLParseException {
		
		for (int i = 0; i < head.size(); i++) {
			if (head.get(i).getType() != "COLON" && head.get(i).getType() != "NAME")
				throw new SDLParseException("Illegal token type in rule head");
			else if (head.get(i).getType() == "NAME") {
				isValidName (head.get(i));
			}
				
		}
		
	}
	
	private void parseBody (ArrayList<Token> body) throws SDLParseException {
		
		for (int i = 0; i < body.size(); i++) {
			if (body.get(i).getType() != "SLITERAL" && body.get(i).getType() != "DLITERAL"
					&& body.get(i).getType() != "BAR" && body.get(i).getType() != "NAME")
				throw new SDLParseException("Illegal token type in rule body");
			else if (body.get(i).getType() == "NAME")
				isValidName(body.get(i));
		}
	}
	
	private void isValidName (Token name) throws SDLParseException {
		char[] characters = name.getValue().toCharArray();
		
		if (!Character.isJavaIdentifierStart(characters[0]))
			throw new SDLParseException("Name begins with an invalid java identifier");
		
		for (int i = 1; i < characters.length; i++) {
			
			if (!Character.isJavaIdentifierPart(characters[i]))
				throw new SDLParseException("Name includes invalid java identifiers");
		}
		
	
	}
	
}
