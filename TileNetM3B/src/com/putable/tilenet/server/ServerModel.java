package com.putable.tilenet.server;

import java.awt.Color;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.Callable;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;

import com.putable.tilenet.Util.Common;
import com.putable.tilenet.Util.XMLTags.LoggedIn;
import com.putable.tilenet.Util.XMLTags.ServerTag;
import com.putable.tilenet.Util.XMLTags.SetTag;
import com.putable.tilenet.Util.XMLTags.XMLTag;
import com.putable.tilenet.controller.Controller;

/**
 * The model for a TileNet implementation
 * 
 * @author ClassCastExceptions
 */
public class ServerModel implements Callable<Void> {
	private boolean isRunning;
	private final int port;
	private final int maxConnections;
	private final ExecutorService ex;
	private final ServerDispatch dispatch;

	/**
	 * A dispatch thread used for the execution of received events. A
	 * {@link Controller} will place events into this
	 * {@link LinkedBlockingQueue}
	 * 
	 * @author ClassCastExceptions
	 */
	public final class ServerDispatch implements Callable<Void> {
		// TODO these Strings need to be sent to the translator then sent
		// through the DTD validate, then again sent to the client so that
		// it may parse the information and set attributes that need to be set
		private final String version = "<server version=\"1.1\">";
		private final String group = "<server group = \"The Class Cast Exceptions\">";
		private final String serverHeader = "<?xml version='1.0' encoding='utf-8'?><!DOCTYPE server SYSTEM \"http://putable.com/TileNet/TileNet1.0.dtd\">";
		private final String name = "<server name = \"TileNet World\">"; // Should
																			// be
																			// changed
																			// as
																			// per
																			// ((c8.8.6.3))

		// Our shared state
		private final LinkedBlockingQueue<XMLTag> queue;
		private final ConcurrentMap<Socket, Controller> connections;

		public ServerDispatch() {
			Thread.currentThread().setName("SERVER_DISPATCH");
			this.queue = new LinkedBlockingQueue<XMLTag>();
			this.connections = new ConcurrentHashMap<Socket, Controller>();
		}

		public LinkedBlockingQueue<XMLTag> getQueue() {
			return this.queue;
		}

		@Override
		public Void call() throws Exception {
			while (true) {
				XMLTag command = queue.take();
				System.out.println("Executoing command: " + command);
				switch (command.getTagType()) {
				case CLIENT:
					break;
				case CMD:
					break;
				case HEAR:
					break;
				case LOGGEDIN:
					break;
				case LOGGEDOUT:
					break;
				case LOGIN:
					break;
				case LOGOUT:
					break;
				case SERVER:
					break;
				case SET:
					break;
				case XREQUEST:
					break;
				case XRESPONSE:
					break;
				default:
					break;

				}
			}
		}

		/**
		 * Put an event into the {@link LinkedBlockingQueue}
		 * 
		 * @param command
		 *            The {@link XMLTag} (event)
		 */
		public void put(XMLTag command) {
			queue.add(command);
		}

		/**
		 * Register a new connection. This method is called by the
		 * {@link Controller} associated with a connection Catching Exceptions
		 * because we need this to keep running
		 * 
		 * @param A
		 *            self reference to the {@link Controller} who called this
		 */
		public void registerConnection(Controller conn) {
			PrintWriter out = null;
			try {
				out = Common.getWriterFromSock(conn.getSock());
			} catch (IOException e) {
				System.err.println(conn + " IOException getting writer");
			}

			// TODO remove this hack
			out.println(serverHeader);

			// TODO add check for redundant connections, probably use the unique
			// id generated?
			out.println(serverStatus());

			// hack Until we can compress the serverstatus and this
			if (!(connections.size() >= maxConnections)) {
				out.println(new LoggedIn("a1",

				"You have connect to class cast exception tileNet"));
				// Add to current connections
				connections.put(conn.getSock(), conn);

				sendSetters(out);
			}

			// TODO create logged-in attributes for objid

			// out.println(new LoggedOut("byebye").toString());

			// TODO remove this hack
			// out.close();
		}

