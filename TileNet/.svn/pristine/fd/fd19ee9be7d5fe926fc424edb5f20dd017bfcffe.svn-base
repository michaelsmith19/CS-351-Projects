package com.putable.tilenet.Util;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import com.putable.tilenet.matrix.Matrix;
import com.putable.tilenet.matrix.MatrixBuilder;
import com.putable.tilenet.matrix.TileNetMatrixBuilder;
import com.putable.tilenet.matrixelement.Agent;
import com.putable.tilenet.matrixelement.MatrixElementBuilder;
import com.putable.tilenet.matrixelement.TileNetMatrixElementBuilder;
import com.putable.tilenet.server.ServerModel;

public class Driver {
	//((c4.1.1.3)) A port can have the range 44456-44459
	private static final int PORT = 44457;
	private static final int MAX_CONNECTIONS = 5;
	
	public static void main(String[] args) throws Exception{
		
		
		//How we can make a home matrix
		MatrixBuilder makeMatrices = new TileNetMatrixBuilder();		
		Matrix home = makeMatrices.orderMatrix("HOME");
		
		//How we can make an agent
		MatrixElementBuilder makeElements = new TileNetMatrixElementBuilder();
		Agent jones = (Agent) makeElements.orderElement("AGENT");		
		
		
		
		//Start server
		ServerModel serverModel = new ServerModel(PORT, MAX_CONNECTIONS);
		ExecutorService servEx = Executors.newSingleThreadExecutor();
		servEx.submit(serverModel);		
		
		//Make an agent for us to see and use
		ExecutorService clientEx = Executors.newSingleThreadExecutor();		
		
		
		//Shuts down when our singular agent is finished (JFrames that are open still count!)
		clientEx.shutdown();
		while(!clientEx.isTerminated()) { }
		servEx.shutdown();
	}
}
