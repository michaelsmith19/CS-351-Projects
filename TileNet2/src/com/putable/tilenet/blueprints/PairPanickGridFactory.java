package com.putable.tilenet.blueprints;

import com.putable.tilenet.AgentManager.AgentManager;
import com.putable.tilenet.AgentManager.AgentManagerImpl;
import com.putable.tilenet.layout.HomeLayout;


public class PairPanickGridFactory implements GridFactory {

	@Override
	public Layout addLayout() {
		return new HomeLayout();
	}

	
	public AgentManager addAgentManager() {
		return new AgentManagerImpl();
	}	
}
