package com.putable.labs.lab7;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Polygon;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import javax.swing.JFrame;
import javax.swing.JPanel;

public class ShapePanel extends JPanel {

	private static final long serialVersionUID = 1L;

	/**
	 * The enum {@link Shape} defines the 3 shapes that this {@link ShapePanel}
	 * can draw.
	 * 
	 * @author BKey
	 * 
	 */
	public enum Shape {
		SQUARE, CIRCLE, TRIANGLE;
	}

	private Random rand = new Random();
	private List<Shape> shapesToDraw = new ArrayList<Shape>();
	private int maxSize;

	public ShapePanel(List<Shape> shapesToDraw, int maxSize) {
		this.shapesToDraw = shapesToDraw;
		this.maxSize = maxSize;
	}

	/**
	 * Uses the {@code Graphics} object to draw each shape in the
	 * {@code shapesToDraw} {@code ArrayList}, exactly once, at a random size.
	 * More specifically, a Pseudo Random Number generator is used to choose a
	 * random {@code int} between 0 and {@code maxSize} (which is the maximum
	 * size of the {@code JFrame}). The value that the PRNG generates is either:
	 * 
	 * The diameter, if drawing a {@link Shape.CIRCLE} The length of a side, if
	 * drawing a {@link Shape.SQUARE} The length of a side, if drawing an
	 * equilaterial {@link Shape.Triangle}
	 * 
	 * Each {@link Shape} should be drawn such that it exists in the center of
	 * the window. That is, the center of each {@link Shape} is exactly
	 * {@code getWidth()/2} for the x-coordinate and {@code getHeight()/2} for
	 * the y-coordinate. All {@link Shape.CIRCLE}s should be {@code Color.RED},
	 * all {@link Shape.SQUARE}s should be {@code Color.BLUE} and all
	 * {@link Shape.TRIANGLE}s should be {@code Color.GREEN}.
	 * 
	 * NOTE: The height of an equilateral triangle is defined as
	 * {@code 1/2*sqrt(3)*l} where {@code l} is the length of the side.
	 * 
	 * @param g
	 *            the {@code Graphics} object in which you will use to draw the
	 *            {@link Shape}s.
	 */

	private void paintShapes(Graphics g) {
		Graphics2D g2d = (Graphics2D) g;

		int midX = maxSize / 2;
		int midY = maxSize / 2;

		for (int i = 0; i < shapesToDraw.size(); i++) {

			int size = rand.nextInt(maxSize);
			switch (shapesToDraw.get(i)) {
			case CIRCLE:
				g2d.setColor(Color.red);
				g2d.drawOval(midX - (size / 2), midY - (size / 2), size, size);
				break;

			case TRIANGLE:
				g2d.setColor(Color.green);
				g2d.drawPolygon(makeTriangle(size));
				break;

			case SQUARE:
				g2d.setColor(Color.blue);
				g2d.drawRect(midX - size / 2, midY - size / 2, size, size);
				break;
			}
		}
	}

	/**
	 * Create a triangle based on the size value given. Return the triangle as a
	 * polygon with three points.
	 * 
	 * @param size
	 *            The size of a side of the triangle
	 * @return the polygon with 3 points that represents the triangle
	 */
	private Polygon makeTriangle(int size) {
		Polygon triangle = new Polygon();
		int height = (int) (Math.sqrt(3) / 2 * size);
		triangle.addPoint(maxSize / 2, maxSize / 2 - height / 2);
		triangle.addPoint(maxSize / 2 - size / 2, maxSize / 2 + height / 2);
		triangle.addPoint(maxSize / 2 + size / 2, maxSize / 2 + height / 2);
		return triangle;
	}

	/**
	 * Override the paintComponent and use it to paint the shapes that were
	 * given on the command line.
	 */
	@Override
	protected void paintComponent(Graphics g) {
		paintShapes(g);
	}

	/**
	 * The driver for this lab 7.
	 * 
	 * @param args
	 *            is an array of {@code Strings} which hold the names of shapes
	 *            to draw.
	 */
	public static void main(String[] args) {
		List<Shape> shapesToDraw = new ArrayList<Shape>();
		String currentShapeName = "";

		if (args.length <= 0)
			System.out
					.println("Please provide arguments as to what shape(s) (Square, Circle or Triangle) you would like to draw.");
		else {
			// fill the list
			for (int i = 0; i < args.length; i++) {
				// can't switch on Strings in Java < 1.7... Gah.
				currentShapeName = args[i];
				if (currentShapeName.equalsIgnoreCase("Circle"))
					shapesToDraw.add(Shape.CIRCLE);
				else if (currentShapeName.equalsIgnoreCase("Square"))
					shapesToDraw.add(Shape.SQUARE);
				else if (currentShapeName.equalsIgnoreCase("Triangle"))
					shapesToDraw.add(Shape.TRIANGLE);
				else {
					// I don't know what shape this is. Explode.
					System.out
							.println("This program only draws Squares, Circles or Triangles. Sorry.");
					System.exit(0);
				}
			}

			JFrame mainFrame = new JFrame("Shape Artist");
			int maxSize = 500;
			JPanel shapePanel = new ShapePanel(shapesToDraw, maxSize);

			shapePanel.setPreferredSize(new Dimension(maxSize, maxSize));

			// add the JPanel to the pane
			mainFrame.getContentPane().add(shapePanel, BorderLayout.CENTER);

			// clean up
			mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			mainFrame.pack();
			mainFrame.setResizable(false);
			mainFrame.setVisible(true);
		}
	}
}
