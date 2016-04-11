package com.putable.tilenet.matrixelement;

import com.putable.tilenet.factory.AgentFactory;
import com.putable.tilenet.factory.ImageFactory;
import com.putable.tilenet.factory.MatrixElementFactory;
import com.putable.tilenet.factory.TokenFactory;


public class TileNetMatrixElementBuilder extends MatrixElementBuilder {

	@Override
	protected MatrixElement makeElement(String type) {
		MatrixElement ele = null;

		if(type.compareTo("AGENT") == 0){

			MatrixElementFactory matrixElementFactory = new AgentFactory();
			ele = new Agent(matrixElementFactory);

		} else if (type.compareTo("IMAGE") == 0){			

			MatrixElementFactory matrixElementFactory = new ImageFactory();
			ele = new Image(matrixElementFactory);

		} else if (type.compareTo("TOKEN") == 0){			

			MatrixElementFactory matrixElementFactory = new TokenFactory();
			ele = new Token(matrixElementFactory);

		} else if (type.compareTo("MATRIX") == 0){
			/*

			MatrixElementFactory matrixElementFactory = new TokenFactory();
			ele = new Token(matrixElementFactory);
			*/
		}

		return ele;
	}



}
