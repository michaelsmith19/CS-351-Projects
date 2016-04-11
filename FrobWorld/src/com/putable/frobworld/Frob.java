package com.putable.frobworld;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;
import java.util.Random;

import com.putable.pqueue.PQueue;

public class Frob extends Being {

	private int BirthMass;
	private int BirthPercent;
	private int UpdatePeriod;
	private GenoType genes;

	/**
	 * constructs a frob with the given genes and point.
	 * 
	 * @param genes
	 *            The GenoType for this frob.
	 * @param today
	 *            The current simulation time, used for calculating nextUpdate.
	 * @param rand
	 *            seeded random used for first random nextUpdate
	 * @param p
	 *            the Point of this frob within the world.
	 */
	public Frob(GenoType genes, int today, Random rand, Point p) {
		this.setGenes(genes);
		this.BirthMass = genes.getBirthMass();
		this.BirthPercent = genes.getBirthPercent();
		this.UpdatePeriod = genes.getUpdatePeriod();
		this.setPoint(p);
		this.setType(Type.FROB);
		this.setNextUpdate(today + rand.nextInt(this.UpdatePeriod));
	}

	public int getBirthMass() {
		return BirthMass;
	}

	public void setBirthMass(int birthMass) {
		BirthMass = birthMass;
	}

	public int getBirthPercent() {
		return BirthPercent;
	}

	public void setBirthPercent(int birthPercent) {
		BirthPercent = birthPercent;
	}

	public int getUpdatePeriod() {
		return UpdatePeriod;
	}

	public void setUpdatePeriod(int updatePeriod) {
		UpdatePeriod = updatePeriod;
	}

	public GenoType getGenes() {
		return genes;
	}

	public void setGenes(GenoType genes) {
		this.genes = genes;
	}

	@Override
	public void execute(Thing[][] world, int simTime, Random rand, PQueue pq) {
		payMassTax(this, world, pq);
		if (world[this.getPoint().x][this.getPoint().y].getType() == Type.NOTHING) {
			return;
		}
		Thing[] neighbors = getNeighbors(this, world);
		Point oldCoords = this.getPoint();
		attemptMove(neighbors, rand, world, pq);

		if (this.getMass() == this.BirthMass) {
			Frob child = makeChild(oldCoords, rand, simTime);
			world[child.getPoint().x][child.getPoint().y] = child;
			pq.insert(child);
			if (this.getMass() == 0)
				pq.insert(this);
			Simulator.frobCount++;
		}

	}

	/**
	 * Attempts to make a child if this frob has moved and just eaten a grass
	 * (the only way to gain mass).
	 */
	private Frob makeChild(Point childPoint, Random rand, int today) {
		int childMass = this.getMass() * this.getBirthPercent() / 100;
		int nParentMass = this.getMass() - childMass;
		GenoType cGenes = this.getGenes();
		cGenes.mutate(rand);
		Frob child = new Frob(cGenes, today, rand, childPoint);
		child.setMass(childMass);
		this.setMass(nParentMass);
		return child;
	}

	/**
	 * Try to move in a direction based on its genes. Depending on what is in
	 * the chosen direction it will react differently according to spec.
	 */
	private void attemptMove(Thing[] neighbors, Random rand, Thing[][] world,
			PQueue pq) {

		int direction = this.getGenes().chooseDirection(neighbors, rand);

		switch (neighbors[direction].getType()) {
		case NOTHING:
			moveFrob(neighbors[direction], world);
			break;
		case GRASS:
			eatGrass((Grass) neighbors[direction], pq, world);
			break;
		case FROB:
			frobHit((Frob) neighbors[direction], world, pq);
			break;
		case ROCK:
			rockHit(world, pq);
			break;
		}
	}

	/**
	 * This just swaps the frob and a nothing. Should be used if the frob is
	 * successful at moving.
	 */
	private void moveFrob(Thing destNode, Thing[][] world) {
		Point destP = destNode.getPoint();
		Point from = this.getPoint();
		this.setPoint(destP);
		destNode.setPoint(from);
		world[destP.x][destP.y] = this;
		world[from.x][from.y] = destNode;
	}

	/**
	 * This kills the grass removes it from the PQueue and then moves the frob
	 * into the newly vacant square.
	 */
	private void eatGrass(Grass grass, PQueue pq, Thing[][] world) {
		int newMass = this.getMass() + grass.getMass();
		if (newMass > this.getBirthMass())
			newMass = this.getBirthMass();
		this.setMass(newMass);
		die(grass, world);
		pq.delete(grass);
		moveFrob(world[grass.getPoint().x][grass.getPoint().y], world);
	}

	/**
	 * Calculates the penalty to the frob being hit. Kills it if its mass is <=
	 * 0.
	 */
	private void frobHit(Frob frob, Thing[][] world, PQueue pq) {
		frob.setMass(frob.getMass() - FROB_HIT_PENALTY);
		if (frob.getMass() <= 0) {
			die(frob, world);
			pq.delete(frob);
		}
	}

	/**
	 * Damages the the frob and kills it if its mass <= 0
	 */
	private void rockHit(Thing[][] world, PQueue pq) {
		this.setMass(this.getMass() - ROCK_BUMP_PENALTY);
		if (this.getMass() <= 0) {
			die(this, world);
		}
	}

	@Override
	public void render(Graphics g, int sizeOfASpace) {
		g.setColor(Color.red);
		g.fillRect(this.getPoint().x * sizeOfASpace, this.getPoint().y
				* sizeOfASpace, sizeOfASpace, sizeOfASpace);
	}

}
