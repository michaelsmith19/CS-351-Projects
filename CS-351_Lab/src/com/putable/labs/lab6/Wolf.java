package com.putable.labs.lab6;

/**
 * This is a concrete {@code Wolf} class. An instance of this class is used
 * specifically to be compared to other living things by fact of who would win
 * in a fight. A fight outcome is based on the attributes of a {@code Wolf}
 * including weight and what they consume for food (i.e. meat, plants or both).
 * 
 * @author BKey
 * 
 */
public class Wolf extends Animal{
	
	/**
	 * The constructor of a {@code Wolf}.
	 * 
	 * @param weight
	 *            the {@code double} value that is the weight in lbs of the
	 *            {@code Wolf}
	 */
	public Wolf(double weight) {
		this.weight = weight;
		this.genus = "Canis";
		this.species = "Wolf";
		// Wolves are omnivores
		this.consumptionType = ConsumptionType.OMNIVORE;
	}

	@Override
	public String toString() {
		return "Wolf(" + genus + ") " + weight;
	}
}
