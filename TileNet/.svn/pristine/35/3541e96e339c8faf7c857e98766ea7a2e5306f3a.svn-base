package com.putable.tilenet.matrix;

import com.putable.tilenet.factory.MatrixFactory;
import com.putable.tilenet.grid.Grid;

public class EmptyMatrix extends Matrix {

	MatrixFactory thisMatrixUses;

	public EmptyMatrix(MatrixFactory thisSpecificfactory) {
		this.thisMatrixUses = thisSpecificfactory;
	}

	@Override
	void makeMatrix() {
		this.setX(1);
		this.setY(1);
		resize();
		keyBindSet = thisMatrixUses.addKeyBindSet();
		grid = thisMatrixUses.addGrid();
	}

	public Grid getGrid() {
		return grid;
	}

}
