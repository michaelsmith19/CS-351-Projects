package com.putable.frobworld;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;

import com.putable.pqueue.PQAble;

/**
 * A Type of {@link Thing} that does not have any events for itself. It only
 * serves as an obstacle to the growth of a {@link Grass} or the movement of a
 * {@link Frob}.
 * 
 * @author michaelsmith
 * 
 */
public class Rock extends Thing {

	public Rock(Type type, Point p) {
		this.setType(type);
		this.setPoint(p);
	}

	@Override
	public int compareTo(PQAble o) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void render(Graphics g, int sizeOfASpace) {
		g.setColor(Color.gray);
		g.fillRect(this.getPoint().x * sizeOfASpace, this.getPoint().y
				* sizeOfASpace, sizeOfASpace, sizeOfASpace);
	}

	@Override
	public String toString() {
		return "R";
	}

}
