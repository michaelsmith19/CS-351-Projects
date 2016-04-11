package com.putable.frobworld;

import java.util.Random;

public class GenoType implements ConstantValues {
	private final int DNA_BIRTH_MASS = 0;
	private final int DNA_BIRTH_PERCENT = 1;
	private final int DNA_UPDATE_PERIOD = 2;

	private final int DNA_NORTH_PREFS = 3;
	private final int DNA_SOUTH_PREFS = 7;
	private final int DNA_EAST_PREFS = 11;
	private final int DNA_WEST_PREFS = 15;
	private final int DNA_EMPTY_OFFSET = 0;
	private final int DNA_ROCK_OFFSET = 1;
	private final int DNA_GRASS_OFFSET = 2;
	private final int DNA_FROB_OFFSET = 3;

	private int[] dna = new int[19];

	public int[] getDna() {
		return dna;
	}

	public void setDna(int[] dna) {
		this.dna = dna;
	}

	/**
	 * Constructs a {@link GenoType} with the seeded random number generator.
	 * 
	 * @param rand
	 *            the seeded random that runs the entire simulation.
	 */
	public GenoType(Random rand) {
		loadDNA(rand);
	}

	private void loadDNA(Random rand) {
		for (int i = 0; i < dna.length; i++) {
			dna[i] = rand.nextInt(256);
		}
	}

	public int getBirthMass() {
		return dna[DNA_BIRTH_MASS] / 2 + 20;
	}

	public int getBirthPercent() {
		return dna[DNA_BIRTH_PERCENT] * 100 / 255;
	}

	public int getUpdatePeriod() {
		return dna[DNA_UPDATE_PERIOD] % 32 + 5;
	}

	/**
	 * Changes the genes with odds of 1/DNA_MUTATION_ODDS_PER_BYTE probability.
	 * 
	 * @param rand
	 *            the seeded random.
	 */
	public void mutate(Random rand) {
		for (int i = 0; i < dna.length; i++) {
			if (rand.nextInt(DNA_MUTATION_ODDS_PER_BYTE) == 0) {
				changeOneBit(i, rand);
			}
		}
	}

	private void changeOneBit(int i, Random rand) {
		int bitToChange = rand.nextInt(8);
		dna[i] = dna[i] ^ (1 << bitToChange);
	}

	/**
	 * Chooses the direction that the frob wants to go (weighted by its dna).
	 * 
	 * @param neighbors
	 *            The 4 {@link Things} within the frobs sensory range.
	 * @param rand
	 *            the random number generator that was seeded.
	 * @return an integer value, 0 for north, 1 for south, 2 for east,and 3 for
	 *         west.
	 */
	public int chooseDirection(Thing[] neighbors, Random rand) {
		int direction = 0;
		int[] dirPrefs = getPrefs(neighbors);
		int sumDirections = sumDirections(dirPrefs);
		int randWeighted = rand.nextInt(sumDirections);
		direction = decrementRand(randWeighted, dirPrefs);
		return direction;
	}

	/**
	 * continues decrementing the randWeighted value by the preferences. The
	 * preference that make randWeighted negative will be the direction chosen.
	 * 
	 * @param randWeighted
	 *            random value between 0 and the sum of all preferences.
	 * @param dirPrefs
	 *            int array that holds the values of the directional prefs.
	 * 
	 * @return an integer representing the direction chosen.
	 */
	private int decrementRand(int randWeighted, int[] dirPrefs) {
		for (int i = 0; i < dirPrefs.length; i++) {
			randWeighted -= dirPrefs[i];
			if (randWeighted < 0)
				return i;
		}
		return dirPrefs.length;
	}

	private int sumDirections(int[] dirPrefs) {
		int sum = 0;
		for (int i = 0; i < dirPrefs.length; i++) {
			sum += dirPrefs[i];
		}
		return sum;
	}

	/**
	 * Based on the neighbors type an the direction. choose which dna[] index
	 * holds the corresponding move preference.
	 * 
	 * @param neighbors
	 *            The 4 adjacent things in the world.
	 * @return an int array of the preferences for the 4 possible moves.
	 */
	private int[] getPrefs(Thing[] neighbors) {
		int[] NSEW = new int[4];
		for (int i = 0; i < 4; i++) {
			switch (neighbors[i].getType()) {
			case NOTHING:
				chooseNSEW(i, NSEW, DNA_EMPTY_OFFSET);
				break;
			case GRASS:
				chooseNSEW(i, NSEW, DNA_GRASS_OFFSET);
				break;
			case FROB:
				chooseNSEW(i, NSEW, DNA_FROB_OFFSET);
				break;
			case ROCK:
				chooseNSEW(i, NSEW, DNA_ROCK_OFFSET);
				break;
			}
		}
		return NSEW;
	}

	/**
	 * Takes the index the int array of prefs and the offset (which depends on
	 * the type of the neighbor) and loads the proper dna preference value into
	 * the array.
	 */
	private void chooseNSEW(int i, int[] NSEW, int offset) {
		switch (i) {
		case 0:
			NSEW[i] = dna[DNA_NORTH_PREFS + offset] + 1;
			break;
		case 1:
			NSEW[i] = dna[DNA_SOUTH_PREFS + offset] + 1;
			break;
		case 2:
			NSEW[i] = dna[DNA_EAST_PREFS + offset] + 1;
			break;
		case 3:
			NSEW[i] = dna[DNA_WEST_PREFS + offset] + 1;
			break;
		}

	}
}
