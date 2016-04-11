package com.putable.tilenet.grid;

import java.awt.Color;
import java.io.File;

import com.putable.tilenet.Util.Common;
import com.putable.tilenet.Util.XMLTags.SetTag;
import com.putable.tilenet.blueprints.Grid;
import com.putable.tilenet.blueprints.GridFactory;

/**
 * @author The Class Cast Exceptions
 * 
 * This contains the token information for the HomeMatrix
 * that is used to be displayed
 *
 */
public class HomeGrid extends Grid {
	GridFactory factory;

	public HomeGrid(GridFactory thisSpecificfactory) {
		this.factory = thisSpecificfactory;
	}

	@Override
	protected void placeParts() {
		this.layout = factory.addLayout();
	}

	@Override
	public void addDefaultTokens() {
		// These things here are REQUIRED
		SetTag matrix = new SetTag("m" + Common.getID());
		matrix.setName("Home Matrix");
		matrix.setX(2);
		matrix.setY(2);
		layout.update(matrix);
		
		
		SetTag welcomeImage = new SetTag("i" + Common.getID());
		welcomeImage.setText(Common.toHexData(new File("resource/home/TileNet1.jpg")));
		layout.update(welcomeImage);
		
		SetTag welcomeTo = new SetTag("t" + Common.getID());
		welcomeTo.setX(0);
		welcomeTo.setY(0);
		welcomeTo.setEnergy(-1);
		welcomeTo.setImage(welcomeImage.getObjid());
		layout.update(welcomeTo);
		
		
		SetTag tileNetImg = new SetTag("i" + Common.getID());
		tileNetImg.setText(Common.toHexData(new File("resource/home/TileNet2.jpg")));
		layout.update(tileNetImg);
		
		SetTag tileNet = new SetTag("t" + Common.getID());
		tileNet.setX(0);
		tileNet.setY(1);
		tileNet.setImage(tileNetImg.getObjid());
		tileNet.setEnergy(-1);
		layout.update(tileNet);
		

		SetTag i = new SetTag("i" + Common.getID());
		i.setText(Common.toHexData(new File("resource/home/PairPanick.jpg")));
		layout.update(i);
		
		// Add 1 to top just to see the corner
		SetTag corner = new SetTag("t" + Common.getID());
		corner.setX(1);
		corner.setY(0);
		corner.setFgColor(Color.BLACK);
		corner.setBgColor(Color.GREEN);
		corner.setImage(i.getObjid());
		corner.setName("PairPanick");
		layout.update(corner);

		
		SetTag i2 = new SetTag("i" + Common.getID());
		i2.setText(Common.toHexData(new File("resource/home/TripleTriad.jpg")));
		layout.update(i2);
		// Add another for tripletriad
		SetTag tripleTriad = new SetTag("t" + Common.getID());
		tripleTriad.setX(1);
		tripleTriad.setY(1);
		tripleTriad.setFgColor(Color.BLACK);
		tripleTriad.setBgColor(Color.GREEN);
		tripleTriad.setImage(i2.getObjid());
		tripleTriad.setName("Triple Triad");
		layout.update(tripleTriad);
	}

}
