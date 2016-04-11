package com.putable.tilenet.Util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import com.putable.tilenet.tests.AutoBot;

/**
 * A Class that holds common utility methods in a static forum
 * 
 * @author ClassCastExceptions
 */
public class Common {
	private static long uniqueCounter;
	private static final long botTimeoutMilli = 8000;

	public static final List<String> botcmd_ConnectDisconnect = Arrays.asList(
			"WAIT_RESPONCE", "SEND:Bot XML Stuff", "SEND:FIN", "EXPECT:FIN",
			"DISCONNECT");
	public static final List<String> botcmd_Connect = Arrays.asList(
			"WAIT_RESPONCE", "SEND:Bot XML Stuff");

	public static long getID() {
		uniqueCounter++;
		return uniqueCounter;
	}

	public static BufferedReader getReaderFromSock(Socket sock)
			throws IOException {
		return new BufferedReader(new InputStreamReader(sock.getInputStream()));
	}

	public static InputStream getInputStreamFromSock(Socket sock)
			throws IOException {
		return sock.getInputStream();
	}

	public static PrintWriter getWriterFromSock(Socket sock) throws IOException {
		return new PrintWriter(sock.getOutputStream(), true);
	}

	public static void sendTheBots(int port, int count, List<String> commands) throws UnknownHostException, IOException {
		ExecutorService botSwarm = Executors.newCachedThreadPool();
		// Create count AutoAgents
		
		for (int i = 0; i < count; i++) {
			AutoBot jones = new AutoBot(null, port, commands);			
			botSwarm.submit(jones);
		}
		// Tell thread pool to shutdown when there's nothing left.
		botSwarm.shutdown();

		try {
			botSwarm.awaitTermination(botTimeoutMilli, TimeUnit.MILLISECONDS);
		} catch (InterruptedException e) {
			// Not a big problem, sometimes the clients are supposed to hang
		}

	}
}
