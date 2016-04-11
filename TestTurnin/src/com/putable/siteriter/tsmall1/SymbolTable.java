package com.putable.siteriter.tsmall1;

import java.util.HashMap;
import java.util.Map;

/**
 * A class which represents a structre for efficiently storing {@link Symbol}s
 * for an SDLParser. Internally stores a {@link HashMap} in order to maintain
 * constant access to the elements inside.
 * 
 * @author Trent Small
 * 
 */
public class SymbolTable
{
	/**
	 * The main {@link Map} of Symbols which will be referenced from this table.
	 */
	private Map<String, Symbol>	map			= new HashMap<String, Symbol>();

	/**
	 * This is the name of this table's Start {@link Symbol}.
	 */
	private String				startName	= "";

	/**
	 * Gets the {@link Symbol} with the same name as {@link #startName} out of
	 * {@link #map}.
	 * 
	 * @return This table's starting {@link Symbol}.
	 */
	public Symbol getStart()
	{
		if (map.containsKey(startName))
		{
			return map.get(startName);
		}
		return null;
	}

	/**
	 * Adds a new {@link Symbol} into this {@link SymbolTable}.
	 * 
	 * @param s
	 *            The {@link Symbol} to insert into the table.
	 */
	public void add(Symbol s)
	{
		map.put(s.getName(), s);
		if (s.getStartType() == Symbol.StartType.START)
		{
			startName = s.getName();
		}
	}

	/**
	 * Gets the selector of the {@link Symbol} with the specified name, it it
	 * has one.
	 * 
	 * @param name
	 *            The name of the {@link Symbol} to look at.
	 * @return The selector if {@code name} is the name of a {@link Symbol} in
	 *         the table <b>AND</b> it has a sSelector, else {@code null}.
	 */
	public String getSelector(String name)
	{
		if (map.containsKey(name))
		{
			return map.get(name).getSelector();
		}
		return null;
	}

	/**
	 * Checks to see if this table contains a {@link Symbol} with the specified
	 * name.
	 * 
	 * @param name
	 *            The name of the {@link Symbol} to check for.
	 * @return {@code true} if this table has a {@link Symbol} with the
	 *         specified name, else {@code false}.
	 */
	public boolean contains(String name)
	{
		return map.containsKey(name);
	}

	/**
	 * Removes all {@link Symbol}s from this table, as per the spec: ((C.2.3.4))
	 */
	public void clear()
	{
		map.clear();
	}

	/**
	 * Retrieves a {@link Symbol} from this table.
	 * 
	 * @param name
	 *            The name of the {@link Symbol} to look for.
	 * @return The {@link Symbol} with the specified name if it is in the table, else
	 *         {@code null}.
	 */
	public Symbol get(String name)
	{
		if (map.containsKey(name))
		{
			return map.get(name);
		}
		return null;
	}
}
