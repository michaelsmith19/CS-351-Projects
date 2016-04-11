package com.putable.frobworld;

import java.awt.Point;
import java.util.Random;

import com.putable.pqueue.PQAble;
import com.putable.pqueue.PQueue;

/**
 * This is the main PQAble class. Only {@link Being} objects will be accepted
 * into the PQueue.
 * 
 * @author michaelsmith
 */

public abstract class Being extends Thing {

	private int nextUpdate;
	private int mass;
	private int updatePeriod;

	public int getUpdatePeriod() {
		return updatePeriod;
	}

	public void setUpdatePeriod(int updatePeriod) {
		this.updatePeriod = updatePeriod;
	}

	public int getNextUpdate() {
		return nextUpdate;
	}

	public void setNextUpdate(int dayToExe) {
		this.nextUpdate = dayToExe;
	}

	public int getMass() {
		return mass;
	}

	public void setMass(int newMass) {
		this.mass = newMass;
	}

	/**
	 * Calculate the mass tax and reset mass. If mass becomes 0 it will die.
	 * 
	 * @param worldArray
	 *            if it dies it will be deleted from the world
	 */
	public void payMassTax(Being event, Thing[][] worldArray, PQueue pq) {
		int mass = event.getMass();
		int newMass;
		if (event.getType() == Type.GRASS) {
			newMass = (mass -= GRASS_MASS_TAX_MILLS * event.getUpdatePeriod()
					/ 1000 + GRASS_FIXED_OVERHEAD);
		} else {
			event = (Frob) event;
			newMass = (mass -= FROB_MASS_TAX_MILLS * event.getUpdatePeriod()
					/ 1000 + FROB_FIXED_OVERHEAD);
		}

		if (newMass <= 0) {
			die(event, worldArray);
		}

		event.setMass(newMass);
	}

	/**
	 * If it has a mass of 0 or less this will be called.
	 * 
	 * @param world
	 *            world array that it will be deleted from
	 */
	public void die(Being event, Thing[][] world) {
		Point p = event.getPoint();
		if (event.getType() == Type.FROB)
			Simulator.frobCount--;
		Nothing dead = new Nothing(Type.NOTHING, p);
		world[p.x][p.y] = dead;
	}

	/**
	 * This method is the contract for concrete methods that are Beaings and
	 * will be entering the PQueue. It provides the functionality for the PQAble
	 * objects to carry out there event
	 */
	public abstract void execute(Thing[][] world, int simTime, Random rand,
			PQueue pq);

	@Override
	public int compareTo(PQAble o) {
		Being comparator = (Being) o;
		if (this.nextUpdate == comparator.nextUpdate)
			return 0;
		else if (this.nextUpdate > comparator.nextUpdate)
			return 1;

		return -1;
	}

}
