package com.putable.tilenet.Util;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import com.putable.tilenet.server.Server;

public class ServerDriver {
	// ((c4.1.1.3)) A port can have the range 44456-44459
	private static final int PORT = 44457;
	private static final int MAX_CONNECTIONS = 5;

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// Start server
		Server serverModel = new Server(PORT, MAX_CONNECTIONS);
		ExecutorService servEx = Executors.newCachedThreadPool();
		servEx.submit(serverModel);
	}

}
