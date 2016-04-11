package com.putable.tilenet.matrixelement;

import com.putable.tilenet.blueprints.Element;
import com.putable.tilenet.blueprints.ElementFactory;

public class Agent extends Element {
	// BuilderFactory
	ElementFactory thisUses;

	// Used by Factory
	public Agent(ElementFactory thisSpecificfactory) {
		this.thisUses = thisSpecificfactory;
	}

	@Override
	protected void placeParts() {
		tag = thisUses.addSetTag();
		type = thisUses.addElemType();		
	}	
	
	@Override
	public String toString(){
		return "AGENT OBJID : " + this.getObjid() + " IS ASSOCIATED WITH THE NAME : " + this.getName();
	}
}
