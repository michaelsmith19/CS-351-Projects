package com.putable.tilenet.matrixelement;

import java.util.List;

import com.putable.tilenet.Util.ElementOp;
import com.putable.tilenet.Util.ElementOp.OpType;
import com.putable.tilenet.Util.XMLTags.XMLTag;
import com.putable.tilenet.blueprints.Element;
import com.putable.tilenet.blueprints.ElementFactory;

public class Token extends Element {
	ElementFactory thisUses;
	ElementOp op;
	
	public Token(ElementFactory thisSpecificfactory) {
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
	
	public ElementOp getOp(){
		return this.op;
	}
	
	public OpType getType(){
		return this.op.getType();
	}
	
	public void setOp(ElementOp op){
		this.op = op;
		this.op.setType(op.getType());
	}
}
