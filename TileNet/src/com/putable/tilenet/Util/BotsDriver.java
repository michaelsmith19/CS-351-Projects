package com.putable.tilenet.Util;

public class BotsDriver {
	private static final int PORT = 44457;
	//private static final int MAX_CONNECTIONS = 5;

	
	public static void main(String[] args) throws Exception{		
		
		System.out.println(Common.sendTheBots(PORT, 5, Common.botcmd_ConnectTalk));	
	}

}
