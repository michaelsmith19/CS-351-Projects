package com.putable.labs.lab6;


public abstract class Animal {
	/**
	 * The {@code Consumption} enum, enumerates the possible type of feeder that
	 * a living thing is and gives a weight factor accordingly.
	 * 
	 * @author BKey
	 */
	public enum ConsumptionType {
		// omnivore has a weightFactor of 2
		OMNIVORE(2),
		// carnivore has a weightFactor of 3
		CARNIVORE(3),
		// herbivore has a weightFactor of 1
		HERBIVORE(1);

		/**
		 * The constructor of a {@code ConsumptionType} enum.
		 * 
		 * @param weightFactor
		 *            the {@code int} value that is the weightFactor used in the
		 *            fight formula
		 */
		ConsumptionType(int weightFactor) {
			this.weightFactor = weightFactor;
		}

		private int weightFactor;

		/**
		 * Gets the {@code int} weight factor associated with this
		 * {@code ConsumptionType}.
		 * 
		 * @return the {@code int} weight factor associated with this
		 *         {@code ConsumptionType}
		 */
		public int getWeightFactor() {
			return weightFactor;
		}
	}
	
	protected double weight;
	protected ConsumptionType consumptionType;
	protected String genus;
	protected String species;

	/**
	 * This method decides if this animal is able to win a fight against the
	 * passed in contender. This is done with the highly scientific fight
	 * formula which gives a score to both fighters based on their specific
	 * {@code ConsumptionType} and weight. The score is given as follows: <br>
	 * score = weightFactor * weight <br>
	 * where weightFactor is 1 for {@code ConsumptionType.HERBIVORE}, 2 for
	 * {@code ConsumptionType.OMNIVORE} and 3 for
	 * {@code ConsumptionType.CARNIVORE}.
	 * 
	 * @param contender
	 *            the {@code Object} that is to fight this animal
	 * @return {@code true} if the score of this animal is greater than the
	 *         score of the contender, {@code false} otherwise.
	 */
	public boolean winsFight(Animal contender) {
		double thisScore;
		double contenderScore;

		// Calculate the fighting score for the animal.
		thisScore = this.getWeight() * this.getConsumptionType().getWeightFactor();
		// Calculate teh fighting score for its opponent.
		contenderScore = contender.getWeight() * contender.getConsumptionType().getWeightFactor();
	
		// compare scores
		if (thisScore > contenderScore)
			return true;
		else
			return false;
	}
	
	public double getWeight() {
		return weight;
	}

	public void setWeight(double weight) {
		this.weight = weight;
	}

	public ConsumptionType getConsumptionType() {
		return consumptionType;
	}

	public void setConsumptionType(ConsumptionType consumptionType) {
		this.consumptionType = consumptionType;
	}
	
	public String getGenus() {
		return genus;
	}
	public void setGenus(String genus) {
		this.genus = genus;
	}
	
	public String getSpecies() {
		return species;
	}
	public void setSpecies(String species) {
		this.species = species;
	}
}
