package com.putable.siteriter.test;

import java.io.IOException;

import junit.framework.Assert;

import org.junit.Test;

import com.putable.siteriter.tsmall1.Symbol;
import com.putable.siteriter.tsmall1.Token;
import com.putable.siteriter.tsmall1.Tokenizer;

/**
 * Tests the Symbol class for correct parsing.
 * 
 * @author Trent Small
 * 
 */
public class SymbolTest
{
	private void symbolStrTest(String ruleStr, Symbol s, boolean start)
	{
		Token[] rule = null;
		try
		{
			rule = Tokenizer.tokenize(ruleStr);
		} catch (IOException e)
		{
			Assert.fail("Tokenizer failed! Fix your code!!");
		}
		Assert.assertEquals(s, new Symbol(rule, start));
	}

	/**
	 * Tests a simple symbol for parsing correctness.
	 */
	@Test
	public void testSimpleSymbol()
	{
		Symbol finalRule = new Symbol(Symbol.StartType.START,
				new Token[][] { Token.Type.NAME.single("bar") }, "foo", null);
		symbolStrTest("foo = bar;", finalRule, true);
	}

	/**
	 * Tests a regular sized string for parsing into a Symbol.
	 */
	@Test
	public void testRegularSymbol()
	{
		Symbol finalRule = new Symbol(Symbol.StartType.SECONDARY_START,
				new Token[][] {
						new Token[] { new Token(Token.Type.NAME, "top"),
								new Token(Token.Type.NAME, "plpage"),
								new Token(Token.Type.NAME, "bot") },
						new Token[] { new Token(Token.Type.DLITERAL,
								"\"nothing\"") },
						new Token[] { new Token(Token.Type.NAME, "something"),
								new Token(Token.Type.SLITERAL, "'plpl'") } },
				"pl", "G");
		symbolStrTest(
				":pl:G = top plpage bot | \"nothing\" | something 'plpl';",
				finalRule, false);
	}

	/**
	 * Tests a larger sized string for parsing into a Symbol.
	 */
	@Test
	public void testLargerSymbol()
	{
		Symbol finalRule = new Symbol(Symbol.StartType.NONE, new Token[][] {
				Token.Type.NAME.single("lenny"),
				new Token[] { new Token(Token.Type.NAME, "Steven"),
						new Token(Token.Type.SLITERAL, "'hawking'") },
				Token.Type.NAME.single("armadillo"),
				new Token[0],
				new Token[0],
				new Token[] { new Token(Token.Type.NAME, "tiger"),
						new Token(Token.Type.DLITERAL, "\"lilly\"") } }, "ng",
				"garland");
		symbolStrTest(
				"ng:garland = lenny | Steven 'hawking' | armadillo | | | tiger\"lilly\";",
				finalRule, false);
	}

	/**
	 * Tests a large sized string for parsing into a Symbol.
	 */
	@Test
	public void testLargeSymbol()
	{
		Symbol finalRule = new Symbol(Symbol.StartType.START, new Token[][] {
				new Token[] { new Token(Token.Type.NAME, "bean"),
						new Token(Token.Type.SLITERAL, "'iced'"),
						new Token(Token.Type.NAME, "tea"),
						new Token(Token.Type.DLITERAL, "\"sucker\""), },
				Token.Type.NAME.single("vanilla"),
				new Token[0],
				new Token[0],
				new Token[] { new Token(Token.Type.NAME, "orange"),
						new Token(Token.Type.NAME, "JUICE"),
						new Token(Token.Type.SLITERAL, "'honk honk'"), },
				Token.Type.DLITERAL.single("\"billows\"") }, "n", "r");
		symbolStrTest(
				":n:r=bean'iced'tea\"sucker\"|vanilla|||orange JUICE 'honk honk'|\"billows\";",
				finalRule, true);
	}
}