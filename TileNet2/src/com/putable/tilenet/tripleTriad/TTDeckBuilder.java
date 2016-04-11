package com.putable.tilenet.tripleTriad;

import java.util.LinkedList;

import com.putable.tilenet.Util.Common;

public class TTDeckBuilder {

	/**
	 * Creates a {@link LinkedList} of {@link TTCards} that serves as 
	 * the game deck.
	 * @return The {@link LinkedList} of TTCards.
	 */
	
	public static LinkedList<TTCard> buildDeck() {
		LinkedList<TTCard> newDeck = new LinkedList<TTCard>();

		newDeck.add(new TTCard("Deepak", "i493", 10, 9, 3, 4, "t"
				+ Common.getID()));

		newDeck.add(new TTCard("Ackley", "i493", 10, 9, 3, 4, "t"
				+ Common.getID()));
		newDeck.add(new TTCard("Ackley", "i493", 10, 9, 3, 4, "t"
				+ Common.getID()));
		newDeck.add(new TTCard("Ackley", "i493", 10, 9, 3, 4, "t"
				+ Common.getID()));
		newDeck.add(new TTCard("Ackley", "i493", 10, 9, 3, 4, "t"
				+ Common.getID()));
		newDeck.add(new TTCard("Ackley", "i493", 10, 9, 3, 4, "t"
				+ Common.getID()));
		newDeck.add(new TTCard("Ackley", "i493", 10, 9, 3, 4, "t"
				+ Common.getID()));
		newDeck.add(new TTCard("Ackley", "i493", 10, 9, 3, 4, "t"
				+ Common.getID()));
		newDeck.add(new TTCard("Ackley", "i493", 10, 9, 3, 4, "t"
				+ Common.getID()));
		newDeck.add(new TTCard("Ackley", "i493", 10, 9, 3, 4, "t"
				+ Common.getID()));

		return newDeck;
	}
}
