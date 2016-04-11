package com.putable.siteriter.tsmall1;

import java.io.IOException;
import java.io.Reader;
import java.io.StringReader;

import com.putable.siteriter.SDLParseException;

/**
 * A class which creates {@link Token}[]s statically from an input
 * {@link Reader}.
 * 
 * @author Trent Small
 * 
 */
public class Tokenizer
{
	/**
	 * Gets the {@code char} from the front of a {@link Reader} without
	 * consuming it.
	 * 
	 * @param r
	 *            The {@link Reader} to read from. This {@link Reader} must
	 *            support {@link Reader#mark(int)}.
	 * @return The {@code char} on the front of the {@link Reader}.
	 * @throws IOException
	 *             If the {@link Reader} cannot {@link Reader#read()} for any
	 *             reason.
	 */
	private static int peek(Reader r) throws IOException
	{
		r.mark(1);
		int ch = r.read();
		r.reset();
		return ch;
	}

	/**
	 * Translates a String into its {@link Token}[] equivalent.
	 * 
	 * @param s
	 *            The String to translate.
	 * @return The Tokens which the String represents.
	 * @throws IOException
	 *             If an internal {@link Reader} created from {@code s} is
	 *             unable to {@link Reader#read()} for any reason.
	 */
	public static Token[] tokenize(String s) throws IOException
	{
		return inTokenize(new StringReader(s));
	}

	/**
	 * Consumes all {@code char}s which satisfy
	 * {@link Character#isWhitespace(char)} at the front of a {@link Reader}.
	 * 
	 * @param reader
	 *            The {@link Reader} to remove leading whitespace from.
	 * @throws IOException
	 *             If the {@link Reader} is unable to {@link Reader#read()} for
	 *             any reason.
	 */
	private static void eatWhitespace(Reader reader) throws IOException
	{
		while (Character.isWhitespace(peek(reader)))
		{
			reader.read();
		}
	}

	/**
	 * Reads either a {@link Token.Type#SLITERAL} or a
	 * {@link Token.Type#DLITERAL} value from a {@link Reader} and returns it
	 * with corresponding quotes around it. The literal <b><em>must</em></b> be
	 * immediately next in the {@link Reader}.
	 * 
	 * @param reader
	 *            The {@link Reader} to read from.
	 * @return Either a {@link Token.Type#SLITERAL} or a
	 *         {@link Token.Type#DLITERAL} value , depending on which one is in
	 *         next the {@link Reader}.
	 * @throws IOException
	 *             If the {@link Reader} is unable to {@link Reader#read()} for
	 *             any reason.
	 * @throws SDLParseException
	 *             If the literal does not end.
	 */
	private static String extractLiteral(Reader reader) throws IOException
	{
		char litC = (char)reader.read();
		StringBuilder acc = new StringBuilder();
		acc.append(litC);
		while ((char)peek(reader) != litC)
		{
			acc.append((char)reader.read());
			if (peek(reader) == -1)
			{
				throw new SDLParseException("Literal did not end correctly. '"
						+ litC + "' expected.");
			}
		}
		acc.append((char)reader.read());
		return acc.toString();
	}

	/**
	 * Reads a {@link Token.Type#NAME} value from a {@link Reader} and returns
	 * it. The {@link Token.Type#NAME} value must be immediately next in the
	 * {@link Reader}.
	 * 
	 * @param reader
	 *            The {@link Reader} to {@link Reader#read()} from.
	 * @return The next {@link Token.Type#NAME} value in the {@link Reader}.
	 * @throws IOException
	 *             If the {@link Reader} is unable to {@link Reader#read()} for
	 *             any readon.
	 */
	private static String extractName(Reader reader) throws IOException
	{
		StringBuilder acc = new StringBuilder();
		String illegals = "=;:|'\"";
		while (!illegals.contains(Character.toString((char)peek(reader)))
				&& !Character.isWhitespace((char)peek(reader)))
		{
			acc.append((char)reader.read());
			if (peek(reader) == -1)
			{
				break;
			}
		}
		return acc.toString();
	}

	/**
	 * Creates a new {@link Reader} which <b><em>will</em></b> support
	 * {@link Reader#mark(int)} by reading {@code r} to the end and building a
	 * new {@link StringReader} out of the characters inside.
	 * 
	 * @param reader
	 *            The {@link Reader} to {@link Reader#read()} from.
	 * @return a new {@link Reader} which must support {@link Reader#mark(int)}.
	 * @throws IOException
	 *             If the {@link Reader} cannot be {@link Reader#read()} from
	 *             for any reason.
	 */
	private static Reader makeMarkSupportedReader(Reader reader)
			throws IOException
	{
		StringBuilder sb = new StringBuilder();
		int ch;
		while ((ch = reader.read()) != -1)
		{
			sb.append((char)ch);
		}
		return new StringReader(sb.toString());
	}

	/**
	 * Translates a {@link Reader} into its {@link Token}[] equivalent.
	 * 
	 * @param r
	 *            The {@link Reader} to translate. If this {@link Reader} does
	 *            not support {@link Reader#mark(int)}, a new {@link Reader}
	 *            that does will be constructed and used.
	 * @return The {@link Token}[] equivalent of what is read from the {@link Reader}.
	 * @throws IOException
	 *             If the {@link Reader} is unable to {@link Reader#read()} for any reason.
	 */
	public static Token[] tokenize(Reader r) throws IOException
	{
		return inTokenize(r.markSupported() ? r : makeMarkSupportedReader(r));
	}

	/**
	 * Translates a {@link Reader} into its {@link Token} equivalent.
	 * 
	 * @param r
	 *            The {@link Reader} to translate. <b><em>Only</b></em> works if this {@link Reader} supports
	 *            {@link Reader#mark(int)}.
	 * @return The {@link Token}[] equivalent of what is read from the {@link Reader}.
	 * @throws IOException
	 *             If the {@link Reader} is unable to {@link Reader#read()} for any reason, or does not
	 *             support {@link Reader#mark(int)}.
	 */

	private static Token[] inTokenize(Reader r) throws IOException
	{
		Token[] tokens = null;
		eatWhitespace(r);
		while (peek(r) != -1)
		{
			switch (peek(r))
			{
			case '|':
				r.read();
				tokens = Token.append(tokens, Token.Type.BAR.single("|"));
				break;
			case ';':
				r.read();
				tokens = Token.append(tokens, Token.Type.SEMICOLON.single(";"));
				break;
			case ':':
				r.read();
				tokens = Token.append(tokens, Token.Type.COLON.single(":"));
				break;
			case '=':
				r.read();
				tokens = Token.append(tokens, Token.Type.EQUAL.single("="));
				break;
			case '"':
				tokens = Token.append(tokens,
						Token.Type.DLITERAL.single(extractLiteral(r)));
				break;
			case '\'':
				tokens = Token.append(tokens,
						Token.Type.SLITERAL.single(extractLiteral(r)));
				break;
			default:
				tokens = Token.append(tokens,
						Token.Type.NAME.single(extractName(r)));
				break;
			}
			eatWhitespace(r);
		}
		return Token.append(tokens, Token.Type.EOI.single(null));
	}
}
