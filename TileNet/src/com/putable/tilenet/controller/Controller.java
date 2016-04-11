package com.putable.tilenet.controller;

import java.io.IOException;
import java.net.Socket;
import java.util.concurrent.Callable;
import java.util.concurrent.LinkedBlockingQueue;

import com.putable.tilenet.Util.XMLParser;
import com.putable.tilenet.Util.XMLTags.XMLTag;
import com.putable.tilenet.connection.AgentConnection;
import com.putable.tilenet.connection.Connection;
import com.putable.tilenet.server.Server.ServerDispatch;

/**
 * Runs in the ThreadPool of {@link Server}. Each instance of {@link Controller}
 * represents a singular incoming connection to the Server.
 * 
 * @author ClassCastExceptions
 */
public class Controller implements Callable<Boolean> {
	private static final LinkedBlockingQueue<XMLTag> queue = new LinkedBlockingQueue<XMLTag>();
	private boolean cleanShutdown = false;
	private final ServerDispatch dispatch;
	private final Connection conn;

	/**
	 * An individual instance of a client connected to the {@link Server}
	 * 
	 * @param dispatch
	 *            The Model this handler will manipulate
	 * @param refrenceNumber
	 *            Unique agent number that identifies this agent
	 * @param sock
	 *            A half-duplex communication socket from Agent to
	 *            {@link Server}
	 * @throws IOException
	 */
	public Controller(ServerDispatch dispatch, Socket sock) throws IOException {
		Thread.currentThread().setName("Controller " + this);
		this.dispatch = dispatch;
		this.conn = new AgentConnection();
		conn.makeFromOpenSocket(sock);
	}

	@Override
	public Boolean call() throws Exception {
		// Requests server information for a brand new Agent
		notifyServer();

		// Spawn a new thread. Because we have to
		XMLParser xmlp = new XMLParser(dispatch.getQueue(), conn);
		xmlp.setInputSource(conn.getIn2());
		Thread parse = new Thread(xmlp, this + " parser");
		parse.start();

		// XMLTag tag = null;

		// EXTRA CONTROLLERS!
		while (!conn.isClosed() && parse.isAlive()) {
			/*
			 * tag = null; tag = queue.poll(1000, TimeUnit.MILLISECONDS);
			 * 
			 * if(tag != null) dispatch.put(new Pair(tag, conn));
			 */
		}

		// TODO Socket closed, was this an abrupt client disconnect?
		if (cleanShutdown) {
			return cleanShutdown;
		} else {
			dispatch.terminateConnection(this);
			return cleanShutdown;
		}
	}

	public Socket getSock() {
		return conn.getSock();
	}

	public Connection getConnection() {
		return this.conn;
	}

	private void notifyServer() throws IOException {
		dispatch.requestServerTag(this, conn);
	}
}