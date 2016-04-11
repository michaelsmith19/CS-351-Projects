package com.putable.tilenet.connection;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.net.Socket;
import java.rmi.UnknownHostException;

import com.putable.tilenet.Util.Common;

public class AgentConnection implements Connection {
	private Socket sock;
	private String hostname;
	private Integer port;
	private PrintWriter out;
	private BufferedReader in;
	private InputStream in2;
	
	private String name;
	
	public AgentConnection(){
		this.name = Common.getID() + "";
	}
	
	public String toString(){
		return this.name;
	}

	@Override
	public InputStream getIn2() {
		return in2;
	}

	@Override
	public PrintWriter getOut() {
		return out;
	}

	@Override
	public BufferedReader getIn() {
		return in;
	}

	@Override
	public Socket getSock() {
		return sock;
	}

	@Override
	public void connect() throws UnknownHostException, IOException {
		if(port == null)
			throw new IOException("Must set port on agent prior to connecting");
		this.sock = new Socket(hostname, port);
		this.out = Common.getWriterFromSock(sock);
		this.in = Common.getReaderFromSock(sock);
		this.in2 = Common.getInputStreamFromSock(sock);
	}

	@Override
	public void disconnect() {
		try {
			sock.close();
		} catch (IOException e) {
			System.err.println("Disconnected with error");
		}
	}

	@Override
	public String getResponce() throws IllegalStateException, IOException {
		if (in == null)
			throw new IllegalStateException("No handle for BufferedReader in");
		while (!in.ready()) {/* Wait until ready */
		}
		return in.readLine();
	}

	@Override
	public void sendMessage(String message) throws IllegalStateException {
		if (out == null)
			throw new IllegalStateException("No handle for PrintWriter out");
		out.println(message);
	}
	
	@Override
	public void sendMessage(Object message) throws IllegalStateException {
		if (out == null)
			throw new IllegalStateException("No handle for PrintWriter out");
		out.println(message.toString());
	}
	
	@Override
	public void setDestination(String hostname, int port) {
		this.hostname = hostname;
		this.port = port;				
	}

	@Override
	public boolean isClosed() {
		return sock.isClosed();
	}

	@Override
	public void makeFromOpenSocket(Socket sock) throws IOException {
		if(sock.isClosed()){
			System.err.println("Error creating socket");
			throw new IOException("Cannot create Connection. You need an established socket!");			
		}
		this.sock = sock;
		this.out = Common.getWriterFromSock(sock);
		this.in = Common.getReaderFromSock(sock);
		this.in2 = Common.getInputStreamFromSock(sock);
	}

}
