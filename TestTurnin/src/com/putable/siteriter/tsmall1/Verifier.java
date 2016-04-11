package com.putable.siteriter.tsmall1;

import java.io.IOException;
import java.io.StringReader;

import com.putable.siteriter.SDLParseException;

/**
 * A static class whose primary purpose is to verify that a {@link Token}[] is a
 * valid SDL program according to the SDL grammar.
 * 
 * @author Trent Small
 * 
 */
public class Verifier
{
	/**
	 * Counts the number of {@link Token}s in an array which have the specified
	 * {@link Token.Type} .
	 * 
	 * @param tokens
	 *            The {@link Token}s to count.
	 * @param type
	 *            The {@link Token.Type} of the {@link Token}s to count.
	 * @return The number of {@link Token} in {@code tokens} which have
	 *         {@link Token.Type} type.
	 */
	private static int tokenCount(Token[] tokens, Token.Type type)
	{
		int count = 0;
		for (int i = 0; i < tokens.length; i++)
		{
			if (tokens[i].getType().equals(type))
			{
				count++;
			}
		}
		return count;
	}

	/**
	 * Splits a {@link Token}[] into several arrays at each occurrence of a
	 * {@link Token} of a particular type. If two {@link Token}s of similar type
	 * occur next to each other, the array in that position will be a {@code
	 * new {@link Token}[0]} . For example:<br>
	 * {@code
	 *   Split([NAME, SEMICOLON, NAME], SEMICOLON) 
	 *   ==
	 *   [[NAME],[NAME]]
	 * }<br>
	 * {@code
	 *   Split([NAME, SEMICOLON, SEMICOLON, NAME], SEMICOLON) 
	 *   ==
	 *   [[NAME],[],[NAME]]
	 * }
	 * 
	 * @param tokens
	 *            The {@link Token}s to split.
	 * @param type
	 *            The {@link Token.Type} to split at.
	 * @return The Split {@link Token}s, as outlined above.
	 */
	public static Token[][] split(Token[] tokens, Token.Type type)
	{
		Token[][] splits = new Token[tokenCount(tokens, type) + 1][];

		int curSplit = 0;
		splits[0] = new Token[0];
		for (int i = 0; i < tokens.length; i++)
		{
			if (!tokens[i].getType().equals(type))
			{
				splits[curSplit] = Token.append(splits[curSplit],
						new Token[] { tokens[i] });
			}
			else
			{
				splits[++curSplit] = new Token[0];
			}
		}

		return splits;
	}

	/**
	 * Verifies {@link Token} by {@link Token} that each {@link Token} in an
	 * array is valid, by checking if its value is valid for its
	 * {@link Token.Type}. Context of each {@link Token} is verified in the
	 * other methods of this Class.
	 * 
	 * @param tokens
	 *            The {@link Token}[] to verify.
	 * @return True if all {@link Token}s are valid.
	 * @throws SDLParseException
	 *             If there is an invalid {@link Token}.
	 */
	private static boolean verifyAllTokens(Token[] tokens)
			throws SDLParseException
	{
		for (Token t : tokens)
		{
			if (!verifyToken(t))
			{
				return false;
			}
		}
		return true;
	}

	/**
	 * Verifies that a {@link Token}'s Value is valid for its {@link Token.Type}
	 * .
	 * 
	 * @param token
	 *            The {@link Token} to check
	 * @return {@code true} if the {@link Token} is valid, else {@code false}.
	 * @throws SDLParseException
	 *             If there is an invalid {@link Token} or the {@link Token} has
	 *             an unknown {@link Token.Type}.
	 */
	private static boolean verifyToken(Token token) throws SDLParseException
	{
		switch (token.getType())
		{
		case NAME:
			return verifyName(token);
		case EQUAL:
		case BAR:
		case COLON:
		case SEMICOLON:
		case DLITERAL:
		case SLITERAL:
		case EOI:
			return true;
		default:
			throw new SDLParseException("Unrecognized Token type: "
					+ token.getType().toString());
		}
	}

	/**
	 * Verifies a {@link Token} to see if it is a valid {@link Token}. This only
	 * works if the {@link Token} is a {@link Token.Type#NAME}.
	 * 
	 * @param t
	 *            The {@link Token} to verify.
	 * @return True if the {@link Token} is a legal {@link Token.Type#NAME}
	 *         {@link Token}.
	 */
	private static boolean verifyName(Token t) throws SDLParseException
	{
		String n = t.getValue();
		if (Character.isJavaIdentifierStart(n.charAt(0)))
		{
			for (int i = 1; i < n.length(); i++)
			{
				if (!Character.isJavaIdentifierPart(n.charAt(i)))
				{
					throw new SDLParseException("Error: Identifier (" + n
							+ ") contains character " + "(" + n.charAt(i)
							+ "), which is not a legal part of "
							+ "an identifier.");
				}
			}
		}
		else
		{
			throw new SDLParseException("Error: Identifier (" + n
					+ ") starts with " + "(" + n.charAt(0)
					+ "), which is not a legal start of " + "an identifier.");
		}
		return true;
	}

	/**
	 * Verifies that a {@link Token}[] representing an SDL program fits the
	 * correctness rules outlined in the spec.
	 * 
	 * @param tokens
	 *            The {@link Token}[] you wish to verify
	 * @return True if the {@code tokens} represent a valid program.
	 * @throws SDLParseException
	 *             if there is a syntactic error of any kind in {@code tokens}.
	 *             ((C.9.2)) ((C.9.3))
	 */
	public static boolean verify(Token[] tokens) throws SDLParseException
	{
		if (!verifyAllTokens(tokens))
		{
			return false;
		}

		return verifyProgram(tokens);
	}

