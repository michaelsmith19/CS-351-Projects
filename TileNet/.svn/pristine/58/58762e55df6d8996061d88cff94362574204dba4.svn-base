package com.putable.tilenet.matrix;


public abstract class MatrixBuilder {
	
	//All factories must create a Matrix
	protected abstract Matrix makeMatrix(String type);
	
	
	public Matrix orderMatrix(String typeOfMatrix){
		Matrix mat = makeMatrix(typeOfMatrix);
		
		mat.makeMatrix();
		
		mat.setId();
		
		/*
		 * Do other stuff like
		 * 
		 * 				mat.verify()
		 * 
		 */
		
		return mat;
	}
}
