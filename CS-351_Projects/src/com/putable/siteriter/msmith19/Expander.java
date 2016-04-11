package com.putable.siteriter.msmith19;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import com.putable.siteriter.SDLParseException;

/**
 * This class takes a string key, the SymbolTable of a loaded grammar
 * and a map of the selectors.  It will expand the start symbol creating 
 * the output string as it encounters terminal tokens (literals).
 * @author michaelsmith
 *
 */

public class Expander {

	private HashMap<String, Symbol> SymbolTable = new HashMap<String, Symbol>();
	private Map<String, Integer> selectors = new HashMap<String, Integer>();
	private String key = "";
	private String page = "";
	private Random rand = new Random();

	
	public Expander(String key, HashMap<String, Symbol> SymbolTable,
			Map<String, Integer> selectors) {
		this.key = key;
		this.SymbolTable = SymbolTable;
		this.selectors = selectors;
	}

	private void seedRand() {
		if (key.startsWith("http:/"))
			rand.setSeed(key.substring(6).hashCode());
	}
	
	private String SecondaryStart() throws SDLParseException {
		String newStart = "";
		
			if (key.charAt(4) != '/'){
				for(int i = 4; i < key.length(); i ++) {
					if (key.charAt(i) == '/')
						break;
					newStart = newStart + key.charAt(i);
				}
			}
			if (newStart.length() == 0)
				throw new SDLParseException("There is no name for the secodary start that was " +
						"started /ss/.");
		return newStart;	
	}

	private String SelectedExpand(String selName,String ruleName, ArrayList<ArrayList<Token>> choices) {
		choices = SymbolTable.get(ruleName).choices;
		String sPage = "";
		//Creates an entry in the selectors map if one does not already exist.
		if (!selectors.containsKey(selName))
			selectors.put(selName, rand.nextInt(Integer.MAX_VALUE));

		int selectedNumber = selectors.get(selName);
		int chooseSelectedSeq = selectedNumber % choices.size();
		ArrayList<Token> sequence = choices.get(chooseSelectedSeq);

		for (int i = 0; i < sequence.size(); i++) {
			if (sequence.get(i).getType() == "SLITERAL"
					|| sequence.get(i).getType() == "DLITERAL")
				sPage = sPage + sequence.get(i).getValue();
			else if (sequence.get(i).getType() == "NAME")
				Expand(sequence.get(i).getValue());

		}
		return sPage;
	}

	/**
	 * This will expand the given start symbol according to the currently loaded
	 * SymbolTable. 
	 * @param startName the key for the rule to be expanded
	 * @return A string which consists of the final literals that are chosen during expansion
	 * @throws SDLParseException
	 */
	
	public String Expand(String startName) {
		if (!SymbolTable.containsKey(startName))
			return page = page + startName + "?";

		if (key.startsWith("/ss/")) {
			try {
			startName = SecondaryStart();
			} catch (Exception e) {e.getMessage();}
		}
			
		seedRand();
		ArrayList<ArrayList<Token>> choices = new ArrayList<ArrayList<Token>>();
		
		// If it has a selector this will be the expansion instead.
		String selName = "";
		if ((selName = SymbolTable.get(startName).getSelector()) != null){
			page = page + SelectedExpand(selName,startName, choices);
			return page;
		}
		
		choices = SymbolTable.get(startName).choices;
		int numPossible = choices.size();

		int chooseSeq = rand.nextInt(Integer.MAX_VALUE) % numPossible;
		ArrayList<Token> sequence = choices.get(chooseSeq);

		for (int i = 0; i < sequence.size(); i++) {
			if (sequence.get(i).getType() == "SLITERAL"
					|| sequence.get(i).getType() == "DLITERAL")
				page = page + sequence.get(i).getValue();
			else if (sequence.get(i).getType() == "NAME")
				Expand(sequence.get(i).getValue());
		}
		
		return page;
	}
}
