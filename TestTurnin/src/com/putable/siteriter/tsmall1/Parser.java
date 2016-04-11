package com.putable.siteriter.tsmall1;

/**
 * Creates Symbols from a list of Tokens and places them in the given
 * SymbolTable.
 * 
 * @author Trent Small
 * 
 */
public class Parser
{
	/**
	 * Clears a {@link SymbolTable} and fills it with information taken from an
	 * array of {@link Token}s representing a valid SDL Program.
	 * 
	 * @param table
	 *            The SymbolTable to fill.
	 * @param program
	 *            Tokens representing a valid SDL program which the table will
	 *            soon represent.
	 */
	public static void refillTable(SymbolTable table, Token[] program)
	{
		/* Remove the EOI token. */
		Token[] uneoi = Verifier.split(program, Token.Type.EOI)[0];
		Token[][] rules = Verifier.split(uneoi, Token.Type.SEMICOLON);

		table.clear();

		for (int i = 0; i < rules.length; i++)
		{
			if (rules[i].length == 0)
			{
				break;
			}
			/* Snap the Semicolons back on */
			rules[i] = Token.append(rules[i], Token.Type.SEMICOLON.single(";"));

			/*
			 * Add them to the table. The first one is a start token, so use
			 * i==0 as the start argument.
			 */
			table.add(new Symbol(rules[i], i == 0));
		}
	}
}
