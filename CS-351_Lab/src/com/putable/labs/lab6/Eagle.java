package com.putable.labs.lab6;

/**
 * This is a concrete {@code Eagle} class. An instance of this class is used
 * specifically to be compared to other living things by fact of who would win
 * in a fight. A fight outcome is based on the attributes of a {@code Eagle}
 * including weight and what they consume for food (i.e. meat, plants or both).
 * 
 * @author BKey
 * 
 */
public class Eagle extends Animal{

	/**
	 * The constructor of a {@code Eagle}.
	 * 
	 * @param weight
	 *            the {@code double} value that is the weight in lbs of the
	 *            {@code Eagle}
	 */
	public Eagle(double weight) {
		this.weight = weight;
		this.genus = "Haliaeetus";
		this.species = "Eagle";
		// Eagles are carnivores
		this.consumptionType = ConsumptionType.CARNIVORE;
	}


	@Override
	public String toString() {
		return "Eagle (" + genus + ") " + weight;
	}
}
