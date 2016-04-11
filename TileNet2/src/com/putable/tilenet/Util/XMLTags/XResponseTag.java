package com.putable.tilenet.Util.XMLTags;

import org.xml.sax.Attributes;

public class XResponseTag extends XMLTag {
	
	private String options;

	public XResponseTag(Attributes atts) {
		super(TagType.XRESPONSE);
		this.options = atts.getValue("options");
	}
	
	@Override
	public String toString() {
		return "<xresponse option=\"" + options + "\">";
	}
	
	public String getOptions(){
		return this.options;
	}
	

	@Override
	public boolean isLegal() {
		// TODO Auto-generated method stub
		return true;
	}

}
