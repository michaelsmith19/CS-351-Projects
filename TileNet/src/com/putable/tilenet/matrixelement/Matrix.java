package com.putable.tilenet.matrixelement;

import com.putable.tilenet.blueprints.Element;
import com.putable.tilenet.blueprints.ElementFactory;

public class Matrix extends Element {
	ElementFactory thisUses;

	public Matrix(ElementFactory thisSpecificfactory) {
		this.thisUses = thisSpecificfactory;
	}

	@Override
	protected void placeParts() {
		this.type = thisUses.addElemType();
		this.tag = thisUses.addSetTag();
	}

}
