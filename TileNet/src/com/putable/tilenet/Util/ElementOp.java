package com.putable.tilenet.Util;

import java.util.ArrayList;


import com.putable.tilenet.Util.XMLTags.XMLTag;
import com.putable.tilenet.matrixelement.Agent;

/**
 * @author Class Cast Exceptions
 * As of right now this class will be determining an operation that a token will do and each
 * token will have associated with it an operation to execute when it is clicked.
 * A new operation can be created if necessary by adding it to the enum, and overriding the 
 * doOp method. 
 */
public class ElementOp {

	
	private OpType type;
	private String id;
	
	/**
	 * The Enum of OpType
	 * {@link BROADCAST} is the {@link OpType} that is sent to all Agents
	 * 	in a Matrix
	 * {@link NONE} is the default {@link OpType} that does nothing
	 * {@link MOVE} is the {@link OpType} that sends the operation
	 *  to the agent doing the action
	 * 
	 */
	public enum OpType {
		BROADCAST,
		NONE,
		MOVE;
	}
	

	/**
	 * Creates a new Operation to be added to a Token
	 * 
	 * @param type
	 * 	the {@link OpType} of this operation
	 */
	public ElementOp(OpType type){
		this.id = "Op" + Common.getID();
		this.type = type;
	}
	
	public OpType getType(){
		return this.type;
	}
	
	public void setType(OpType type){
		this.type = type;
	}
	
	public String getID(){
		return this.id;
	}
	
	

	
	/**Override this method every time a new TokenOp is made for a token
	 * @return the list of SetTags that will be sent to the Client
	 */
	public ArrayList<XMLTag> doOp(Agent agent){
		
		return null;
		
	}
	
	
}
