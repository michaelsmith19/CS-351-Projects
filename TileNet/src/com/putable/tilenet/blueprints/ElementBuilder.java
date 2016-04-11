package com.putable.tilenet.blueprints;

import com.putable.tilenet.blueprints.Element.ElemType;

public abstract class ElementBuilder {
	
	protected abstract Element makeElement(ElemType type);
	
	public Element orderElement(ElemType type){
		Element ele = makeElement(type);
		
		ele.placeParts();
		
		//Give unique ID
		ele.setId();			

		/*
		 * Other stuff
		 * 
		 * ele.verify()
		 * 
		 * Etc..
		 */
		
		return ele;
		
	}

}
