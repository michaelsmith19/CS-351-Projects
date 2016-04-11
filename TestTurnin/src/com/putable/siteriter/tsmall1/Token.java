package com.putable.siteriter.tsmall1;

/**
 * A Class representing an immutable Token object, which consists of a
 * {@link Token.Type} and a String value.
 * 
 * @author Trent Small
 * 
 */
public class Token
{
	/**
	 * A list of legal Types for a {@link Token} to contain in SDL.
	 * 
	 * @author Trent Small
	 * 
	 */
	public enum Type
	{
		/**
		 * The {@link Type} representing a Rule Assignment ({@code =}) symbol.
		 */
		EQUAL
		{
			public Token[] single(String value)
			{
				return new Token[] { new Token(EQUAL, "=") };
			}
		},
		/**
		 * The {@link Type} representing a Choice ({@code |}) symbol.
		 */
		BAR
		{
			public Token[] single(String value)
			{
				return new Token[] { new Token(BAR, "|") };
			}
		},
		/**
		 * The {@link Type} representing a Selector or Secondary Start ({@code :}
		 * ) symbol.
		 */
		COLON
		{
			public Token[] single(String value)
			{
				return new Token[] { new Token(COLON, ":") };
			}
		},
		/**
		 * The {@link Type} representing a Rule Termination ({@code ;}) symbol.
		 */
		SEMICOLON
		{
			public Token[] single(String value)
			{
				return new Token[] { new Token(SEMICOLON, ";") };
			}
		},
		/**
		 * The {@link Type} representing a Literal surrounded with Double Quotes
		 * ({@code ""});
		 */
		DLITERAL
		{
			public Token[] single(String value)
			{
				return new Token[] { new Token(DLITERAL, value) };
			}
		},
		/**
		 * The {@link Type} representing a Literal surrounded with Single Quotes
		 * ({@code ''});
		 */
		SLITERAL
		{
			public Token[] single(String value)
			{
				return new Token[] { new Token(SLITERAL, value) };
			}
		},
		/**
		 * The {@link Type} representing a name (any token which is not
		 * classified by the other types).
		 */
		NAME
		{
			public Token[] single(String value)
			{
				return new Token[] { new Token(NAME, value) };
			}
		},
		/**
		 * The {@link Type} representing the End of SDL Input.
		 */
		EOI
		{
			public Token[] single(String value)
			{
				return new Token[] { new Token(EOI, null) };
			}
		};
		/**
		 * Creates a new Token array containing a single {@link Token} of this
		 * {@link Type}.
		 * 
		 * @param value
		 *            The value to store inside this {@link Token}.
		 * @return A new {@link Token}[] with a single Token of this type.
		 */
		public abstract Token[] single(String value);
	};

	/**
	 * This {@link Token}'s {@link Type}.
	 */
	private Type	type;
	/**
	 * This {@link Token}'s value.<br>
	 * If {@link type} represents a {@code char} operator, this will be that
	 * {@code char}.<br>
	 * If this is a {@link Type#EOI}, this will be {@code null}.<br>
	 * If this is a {@link Type#DLITERAL} or {@link Type#SLITERAL}, this will be
	 * the entire literal including quotes.<br>
	 * If this is a {@link Type#name}, the entire name is stored, even if it is
	 * invalid.
	 */
	private String	value;

	/**
	 * Creates a new {@link Token}.
	 * 
	 * @param type
	 *            The {@link Type} of the {@link Token}.
	 * @param value
	 *            The value of the {@link Token}.
	 */
	public Token(Type type, String value)
	{
		this.type = type;
		this.value = value;
	}

	/**
	 * Returns the {@link Type} of this {@link Token}.
	 * 
	 * @return The {@link Type} of this {@link Token}.
	 */
	public Type getType()
	{
		return type;
	}

	/**
	 * Returns the value stored inside this {@link Token}.
	 * 
	 * @return The value stored inside this {@link Token}.
	 */
	public String getValue()
	{
		return value;
	}

	/**
	 * Takes two {link Token}[]s and appends their values, creating a new array
	 * which has the sequential values of a and <b><em>then</b></em> b. If
	 * either array is null, this method treats them as if they are
	 * {@code Token[0]} instead.
	 * 
	 * @param a
	 *            The first array.
	 * @param b
	 *            The second array.
	 * @return The appended array, containing {a[0], a[1] .. a[n], b[0], b[1] ..
	 *         b[n]}
	 */
	public static Token[] append(Token[] a, Token[] b)
	{
		if (a == null)
		{
			a = new Token[0];
		}
		if (b == null)
		{
			b = new Token[0];
		}
		Token[] c = new Token[a.length + b.length];
		for (int i = 0; i < a.length; i++)
		{
			c[i] = a[i];
		}
		for (int i = 0; i < b.length; i++)
		{
			c[i + a.length] = b[i];
		}

		return c;
	}

	@Override
	public String toString()
	{
		return type.toString() + ": \"" + value + "\"";
	}

	@Override
	public boolean equals(Object obj)
	{
		if (obj instanceof Token)
		{
			Token t = (Token)obj;

			return t.type == type
					&& ((t.value == null && value == null) || t.value
							.equals(value));
		}
		return false;
	}

	/**
	 * Returns a {@link Token}[] with a Single {@link Token} with the specified
	 * parameters inside.
	 * 
	 * @param t
	 *            The Type of the new {@link Token}.
	 * @param value
	 *            The Value of the new {@link Token}.
	 * @return A {@link Token}[] with a single {@link Token} inside.
	 * @deprecated Use {@link Type#single(String)} instead.
	 */
	public static Token[] SingleToken(Type t, String value)
	{
		return new Token[] { new Token(t, value) };
	}

}
