package com.putable.frobworld;

import java.awt.Graphics;
import java.awt.Point;

import com.putable.pqueue.AbstractPQAble;
import com.putable.pqueue.PQAble;

/**
 * The highest level class that is PQAble. The entire worldArray should be made
 * up of {@link Thing} objects for simplicity. This includes the place holder of
 * {@link Nothing} objects.
 * 
 * @author michaelsmith
 * 
 */
public abstract class Thing extends AbstractPQAble implements ConstantValues {
	/**
	 * An enum to hold the type of the {@link PQAble} object. This only can hold
	 * values of the concrete types of {@link Thing} namely Rock,Grass ,Frob,
	 * and Nothing
	 * 
	 * @author michaelsmith
	 * 
	 */
	public enum Type {
		ROCK, GRASS, FROB, NOTHING;
	}

	/** Holds the coordinates for this {@link Thing} in the world array */
	private Point point = new Point();
	/** Holds the enum {@link Type} of this Thing */
	private Type type;

	public Point getPoint() {
		return point;
	}

	public void setPoint(Point point) {
		this.point = point;
	}

	public Type getType() {
		return type;
	}

	/** Sets the enum type of this concrete Thing */
	public void setType(Type type) {
		this.type = type;
	}

	/** @return The Point north of this node */
	public Point north() {
		Point p = new Point(this.point.x, this.point.y - 1);
		return p;
	}

	/** @return The Point east of this node */
	public Point east() {
		Point p = new Point(this.point.x + 1, this.point.y);
		return p;
	}

	/** @return The Point south of this node */
	public Point south() {
		Point p = new Point(this.point.x, this.point.y + 1);
		return p;
	}

	/** @return The Point west of this node */
	public Point west() {
		Point p = new Point(this.point.x - 1, this.point.y);
		return p;
	}

	/**
	 * Given a Being to be executed this will return the 4 nodes in its
	 * neighborhood.
	 * 
	 * @param event
	 *            the Thing whose neighbors are being found.
	 * @param worldArray
	 *            the current world state.
	 * @return A Thing[] containing the 4 neighbors with 0 being north 1 being
	 *         east 2 being south and 3 being west.
	 */
	public Thing[] getNeighbors(Thing event, Thing[][] worldArray) {
		Point p = event.getPoint();
		int x = p.x;
		int y = p.y;
		/**
		 * holds all neighbors of a given node 0 is north 1 is south 2 is east 3
		 * is west
		 */
		Thing[] neighbors = new Thing[4];
		neighbors[0] = worldArray[x][y - 1];
		neighbors[1] = worldArray[x][y + 1];
		neighbors[2] = worldArray[x + 1][y];
		neighbors[3] = worldArray[x - 1][y];
		return neighbors;
	}

	public abstract void render(Graphics g, int sizeOfASpace);
}
