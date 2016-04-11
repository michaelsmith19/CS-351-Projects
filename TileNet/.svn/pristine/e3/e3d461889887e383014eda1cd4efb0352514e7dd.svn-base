package com.putable.tilenet.blueprints;

import com.putable.tilenet.blueprints.Grid.GridType;
import com.putable.tilenet.grid.HomeGrid;
import com.putable.tilenet.grid.SplashGrid;

public class TileNetGridBuilder extends GridBuilder{	

	@Override
	protected Grid makeGrid(GridType type) {
		Grid grid = null;		

		if(type == GridType.HOME){
			GridFactory factory = new HomeGridFactory();
			grid = (HomeGrid)  new HomeGrid(factory);
			grid.setName("HOME MATRIX");			
			
		} else if (type == GridType.SPLASH){			
			GridFactory factory = new SplashGridFactory();
			grid = new SplashGrid(factory);
			grid.setName("SPLASH MATRIX");
			
		}		
		return grid;
	}
}
