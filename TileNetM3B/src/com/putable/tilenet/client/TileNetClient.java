package com.putable.tilenet.client;

import java.awt.Dimension;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.xml.parsers.ParserConfigurationException;

import org.xml.sax.SAXException;

import com.putable.tilenet.Util.ChatFeature;
import com.putable.tilenet.Util.XMLParser;
import com.putable.tilenet.Util.XMLTags.LoggedIn;
import com.putable.tilenet.Util.XMLTags.LoginTag;
import com.putable.tilenet.Util.XMLTags.ServerTag;
import com.putable.tilenet.Util.XMLTags.SetTag;
import com.putable.tilenet.Util.XMLTags.XMLTag;
import com.putable.tilenet.Util.XMLTags.XMLTag.TagType;
import com.putable.tilenet.connection.AgentConnection;
import com.putable.tilenet.connection.Connection;
import com.putable.tilenet.matrix.EmptyMatrix;
import com.putable.tilenet.matrix.Matrix;
import com.putable.tilenet.matrix.MatrixBuilder;
import com.putable.tilenet.matrix.TileNetMatrixBuilder;

public class TileNetClient {
	private final String clientHeader = "<?xml version='1.0' encoding='utf-8'?><!DOCTYPE client SYSTEM \"http://putable.com/TileNet/TileNet1.0.dtd\">";
	private static final int PORT = 44455;
	private JFrame jframe = new JFrame("Class Cast Exceptions TileNet");
	private JPanel chatPanel = new ChatFeature();
	private JPanel matrixPanel;
	private Connection conn;
	private EmptyMatrix matrix;


	private BlockingQueue<XMLTag> bq;

	public TileNetClient() {
		this.conn = new AgentConnection();
		jframe.setPreferredSize(new Dimension(500 + chatPanel.getWidth(),
				500 + chatPanel.getHeight()));
		jframe.getContentPane().add(chatPanel, "South");
		MatrixBuilder makeMatrices = new TileNetMatrixBuilder();
		Matrix empty = makeMatrices.orderMatrix("EMPTY");
		this.matrix = (EmptyMatrix) empty;
		this.matrixPanel = matrix.draw();
		jframe.getContentPane().add(matrixPanel, "North");
		
		jframe.pack();
	}

	/**
	 * Creates an InputDialog to get the IP address or hostName of a 'hopefully'
	 * running server.
	 * 
	 * @return A {@link String} that contains the hostName or IP address of the
	 *         server.
	 */
	private String getUserInput(String message) {
		return JOptionPane.showInputDialog(jframe, message,
				"Please provide your connecting information",
				JOptionPane.QUESTION_MESSAGE);
	}
	

