package com.putable.siteriter.msmith19;

/**
 * This class holds the information necessary for 
 * any single Token. These are its Value which is the 
 * actual input and its Type which is the kind of 
 * unit that this Token is.
 * @author michaelsmith
 *
 */

public class Token {
	
	private String type;
	private String value;
	
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getValue() {
		return value;
	}
	public void setValue(String value) {
		this.value = value;
	}
}
