package com.putable.tilenet.blueprints;

import com.putable.tilenet.Util.Common;
import com.putable.tilenet.Util.R2Vect;
import com.putable.tilenet.Util.XMLTags.SetTag;

public abstract class Element {
	// Fields of a matrixElement
	private String name;
	private String objid;

	// Parts of an Element that are built
	public SetTag tag;
	public ElemType type;

	// These methods are ran by the Builder
	public void setId() {
		this.objid = type.prefix + Common.getID();
		this.tag.setObjid(this.objid);
	}

	protected abstract void placeParts();

	

	// to get the tag from other areas
	public SetTag getSetTag() {
		return this.tag;
	}

	/*
	 * In here should computations that we couldn't accomplish via the 'parts'
	 * of a matrix element. Everything in here should be generic to ALL
	 * MatrixElements
	 */

	public enum ElemType {
		AGENT('a'), KEY('k'), TOKEN('t'), IMAGE('i'), MATRIX('m');
		public final String prefix;

		ElemType(char c) {
			this.prefix = Character.toString(c);
		}
	}

	public void setId(String id) {
		this.objid = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
		this.tag.setName(name);
	}

	public String getObjid() {
		return objid;
	}

	public R2Vect getVect() {
		return new R2Vect(tag.getX(), tag.getY());
	}

	public void setVect(R2Vect vect) {
		tag.setX(vect.x);
		tag.setY(vect.y);
	}

	public String toString() {
		return name + "@" + objid;
	}
}
