package com.putable.tilenet.Util.XMLTags;

import org.xml.sax.Attributes;

public class ClientTag extends XMLTag {
	private String version;

	/**
	 * Default constructor for clientTag makes the only client tag we'll ever
	 * need.
	 */
	public ClientTag() {
		super(TagType.CLIENT);
		this.version = "1.0";
	}

	public ClientTag(Attributes atts) {
		super(TagType.CLIENT);
		this.version = atts.getValue("version");
	}

	@Override
	public String toString() {
		return "<client version=\"" + version + "\">";
	}

	public String getVersion() {
		return version;
	}
}
