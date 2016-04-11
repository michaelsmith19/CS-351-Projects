package com.putable.tilenet.matrix;

import java.awt.Color;
import java.awt.Point;

import com.putable.tilenet.Util.XMLTags.SetTag;
import com.putable.tilenet.factory.MatrixFactory;
import com.putable.tilenet.matrixelement.MatrixElementBuilder;
import com.putable.tilenet.matrixelement.TileNetMatrixElementBuilder;
import com.putable.tilenet.matrixelement.Token;

public class HomeMatrix extends Matrix {
	
	MatrixFactory thisMatrixUses;
	
	public HomeMatrix(MatrixFactory thisSpecificfactory){		
		this.thisMatrixUses = thisSpecificfactory;		
	}

	@Override
	void makeMatrix() {	
		this.setX(1);
		this.setY(1);
		resize();
		keyBindSet = thisMatrixUses.addKeyBindSet();
		grid = thisMatrixUses.addGrid();	
		setTokens();
	}
	
	public void setTokens(){
		/*TODO generate tokens inside matrix and send them to the
		 * client*/
		
		//making a token for the home matrix
		MatrixElementBuilder makeTokens = new TileNetMatrixElementBuilder();	
		com.putable.tilenet.matrixelement.MatrixElement token1 = makeTokens.orderElement("TOKEN");
		SetTag tok1 = new SetTag(token1.getObjid());
		tok1.setBgColor(Color.GRAY);
		tok1.setFgColor(Color.WHITE);
		tok1.setX(0);
		tok1.setY(0);
		tok1.setText("blah");
		tok1.setName("Welcome to TileNet");
		token1.setSetTag(tok1);
		grid.getLayout().put(new Point(0,0), (Token) token1);
	}
	

	

}
