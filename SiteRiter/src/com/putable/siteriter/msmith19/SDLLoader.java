package com.putable.siteriter.msmith19;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * This class takes a recognized (valid) ArrayList<Token> and loads the tokens
 * into symbols representing the rules. These symbols are loaded into the
 * SymbolTable and returned.
 * 
 * @author michaelsmith
 * 
 */

public class SDLLoader {

	private ArrayList<Token> tokens = new ArrayList<Token>();
	private HashMap<String, Symbol> SymbolTable = new HashMap<String, Symbol>();
	public String firstName = "";

	public SDLLoader(ArrayList<Token> newTokens) {
		tokens = newTokens;
	}

	/**
	 * This method uses the ArrayList of tokens given to the constructor and
	 * turns them into Symbols. It loads the symbols into a SymbolTable then
	 * returns it.
	 * 
	 * @return a hashMap<String,Symbol> which contains all the symbols
	 */

	public HashMap<String, Symbol> LoadSymbols() {

		for (int i = 0; i < tokens.size();) {
			Symbol rule = new Symbol();

			// Within this loop we parse the head of the new rule.

			if (tokens.get(i).getType().equals("COLON")) {
				rule.setStart(true);
				i++;
			}

			if (tokens.get(i).getType().equals("NAME")) {
				if (i == 0 || i == 1)
					firstName = tokens.get(i).getValue();
				// If the ROBOTS_TXT is a rule set it as a secondary start.
				if (tokens.get(i).getValue().equals("ROBOTS_TXT"))
					rule.setStart(true);
				rule.setName(tokens.get(i).getValue());
				i++;
			}

			if (tokens.get(i).getType().equals("COLON")) {
				i++;
			}

			if (tokens.get(i).getType().equals("NAME")) {
				rule.setSelector(tokens.get(i).getValue());
				i++;
			}

			// Once the head of the rule is finished we can call
			// loadChoices(int i, Symbol rule) and that will load the
			// body into the given Symbol.
			if (tokens.get(i).getType().equals("EQUAL")) {
				i++;
				i = loadChoices(i, rule);
				i++;
			}

			SymbolTable.put(rule.getName(), rule);
		}

		return SymbolTable;
	}

	/**
	 * This takes the index of the current token and the Symbol that is being
	 * created. It reads in all the tokens that make up the body. It separates
	 * them based on the bar operators into separate choices. The choices are
	 * then loaded into the Symbol. It then returns the index after the end of
	 * that rule.
	 * 
	 * @param i
	 * @param rule
	 * @return
	 */
	public int loadChoices(int i, Symbol rule) {
		ArrayList<ArrayList<Token>> choices = new ArrayList<ArrayList<Token>>();

		ArrayList<Token> sequence = new ArrayList<Token>();

		while (!tokens.get(i).getType().equals("SEMICOLON")) {

			if (tokens.get(i).getType().equals("NAME")
					|| tokens.get(i).getType().equals("SLITERAL")
					|| tokens.get(i).getType().equals("DLITERAL")) {
				sequence.add(tokens.get(i));
				i++;

			} else if (tokens.get(i).getType().equals("BAR")) {
				choices.add(sequence);
				sequence = new ArrayList<Token>();
				i++;
			}

		}

		choices.add(sequence);
		rule.setChoices(choices);
		return i;
	}

	public String getFirstName() {
		return firstName;
	}

}
