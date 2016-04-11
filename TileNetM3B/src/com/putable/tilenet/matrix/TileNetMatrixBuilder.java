package com.putable.tilenet.matrix;

import com.putable.tilenet.factory.EmptyMatrixFactory;
import com.putable.tilenet.factory.HomeMatrixFactory;
import com.putable.tilenet.factory.MatrixFactory;
import com.putable.tilenet.factory.SplashMatrixFactory;

public class TileNetMatrixBuilder extends MatrixBuilder{	
	
	@Override
	protected Matrix makeMatrix(String type) {
		Matrix mat = null;
		
		if(type.compareTo("EMPTY") == 0){
			//TODO change this
			MatrixFactory matrixFactory = new EmptyMatrixFactory();
			mat = new EmptyMatrix(matrixFactory);
			mat.setName("Empty");
		}
		
		if(type.compareTo("HOME") == 0){
			
			MatrixFactory matrixFactory = new HomeMatrixFactory();
			mat = new HomeMatrix(matrixFactory);
			mat.setName("HOME MATRIX ABSTRACT FACTORY MADE!");
			
		} else if (type.compareTo("SPLASH") == 0){
			
			MatrixFactory matrixFactory = new SplashMatrixFactory();
			mat = new SplashMatrix(matrixFactory);
			mat.setName("SPLASH MATRIX ABSTRACT FACTORY MADE!");
			
			
		}		
		return mat;
	}

	

}
