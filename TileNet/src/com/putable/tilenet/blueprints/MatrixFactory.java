package com.putable.tilenet.blueprints;

import com.putable.tilenet.Util.XMLTags.SetTag;
import com.putable.tilenet.blueprints.Element.ElemType;

public class MatrixFactory implements ElementFactory {

	@Override
	public SetTag addSetTag() {
		return new SetTag("NOT INITILIZED");
	}

	@Override
	public ElemType addElemType() {
		return ElemType.MATRIX;
	}

}
