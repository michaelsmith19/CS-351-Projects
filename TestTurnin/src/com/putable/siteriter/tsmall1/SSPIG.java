package com.putable.siteriter.tsmall1;

import java.util.Map;

/**
 * The Super Stochastic Page Information Generator for this SiteRiter
 * implementation. This generates page data for
 * {@link com.putable.siteriter.SDLParser#makePage(String, Map)}
 * 
 * @author Trent Small
 * 
 */
public class SSPIG
{
	/**
	 * Gets the rest of a {@code String} up until a certain character is
	 * reached. Example: readUntil("cookies", k) == "coo"
	 * 
	 * @param s
	 *            The {@code String} to read from.
	 * @param c
	 *            The {@code char} to stop at.
	 * @return The String, which contains all {@code char}s up to the first c.
	 */
	private static String readUntil(String s, char c)
	{
		StringBuilder sb = new StringBuilder();
		int sIdx = 0;
		while (s.length() > 0 && s.charAt(sIdx) != c)
		{
			sb.append(s.charAt(sIdx++));
		}
		return sb.toString();
	}

	/**
	 * Finds out if there is a start symbol specified in the key and returns it.
	 * ((C.3.3.5.2)) ((C.7))
	 * 
	 * @param key
	 *            The key to search.
	 * @return The name of the start symbol if it is found, or {@code null} if
	 *         it is not found.
	 */
	private static String startSymbolStr(String key)
	{
		String startStr = null;
		if (key.startsWith("/ss/"))
		{
			key = key.substring(4);
			if (key.contains("/"))
			{
				String nextTag = readUntil(key, '/');
				if (nextTag != "")
				{
					startStr = nextTag;
				}
			}
		}
		return startStr;
	}

	/**
	 * Looks into a {@link SymbolTable} for a symbol with the specified name.
	 * 
	 * @param table
	 *            The {@link SymbolTable} to search through.
	 * @param startStr
	 *            The name of the {@link Symbol} to retrieve.
	 * @return The {@link Symbol} with the same name as startStr. If there is no
	 *         such Symbol, returns table's default Start Symbol.
	 */
	private static Symbol getStartSymbol(SymbolTable table, String startStr)
	{
		Symbol startSym = null;
		if (startStr != null)
		{
			startSym = table.get(startStr);
		}
		if (startSym == null)
		{
			startSym = table.getStart();
		}
		return startSym;
	}

	/**
	 * Checks to see if the specified key requires any advanced processing.
	 * 
	 * @param key
	 *            The key to check.
	 * @return {@code true} if this key requires special processing, else
	 *         {@code false}
	 */
	private static boolean isSpecialKey(String key)
	{
		return key.equals("/robots.txt");
	}

	/**
	 * If the key is a special key (i.e., returns {@code true} from
	 * {@link #isSpecialKey(String)}), retrieves its special {@link Symbol} from
	 * the specified {@link SymbolTable}.
	 * 
	 * @param table
	 *            The {@link SymbolTable} to search through.
	 * @param key
	 *            The special key.
	 * @return The Symbol related to the special key if it is found, else
	 *         {@code null}.
	 */
	private static Symbol getSpecialSymbol(SymbolTable table, String key)
	{
		if (key.equals("/robots.txt") && table.contains("ROBOTS_TXT"))
		{
			return table.get("ROBOTS_TXT");
		}
		return null;
	}

	/**
	 * Makes a page based on the rules of Rule expansion in the spec. See
	 * {@link com.putable.siteriter.SDLParser}
	 * 
	 * @param key
	 *            The {@code String}-basis for generating the page.
	 * @param selectors
	 *            A {@link java.util.Map} of selectors for page generation.
	 * @param table
	 *            The {@link SymbolTable} used for looking up rules.
	 * @return A generated page based on the rules outlined in the spec.
	 */
	public static String makePage(String key, Map<String, Integer> selectors,
			SymbolTable table)
	{

		String startStr = startSymbolStr(key);
		Symbol startSymbol = getStartSymbol(table, startStr);

		if (isSpecialKey(key))
		{
			startSymbol = getSpecialSymbol(table, key);
			if (startSymbol == null)
			{
				return null;
			}
		}

		SymbolExpander sexpr = new SymbolExpander(table, startSymbol,
				key.hashCode(), selectors);
		return sexpr.expand();
	}
}