	/**
	 * Verifies that a Token[] representing an SDL language Program fits the
	 * correctness rules of a Program outlined in the spec.
	 * 
	 * @param tokens
	 *            The {@link Token}[] to verify.
	 * @return True if {@code tokens} represents a valid Program production.
	 */
	private static boolean verifyProgram(Token[] tokens)
			throws SDLParseException
	{
		/* There must be at least one rule. */
		if (tokenCount(tokens, Token.Type.SEMICOLON) < 1)
		{
			throw new SDLParseException(
					"Parse Invalid. Program must contain at least one rule.");
		}

		Token[][] rules = split(tokens, Token.Type.SEMICOLON);
		for (int i = 0; i < rules.length - 1; i++)
		{
			if (!verifyRule(Token.append(rules[i],
					Token.Type.SEMICOLON.single(";"))))
			{
				return false;
			}
		}

		/* The last set of tokens must only be a single EOI token. */
		if (rules[rules.length - 1].length != 1)
		{
			throw new SDLParseException(
					"Parse Invalid. Program must have a terminating EOI token.");
		}
		if (!rules[rules.length - 1][0].getType().equals(Token.Type.EOI))
		{
			throw new SDLParseException(
					"Parse Invalid. Program must have a terminating EOI token.");
		}

		return true;
	}

	/**
	 * Verifies that a {@link Token}[] representing an SDL language Rule fits
	 * the correctness rules of a Rule outlined in the spec.
	 * 
	 * @param tokens
	 *            The {@link Token}[] to verify.
	 * @return True if {@code tokens} represent a valid Rule production.
	 */
	private static boolean verifyRule(Token[] tokens) throws SDLParseException
	{
		/* The rule must end in a SEMICOLON token. */
		if (!tokens[tokens.length - 1].getType().equals(Token.Type.SEMICOLON))
		{
			throw new SDLParseException(
					"Parse Invalid. Rule does not end with a Semicolon.");
		}
		Token[] unsemi = split(tokens, Token.Type.SEMICOLON)[0];

		Token[][] halves = split(unsemi, Token.Type.EQUAL);
		if (halves.length != 2)
		{
			throw new SDLParseException(
					"Parse Invalid. Rule does not contain an EQUAL token.");
		}

		if (!verifyHead(halves[0]))
		{
			return false;
		}
		if (!verifyChoice(halves[1]))
		{
			return false;
		}

		return true;
	}

	/**
	 * Verifies that a {@link Token}[] is of a valid {@code <Head>} production.
	 * See the spec for details.
	 * 
	 * @param tokens
	 *            The {@link Token}[] to verify.
	 * @return True if {@code tokens} are a valid {@code <Head>} production.
	 * @throws SDLParseException
	 *             If {@code tokens} do not represent a valid head.
	 */
	private static boolean verifyHead(Token[] tokens) throws SDLParseException
	{
		/* A Head can only have one of four forms: */

		/* Form 1: A single NAME token. */
		if (tokens.length == 1 && tokens[0].getType().equals(Token.Type.NAME))
		{
			return true;
		}
		/* Form 2: A NAME with a COLON token ahead of it. */
		else if (tokens.length == 2)
		{
			if (tokens[0].getType().equals(Token.Type.COLON)
					&& tokens[1].getType().equals(Token.Type.NAME))
			{
				return true;
			}
		}
		/* Form 3: NAME COLON NAME . */
		else if (tokens.length == 3)
		{
			if (tokens[0].getType().equals(Token.Type.NAME)
					&& tokens[1].getType().equals(Token.Type.COLON)
					&& tokens[2].getType().equals(Token.Type.NAME))
			{
				return true;
			}
		}
		/* Form 4: COLON NAME COLON NAME . */
		else if (tokens.length == 4)
		{
			if (tokens[0].getType().equals(Token.Type.COLON)
					&& tokens[1].getType().equals(Token.Type.NAME)
					&& tokens[2].getType().equals(Token.Type.COLON)
					&& tokens[3].getType().equals(Token.Type.NAME))
			{
				return true;
			}
		}
		throw new SDLParseException(
				"Parse Invalid. Head is in an incorrect format.");
	}

	/**
	 * Verifies that a {@link Token}[] is of a valid {@code <Choice>}
	 * production. See the spec for deatils.
	 * 
	 * @param tokens
	 *            The {@link Token}[] to verify.
	 * @return True if {@code tokens} represent a valid {@code <Choice>}
	 *         production.
	 * @throws SDLParseException
	 *             If {@code tokens} do not represent a valid choice.
	 */
	private static boolean verifyChoice(Token[] tokens)
			throws SDLParseException
	{
		/*
		 * As long as the Tokens in the Choice clause are a combination of
		 * NAMEs, BARs, SLITERALs and DLITERALs, it is considered valid.
		 */
		for (int i = 0; i < tokens.length; i++)
		{
			switch (tokens[i].getType())
			{
			case NAME:
			case BAR:
			case SLITERAL:
			case DLITERAL:
				break;
			default:
				throw new SDLParseException(
						"Parse Invalid. Only NAME, BAR, SLITERAL, and DLITERAL tokens "
								+ "are valid in a CHOICE.");
			}
		}
		return true;
	}

	/**
	 * Verifies that a String representing an SDL program fits the correctness
	 * rules outlined in the spec. This is an overload which is normally only
	 * used for testing purposes.
	 * 
	 * @param s
	 *            The String to verify.
	 * @return True if the String represents a valid program.
	 * @throws IOException
	 *             If this String has any SDL syntax errors.
	 * 
	 */
	public static boolean verify(String s) throws IOException
	{
		return verify(Tokenizer.tokenize(new StringReader(s)));
	}
}