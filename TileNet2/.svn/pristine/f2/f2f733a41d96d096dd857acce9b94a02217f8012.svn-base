package com.putable.tilenet.connection;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.net.Socket;
import java.rmi.UnknownHostException;

import javax.xml.parsers.ParserConfigurationException;

import org.xml.sax.SAXException;

import com.putable.tilenet.Util.XMLParser;
import com.putable.tilenet.Util.XMLTags.XMLTag;
import com.putable.tilenet.Util.XMLTags.XMLTag.TagType;
import com.putable.tilenet.matrixelement.Agent;
import com.putable.tilenet.server.Server;
import com.putable.tilenet.tests.AutoBot;

/**
 * This will constitute a connection held by an {@link Agent}
 * 
 * @author ClassCastExceptions
 * 
 */
public interface Connection {

	/**Should only be called FROM CONTROLLER, and only once. (everything will be reverse!)
	 * @param sock
	 * @throws IOException
	 * 		On attempt to make a connection with an already open sock 
	 */
	public void makeFromOpenSocket(Socket sock) throws IOException;
	
	/**
	 * @see Socket#isClosed()
	 */
	public boolean isClosed();
	
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
	 *         {@link Server}
	 */
	public BufferedReader getIn();

	/**
	 * Gets the {@link Socket} associated by this {@link Agent}
	 * 
	 * @return The {@link Socket} used for communication
	 */
	public Socket getSock();

	/**Used by {@link XMLParser}
	 * @return
	 */
	public InputStream getIn2();

	/**
	 * Will connect to the port and destination given through
	 *  setDestination(). This is expected to block.
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
	 */
	public void disconnect();

	/**
	 * Will grab the next newline delimited string from the incoming stream
	 * 
	 * @return The {@link String}
	 * @throws IllegalStateException
	 *             When the Socket is not open
	 * @throws IOException
	 *             On I/O error while waiting for a read
	 */
	public String getResponce() throws IllegalStateException, IOException;

	/**
	 * Sends a message to the end point of this socket
	 * 
	 * @param message
	 *            Message to be sent
	 * @throws IllegalStateException
	 *             On attempt to send message on a closed {@link Socket}
	 */
	public void sendMessage(String message) throws IllegalStateException;
	
	/**Sends a message to the end point of this socket
	 * @param message
	 * 	converted by {@link Object#toString()}
	 * @throws IllegalStateException 
	 * 	On attempt to send message on a closed {@link Socket}
	 */
	public void sendMessage(Object message)throws IllegalStateException;
	
}
