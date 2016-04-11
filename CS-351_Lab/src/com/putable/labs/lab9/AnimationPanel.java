package com.putable.labs.lab9;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.Timer;

/**
 * Create a simple animation of a ball bouncing around a 500 by 500 pixel
 * window. It uses a {@link Timer} to repaint() every millisecond. It implements
 * {@link ActionListener} so the AnimationPanel object is used as the Listener
 * for the Timer.
 * 
 * @author michaelsmith
 * 
 */
public class AnimationPanel extends JPanel implements ActionListener {

	private static final long serialVersionUID = 1L;
	private final int HEIGHT = 50;
	private final int WIDTH = 50;

	private int x = 0;
	private int y = 0;
	private int controlY = 0;
	private int controlX = 0;

	/**
	 * When an AnimationPanel is constructed it will immediately create a Timer
	 * object with itself as the Listener and call its start() method.
	 */
	public AnimationPanel() {
		new Timer(1, this).start();
	}

	@Override
	public void actionPerformed(ActionEvent arg0) {
		changeCoords();
		repaint();
	}

	/**
	 * Change the value of y and maybe x by 1 only for every ActionEvent
	 * 
	 */
	private void changeCoords() {
		bounce();
	}

	/**
	 * This holds the actual work that changes the control variables when it
	 * reaches an edge to "bounce" off the walls.
	 */
	private void bounce() {
		changeControl();
		switch (controlY) {
		case 0:
			y++;
			if (y % 5 == 0 && controlX == 0)
				x++;
			else if (y % 5 == 0)
				x--;
			break;
		case 1:
			y--;
			if (y % 5 == 0 && controlX == 0)
				x++;
			else if (y % 5 == 0)
				x--;
			break;
		}
	}

	/**
	 * Watches the x and y values for when the control values need to change.
	 * When they change it means they need to go in another direction.
	 */
	private void changeControl() {
		if (x == this.getWidth() - WIDTH)
			controlX = 1;
		if (x == 0)
			controlX = 0;
		if (y == this.getHeight() - HEIGHT)
			controlY = 1;
		if (y == 0)
			controlY = 0;
	}

	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		g.setColor(Color.red);
		g.fillOval(x, y, WIDTH, HEIGHT);
	}

	/**
	 * The driver for this animation.
	 * 
	 * @param args
	 *            none. This driver shouldn't take any arguments.
	 */
	public static void main(String[] args) {
		JFrame mainFrame = new JFrame("Bouncing Ball");
		int maxSize = 500;
		JPanel animation = new AnimationPanel();

		animation.setPreferredSize(new Dimension(maxSize, maxSize));

		// add the JPanel to the pane
		mainFrame.getContentPane().add(animation, BorderLayout.CENTER);

		// clean up
		mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		mainFrame.pack();
		mainFrame.setResizable(false);
		mainFrame.setVisible(true);
	}

}
