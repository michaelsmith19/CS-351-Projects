package com.putable.siteriter.tsmall1;

/**
 * A Class which represents an Expandable Symbol. This is the final form of a
 * Parsed SDL Expression.
 * 
 * @author Trent Small
 * 
 */
public class Symbol
{
	/**
	 * Represents a {@link Symbol}'s capabilities of expanding as a Start or
	 * Secondary Start Symbol.
	 * 
	 * @author Trent Small
	 * 
	 */
	public enum StartType
	{
		/**
		 * A Symbol of this StartType <em><b>cannot</em></b> be used as any kind
		 * of Start Symbol.
		 */
		NONE,

		/**
		 * A Symbol of this StartType will be used as a Start Symbol by default.
		 */
		START,

		/**
		 * A Symbol of this StartType will be used as a Secondary Start Symbol
		 * when requested.
		 */
		SECONDARY_START,
	}

	/**
	 * This {@link Symbol}'s {@link StartType}.
	 */
	private StartType	stype		= StartType.NONE;

	/**
	 * These {@link Token}s represent each choice that the {@link Symbol} may
	 * expand.
	 */
	private Token[][]	choices		= null;

	/**
	 * If specified by the {@code [:]NAME[:SELECTOR]} syntax, will store this
	 * rule's selector. If not specified, will be {@code null}.
	 */
	private String		selector	= null;

	/**
	 * The name of this {@link Symbol}.
	 */
	private String		name;

	/**
	 * Used in order to test Equality between two {@link Symbol}s. This ensures
	 * that this Symbol's {@link #choices} array is equal to {@link #other}'s
	 * {@link #choices} array. This will also work if these two Symbols have
	 * null {@link #choices}.
	 * 
	 * @param other
	 *            The Other {@link Symbol} to check {@link #choices} equality
	 *            for.
	 * @return {@code true} if these two {@link Symbol}s have equal
	 *         {@link #choices} arrays, else {@code false}.
	 */
	private boolean choicesEqual(Symbol other)
	{
		Token[][] ochs = other.getChoices();
		if (choices == null && ochs == null)
		{
			return true;
		}
		else if ((choices == null && ochs != null)
				|| (choices != null && ochs == null))
		{
			return false;
		}
		else
		{
			if (ochs.length != choices.length)
			{
				return false;
			}
			for (int i = 0; i < ochs.length; i++)
			{
				if (ochs[i].length != choices[i].length)
				{
					return false;
				}
				for (int j = 0; j < choices[i].length; j++)
				{
					if (!choices[i][j].equals(ochs[i][j]))
					{
						return false;
					}
				}
			}
			return true;
		}
	}

	/**
	 * Used in order to test if two {@code Symbol}s are equal. This checks to
	 * see if this Symbol and another Symbol have the same {@link #selector}.
	 * 
	 * @param other
	 *            The other Symbol to compare {@link #selector}s with.
	 * @return {@code true} if these two Symbols have the same {@link #selector}
	 *         , even if they are null, else {@code false}.
	 */
	private boolean selectorEquals(Symbol other)
	{
		String osel = other.getSelector();
		if (selector == null && osel == null)
		{
			return true;
		}
		else if ((selector == null && osel != null)
				|| (selector != null && osel == null))
		{
			return false;
		}
		else
		{
			return selector.equals(osel);
		}
	}

	@Override
	public boolean equals(Object o)
	{
		if (o instanceof Symbol)
		{
			Symbol other = (Symbol)o;
			return choicesEqual(other) && selectorEquals(other)
					&& name.equals(other.getName())
					&& stype.equals(other.stype);
		}
		return false;
	}

	/**
	 * Gets the {@link StartType} of this {@link Symbol}. ((C.2.4.1))
	 * 
	 * @return This Symbol's StartType.
	 */
	public StartType getStartType()
	{
		return stype;
	}

