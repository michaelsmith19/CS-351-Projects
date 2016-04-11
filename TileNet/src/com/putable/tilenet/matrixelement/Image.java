package com.putable.tilenet.matrixelement;

import com.putable.tilenet.blueprints.Element;
import com.putable.tilenet.blueprints.ElementFactory;

public class Image extends Element{
	ElementFactory thisUses;
	
	public Image(ElementFactory thisSpecificFactory){
		thisUses = thisSpecificFactory;
	}
		
	@Override
	protected void placeParts() {
		tag = thisUses.addSetTag();
		type = thisUses.addElemType();
	}

}
