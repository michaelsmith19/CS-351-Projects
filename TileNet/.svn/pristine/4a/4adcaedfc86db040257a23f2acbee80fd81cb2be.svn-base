package com.putable.tilenet.matrix;

import com.putable.tilenet.factory.MatrixFactory;

public class SplashMatrix extends Matrix {
MatrixFactory thisMatrixUses;
	
	public SplashMatrix(MatrixFactory thisSpecificfactory){
		
		this.thisMatrixUses = thisSpecificfactory;
		
	}

	@Override
	void makeMatrix() {
		
		keyBindSet = thisMatrixUses.addKeyBindSet();
		grid = thisMatrixUses.addGrid();
		
	}
}
