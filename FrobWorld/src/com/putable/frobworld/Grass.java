package com.putable.frobworld;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;
import java.util.Random;

import com.putable.pqueue.PQueue;

/**
 * A concrete {@link Being} which when executed performs all behavior that a
 * {@link Grass} object should by the spec.
 * 
 * @author michaelsmith
 * 
 */
public class Grass extends Being {

	/**
	 * a constructor that takes an int and sets that as its execution time for
	 * priority within the queue.
	 * 
	 * @param dayToExe
	 *            an integer representing the day it should execute.
	 */
	public Grass(int dayToExe, Type type, Point p) {
		this.setNextUpdate(dayToExe);
		this.setMass(GRASS_GENESIS_MASS);
		this.setUpdatePeriod(GRASS_INITIAL_UPDATE_PERIOD);
		this.setType(type);
		this.setPoint(p);
	}

	/**
	 * This will attempt to split the parent Grass. If it fails it will return
	 * null if it succeeds it will return the child it created.
	 * 
	 * @param neighbors
	 *            the {@link Thing} objects in all four directions 0 = north, 1
	 *            = east, 2 = south, 3 = west.
	 * @param rand
	 *            an random number generator.
	 * @param today
	 *            the current day of excecution.
	 * @return {@link Grass} or null.
	 */
	private Grass split(Thing[] neighbors, Random rand, int today) {
		int numGrass = 0;
		int numEmpty = 0;
		for (int i = 0; i < neighbors.length; i++) {
			switch (neighbors[i].getType()) {
			case GRASS:
				numGrass++;
				break;
			case ROCK:
				break;
			case FROB:
				break;
			case NOTHING:
				numEmpty++;
				break;
			}
		}
		if (numGrass >= GRASS_CROWD_LIMIT || numEmpty == 0) {
			this.setMass(GRASS_BIRTH_MASS);
			doubleUpdatePeriod(this);
			return null;
		}
		Grass child = createChild(rand, today, neighbors);
		return child;
	}

	/**
	 * guesses random directions until it finds a valid one. Then returns a
	 * Point with the valid coords.
	 * 
	 * @param rand
	 *            to choose a random direction.
	 * @param neighbors
	 *            the 4 possible spaces.
	 * @return Point that the child will fill.
	 */
	private Point chooseDirection(Random rand, Thing[] neighbors) {

		int chooseRand = rand.nextInt(4);

		while (neighbors[chooseRand].getType() != Type.NOTHING) {
			chooseRand = rand.nextInt(4);
		}

		return getChildsCoords(chooseRand);
	}

	/**
	 * Use the int direction to make the point north, east, south, west
	 * 
	 * @param direction
	 * @return
	 */
	private Point getChildsCoords(int direction) {
		Point p = new Point();
		switch (direction) {
		case 0:
			p = this.north();
			break;
		case 1:
			p = this.south();
			break;
		case 2:
			p = this.east();
			break;
		case 3:
			p = this.west();
			break;
		}
		return p;
	}

	/**
	 * Creates a new Grass that is the child of the split.
	 * 
	 * @param rand
	 *            used for randomizing first update day.
	 * @param today
	 *            the current time in days of the simulation.
	 * @param chosen
	 *            the number representing the direction
	 * @return The Grass object child made from this split.
	 */
	private Grass createChild(Random rand, int today, Thing[] neighbors) {

		int childMass = this.getMass() * GRASS_BIRTH_PERCENT / 100;
		int nParentMass = this.getMass() - childMass;

		Grass child = new Grass(today
				+ rand.nextInt(GRASS_INITIAL_UPDATE_PERIOD), Type.GRASS,
				chooseDirection(rand, neighbors));
		child.setMass(childMass);
		this.setMass(nParentMass);
		return child;
	}

	/**
	 * doubles the update period of a {@link Grass} object up to
	 * GRASS_MAX_UPDATE_PERIOD.
	 * 
	 * @param grass
	 *            the grass whose update period will change
	 */
	private void doubleUpdatePeriod(Being grass) {
		int updatePeriod = grass.getUpdatePeriod();
		int newUPeriod = updatePeriod * 2;
		if (updatePeriod == GRASS_MAX_UPDATE_PERIOD) {
			return;
		} else if (newUPeriod > GRASS_MAX_UPDATE_PERIOD) {
			grass.setUpdatePeriod(GRASS_MAX_UPDATE_PERIOD);
		} else
			grass.setUpdatePeriod(newUPeriod);
	}

	@Override
	public void execute(Thing[][] worldArray, int simTime, Random rand,
			PQueue eventQueue) {
		// 'Pay' the mass tax delete if dies.
		payMassTax(this, worldArray, eventQueue);

		if (this.getMass() >= GRASS_BIRTH_MASS) {
			Thing[] neighbors = getNeighbors(this, worldArray);
			Grass child = split(neighbors, rand, simTime);
			if (child != null) {
				worldArray[child.getPoint().x][child.getPoint().y] = child;
				eventQueue.insert(child);
			}
		}
	}

	@Override
	public String toString() {
		return "x coord " + this.getPoint().x + " y coord " + this.getPoint().y;
	}

	@Override
	public void render(Graphics g, int sizeOfASpace) {
		g.setColor(Color.green);
		g.fillRect(this.getPoint().x * sizeOfASpace, this.getPoint().y
				* sizeOfASpace, sizeOfASpace, sizeOfASpace);

	}
}
