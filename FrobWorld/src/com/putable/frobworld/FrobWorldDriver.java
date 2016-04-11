package com.putable.frobworld;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.util.Scanner;

import javax.swing.JFrame;
import javax.swing.JPanel;

public class FrobWorldDriver {

	/**
	 * This driver will run the FrobWorld in interactive mode (in a GUI) if no
	 * command line arguments are given. If args[0] is "batch" then it will
	 * expect an input file in System.in and will parse the file according to
	 * spec.
	 * 
	 * @param args
	 *            args[0] should be "batch" or blank.
	 */
	public static void main(String[] args) throws InterruptedException {

		if (args.length > 0 && !args[0].equals("batch"))
			printUsage();
		else if (args.length > 0 && args[0].equals("batch")) {
			runAsBatch();
			return;
		}

		FrobWorld simWorld = new FrobWorld();
		simWorld.initWorld((int) System.nanoTime());

		JFrame mainFrame = new JFrame("FrobWorld!!");
		int sizeOfASpace = 12;
		JPanel animation = new FrobWorldPanel(simWorld, sizeOfASpace);
		simWorld.runSim(animation);
		animation.setPreferredSize(new Dimension(sizeOfASpace * 100,
				sizeOfASpace * 50));

		// add the JPanel to the pane
		mainFrame.getContentPane().add(animation, BorderLayout.CENTER);

		// clean up
		mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		mainFrame.pack();
		mainFrame.setResizable(false);
		mainFrame.setVisible(true);

	}

	/**
	 * creates a {@link Scanner} to look at the inputfile and decides whether it
	 * is a runCount or a runThese file.
	 */
	private static void runAsBatch() {
		Scanner scan = new Scanner(System.in);
		int nextInt = 0;
		nextInt = scan.nextInt();
		System.out.println("Seed     day extinct");
		if (nextInt != 0) {
			runCount(nextInt);
		}
		if (nextInt == 0) {
			runThese(scan);
		}

	}

	/**
	 * Takes a scanner and continues to read in one integer at a time until it
	 * reads in an ending 0.
	 * 
	 * @param scan
	 *            the {@link Scanner} that holds the inputFile.
	 */
	private static void runThese(Scanner scan) {
		int next = scan.nextInt();
		while (next != 0) {
			FrobWorld simWorld = new FrobWorld();
			System.out.print(next);
			simWorld.initWorld(next);
			simWorld.runSim();
			next = scan.nextInt();
		}
	}

	/**
	 * Runs the number of simulations that was detailed in the runCount file.
	 * System.nanoTime() provides the seed for each random number.
	 * 
	 * @param numRuns
	 *            The amount of times the simulation will be run.
	 */
	private static void runCount(int numRuns) {

		for (int i = 0; i < numRuns; i++) {
			FrobWorld simWorld = new FrobWorld();
			int seed = (int) System.nanoTime();
			System.out.print(seed);
			simWorld.initWorld(seed);
			simWorld.runSim();
		}
	}

	private static void printUsage() {
		System.out
				.println("The first command line argument must be an the word batch or blank");
	}

}
