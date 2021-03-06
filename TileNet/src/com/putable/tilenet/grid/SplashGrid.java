package com.putable.tilenet.grid;

import java.awt.Color;
import java.io.File;
import com.putable.tilenet.Util.Common;
import com.putable.tilenet.Util.XMLTags.SetTag;
import com.putable.tilenet.blueprints.Grid;
import com.putable.tilenet.blueprints.GridFactory;

public class SplashGrid extends Grid {
	GridFactory factory;

	public SplashGrid(GridFactory thisSpecificfactory) {
		this.factory = thisSpecificfactory;
	}

	@Override
	protected void placeParts() {
		this.layout = factory.addLayout();
	}

	@Override
	public void addDefaultTokens() {
		//Make our splash
		SetTag matrix = new SetTag("m" + Common.getID());
		matrix.setName("Splash");
		matrix.setX(5);
		matrix.setY(5);	
		layout.update(matrix);
		
		for(int i = 1 ; i < 5; i ++){
			SetTag s = new SetTag("t" + Common.getID());
			s.setX(i);
			s.setY(i);
			s.setFgColor(Color.BLUE);
			layout.update(s);			
		}		
		
		SetTag i = new SetTag("i10");
		i.setX(400);
		i.setY(225);
		i.setText(Common.toHexData(new File("resource/splash/1")));
		layout.update(i);
		
		SetTag s = new SetTag("t" + Common.getID());
		s.setX(0);
		s.setY(0);
		s.setImage("i10");
		layout.update(s);
		


	}
}
