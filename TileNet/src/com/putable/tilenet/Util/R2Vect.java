package com.putable.tilenet.Util;

/**
 * A simple vector in R^2.
 * 
 * @author Zach
 * 
 */
public final class R2Vect {
	public int x;
	public int y;

	public R2Vect(R2Vect v) {
		this.x = v.x;
		this.y = v.y;
	}

	public R2Vect(int x, int y) {
		this.x = x;
		this.y = y;
	}

	public R2Vect add(R2Vect v) {
		return new R2Vect(x + v.x, y + v.y);
	}

	public R2Vect subtract(R2Vect v) {
		return new R2Vect(x - v.x, y - v.y);
	}

	public int magnitude() {
		return (int) Math.sqrt((Math.pow(x, 2)) + Math.pow(y, 2));
	}

	public R2Vect moveUp() {
		return new R2Vect(x, y - 1);
	}

	public R2Vect moveRight() {
		return new R2Vect(x + 1, y);
	}

	public R2Vect moveDown() {
		return new R2Vect(x, y + 1);
	}

	public R2Vect moveLeft() {
		return new R2Vect(x - 1, y);
	}

	public String toString() {
		return "(" + x + "," + y + ")";
	}
	
	public int hashCode() {
		return (43 * (43 + x) + y);
	}
	
	public boolean equals(Object o) {
		boolean result = false;
		if (o instanceof R2Vect) {
			R2Vect u = (R2Vect) o;
			result = (this.x == u.x && this.y == u.y);
		}
		return result;
	}
}
