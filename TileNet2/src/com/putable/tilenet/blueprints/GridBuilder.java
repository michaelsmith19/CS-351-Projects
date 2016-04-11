package com.putable.tilenet.blueprints;

import com.putable.tilenet.blueprints.Grid.GridType;

public abstract class GridBuilder {	

	protected abstract Grid makeGrid(GridType type);
	
	
	public Grid orderGrid(GridType type){
		Grid grid = makeGrid(type);
		
		//Working on generic grid
		grid.placeParts();
		grid.setType(type);
		
		return grid;
	}
}
