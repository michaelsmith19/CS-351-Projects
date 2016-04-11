package gui;

import javax.swing.*;

public class EasyFrame extends JFrame{

	private static final long serialVersionUID = 1L;

	/**
	 * Create a simple JFrame.
	 * 
	 * @param args
	 */
	
	public static void buildFrame() {
		// Create the JFrame and make it closable.
		JFrame frame = new JFrame("Just some coding practice");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	
		JButton b1 = new JButton ("Blue");
		
		JPanel mainPanel = new JPanel();
		mainPanel.add(b1);
		
		
		frame.getContentPane().add(mainPanel);
		
		// Display the window
		frame.pack();
		frame.setVisible(true);
	}
	
	public static void main(String[] args) {
		//Schedule a job for the event-dispatching thread:
        //creating and showing this application's GUI.
        javax.swing.SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                buildFrame();
            }
        });
    }
}


