package com.putable.siteriter.test;

import static org.junit.Assert.fail;

import java.io.IOException;

import org.junit.Test;

import com.putable.siteriter.tsmall1.Token;
import com.putable.siteriter.tsmall1.Tokenizer;

/**
 * Tests the Token and Tokenizer class to make sure that everything is sane.
 * 
 * @author Trent Small
 * 
 */
public class TokenTest
{
	/**
	 * An in-depth array asserter, which reports the first elements that are not
	 * equal.
	 * 
	 * @param The
	 *            Tokens to test equality from
	 * @param The
	 *            Tokens to test equality to
	 */
	private void arrayAssertReport(Token[] expected, Token[] test)
	{
		for (int i = 0; i < expected.length; i++)
		{
			if (expected.length <= i)
			{
				fail("expected array is shorter than test array.");
			}
			else if (test.length <= i)
			{
				fail("test array is shorter than expected array.");
			}
			if (!expected[i].equals(test[i]))
			{
				fail("Array index i unequal: " + expected[i].toString()
						+ " != " + test[i].toString());
			}
		}
	}

	private void tryTokenizeTest(Token[] expected, String input)
	{
		try
		{
			arrayAssertReport(expected, Tokenizer.tokenize(input));
		} catch (IOException e)
		{
			fail("Tokenize threw exception: " + e.getMessage());
		}
	}

	/**
	 * Tests Tokenizer to tokenize a single NAME.
	 */
	@Test
	public void testTokenizeName()
	{
		Token[] afterTokens = new Token[] { new Token(Token.Type.NAME, "foo"),
				new Token(Token.Type.EOI, null) };
		String testInput = "foo";
		tryTokenizeTest(afterTokens, testInput);
	}

	/**
	 * Tests Tokenizer to tokenize a single BAR.
	 */
	@Test
	public void testTokenizeBar()
	{
		Token[] afterTokens = new Token[] { new Token(Token.Type.BAR, "|"),
				new Token(Token.Type.EOI, null) };
		String testInput = "|";
		tryTokenizeTest(afterTokens, testInput);
	}

	/**
	 * Tests Tokenizer to tokenize a single SEMICOLON.
	 */
	@Test
	public void testTokenizeSemicolon()
	{
		Token[] afterTokens = new Token[] {
				new Token(Token.Type.SEMICOLON, ";"),
				new Token(Token.Type.EOI, null) };
		String testInput = ";";
		tryTokenizeTest(afterTokens, testInput);
	}

	/**
	 * Tests Tokenizer to tokenize a single SLITERAL.
	 */
	@Test
	public void testTokenizeSLiteral()
	{
		Token[] afterTokens = new Token[] {
				new Token(Token.Type.SLITERAL, "'Holy moley a test SLITERAL'"),
				new Token(Token.Type.EOI, null) };
		String testInput = "'Holy moley a test SLITERAL'";
		tryTokenizeTest(afterTokens, testInput);
	}

	/**
	 * Tests Tokenizer to tokenize a single DLITERAL.
	 */
	@Test
	public void testTokenizeDLiteral()
	{
		Token[] afterTokens = new Token[] {
				new Token(Token.Type.DLITERAL,
						"\"A DLITERAL? Can life get better?\""),
				new Token(Token.Type.EOI, null) };
		String testInput = "\"A DLITERAL? Can life get better?\"";
		tryTokenizeTest(afterTokens, testInput);
	}

	/**
	 * Tests Tokenizer to tokenize a non-terminating DLITERAL from the left
	 * side.
	 */
	@Test
	public void testTokenizeBrokenLeftDLiteral()
	{
		try
		{
			Tokenizer.tokenize("\"This DLITERAL is bwoken.");
			fail("Incorrectly parsed a broken DLITERAL.");
		} catch (IOException e)
		{}
	}

	/**
	 * Tests Tokenizer to tokenize a non-terminating DLITERAL from the right
	 * side.
	 */
	@Test
	public void testTokenizeBrokenRightDLiteral()
	{
		try
		{
			Tokenizer.tokenize("This DLITERAL is bwoken.\"");
			fail("Incorrectly parsed a broken DLITERAL.");
		} catch (IOException e)
		{}
	}

	/**
	 * Tests Tokenizer to tokenize a non-terminating SLITERAL from the left
	 * side.
	 */
	@Test
	public void testTokenizeBrokenRightSLiteral()
	{
		try
		{
			Tokenizer.tokenize("This SLITERAL is bwoken.'");
			fail("Incorrectly parsed a broken SLITERAL.");
		} catch (IOException e)
		{}
	}

