package com.putable.tilenet.blueprints;

import com.putable.tilenet.AgentManager.AgentManager;
import com.putable.tilenet.AgentManager.AgentManagerImpl;
import com.putable.tilenet.blueprints.Element.ElemType;
import com.putable.tilenet.layout.HomeLayout;
import com.putable.tilenet.matrixelement.Matrix;

public class HomeGridFactory implements GridFactory {

	@Override
	public Layout addLayout() {
		return new HomeLayout();
	}

	@Override
	public AgentManager addAgentManager() {
		return new AgentManagerImpl();
	}

	@Override
	public Matrix addMatrix() {
		ElementBuilder makeElements = new TileNetElementBuilder();
		Matrix m = (Matrix) makeElements.orderElement(ElemType.MATRIX);		
		return m;
	}	
	
}