	private void attemptConnection(String serverAddress) {
		
		
		// Try to connect to the port. If successful you will receive a valid
		// xml doctype and opening server tag.
		try {
			conn.setDestination(serverAddress, PORT);
			conn.connect();
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, "Cannot connect to: "
					+ serverAddress);
			return;
		}
		getWriter();
	}

	private void startParsing() throws ParserConfigurationException,
			SAXException, IOException {
		bq = new LinkedBlockingQueue<XMLTag>();

		XMLParser xmlp = new XMLParser(bq);
		xmlp.setInputSource(conn.getIn2());

		Thread parseThread = new Thread(xmlp, "parseThread");
		parseThread.start();

	}

	private boolean connected = false;
	private boolean loggedIn = false;

	/**
	 * First we make sure we're logged in + connected, then we can start our set
	 * processing. at the moment it is the prized banana forloop but in our
	 * actual implementation it will become a switch statement to accomodate the
	 * remaining tags.
	 * 
	 * @param event
	 */
	private int isGridFinished = 0;

	private void processEvent(XMLTag event) {
		// System.out.println("THE NEXT EVENT IS : " + event);
		if (loggedIn && connected) {

			switch (event.getTagType()) {
			case SET: {
				this.matrix.receiveLatestTag((SetTag) event);
				matrixPanel = matrix.draw();
				jframe.repaint();
				
				
				/*if (isGridFinished == 0 || isGridFinished == 3)
					sTag.setBgColor(Color.black);
				else
					sTag.setBgColor(Color.gray);
				sTag.setFgColor(Color.white);
				Token tok = new Token(sTag);
				Point position = new Point(sTag.getX(), sTag.getY());
				clientGrid.addTokenToLayout(tok, position);
				isGridFinished++;
				break;*/
			}
			default:
				System.out.println("whaaa?");
			}

		} else if (connected) {
			if (event.getTagType() == TagType.LOGGEDIN)
				confirmLogin((LoggedIn) event);

			else
				System.out.println("expected Loggedin tag");

		}

		else
			tryToConnect(event);

	}

	private String ourObjid;

	/**
	 * this is what we do when we find a loggedintag, right now it prints to
	 * console but ideally that will be to our chat window, the objectID will be
	 * needed elsewhere in the client.
	 * 
	 * @param li
	 */
	private void confirmLogin(LoggedIn li) {
		ourObjid = li.getObjid();
		System.out.println(li.getMessage());
		loggedIn = true;
	}

	/**
	 * This is servertag preprocessing, can expand to work more error handling.
	 * Might go back and do something similar for confirmLogin
	 * 
	 * @param event
	 */
	private void tryToConnect(XMLTag event) {
		if (event.getTagType() == TagType.SERVER) {
			establishSession((ServerTag) event);
		} else
			System.out.println("WHAT R U DOING THAT IS NOT A SERVER TAG");
		// TODO error handling
	}

	/**
	 * Still need closed/busy cases and a dialogue box for username/password
	 * 
	 * @param st
	 */
	private void establishSession(ServerTag st) {
		if (st.getStatus().equals("open")) {
			connected = true;
			// TODO SHOUT REALLY LOUD, SET NAME AND WHATNOTNOT BASED ON INFO IN
			// SERVER TAG
			login();
		} else
			System.out.println("I'LL HANDLE THE REST I SWEAR IT");
	}

	/**
	 * sends a logintag to server as well as the opening header for a client.
	 */
	private void login() {
		// TODO GET USERNAME AND PARSSWORD FROM INPUT
		String userName = getUserInput("please provide a user name");
		String password = getUserInput("please provide a password");
		toServer.print(clientHeader);
		toServer.print(new LoginTag(userName, password));
	}

	private PrintWriter toServer;

	private void getWriter() {
		toServer = conn.getOut();
	}

	public void run() throws ParserConfigurationException, SAXException,
			IOException, InterruptedException {
		String serverAddress = getUserInput("Please enter the IP address or host name");
		// Using the address from the input try to connect a socket.

		attemptConnection(serverAddress);

		// If successful make a parser
		startParsing();

		while (true) {
			XMLTag nextEvent = bq.take();
			System.out.println(nextEvent);
			processEvent(nextEvent);
		}
	}
	

	/*private JPanel drawClientGrid() {		
		matrixPanel.setLayout(new GridLayout(2, 2));
		matrixPanel.setSize(new Dimension(400, 400));
		for (int x = 0; x < 2; x++) {
			for (int y = 0; y < 2; y++) {
				Point p = new Point(x, y);
				ImageIcon ic = new ImageIcon((new BufferedImage(200, 200,
						BufferedImage.TYPE_4BYTE_ABGR)));
				if (clientGrid.getLayout().get(p) != null)
					ic = clientGrid.getLayout().get(p).draw(200, 200);
				JLabel temp = new JLabel();
				temp.setIcon(ic);
				matrixPanel.add(temp);
			}
		}
		return matrixPanel;
	}*/

	public static void main(String[] args) throws ParserConfigurationException,
			SAXException, IOException, InterruptedException {

		TileNetClient client = new TileNetClient();		
		client.jframe.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		client.jframe.setVisible(true);
		client.run();
	}

}