	/**
	 * Tests Tokenizer to tokenize a non-terminating SLITERAL from the left
	 * side.
	 */
	@Test
	public void testTokenizeBrokenLeftSLiteral()
	{
		try
		{
			Tokenizer.tokenize("'This SLITERAL is bwoken.");
			fail("Incorrectly parsed a broken SLITERAL.");
		} catch (IOException e)
		{}
	}

	/**
	 * Tests Tokenizer to tokenize an EQUAL token.
	 */
	@Test
	public void testTokenizeEqual()
	{
		Token[] afterTokens = new Token[] { new Token(Token.Type.EQUAL, "="),
				new Token(Token.Type.EOI, null) };
		String testInput = "=";
		tryTokenizeTest(afterTokens, testInput);
	}

	/**
	 * Tests Tokenizer to tokenize an empty sequence.
	 */
	@Test
	public void testTokenizeEmpty()
	{
		Token[] afterTokens = new Token[] { new Token(Token.Type.EOI, null) };
		String testInput = "";
		tryTokenizeTest(afterTokens, testInput);
	}

	/**
	 * Tests a small but complicated token string.
	 */
	@Test
	public void testTokenizeSmallTest()
	{
		String testInput = "foo:a:b:c = bar; bar = 'a' | foo | baz; :baz:a:b = |\"b\"||| jimmy;";
		Token[] afterTokens = new Token[] { new Token(Token.Type.NAME, "foo"),
				new Token(Token.Type.COLON, ":"),
				new Token(Token.Type.NAME, "a"),
				new Token(Token.Type.COLON, ":"),
				new Token(Token.Type.NAME, "b"),
				new Token(Token.Type.COLON, ":"),
				new Token(Token.Type.NAME, "c"),
				new Token(Token.Type.EQUAL, "="),
				new Token(Token.Type.NAME, "bar"),
				new Token(Token.Type.SEMICOLON, ";"),
				new Token(Token.Type.NAME, "bar"),
				new Token(Token.Type.EQUAL, "="),
				new Token(Token.Type.SLITERAL, "'a'"),
				new Token(Token.Type.BAR, "|"),
				new Token(Token.Type.NAME, "foo"),
				new Token(Token.Type.BAR, "|"),
				new Token(Token.Type.NAME, "baz"),
				new Token(Token.Type.SEMICOLON, ";"),
				new Token(Token.Type.COLON, ":"),
				new Token(Token.Type.NAME, "baz"),
				new Token(Token.Type.COLON, ":"),
				new Token(Token.Type.NAME, "a"),
				new Token(Token.Type.COLON, ":"),
				new Token(Token.Type.NAME, "b"),
				new Token(Token.Type.EQUAL, "="),
				new Token(Token.Type.BAR, "|"),
				new Token(Token.Type.DLITERAL, "\"b\""),
				new Token(Token.Type.BAR, "|"), new Token(Token.Type.BAR, "|"),
				new Token(Token.Type.BAR, "|"),
				new Token(Token.Type.NAME, "jimmy"),
				new Token(Token.Type.SEMICOLON, ";"),
				new Token(Token.Type.EOI, null) };
		tryTokenizeTest(afterTokens, testInput);
	}

