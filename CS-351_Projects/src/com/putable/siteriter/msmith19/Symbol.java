package com.putable.siteriter.msmith19;

import java.util.ArrayList;

public class Symbol {

	private String name;
	private boolean isStart = false;
	private String selector = null;
	
	public ArrayList<ArrayList<Token>> choices = new ArrayList<ArrayList<Token>>();

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public boolean isStart() {
		return isStart;
	}

	public void setStart(boolean isStart) {
		this.isStart = isStart;
	}

	public String getSelector() {
		return selector;
	}

	public void setSelector(String selector) {
		this.selector = selector;
	}

	public ArrayList<ArrayList<Token>> getChoices() {
		return choices;
	}

	public void setChoices(ArrayList<ArrayList<Token>> choices) {
		this.choices = choices;
	}
	
	@Override
	public String toString() {
		
		return "The name of this symbol is: " + name;
		
	}
	
	
}
