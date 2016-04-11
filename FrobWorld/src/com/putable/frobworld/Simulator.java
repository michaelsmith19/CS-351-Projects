package com.putable.frobworld;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Random;

import javax.swing.JPanel;
import javax.swing.Timer;

import com.putable.frobworld.Thing.Type;
import com.putable.pqueue.PQueue;

/**
 * This actually runs the simulation. It holds the current day as well as the
 * PQueue and actually calls the {@link Being} execute method to run the
 * simulation.
 * 
 * @author michaelsmith
 * 
 */
public class Simulator implements ConstantValues, ActionListener {

	private Random rand;
	private PQueue eventQueue;
	private Thing[][] worldArray;
	private int today = 0;
	private JPanel animate;
	public static int frobCount = INIT_FROBS;

	/**
	 * constructs a simulator with an initial world. The PQueue holding the
	 * events to run and a random number generator that was seeded by
	 * {@link FrobWorld}.
	 * 
	 * @param world
	 *            the initial world.
	 * @param pq
	 *            the Priority Queue holding the Being objects to be executed
	 * @param rand
	 *            The seeded random number generator.
	 */
	public Simulator(Thing[][] world, PQueue pq, Random rand, JPanel animate) {
		this.eventQueue = pq;
		this.worldArray = world;
		this.rand = rand;
		this.animate = animate;
		new Timer(1, this).start();
	}

	/**
	 * constructs a Simulator without the ability to animate itself on a JPanel
	 */
	public Simulator(Thing[][] world, PQueue pq, Random rand) {
		this.eventQueue = pq;
		this.worldArray = world;
		this.rand = rand;
		Simulator.frobCount = INIT_FROBS;
	}

	/**
	 * Takes a PQAble Being object and calls its execute method. It then adds it
	 * back to the PQueue with a new nextUpdate value.
	 * 
	 * @param event
	 *            the Being to be executed.
	 */
	private synchronized void runDay() {
		Being event = (Being) eventQueue.top();
		while (event.getNextUpdate() == today) {
			event = (Being) eventQueue.remove();
			event.execute(worldArray, today, rand, eventQueue);
			event.setNextUpdate(today + event.getUpdatePeriod());

			if (event.getMass() > 0) {
				eventQueue.insert(event);
			}
			event = (Being) eventQueue.top();
		}
		today++;
	}

	/**
	 * Runs the simulation when in batch mode. This has no connection to a
	 * JPanel and does not animate. It just runs the simulation and prints out
	 * some data after every run is finished.
	 */
	public void runSimulation() {

		while (today <= MAX_SIMULATION_LENGTH && frobCount != 0) {
			runDay();
		}

		if (frobCount == 0) {
			System.out.println("   " + today);
			return;
		} else if (today >= MAX_SIMULATION_LENGTH && frobCount > 0) {
			getStatistics();
			return;
		}
	}

	/**
	 * analyzes the current world state and prints out some data based on the
	 * remaining frobs. This data is mainly the standard deviation of the frobs
	 * update periods.
	 */
	private void getStatistics() {
		int sumOfMetabolism = 0;
		int[] metabolisms = new int[frobCount];
		int l = 0;
		for (int i = 0; i < WORLD_HEIGHT; i++) {
			for (int j = 0; j < WORLD_WIDTH; j++) {
				if (worldArray[j][i].getType() == Type.FROB) {
					Frob frob = (Frob) worldArray[j][i];
					sumOfMetabolism += frob.getUpdatePeriod();
					if (l < frobCount) {
						metabolisms[l] = frob.getUpdatePeriod();
						l++;
					}
				}
			}
		}
		int mean = sumOfMetabolism / frobCount;
		calculateStandardDeviation(mean, metabolisms);
	}

	private void calculateStandardDeviation(int mean, int[] metabolisms) {
		int sumOfSquaredDiff = 0;
		for (int i = 0; i < metabolisms.length; i++) {
			sumOfSquaredDiff += Math.pow(metabolisms[i] - mean, 2);
		}
		int variance = sumOfSquaredDiff / metabolisms.length;
		int standardDeviation = (int) Math.sqrt(variance);
		System.out.println("  " + frobCount + "   " + mean + "   "
				+ standardDeviation);

	}

	@Override
	public void actionPerformed(ActionEvent arg0) {
		if (today <= MAX_SIMULATION_LENGTH && frobCount != 0) {
			runDay();
		} else {
			getStatistics();
			this.resetWorld();
		}
		animate.repaint();
	}

	/**
	 * Creates a new {@link FrobWorld} and then copies all of the new world
	 * information into the {@link Simulator}.
	 */
	private void resetWorld() {
		FrobWorld newWorld = new FrobWorld();
		newWorld.initWorld((int) System.nanoTime());
		this.worldArray = newWorld.getWorldArray();
		this.eventQueue = newWorld.getEventQueue();
		this.rand = newWorld.getRand();
		this.today = 0;
		FrobWorldPanel panel = (FrobWorldPanel) animate;
		panel.setFrobWorld(newWorld);
	}
}
