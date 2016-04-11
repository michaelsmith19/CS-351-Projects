package com.putable.tilenet.matrixelement;

import com.putable.tilenet.blueprints.Element;
import com.putable.tilenet.blueprints.ElementFactory;

public class Token extends Element {
	ElementFactory thisUses;

	public Token(ElementFactory thisSpecificfactory) {
		this.thisUses = thisSpecificfactory;
	}	

	@Override
	protected void placeParts() {
		tag = thisUses.addSetTag();
		type = thisUses.addElemType();
	}	
}
