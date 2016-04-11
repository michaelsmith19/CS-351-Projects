package com.putable.siteriter.msmith19;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * This class takes a recognized (valid) ArrayList<Token> and loads the 
 * tokens into symbols representing the rules. These symbols are loaded into 
 * the SymbolTable and returned.
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
	 * turns them into Symbols. It loads the symbols into a SymbolTable
	 * then returns it.
	 * @return a hashMap<String,Symbol> which contains all the symbols
	 */
	
	public HashMap<String, Symbol> LoadSymbols() {
		
		for (int i = 0; i < tokens.size(); ) {
			Symbol rule = new Symbol();
			
			
			if (tokens.get(i).getType() == "COLON") {
				rule.setStart(true);
				i++;
			}

			if (tokens.get(i).getType() == "NAME") {
					if (i == 0 || i == 1)
						firstName = tokens.get(i).getValue();
				rule.setName(tokens.get(i).getValue());
				i++;
			}
			
			if (tokens.get(i).getType() == "COLON") {
				i++;
			}
			
			if (tokens.get(i).getType() == "NAME") {
				rule.setSelector(tokens.get(i).getValue());
				i++;
			}
			
			if (tokens.get(i).getType() == "EQUAL") {
				i++;
				i = loadChoices(i, rule);
				i++;
			}
			
			SymbolTable.put(rule.getName(), rule);
		}

		return SymbolTable;
	}

	public int loadChoices(int i, Symbol rule) {
		ArrayList<ArrayList<Token>> choices = new ArrayList<ArrayList<Token>>();

		ArrayList<Token> sequence = new ArrayList<Token>();

		while (tokens.get(i).getType() != "SEMICOLON") {

			if (tokens.get(i).getType() == "NAME"
					|| tokens.get(i).getType() == "SLITERAL"
					|| tokens.get(i).getType() == "DLITERAL") {
				sequence.add(tokens.get(i));
				i++;

			} else if (tokens.get(i).getType() == "BAR") {
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
