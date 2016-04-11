package com.putable.siteriter.test;

import static org.junit.Assert.fail;

import java.io.IOException;

import org.junit.Assert;
import org.junit.Test;

import com.putable.siteriter.tsmall1.Token;
import com.putable.siteriter.tsmall1.Tokenizer;
import com.putable.siteriter.tsmall1.Verifier;

/**
 * These tests will check the Verifier for correctness. They assume that the
 * Tokenizer is correct.
 * 
 * @author Trent Small
 * 
 */
public class VerifierTest
{
	private void testSplits(String splStr, Token[][] expected, Token.Type type)
	{
		try
		{
			Token[] tokens = Tokenizer.tokenize(splStr);
			Token[][] splits = Verifier.split(tokens, type);
			Assert.assertArrayEquals(expected, splits);
		} catch (IOException e)
		{
			fail("Hey, the tokenizer failed! Fix your code!!");
		}
	}

	/**
	 * Tests Verify.Split for correctness on a small test.
	 */
	@Test
	public void testVerifySplitSmall()
	{

		Token[][] expected = new Token[][] {
				new Token[] { new Token(Token.Type.NAME, "foo") },
				new Token[] { new Token(Token.Type.NAME, "bar") },
				new Token[] { new Token(Token.Type.NAME, "jimmy") },
				new Token[] { new Token(Token.Type.EOI, null) } };
		testSplits("foo; bar; jimmy;", expected, Token.Type.SEMICOLON);
	}

	/**
	 * Tests Verify.Split for correctness on a more complicated test.
	 */
	@Test
	public void testVerifySplitSequential()
	{
		Token[][] expected = new Token[][] {
				new Token[] { new Token(Token.Type.COLON, ":"),
						new Token(Token.Type.NAME, "foo"),
						new Token(Token.Type.COLON, ":"),
						new Token(Token.Type.NAME, "s"),
						new Token(Token.Type.EQUAL, "=") },
				new Token[0],
				new Token[0],
				new Token[] { new Token(Token.Type.NAME, "bar") },
				new Token[0],
				new Token[0],
				new Token[] { new Token(Token.Type.NAME, "narf") },
				new Token[0],
				new Token[0],
				new Token[] { new Token(Token.Type.SEMICOLON, ";"),
						new Token(Token.Type.EOI, null) } };
		testSplits(":foo:s = |||bar|||narf|||;", expected, Token.Type.BAR);
	}

	/**
	 * Tests Verifier.Split for handling splits without the split Token being
	 * present in the tokens.
	 */
	@Test
	public void testVerifySplitMissingToken()
	{
		Token[][] expected = new Token[][] { new Token[] {
				new Token(Token.Type.NAME, "foo"),
				new Token(Token.Type.COLON, ":"),
				new Token(Token.Type.NAME, "bar"),
				new Token(Token.Type.NAME, "jimmy"),
				new Token(Token.Type.NAME, "billy"),
				new Token(Token.Type.SLITERAL, "'a'"),
				new Token(Token.Type.SLITERAL, "'b'"),
				new Token(Token.Type.DLITERAL, "\"c\""),
				new Token(Token.Type.SEMICOLON, ";"),
				new Token(Token.Type.EOI, null) } };

		testSplits("foo: bar jimmy billy 'a' 'b' \"c\";", expected,
				Token.Type.EQUAL);
	}

	/**
	 * Tests Verifier to check for a Token which is on the end of the Token[].
	 */
	@Test
	public void testVerifySplitAtEnd()
	{
		Token[][] expected = new Token[][] {
				new Token[] { new Token(Token.Type.NAME, "foo"),
						new Token(Token.Type.COLON, ":"),
						new Token(Token.Type.NAME, "bar"),
						new Token(Token.Type.NAME, "jimmy"),
						new Token(Token.Type.NAME, "billy"),
						new Token(Token.Type.SLITERAL, "'a'"),
						new Token(Token.Type.SLITERAL, "'b'"),
						new Token(Token.Type.DLITERAL, "\"c\""),
						new Token(Token.Type.SEMICOLON, ";") }, new Token[0] };

		testSplits("foo: bar jimmy billy 'a' 'b' \"c\";", expected,
				Token.Type.EOI);
	}

	/**
	 * Tests Verifier to check for illegally started identifiers.
	 */
	@Test
	public void testVerifyIllegalStartIdentifiers()
	{
		String testStr = "foo = 2bar; 2bar = bar; bar = 'a';";
		try
		{
			Verifier.verify(testStr);
			fail("Illegal identifier passed through Verifier.");
		} catch (IOException e)
		{}
	}

	/**
	 * Tests Verifier to check for identifiers with illegal characters inside of
	 * them.
	 */
	@Test
	public void testVerifyIllegalMidIdentifiers()
	{
		String testStr = "foo = bar; bar2 = bar; bar = |'a'|po#nd;";
		try
		{
			Verifier.verify(testStr);
			fail("Illegal identifier passed through Verifier.");
		} catch (IOException e)
		{}
	}
}