package com.putable.siteriter.msmith19;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import com.putable.siteriter.SDLParseException;

/**
 * This class takes a string key, the SymbolTable of a loaded grammar and a map
 * of the selectors. It will expand the start symbol creating the output string
 * as it encounters terminal tokens (literals).
 * 
 * @author michaelsmith
 * 
 */

public class Expander {

	private HashMap<String, Symbol> SymbolTable = new HashMap<String, Symbol>();
	private Map<String, Integer> selectors = new HashMap<String, Integer>();
	private String page = "";
	private Random rand = new Random();
	private String startName = "";

	/**
	 * Creates an expander
	 * 
	 * @param key
	 *            the key provided by the user.
	 * @param SymbolTable
	 *            the loaded symbols
	 * @param selectors
	 *            the selectors map for this loaded grammar.
	 * @param startName
	 *            the rule name to be expanded.
	 */

	public Expander(String key, HashMap<String, Symbol> SymbolTable,
			Map<String, Integer> selectors, String startName) {
		this.SymbolTable = SymbolTable;
		this.selectors = selectors;
		// Set the random seed from the key once.
		rand.setSeed(key.hashCode());
		// See if you will start with the first rule or a secondary start.
		String sStart = "";
		if (key.startsWith("/ss/")) {
			sStart = SecondaryStart(key);
		} else if (key.equals("/robots.txt"))
			this.startName = "ROBOTS_TXT";
		if (SymbolTable.containsKey(sStart)
				&& SymbolTable.get(sStart).isStart())
			this.startName = sStart;
	}

	/**
	 * Takes the key and returns the string with the secondary starts name.
	 * 
	 * @param key
	 * @return
	 */
	private String SecondaryStart(String key) {
		String newStart = "";

		if (key.charAt(4) != '/') {
			for (int i = 4; i < key.length(); i++) {
				if (key.charAt(i) == '/') {
					break;
				} else if (i == key.length() - 1) {
					newStart = "";
					break;
				}
				newStart = newStart + key.charAt(i);
			}
		}

		return newStart;
	}

	/**
	 * If a rule has a selector it will call this expander method.
	 * 
	 * @param selName
	 *            name of the selector.
	 * @param ruleName
	 *            the key for the SymbolTable.
	 * @param choices
	 * @return the String of the expanded page.
	 */

	private String SelectedExpand(String selName, String ruleName,
			ArrayList<ArrayList<Token>> choices) {
		choices = SymbolTable.get(ruleName).choices;
		String sPage = "";
		// Creates an entry in the selectors map if one does not already exist.
		if (!selectors.containsKey(selName))
			selectors.put(selName, rand.nextInt(Integer.MAX_VALUE));

		int chooseSelectedSeq = selectors.get(selName) % choices.size();
		ArrayList<Token> sequence = choices.get(chooseSelectedSeq);

		for (int i = 0; i < sequence.size(); i++) {
			if (sequence.get(i).getType() == "SLITERAL"
					|| sequence.get(i).getType() == "DLITERAL")
				sPage = sPage + sequence.get(i).getValue();
			else if (sequence.get(i).getType() == "NAME")
				sPage = sPage + Expand(sequence.get(i).getValue());

		}
		return sPage;
	}

	/**
	 * This will expand the given start symbol according to the currently loaded
	 * SymbolTable.
	 * 
	 * @param startName
	 *            the key for the rule to be expanded
	 * @return A string which consists of the final literals that are chosen
	 *         during expansion
	 * @throws SDLParseException
	 */

	public String Expand(String startName) {
		if (this.startName != startName && this.startName != "")
			startName = this.startName;
		// If /robots.txt was the key but the rule does not exist return null.
		if (startName.equals("ROBOTS_TXT")
				&& !SymbolTable.containsKey("ROBOTS_TXT"))
			return null;

		if (!SymbolTable.containsKey(startName))
			return page = page + startName + "?";

		ArrayList<ArrayList<Token>> choices = new ArrayList<ArrayList<Token>>();

		// If it has a selector this will be the expansion instead.
		if (SymbolTable.get(startName).getSelector() != null) {
			page = page
					+ SelectedExpand(SymbolTable.get(startName).getSelector(),
							startName, choices);
			return page;
		}

		choices = SymbolTable.get(startName).choices;

		int chooseSeq = rand.nextInt(Integer.MAX_VALUE) % choices.size();
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
