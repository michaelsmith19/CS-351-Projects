package com.putable.tilenet.Util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

import com.putable.tilenet.Util.XMLTags.ClientTag;
import com.putable.tilenet.Util.XMLTags.LoginTag;
import com.putable.tilenet.tests.AutoBot;

/**
 * A Class that holds common utility methods in a static forum
 * 
 * @author ClassCastExceptions
 */
public class Common {
	private static long uniqueCounter;
	private static final long botTimeoutMilli = 8000;

	public final static String clientHeader = "<?xml version='1.0' encoding='utf-8'?><!DOCTYPE client SYSTEM \"http://putable.com/TileNet/TileNet1.0.dtd\">";
	public static final List<String> botcmd_ConnectDisconnect = Arrays.asList(
			"WAIT_RESPONCE",
			"SEND:" + clientHeader + new ClientTag(),
			"SEND:" + new LoginTag("gladios", "cake"),
			"WAIT_RESPONCE",
			"EXPECTED:T:LOGGEDIN",
			"DISCONNECT");
	public static final List<String> botcmd_Connect = Arrays.asList(
			"WAIT_RESPONCE",
			"SEND:" + clientHeader + new ClientTag(),
			"SEND:" + new LoginTag("gladios", "cake"),
			"WAIT_RESPONCE",
			"EXPECTED:T:LOGGEDIN");

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

	public static boolean sendTheBots(int port, int count, List<String> commands) throws UnknownHostException, IOException, InterruptedException, ExecutionException {
		boolean success = true;
		ExecutorService botSwarm = Executors.newCachedThreadPool();
		List<Future<Boolean>> backToThe = new ArrayList<Future<Boolean>>();		
		// Create count AutoAgents		
		
		for (int i = 0; i < count; i++) {
			AutoBot jones = new AutoBot(null, port, commands);			
			backToThe.add(botSwarm.submit(jones));
		}		
		for(Future<Boolean> f : backToThe)
			success &= f.get();
		
		// Tell thread pool to shutdown when there's nothing left.
		botSwarm.shutdown();
		try {
			botSwarm.awaitTermination(botTimeoutMilli, TimeUnit.MILLISECONDS);
		} catch (InterruptedException e) {
			return false;
		}
		
		return success;

	}
	
	
}
