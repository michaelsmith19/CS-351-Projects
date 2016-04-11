package com.putable.tilenet.matrixelement;



public abstract class MatrixElementBuilder {
	
	protected abstract MatrixElement makeElement(String type);
	
	public MatrixElement orderElement(String elementType){
		MatrixElement ele = makeElement(elementType);
		
		ele.makeElement();
		
		
		
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
