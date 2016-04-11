package com.putable.tilenet.tests;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

import com.putable.tilenet.server.Server;

@RunWith(Suite.class)
@SuiteClasses({ AttributeTransformTest.class, ServerModelTest.class, AgentTest.class })
public class AllTests {
	public static final int TEST_PORT = 9999;
	public static final int MAX_CONNECTIONS = 20;
	
	//Where the Server lives
	private static final ExecutorService servEx = Executors.newSingleThreadExecutor();
	public static final Server serverModel = new Server(TEST_PORT, MAX_CONNECTIONS);

	@BeforeClass
	public static void setUp() throws Exception {
		servEx.submit(serverModel);
		//Wait 1 second
		Thread.sleep(1000);
		assertTrue(serverModel.isRunning());
	}

	@AfterClass
	public static void tearDown() throws Exception {
		serverModel.terminateServer();
		servEx.shutdown();
		//Wait 1 second
		Thread.sleep(1000);
		assertFalse(serverModel.isRunning());
	}

}
