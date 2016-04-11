package com.putable.siteriter.msmith19;

import java.io.IOException;
import java.io.Reader;
import java.util.HashMap;
import java.util.Map;

import com.putable.siteriter.SDLParser;

/**
 * This is a feature complete implementation of the SDLParser interface.
 * @author michaelsmith
 *
 */

public class SDLParserImpl implements SDLParser {

	private HashMap<String,Symbol> SymbolTable = new HashMap<String, Symbol>();
	private String firstName;
	Map<String,Integer> selectors = new HashMap<String,Integer>();
	
	@Override
	public void load(Reader reader) throws IOException {
		Tokenizer tok = new Tokenizer();
		tok.tokenize(reader);
		SDLRecognizer recognizer = new SDLRecognizer(tok.getTokens());
		recognizer.parseTokens();
		SDLLoader loader = new SDLLoader(tok.getTokens());
		SymbolTable = loader.LoadSymbols();
		firstName = loader.getFirstName();
	}

	@Override
	public String makePage(String key, Map<String, Integer> selectors) {
		if( key == null || selectors == null)
			throw new NullPointerException();
		if (SymbolTable.isEmpty() == true) 
			throw new IllegalStateException();
		
		String outPut = "";
		Expander creator = new Expander(key,SymbolTable,selectors,firstName);
		outPut = creator.Expand(firstName);
		return outPut;
	}

	public void clearSymbolTable() {
		SymbolTable.clear();
	}
}
