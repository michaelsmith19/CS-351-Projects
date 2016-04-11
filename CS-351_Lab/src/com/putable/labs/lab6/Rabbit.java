package com.putable.labs.lab6;

/**
 * This is a concrete {@code Rabbit} class. An instance of this class is used
 * specifically to be compared to other living things by fact of who would win
 * in a fight. A fight outcome is based on the attributes of a {@code Rabbit}
 * including weight and what they consume for food (i.e. meat, plants or both).
 * 
 * @author BKey
 * 
 */
public class Rabbit extends Animal {

	/**
	 * The constructor of a {@code Rabbit}.
	 * 
	 * @param weight
	 *            the {@code double} value that is the weight in lbs of the
	 *            {@code Rabbit}
	 */
	public Rabbit(double weight) {
		this.weight = weight;
		this.genus = "Sylvilagus";
		this.species = "Rabbit";
		// Rabbits are herbivores
		this.consumptionType = ConsumptionType.HERBIVORE;
	}


	@Override
	public String toString() {
		return "Rabbit(" + genus + ") " + weight;
	}
}