	/**
	 * Tests the tokenizer in tokenizing a large test.
	 */
	@Test
	public void testTokenizeLargeTest()
	{
		String testInput = "page = en;"
				+ "hostport = \"localhost:8000\";"
				+ ":en = top enpage bot;"
				+ ":pl = top plpage bot;"
				+ "top = '<html><head><base href=\"http://' hostport '\"/></head><body>"
				+ "Languages: <a href=\"./ss/en/\">English</a> <a href=\"./ss/pl/\">Pig Latin</a><hr>"
				+ "';" + "enpage = ensubj enobj '.';"
				+ "plpage = plsubj plobj '!';" +

				"d = \"0\"|\"1\"|\"2\"|\"3\"|\"4\"|\"5\"|\"6\"|\"7\"|\"8\"|\"9\";"
				+ "ds = d d d d d d d d;" +

				"enslink = '<a href=\"ss/en/' ds '\">';" + "enelink = elink;" +

				"plslink = '<a href=\"ss/pl/' ds '\">';" + "plelink = elink;" +

				"elink = \"</a>\";" +

				"ensubj = \"Foo\" | \"Bar\" | \"Baz\" | \"Mumble\" ;"
				+ "plsubj = \"Oofay\" | \"Arbay\" | \"Azbay\" | \"Umblemay\";" +

				"enobj = \" is \" enslink enadj enelink;"
				+ "plobj = \" isay \" plslink pladj plelink;" +

				"enadj = \"gah\"|\"hop\"|\"zot\"|\"borked\";"
				+ "pladj = \"ahgay\"|\"ophay\"|\"otzay\"|\"orkedbay\";" +

				"bot = \"</body></html>" + "\";";
		Token[] afterTokens = new Token[] {
				new Token(Token.Type.NAME, "page"),
				new Token(Token.Type.EQUAL, "="),
				new Token(Token.Type.NAME, "en"),
				new Token(Token.Type.SEMICOLON, ";"),
				new Token(Token.Type.NAME, "hostport"),
				new Token(Token.Type.EQUAL, "="),
				new Token(Token.Type.DLITERAL, "\"localhost:8000\""),
				new Token(Token.Type.SEMICOLON, ";"),
				new Token(Token.Type.COLON, ":"),
				new Token(Token.Type.NAME, "en"),
				new Token(Token.Type.EQUAL, "="),
				new Token(Token.Type.NAME, "top"),
				new Token(Token.Type.NAME, "enpage"),
				new Token(Token.Type.NAME, "bot"),
				new Token(Token.Type.SEMICOLON, ";"),
				new Token(Token.Type.COLON, ":"),
				new Token(Token.Type.NAME, "pl"),
				new Token(Token.Type.EQUAL, "="),
				new Token(Token.Type.NAME, "top"),
				new Token(Token.Type.NAME, "plpage"),
				new Token(Token.Type.NAME, "bot"),
				new Token(Token.Type.SEMICOLON, ";"),
				new Token(Token.Type.NAME, "top"),
				new Token(Token.Type.EQUAL, "="),
				new Token(Token.Type.SLITERAL,
						"'<html><head><base href=\"http://'"),
				new Token(Token.Type.NAME, "hostport"),
				new Token(
						Token.Type.SLITERAL,
						"'\"/></head><body>"
								+ "Languages: <a href=\"./ss/en/\">English</a> <a href=\"./ss/pl/\">Pig Latin</a><hr>"
								+ "'"), new Token(Token.Type.SEMICOLON, ";"),
				new Token(Token.Type.NAME, "enpage"),
				new Token(Token.Type.EQUAL, "="),
				new Token(Token.Type.NAME, "ensubj"),
				new Token(Token.Type.NAME, "enobj"),
				new Token(Token.Type.SLITERAL, "'.'"),
				new Token(Token.Type.SEMICOLON, ";"),
				new Token(Token.Type.NAME, "plpage"),
				new Token(Token.Type.EQUAL, "="),
				new Token(Token.Type.NAME, "plsubj"),
				new Token(Token.Type.NAME, "plobj"),
				new Token(Token.Type.SLITERAL, "'!'"),
				new Token(Token.Type.SEMICOLON, ";"),
				new Token(Token.Type.NAME, "d"),
				new Token(Token.Type.EQUAL, "="),
				new Token(Token.Type.DLITERAL, "\"0\""),
				new Token(Token.Type.BAR, "|"),
				new Token(Token.Type.DLITERAL, "\"1\""),
				new Token(Token.Type.BAR, "|"),
				new Token(Token.Type.DLITERAL, "\"2\""),
				new Token(Token.Type.BAR, "|"),
				new Token(Token.Type.DLITERAL, "\"3\""),
				new Token(Token.Type.BAR, "|"),
				new Token(Token.Type.DLITERAL, "\"4\""),
				new Token(Token.Type.BAR, "|"),
				new Token(Token.Type.DLITERAL, "\"5\""),
				new Token(Token.Type.BAR, "|"),
				new Token(Token.Type.DLITERAL, "\"6\""),
				new Token(Token.Type.BAR, "|"),
				new Token(Token.Type.DLITERAL, "\"7\""),
				new Token(Token.Type.BAR, "|"),
				new Token(Token.Type.DLITERAL, "\"8\""),
				new Token(Token.Type.BAR, "|"),
				new Token(Token.Type.DLITERAL, "\"9\""),
				new Token(Token.Type.SEMICOLON, ";"),
				new Token(Token.Type.NAME, "ds"),
				new Token(Token.Type.EQUAL, "="),
				new Token(Token.Type.NAME, "d"),
				new Token(Token.Type.NAME, "d"),
				new Token(Token.Type.NAME, "d"),
				new Token(Token.Type.NAME, "d"),
				new Token(Token.Type.NAME, "d"),
				new Token(Token.Type.NAME, "d"),
				new Token(Token.Type.NAME, "d"),
				new Token(Token.Type.NAME, "d"),
				new Token(Token.Type.SEMICOLON, ";"),
				new Token(Token.Type.NAME, "enslink"),
				new Token(Token.Type.EQUAL, "="),
				new Token(Token.Type.SLITERAL, "'<a href=\"ss/en/'"),
				new Token(Token.Type.NAME, "ds"),
				new Token(Token.Type.SLITERAL, "'\">'"),
				new Token(Token.Type.SEMICOLON, ";"),
				new Token(Token.Type.NAME, "enelink"),
				new Token(Token.Type.EQUAL, "="),
				new Token(Token.Type.NAME, "elink"),
				new Token(Token.Type.SEMICOLON, ";"),
				new Token(Token.Type.NAME, "plslink"),
				new Token(Token.Type.EQUAL, "="),
				new Token(Token.Type.SLITERAL, "'<a href=\"ss/pl/'"),
				new Token(Token.Type.NAME, "ds"),
				new Token(Token.Type.SLITERAL, "'\">'"),
				new Token(Token.Type.SEMICOLON, ";"),
				new Token(Token.Type.NAME, "plelink"),
				new Token(Token.Type.EQUAL, "="),
				new Token(Token.Type.NAME, "elink"),
				new Token(Token.Type.SEMICOLON, ";"),
				new Token(Token.Type.NAME, "elink"),
				new Token(Token.Type.EQUAL, "="),
				new Token(Token.Type.DLITERAL, "\"</a>\""),
				new Token(Token.Type.SEMICOLON, ";"),
				new Token(Token.Type.NAME, "ensubj"),
				new Token(Token.Type.EQUAL, "="),
				new Token(Token.Type.DLITERAL, "\"Foo\""),
				new Token(Token.Type.BAR, "|"),
				new Token(Token.Type.DLITERAL, "\"Bar\""),
				new Token(Token.Type.BAR, "|"),
				new Token(Token.Type.DLITERAL, "\"Baz\""),
				new Token(Token.Type.BAR, "|"),
				new Token(Token.Type.DLITERAL, "\"Mumble\""),
				new Token(Token.Type.SEMICOLON, ";"),
				new Token(Token.Type.NAME, "plsubj"),
				new Token(Token.Type.EQUAL, "="),
				new Token(Token.Type.DLITERAL, "\"Oofay\""),
				new Token(Token.Type.BAR, "|"),
				new Token(Token.Type.DLITERAL, "\"Arbay\""),
				new Token(Token.Type.BAR, "|"),
				new Token(Token.Type.DLITERAL, "\"Azbay\""),
				new Token(Token.Type.BAR, "|"),
				new Token(Token.Type.DLITERAL, "\"Umblemay\""),
				new Token(Token.Type.SEMICOLON, ";"),
				new Token(Token.Type.NAME, "enobj"),
				new Token(Token.Type.EQUAL, "="),
				new Token(Token.Type.DLITERAL, "\" is \""),
				new Token(Token.Type.NAME, "enslink"),
				new Token(Token.Type.NAME, "enadj"),
				new Token(Token.Type.NAME, "enelink"),
				new Token(Token.Type.SEMICOLON, ";"),
				new Token(Token.Type.NAME, "plobj"),
				new Token(Token.Type.EQUAL, "="),
				new Token(Token.Type.DLITERAL, "\" isay \""),
				new Token(Token.Type.NAME, "plslink"),
				new Token(Token.Type.NAME, "pladj"),
				new Token(Token.Type.NAME, "plelink"),
				new Token(Token.Type.SEMICOLON, ";"),
				new Token(Token.Type.NAME, "enadj"),
				new Token(Token.Type.EQUAL, "="),
				new Token(Token.Type.DLITERAL, "\"gah\""),
				new Token(Token.Type.BAR, "|"),
				new Token(Token.Type.DLITERAL, "\"hop\""),
				new Token(Token.Type.BAR, "|"),
				new Token(Token.Type.DLITERAL, "\"zot\""),
				new Token(Token.Type.BAR, "|"),
				new Token(Token.Type.DLITERAL, "\"borked\""),
				new Token(Token.Type.SEMICOLON, ";"),
				new Token(Token.Type.NAME, "pladj"),
				new Token(Token.Type.EQUAL, "="),
				new Token(Token.Type.DLITERAL, "\"ahgay\""),
				new Token(Token.Type.BAR, "|"),
				new Token(Token.Type.DLITERAL, "\"ophay\""),
				new Token(Token.Type.BAR, "|"),
				new Token(Token.Type.DLITERAL, "\"otzay\""),
				new Token(Token.Type.BAR, "|"),
				new Token(Token.Type.DLITERAL, "\"orkedbay\""),
				new Token(Token.Type.SEMICOLON, ";"),

				new Token(Token.Type.NAME, "bot"),
				new Token(Token.Type.EQUAL, "="),
				new Token(Token.Type.DLITERAL, "\"</body></html>\""),
				new Token(Token.Type.SEMICOLON, ";"),
				new Token(Token.Type.EOI, null) };

		tryTokenizeTest(afterTokens, testInput);
	}
}
