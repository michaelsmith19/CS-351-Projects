package com.putable.tilenet.matrixelement;

import com.putable.tilenet.Util.Common;
import com.putable.tilenet.Util.XMLTags.SetTag;

public abstract class MatrixElement {
	
	//Fields of a matrixElement
	private String name;
	private String objid;

	// Parts of MatrixElement
	SetTag tag;
	ElemType type;	

	abstract void makeElement();
	
	/*
	 * In here should computations that we couldn't accomplish via
	 * the 'parts' of a matrix element. Everything in here should be generic
	 * to ALL MatrixElements  
	 * 
	 */

	public String getName() { return name; }
	public void setName(String name) { this.name = name;	}
	public String getObjid() { return objid; }

	// Is set for all MatrixElements after creation in the builder
	public void setId() {
		this.objid = type.getPrefix() + Common.getID();
	}

	public String toString() {
		return name + "@" + objid;
	}
	
	public SetTag getSetTag(){
		return this.tag;
	}
	
	public void setSetTag(SetTag tag){
		this.tag = tag;
	}

	
}
