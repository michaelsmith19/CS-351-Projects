package com.putable.tilenet.Util.XMLTags;

import org.xml.sax.Attributes;

public class LogoutTag extends XMLTag {
	private String message;

	/**
	 * Constructor for LogoutTag creation outside of the XML Parser. 	
	 * The parameters are REQUIRED for each tag so they must not be null.
	 * 
	 * @param message
	 *            A {@link String} that is sent to the server when logging out.
	 */
	public LogoutTag(String message) {
		super(TagType.LOGOUT);
		this.message = message;

	}

	/**
	 * Constructor for LogoutTag creation inside the XML parser.
	 * 
	 * @param atts - SAX attribute list.
	 */
	public LogoutTag(Attributes atts) {
		super(TagType.LOGOUT);
		this.message = atts.getValue("message");
	}

	public String getMessage() {
		return message;
	}

	@Override
	public String toString() {
		return "<logout message=\"" + message + "\"/>\n</client>";

	}

	@Override
	public boolean isLegal() {
		// The parser will also handle all the string validation for this
		// tag.
		return true;
	}
}
