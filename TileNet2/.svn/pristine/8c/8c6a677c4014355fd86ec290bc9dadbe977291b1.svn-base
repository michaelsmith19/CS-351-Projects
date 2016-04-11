package com.putable.tilenet.Util.XMLTags;

import org.xml.sax.Attributes;

public class ClientTag extends XMLTag {
	private String version;

	/**
	 * Out of parser constructor for clientTags, creates a client tag with version="1.1"
	 */
	public ClientTag() {
		super(TagType.CLIENT);
		this.version = "1.0";
	}

	/**
	 * Constructor for clientTags to be used ONLY inside the SAX XML parser. 
	 * @param atts - SAX attribute list
	 */
	public ClientTag(Attributes atts) {
		super(TagType.CLIENT);
		this.version = atts.getValue("version");
	}

	@Override
	public String toString() {
		return "<client version=\"" + version + "\">";
	}

	/**
	 * Returns the version attribute value of this clientTag.
	 * @return Version of the client tag.
	 */
	public String getVersion() {
		return version;
	}

	@Override
	public boolean isLegal() {
		// Makes sure that the client version is 1.0
		if (version.equals("1.0"))
			return true;

		return false;
	}
}
