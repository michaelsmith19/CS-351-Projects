package com.putable.tilenet.blueprints;

import com.putable.tilenet.blueprints.Grid.GridType;

public abstract class GridBuilder {	

	protected abstract Grid makeGrid(GridType type);
	
	
	public Grid orderGrid(GridType type){
		Grid grid = makeGrid(type);
		
		//Working on generic grid
		grid.placeParts();
		grid.setType(type);
		
		//This gives default sizes, since SetTag has no values.
		switch(type){
		case HOME:
			grid.m.tag.setX(5);
			grid.m.tag.setY(5);
			break;
		case SPLASH:
			break;
		default:
			break;

		}
		
		//Populate Layout
		grid.layout.init();
		
		
		return grid;
	}
}
