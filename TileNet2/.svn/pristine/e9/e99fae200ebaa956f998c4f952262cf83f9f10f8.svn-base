package com.putable.tilenet.tests;

import static org.junit.Assert.assertTrue;

import java.io.IOException;
import java.net.ConnectException;
import java.net.UnknownHostException;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.putable.tilenet.Util.Common;
import com.putable.tilenet.server.ServerModel;

public class AgentTest {
	private final ServerModel  serverModel = AllTests.serverModel;	

	@Before
	public void beforeEveryTest(){
		assertTrue(serverModel.getConnected() == 0);
		assertTrue(serverModel.isRunning());
	}
	
	@After
	public void afterEveryTest() throws Exception{
		serverModel.removeAll();		
	}

	@Test(expected = ConnectException.class)
	public void testNoServerRunningAtAddress() throws UnknownHostException, IOException {
		Common.sendTheBots(AllTests.TEST_PORT + 20, 1, Common.botcmd_Connect);		
	}

}