		private void sendSetters(PrintWriter out) {
			SetTag hackMatrix = new SetTag("t1");
			hackMatrix.setBgColor(Color.gray);
			hackMatrix.setFgColor(Color.white);
			hackMatrix.setX(0);
			hackMatrix.setY(0);
			hackMatrix.setName("welcome");
			out.println(hackMatrix);
			SetTag hackMatrix2 = new SetTag("t2");
			hackMatrix2.setBgColor(Color.BLACK);
			hackMatrix2.setFgColor(Color.white);
			hackMatrix2.setX(0);
			hackMatrix2.setY(1);
			hackMatrix2.setName("to");
			out.println(hackMatrix2);
			SetTag hackMatrix3 = new SetTag("t3");
			hackMatrix3.setBgColor(Color.gray);
			hackMatrix3.setFgColor(Color.white);
			hackMatrix3.setX(1);
			hackMatrix3.setY(0);
			hackMatrix3.setName("tile");
			out.println(hackMatrix3);
			SetTag hackMatrix4 = new SetTag("t4");
			hackMatrix4.setBgColor(Color.BLACK);
			hackMatrix4.setFgColor(Color.white);
			hackMatrix4.setX(1);
			hackMatrix4.setY(1);
			hackMatrix4.setName("net");
			out.println(hackMatrix4);
		}

		/**
		 * Returns a busy server tag
		 * 
		 * @return
		 */
		private XMLTag serverStatus() {
			return (connections.size() >= maxConnections) ? new ServerTag(
					"busy") : new ServerTag("open");
		}

		/**
		 * Ask the client to terminate itself
		 * 
		 * @param controller
		 *            The connection handler for the client
		 * @throws IOException
		 *             When writing to the socket
		 */
		public void sendTerminationXML(Controller controller)
				throws IOException {
			connections.remove(controller.getSock());
			PrintWriter outgoing = Common.getWriterFromSock(controller
					.getSock());
			outgoing.println("<logged-out message=\"you logged out\">");
			// XXX Should kill socket after??
		}

		/**
		 * Tells the Server to kill and remove the connection immediately
		 * 
		 * @param controller
		 *            The connection handler for the client
		 * @throws IOException
		 */
		public void terminateConnection(Controller controller)
				throws IOException {
			connections.remove(controller.getSock());
			controller.getSock().close();
		}

		/**
		 * Removes every connection, sets the server like it was just started
		 * 
		 * @throws IOException
		 */
		private void terminateAllConnections() throws IOException {
			for (Socket sock : connections.keySet()) {
				sock.close();
			}
			connections.clear();
		}
	}

	// /-------------END INNERCLASS

	/**
	 * A singular server constructor
	 * 
	 * @param port
	 *            The port to listen for connections on
	 * @param maxConnections
	 *            Maximum number of concurrent connections to accept, after
	 *            which the server will reply it is busy
	 */
	public ServerModel(int port, int maxConnections) {
		this.isRunning = false;
		this.port = port;
		this.maxConnections = maxConnections;
		this.ex = Executors.newCachedThreadPool();
		this.dispatch = new ServerDispatch();
	}

	@Override
	public Void call() throws Exception {
		// Give this Thread a name
		Thread.currentThread().setName("SERVERMODEL");

		ServerSocket serverSocket = null;
		try {
			serverSocket = new ServerSocket(port);
			isRunning = true;
		} catch (Exception e) {
			System.err.println("Failed to bind port: " + port + "\n");
			throw e;
		}

		// ----------Place Dispatcher
		ex.submit(dispatch);
		// ----------

		while (isRunning) {
			ex.submit(new Controller(dispatch, serverSocket.accept()));
		}

		ex.shutdownNow();
		while (!ex.isTerminated()) {
		}
		serverSocket.close();
		return null;
	}

	public void terminateServer() {
		System.out.println("Server received shutdown request");
		this.isRunning = false;
	}

	public int getConnected() {
		return dispatch.connections.size();
	}

	public void removeAll() {
		try {
			dispatch.terminateAllConnections();
		} catch (IOException e) {
			System.err.println("Error terminating connections on server");
		}
	}

	public boolean isRunning() {
		return isRunning;
	}

	public ServerDispatch getDispatcher() {
		return dispatch;
	}
}
