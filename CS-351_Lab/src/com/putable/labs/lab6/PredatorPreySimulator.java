package com.putable.labs.lab6;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * The {@code PredatorPreySimulator} is an application that, based on the
 * created population of the Animal Kingdom, randomly chooses two animals and
 * prints out the outcome of a potential fight seen in the wild. <br>
 * The print statement must follow this format: <br>
 * ANIMAL1.toString() + "(" + GENUS_NAME + ")" + " " + WEIGHT + " vs. " +
 * ANIMAL2.toString() + "(" + GENUS_NAME + ")" + " " + WEIGHT + ": " + WEIGHT +
 * "lb " + WINNER.toString() + " wins!"<br>
 * where GENUS_NAME is the {@code String} value of the animal's genus name.
 * 
 * @author BKey
 * 
 */
public class PredatorPreySimulator {

	public static void main(String[] args) {
		List<Animal> animalKingdom = new ArrayList<Animal>();
		Animal animal1, animal2;

		boolean animal1Wins;
		String output = "";

		createKingdom(animalKingdom);

		// randomly pick 2 NOTE: must set the seed to a value if you
		// want repeatability. I don't.
		Random prng = new Random();
		animal1 = animalKingdom.get(prng.nextInt(animalKingdom.size()));
		animal2 = animalKingdom.get(prng.nextInt(animalKingdom.size()));

		animal1Wins = animal1.winsFight(animal2);
		
		// First add the information of the 2 fighting. Then add the info about
		//which animal won the fight.
		output = animal1.toString() + " vs. " + animal2.toString() + ": ";
		if (animal1Wins) {
			output += animal1.getWeight() + "lb " + animal1.getSpecies() + " wins!";
		}else
			output += animal2.getWeight() + "lb " + animal2.getSpecies() + " wins!";

		System.out.println(output);
	}

	/**
	 * Create and fill the animal kingdom.
	 * 
	 * @param animalKingdom
	 *            the {@code ArrayList<Object>} that holds all of the animals in
	 *            the kingdom
	 */
	private static void createKingdom(List<Animal> animalKingdom) {
		Wolf smallWolf = new Wolf(60.5);
		Wolf mediumWolf = new Wolf(87.2);
		Wolf largeWolf = new Wolf(105.7);
		Eagle smallEagle = new Eagle(3.3);
		Eagle largeEagle = new Eagle(21.0);
		Rabbit smallRabbit = new Rabbit(1.7);
		Rabbit mediumRabbit = new Rabbit(2.2);
		Rabbit largeRabbit = new Rabbit(10);

		animalKingdom.add(smallWolf);
		animalKingdom.add(mediumWolf);
		animalKingdom.add(largeWolf);
		animalKingdom.add(smallEagle);
		animalKingdom.add(largeEagle);
		animalKingdom.add(smallRabbit);
		animalKingdom.add(mediumRabbit);
		animalKingdom.add(largeRabbit);
	}
}
