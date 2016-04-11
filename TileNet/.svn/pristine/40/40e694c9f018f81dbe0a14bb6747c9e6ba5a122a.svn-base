package com.putable.tilenet.tests;

import static org.junit.Assert.assertTrue;

import java.io.IOException;
import java.net.UnknownHostException;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.putable.tilenet.Util.Common;
import com.putable.tilenet.server.ServerModel;

public class ServerModelTest {
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
	
	@Test
	public void testMaxConnection() throws UnknownHostException, IOException {
		Common.sendTheBots(AllTests.TEST_PORT, AllTests.MAX_CONNECTIONS, Common.botcmd_Connect);
		
		assertTrue("Expected: " + AllTests.MAX_CONNECTIONS + " But got: " + serverModel.getConnected(), serverModel.getConnected() == AllTests.MAX_CONNECTIONS);
	}

	@Test(expected = Exception.class)
	public void testPortBind() throws InterruptedException, ExecutionException  {
		ExecutorService testEx = Executors.newSingleThreadExecutor();
		
		ServerModel redundantserver = new ServerModel(AllTests.TEST_PORT, AllTests.MAX_CONNECTIONS);
		testEx.submit(redundantserver).get();		
	}
}
