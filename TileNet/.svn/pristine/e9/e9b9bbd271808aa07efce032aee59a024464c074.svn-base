package com.putable.tilenet.matrixelement;

import com.putable.tilenet.factory.MatrixElementFactory;

public class Agent extends MatrixElement {
	// BuilderFactory
	MatrixElementFactory thisUses;

	// Used by Factory
	public Agent(MatrixElementFactory thisSpecificfactory) {
		this.thisUses = thisSpecificfactory;
	}

	@Override
	void makeElement() {
		tag = thisUses.addSetTag();
		type = thisUses.addElemType();		
	}	
}
