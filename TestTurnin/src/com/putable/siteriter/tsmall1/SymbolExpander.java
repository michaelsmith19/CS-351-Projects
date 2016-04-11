package com.putable.siteriter.tsmall1;

import java.util.Map;
import java.util.Random;

/**
 * A class which creates an expansion of a {@link SymbolTable} based on its
 * specified parameters.
 * 
 * @author Trent Small
 */
public class SymbolExpander
{
	/**
	 * The table to expand.
	 */
	private SymbolTable				table;

	/**
	 * The Symbol to begin expansion with.
	 */
	private Symbol					startSymbol;

	/**
	 * A PRNG used when selecting choices.
	 */
	private Random					rand;

	/**
	 * The map of selectors used when selecting choices.
	 */
	private Map<String, Integer>	selectors;

	/**
	 * The end result of an expansion. Running {@link #expand()} will always
	 * store the same result here.
	 */
	private String					expansion	= null;

	/**
	 * Creates a new {@link SymbolExpander}, used for expanding a specific
	 * Symbol Expansion. Always expands the same {@code String} once created.
	 * ((C.3.3.5.1))
	 * 
	 * @param table
	 *            The {@link SymbolTable} which contains the {@link Symbol}s
	 *            used in expansion.
	 * @param startSymbol
	 *            The first {@link Symbol} to expand.
	 * @param randSeed
	 *            The seed to use for the PRNG every time {@link #expand()} is
	 *            called.
	 * @param selectors
	 *            The selctors to use during a selector expansion.
	 */
	public SymbolExpander(SymbolTable table, Symbol startSymbol, long randSeed,
			Map<String, Integer> selectors)
	{
		this.table = table;
		this.startSymbol = startSymbol;
		this.rand = new Random(randSeed);
		this.selectors = selectors;
	}

	/**
	 * Creates a full expansion from the parameters specified at construction.
	 * Once created, this always produces the same string. Because of this, a
	 * String is held which memoizes this expansion, resulting in constant time
	 * subsequent calls to this method. ((C.3.3.5.3))
	 * 
	 * @return The expansion of this particular {@link SymbolExpander}.
	 */
	public String expand()
	{
		if (expansion == null)
		{
			StringBuilder sb = new StringBuilder();
			expandSymbol(startSymbol, sb);
			expansion = sb.toString();
		}
		return expansion;
	}

	/**
	 * Returns an index of a Choice based on the numbers of sequences stored
	 * inside. This is based on the Independent Select procedure outlined in the
	 * spec at ((C.3.4.2.1.1)).
	 * 
	 * @param choiceLen
	 *            The length of the choice.
	 * @return The index of the choice which has been selected.
	 */
	private int independentSelect(int choiceLen)
	{
		return rand.nextInt(choiceLen);
	}

	/**
	 * Returns an index of a Choice based on the numbers of sequences stored
	 * inside. This is based on the Selected Select procedure outlined in the
	 * spec at ((C.3.4.2.1.2)).
	 * 
	 * @param selector
	 *            The Selector of the {@link Symbol} currently being expanded.
	 * @param choiceLen
	 *            The length of the choice.
	 * @return The index of the choice which has been selected.
	 */
	private int selectedSelect(String selector, int choiceLen)
	{
		if (!selectors.containsKey(selector))
		{
			/*
			 * Get a random number in the range of [0..Integer.MAX_VALUE-1],
			 * because Random.nextInt(int) excludes its argument in the range.
			 */
			selectors.put(selector, rand.nextInt(Integer.MAX_VALUE));
		}

		/*
		 * Java's mod (%) can be used here because selectors.get in this case is
		 * guaranteed to be positive.
		 */
		return selectors.get(selector) % choiceLen;
	}

	/**
	 * Expands a {@link Symbol} and appends the expansion to a
	 * {@link StringBuilder}.
	 * 
	 * @param s
	 *            The {@link Symbol} to expand. It does not nessecarily have to
	 *            be in {@link #table}, but any of its references need to be in
	 *            {@link #table} in order to get referenced.
	 * @param sb
	 *            The {@link StringBuilder} to append this expansion to.
	 */
	private void expandSymbol(Symbol s, StringBuilder sb)
	{
		String selector = s.getSelector();
		Token[][] choices = s.getChoices();
		Token[] sequence;
		if (selector == null)
		{
			sequence = choices[independentSelect(choices.length)];
		}
		else
		{
			sequence = choices[selectedSelect(selector, choices.length)];
		}

		expandSequence(sequence, sb);
	}

	/**
	 * Expands a {@link Token}[] sequentially and appends them to a
	 * {@link StringBuilder}. ((C.3.4.3))
	 * 
	 * @param sequence
	 *            The sequence of {@link Token}s to expand.
	 * @param sb
	 *            The {@link StringBuilder} to append this expansion to.
	 */
	private void expandSequence(Token[] sequence, StringBuilder sb)
	{
		for (int i = 0; i < sequence.length; i++)
		{
			switch (sequence[i].getType())
			{
			case NAME:
				expandName(sequence[i], sb);
				break;
			case DLITERAL:
			case SLITERAL:
				expandLiteral(sequence[i], sb);
				break;
			default:
				break; /*
						 * At this point the parsing would not have allowed a
						 * different Token.Type to be in this spot.
						 */
			}
		}
	}

	/**
	 * Expands either a {@link Token.Type#SLITERAL} or a
	 * {@link Token.Type#DLITERAL} {@link Token} and appends it to a
	 * {@link StringBuilder}. ((C.3.4.3.1.2)) ((C.3.4.3.1.3))
	 * 
	 * @param t
	 *            The {@link Token} to expand. This {@link Token} MUST either be
	 *            a {@link Token.Type#SLITERAL} or a {@link Token.Type#DLITERAL}
	 * @param sb
	 *            The {@link StringBuilder} to append this expansion to.
	 */
	private void expandLiteral(Token t, StringBuilder sb)
	{
		String expStr = t.getValue().substring(1, t.getValue().length() - 1);
		sb.append(expStr);
	}

	/**
	 * Expands a {@link Token.Type#NAME} {@link Token} and appends it to a
	 * {@link StringBuilder}. ((C.3.4.3.1.1))
	 * 
	 * @param t
	 *            The {@link Token} to expand. This {@link Token} MUST be a
	 *            {@link Token.Type#NAME} {@link Token}.
	 * @param sb
	 *            The {@link StringBuilder} to append this expansion to.
	 */
	private void expandName(Token t, StringBuilder sb)
	{
		if (table.contains(t.getValue()))
		{
			expandSymbol(table.get(t.getValue()), sb);
		}
		else
		{
			sb.append(t.getValue() + "?");
		}
	}
}
