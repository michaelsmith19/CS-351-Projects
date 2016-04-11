package com.putable.siteriter.tsmall1;

import java.io.IOException;
import java.io.Reader;
import java.util.Map;

import com.putable.siteriter.SDLParser;

/**
 * An implementation of an SDLParser. This is a full implementation of the
 * SDLParser described in the spec.
 * 
 * @author Trent Small
 * 
 */
public class SDLParserImpl implements SDLParser
{
	/**
	 * A {@link SymbolTable} which is written to in {@link #load(Reader)} and is
	 * read from in {@link #makePage(String, Map)}. Must be in each instance of
	 * this class as per ((C.2.3.5))
	 */
	private SymbolTable	symbols	= new SymbolTable();

	/**
	 * A flag which represents the state of the {@link #load(Reader)} method.
	 * This is only true once {@link #load(Reader)} has finished successfully.
	 */
	private boolean		loaded	= false;

	@Override
	public void load(Reader reader) throws IOException
	{
		loaded = false;
		Token[] tokens = Tokenizer.tokenize(reader);
		if (Verifier.verify(tokens))
		{
			Parser.refillTable(symbols, tokens);
			loaded = true;
		}
	}

	@Override
	public String makePage(String key, Map<String, Integer> selectors)
	{
		if (!loaded)
		{
			throw new IllegalStateException(
					"makePage cannot be called while SDLParser is loading!");
		}
		return SSPIG.makePage(key, selectors, symbols);
	}

}
