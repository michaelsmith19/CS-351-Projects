package com.putable.siteriter.msmith19;

import java.util.ArrayList;

/**
 * This class holds all the information for a single rule.
 * 
 * @author michaelsmith
 * 
 */

public class Symbol
{

	// XXX Again, as in the Token class, name and selector are
	// not going to change after they are assigned. I would
	// make these two fields final and make this class have a
	// constructor.
	private String						name;
	/** Is the rule flagged as a secondary start */
	private boolean						isStart		= false;
	/** If the rule has no selector this will be null */
	private String						selector	= null;

	public ArrayList<ArrayList<Token>>	choices		= new ArrayList<ArrayList<Token>>();

	public String getName()
	{
		return name;
	}

	public void setName(String name)
	{
		this.name = name;
	}

	public boolean isStart()
	{
		return isStart;
	}

	public void setStart(boolean isStart)
	{
		this.isStart = isStart;
	}

	public String getSelector()
	{
		return selector;
	}

	public void setSelector(String selector)
	{
		this.selector = selector;
	}

	public ArrayList<ArrayList<Token>> getChoices()
	{
		return choices;
	}

	public void setChoices(ArrayList<ArrayList<Token>> choices)
	{
		this.choices = choices;
	}

	@Override
	public String toString()
	{
		return "The name of this symbol is: " + name;

	}

}
