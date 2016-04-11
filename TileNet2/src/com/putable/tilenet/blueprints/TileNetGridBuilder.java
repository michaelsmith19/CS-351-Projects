package com.putable.tilenet.blueprints;

import com.putable.tilenet.blueprints.Grid.GridType;
import com.putable.tilenet.grid.HomeGrid;
import com.putable.tilenet.grid.PairPanickGrid;
import com.putable.tilenet.grid.SplashGrid;
import com.putable.tilenet.grid.TripleTriadGrid;

public class TileNetGridBuilder extends GridBuilder {

	@Override
	protected Grid makeGrid(GridType type) {
		Grid grid = null;

		if (type == GridType.HOME) {
			GridFactory factory = new HomeGridFactory();
			grid = (HomeGrid) new HomeGrid(factory);
			grid.setName("HOME MATRIX");

		} else if (type == GridType.SPLASH) {
			GridFactory factory = new SplashGridFactory();
			grid = new SplashGrid(factory);
			grid.setName("SPLASH MATRIX");

		} else if (type == GridType.PAIRPANICK) {
			GridFactory factory = new PairPanickGridFactory();
			grid = new PairPanickGrid(factory);
			grid.setName("PAIR PANICK MATRIX");
		} else if (type == GridType.TRIPLETRIAD) {
			GridFactory factory = new TripleTriadGridFactory();
			grid = new TripleTriadGrid(factory);
			grid.setName("TRIPLE TRIAD MATRIX");
		}

		return grid;
	}
}
