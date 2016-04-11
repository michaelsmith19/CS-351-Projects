package com.putable.tilenet.tests;

import static org.junit.Assert.assertTrue;

import java.io.IOException;
import java.net.UnknownHostException;
import java.util.concurrent.ExecutionException;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.putable.tilenet.Util.Common;
import com.putable.tilenet.server.Server;

public class AgentTest {
	private final Server  serverModel = AllTests.serverModel;	

	@Before
	public void beforeEveryTest(){
		assertTrue(serverModel.getConnected() == 0);
		assertTrue(serverModel.isRunning());
	}
	
	@After
	public void afterEveryTest() throws Exception{
		serverModel.removeAll();		
	}

	@Test(expected = ExecutionException.class)
	public void testNoServerRunningAtAddress() throws UnknownHostException, IOException, InterruptedException, ExecutionException {
		Common.sendTheBots(AllTests.TEST_PORT +20, 1, Common.botcmd_Connect);		
	}

}
