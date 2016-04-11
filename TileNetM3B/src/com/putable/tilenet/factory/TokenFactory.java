package com.putable.tilenet.factory;

import com.putable.tilenet.Util.XMLTags.SetTag;
import com.putable.tilenet.matrixelement.ElemType;

public class TokenFactory implements MatrixElementFactory {

	@Override
	public SetTag addSetTag() {		
		return new SetTag("Token NOT INITILIZED");
	}
 
	@Override
	public ElemType addElemType() {
		return ElemType.TOKEN;
	}
}