	/**
	 * Gets the {@link #name} of this {@link Symbol}. ((C.2.4.1))
	 * 
	 * @return This Symbol's name.
	 */
	public String getName()
	{
		return name;
	}

	/**
	 * Gets the {@link selector} of this {@link Symbol}. If there is no
	 * {@link selector} present, this value will be {@code null}. ((C.2.4.3))
	 * 
	 * @return The Selector of this Symbol, or null if it is not present.
	 */
	public String getSelector()
	{
		return selector;
	}

	/**
	 * Gets the {@link #choices} from this {@link Symbol}. ((C.2.4.2))
	 * 
	 * @return This Symbol's {@link #choices}, or {@code null} if they do not
	 *         exist.
	 */
	public Token[][] getChoices()
	{
		return choices;
	}

	/**
	 * Creates a new {@link Symbol} with the specified parameters.
	 * 
	 * @param rule
	 *            The {@link Token}s which represent a SDL Rule.
	 * @param startToken
	 *            If this is supposed to be used as a Start Symbol, set this to
	 *            {@code true}.
	 */
	public Symbol(Token[] rule, boolean startToken)
	{
		if (startToken)
		{
			stype = StartType.START;
		}
		readFromRule(rule);
	}

	/**
	 * Creates a new {@link Symbol} with the specified parameters. This is a
	 * more generalized constructor which is designed to be used for testing.
	 * 
	 * @param stype
	 *            The {@link StartType} for this Symbol.
	 * @param choices
	 *            The {@link #choices} of this Symbol.
	 * @param name
	 *            The {@link #name} of this Symbol.
	 * @param selector
	 *            The {@link #selector} of this Symbol.
	 */
	public Symbol(StartType stype, Token[][] choices, String name,
			String selector)
	{
		this.stype = stype;
		this.choices = choices;
		this.name = name;
		this.selector = selector;
	}

	/**
	 * Sets the {@link StartType} of this {@link Symbol} to
	 * {@link StartType#SECONDARY_START}, if it is not already
	 * {@link StartType#START}.
	 */
	private void setSecondaryIfNotPrimary()
	{
		if (!stype.equals(StartType.START))
		{
			stype = StartType.SECONDARY_START;
		}
	}

	/**
	 * Creates the internals of a {@link Symbol} based on its {@link Token}[]
	 * representation. <em><b>This must have already been verified through
	 * {@link Verifier#verify(Token[])}</em></b>.
	 * 
	 * @param rule
	 *            The Token array representation of this Symbol.
	 */
	private void readFromRule(Token[] rule)
	{
		Token[] unsemi = Verifier.split(rule, Token.Type.SEMICOLON)[0];
		Token[][] halves = Verifier.split(unsemi, Token.Type.EQUAL);

		readFromHead(halves[0]);
		readFromChoices(halves[1]);
	}

	/**
	 * Sets the {@link #choices} of this {@link Symbol} based on a {@link Token}[] which represents
	 * <em><b>ONLY</em></b> a choice clause of an SDL rule.
	 * 
	 * @param tokens
	 *            The {@link Token}s which represent an SDL choice.
	 */
	private void readFromChoices(Token[] tokens)
	{
		choices = Verifier.split(tokens, Token.Type.BAR);
	}

	/**
	 * Reads the head from a {@link Token}[] representing a
	 * valid SDL head.
	 * 
	 * @param head
	 *            The {@link Token}[] that represents this symbol's head.
	 */
	private void readFromHead(Token[] head)
	{
		if (head.length == 1)
		{
			name = head[0].getValue();
		}
		else if (head.length == 2)
		{
			setSecondaryIfNotPrimary();
			name = head[1].getValue();
		}
		else if (head.length == 3)
		{
			name = head[0].getValue();
			selector = head[2].getValue();
		}
		else
		{
			setSecondaryIfNotPrimary();
			name = head[1].getValue();
			selector = head[3].getValue();
		}
	}
}
