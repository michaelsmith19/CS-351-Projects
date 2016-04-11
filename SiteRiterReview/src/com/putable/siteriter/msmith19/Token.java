package com.putable.siteriter.msmith19;

/**
 * This class holds the information necessary for any single Token. These are
 * its Value which is the actual input and its Type which is the kind of unit
 * that this Token is.
 * 
 * @author michaelsmith
 * 
 */
public class Token
{
	// XXX Not sure if these are going to change later
	// after they get created, but Tokens should
	// probably be atomic, so I would maybe set
	// these to be final.
	private String	type;
	private String	value;

	// XXX Since you are always setting a Token's value
	// and type as soon as you make it, you should
	// really have made a constructor for one. Here
	// is a sample:

	/*
	 * public Token(String type, String value) 
	 * { 
	 * 		this.type = type; 
	 * 		this.value = value; 
	 * }
	 */

	public String getType()
	{
		return type;
	}

	public void setType(String type)
	{
		this.type = type;
	}

	public String getValue()
	{
		return value;
	}

	public void setValue(String value)
	{
		this.value = value;
	}
}
