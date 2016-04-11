package com.putable.frobworld;

/**
 * Holds all of the constant values used during the execution of the simulation.
 * All classes that need them will either implement this interface or be
 * subclasses of a class that implements them.
 * 
 * @author michaelsmith
 */

public interface ConstantValues {

	final int WORLD_WIDTH = 100;
	final int WORLD_HEIGHT = 50;

	final int MAX_SIMULATION_LENGTH = 25000;

	final int ROCK_BUMP_PENALTY = 30;
	final int FROB_HIT_PENALTY = 10;

	final int INIT_FROBS = 50;
	final int INIT_GRASSES = 250;
	final int INIT_ROCKS = 100;

	final int GRASS_FIXED_OVERHEAD = 0;
	final int GRASS_GENESIS_MASS = 10;
	final int GRASS_BIRTH_MASS = 30;
	final int GRASS_INITIAL_UPDATE_PERIOD = 10;
	final int GRASS_CROWD_LIMIT = 2;
	final int GRASS_MAX_UPDATE_PERIOD = 100;
	final int GRASS_BIRTH_PERCENT = 40;

	final int FROB_FIXED_OVERHEAD = 2;
	final int FROB_GENESIS_MASS = 100;

	final int DNA_MUTATION_ODDS_PER_BYTE = 20;

	final int GRASS_MASS_TAX_MILLS = -200;
	final int FROB_MASS_TAX_MILLS = 100;

}
