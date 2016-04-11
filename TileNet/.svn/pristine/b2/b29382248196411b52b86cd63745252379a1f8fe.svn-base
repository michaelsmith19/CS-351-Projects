package com.putable.tilenet.connection;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.net.Socket;
import java.rmi.UnknownHostException;

import com.putable.tilenet.matrixelement.Agent;
import com.putable.tilenet.server.ServerModel;

/**
 * This will constitute a connection held by an {@link Agent}
 * 
 * @author ClassCastExceptions
 * 
 */
public interface Connection {

	
	/**Sets the destination to connect to
	 * @param hostname
	 * 	String of server
	 * @param port
	 * 	The port
	 */
	public void setDestination(String hostname, int port);
	
	/**
	 * Called to write to a {@link Socket}
	 * 
	 * @return A {@link PrintWriter} to be the sole means of communication to
	 *         the end point
	 */
	public PrintWriter getOut();

	/**
	 * @return A BufferedReader to be used to receive communication from a
	 *         {@link ServerModel}
	 */
	public BufferedReader getIn();

	/**
	 * Gets the {@link Socket} associated by this {@link Agent}
	 * 
	 * @return The {@link Socket} used for communication
	 */
	public Socket getSock();

	public InputStream getIn2();

	/**
	 * Will connect to the port and destination given through
	 *  setDestination() 
	 * 
	 * @throws UnknownHostException
	 *             on.... unknown host
	 * @throws IOException
	 *             On other errors except UnknownHost
	 */
	public void connect()
			throws UnknownHostException, IOException;

	/**
	 * Will disconnect from the current socket.
	 * 
	 * @throws IOException
	 *             On failure to close incoming Socket
	 */
	public void disconnect() throws IOException;

	/**
	 * Will grab the next newline delimited string from the incoming stream
	 * 
	 * @return The {@link String}
	 * @throws IllegalStateException
	 *             When the Socket is not open
	 * @throws IOException
	 *             On I/O error while waiting for a read
	 */
	public String getServerResponce() throws IllegalStateException, IOException;

	/**
	 * Sends a message to the end point of this socket
	 * 
	 * @param message
	 *            Message to be sent
	 * @throws IllegalStateException
	 *             On attempt to send message on a closed {@link Socket}
	 */
	public void sendMessage(String message) throws IllegalStateException;

	/**
	 * Will wait until a specific string is received from the server
	 * 
	 * @param expected
	 *            The string to wait for
	 * @throws IOException
	 *             On I/O error while waiting for a read
	 * @throws IllegalStateException
	 *             When the {@link Socket} is not open
	 */
	public void expect(String expected) throws IllegalStateException,
			IOException;
}
