package com.putable.tilenet.Util;

import java.awt.BorderLayout;
import java.awt.Dimension;

import javax.swing.JFrame;

import javax.swing.JPanel;

import com.putable.tilenet.Util.XMLTags.SetTag;
import com.putable.tilenet.blueprints.Grid.GridType;
import com.putable.tilenet.blueprints.GridBuilder;
import com.putable.tilenet.blueprints.TileNetGridBuilder;
import com.putable.tilenet.client.ChatPanel;
import com.putable.tilenet.grid.HomeGrid;
import com.putable.tilenet.grid.SplashGrid;

/**
 * An deprecated driver class used primarily for testing
 * 
 * @author Class Cast Exceptions
 *
 */
public class Driver {
	// ((c4.1.1.3)) A port can have the range 44456-44459
	//private static final int PORT = 44457;
	//private static final int MAX_CONNECTIONS = 5;

	public static void main(String[] args) throws Exception {
		/*
		 * // How we can make a home matrix GridBuilder makeMatrices = new
		 TileNetGridBuilder(); HomeGrid home = (HomeGrid)
		 makeMatrices.orderGrid(GridType.HOME); HomeGrid client = (HomeGrid)
		 makeMatrices.orderGrid(GridType.HOME); home.addDefaultTokens();
		 System.err.println("\n\n\n\n\n\n");
		 */

		/*
		 * 
		 * SetTag updateme = new SetTag("t10"); updateme.setX(1);
		 * updateme.setY(1); updateme.setFgColor(Color.BLUE);
		 * updateme.setBgColor(Color.BLUE); client.update(updateme);
		 * 
		 * updateme.setFgColor(Color.GREEN); updateme.setBgColor(Color.GREEN);
		 * client.update(updateme);
		 */

		// How we can make Elements
		// ElementBuilder makeElements = new TileNetElementBuilder();
		// Matrix homeMatrix = (Matrix)
		// makeElements.orderElement(ElemType.MATRIX);

		
		 //Can view Grids JFrame frame = new JFrame();
		GridBuilder maker = new TileNetGridBuilder();
		SplashGrid source = (SplashGrid) maker.orderGrid(GridType.SPLASH);
		HomeGrid client = (HomeGrid) maker.orderGrid(GridType.HOME);
		source.addDefaultTokens();
		System.err.println("\n\n\n\n\n\n");		
		
		for(SetTag tag : source.getLayout().toSetTags())
			client.update(tag);	
		
		JFrame frame = new JFrame();
		frame.setLocationRelativeTo(null);
		frame.setPreferredSize(new Dimension(800,800));
		ChatPanel chat = new ChatPanel();
		JPanel panel = new JPanel();		
		panel.add(client.toJPanel(500, 500), BorderLayout.NORTH);
		panel.add(chat, BorderLayout.SOUTH);
		frame.add(panel);
		frame.pack();
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setAlwaysOnTop(true);
		frame.setVisible(true);
		
					 
		

		// Start a server
		//Server serverModel = new Server(PORT, MAX_CONNECTIONS);
		//ExecutorService servEx = Executors.newSingleThreadExecutor();
		//servEx.submit(serverModel);

		// Have a GUI
		//TileNetClient gui = new TileNetClient();

		// Bots can connect also
		// System.out.println(Common.sendTheBots(PORT, MAX_CONNECTIONS,
		// Common.botcmd_Connect));

		// Awesomely, the EXITONCLOSE calls system.exit()
		// So we don't have to work about shutting down server if run in here
		// while(!servEx.isTerminated()) { }

	}
}
