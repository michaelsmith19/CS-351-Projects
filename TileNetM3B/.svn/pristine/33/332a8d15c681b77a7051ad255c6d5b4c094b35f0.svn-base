package com.putable.tilenet.controller;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.net.Socket;
import java.util.concurrent.Callable;

import com.putable.tilenet.Util.Common;
import com.putable.tilenet.Util.XMLParser;
import com.putable.tilenet.Util.XMLTags.ClientTag;
import com.putable.tilenet.Util.XMLTags.XMLTag;
import com.putable.tilenet.server.ServerModel;
import com.putable.tilenet.server.ServerModel.ServerDispatch;

/**
 * Runs in the ThreadPool of {@link ServerModel}. Each instance of
 * {@link Controller} represents a singular incoming connection to the Server.
 * 
 * @author ClassCastExceptions
 */
public class Controller implements Callable<Boolean> {
	private boolean cleanShutdown = false;
	private final ServerDispatch dispatch;
	private final Socket sock;
	private final BufferedReader incoming;
	private InputStream incoming2;

	/**
	 * An individual instance of a client connected to the {@link ServerModel}
	 * 
	 * @param dispatch
	 *            The Model this handler will manipulate
	 * @param refrenceNumber
	 *            Unique agent number that identifies this agent
	 * @param sock
	 *            A half-duplex communication socket from Agent to
	 *            {@link ServerModel}
	 * @throws IOException
	 */
	public Controller(ServerDispatch dispatch, Socket sock) throws IOException {
		this.dispatch = dispatch;
		this.sock = sock;
		this.incoming = Common.getReaderFromSock(sock);
		this.incoming2 = Common.getInputStreamFromSock(sock);
	}

	@Override
	public Boolean call() throws Exception {

		// Requests server information for a brand new Agent
		registerConnection();

		XMLParser xmlp = new XMLParser(dispatch.getQueue());
		xmlp.setInputSource(incoming2);
		
		if(!sock.isClosed()){
			xmlp.beginParse();
		}
//		while (!sock.isClosed()) {
//
//			// Should already be parsed string
//			XMLTag command = getAgentMessage();
//			
//			System.out.println("Registering command: " + command);
//			dispatch.put(command);
//
//		}

		// Socket closed, was this an abrupt client disconnect?
		if (cleanShutdown) {
			return cleanShutdown;
		} else {
			dispatch.terminateConnection(this);
			return cleanShutdown;
		}

	}

	public Socket getSock() {
		return sock;
	}

	private XMLTag getAgentMessage() throws IOException {
		while (!incoming.ready()) { /* Wait */
		}

		// TODO parser from stream goes here
		System.out.println("Receiving: " + incoming.readLine());
		System.out.println("Now we just have to parse it!");

		// TODO remove dirty dirty hack
		return new ClientTag();
	}

	private void registerConnection() throws IOException {
		dispatch.registerConnection(this);
	}

	private void requestTermination() throws IOException {
		dispatch.sendTerminationXML(this);
	}

}