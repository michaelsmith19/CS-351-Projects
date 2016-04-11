package com.putable.tilenet.Util;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextArea;

import com.putable.tilenet.server.Server;

public class ServerDriver {
	// ((c4.1.1.3)) A port can have the range 44456-44459
	private static final int PORT = 44457;
	private static final int MAX_CONNECTIONS = 5;

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// Start server
		final Server serverModel = new Server(PORT, MAX_CONNECTIONS);
		final ExecutorService servEx = Executors.newCachedThreadPool();
		servEx.submit(serverModel);
		
		JPanel panel = new JPanel();
		panel.setPreferredSize(new Dimension(275,100));
		panel.setLayout(new BorderLayout());
		JTextArea text = new JTextArea("You are running the Server on Port " + PORT);
		text.setEditable(false);
		panel.add(text, BorderLayout.NORTH);
		JButton button = new JButton("Terminate Server");

		panel.add(button, BorderLayout.SOUTH);
		final JFrame frame = new JFrame("Server");
		frame.setLayout(new BorderLayout());
		frame.add(panel, BorderLayout.CENTER);
		frame.setPreferredSize(new Dimension(275, 100));
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.pack();
		frame.setVisible(true);
		
		button.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent arg0) {
				servEx.shutdown();
				serverModel.terminateServer();
				frame.dispose();
			}
			
		});
		
		
		
		
		
		
	}
	
	

}
