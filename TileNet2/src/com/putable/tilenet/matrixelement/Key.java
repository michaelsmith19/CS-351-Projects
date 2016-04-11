package com.putable.tilenet.matrixelement;

import java.util.List;

import com.putable.tilenet.Util.ElementOp;
import com.putable.tilenet.Util.ElementOp.OpType;
import com.putable.tilenet.Util.XMLTags.XMLTag;
import com.putable.tilenet.blueprints.Element;
import com.putable.tilenet.blueprints.ElementFactory;

public class Key extends Element {
	// BuilderFactory
	ElementFactory thisUses;
	ElementOp op;

	// Used by Factory
	public Key(ElementFactory thisSpecificfactory) {
		this.thisUses = thisSpecificfactory;
		this.op = new ElementOp(OpType.NONE);
	}

	@Override
	protected void placeParts() {
		tag = thisUses.addSetTag();
		type = thisUses.addElemType();		
	}	
	
	
	public List<XMLTag> doOp(Agent agent){
		return op.doOp(agent);
	}
	
	public void setOp(ElementOp op){
		this.op = op;
		this.op.setType(op.getType());
	}
}
