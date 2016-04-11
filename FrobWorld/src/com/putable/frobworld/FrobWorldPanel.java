package com.putable.frobworld;

import java.awt.Graphics;

import javax.swing.JPanel;

public class FrobWorldPanel extends JPanel {
	/** Randomly generated serial number */
	private static final long serialVersionUID = -5102064231029076779L;

	private FrobWorld frobWorld;

	public FrobWorld getFrobWorld() {
		return frobWorld;
	}

	public void setFrobWorld(FrobWorld frobWorld) {
		this.frobWorld = frobWorld;
	}

	private int sizeOfASpace;

	public FrobWorldPanel(FrobWorld world, int sizeOfASpace) {
		this.frobWorld = world;
		this.sizeOfASpace = sizeOfASpace;
	}

	@Override
	public void paintComponent(Graphics g) {
		frobWorld.renderWorld(g, sizeOfASpace);
	}
}
