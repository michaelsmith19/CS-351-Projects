package com.putable.frobworld;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;

import com.putable.pqueue.PQAble;

/**
 * A place holder that represents the absence of a {@link Thing} such as a Rock
 * a Grass or a Frob. Its toString() will print only a space character.
 * 
 * @author michaelsmith
 * 
 */
public class Nothing extends Thing {

	public Nothing(Type type, Point p) {
		this.setPoint(p);
		this.setType(type);
	}

	@Override
	public int compareTo(PQAble o) {
		return 0;
	}

	@Override
	public String toString() {
		return " ";
	}

	@Override
	public void render(Graphics g, int sizeOfASpace) {
		g.setColor(Color.white);
		g.fillRect(this.getPoint().x * sizeOfASpace, this.getPoint().y
				* sizeOfASpace, sizeOfASpace, sizeOfASpace);

	}

}
