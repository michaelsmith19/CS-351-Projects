package com.putable.tilenet.Util.XMLTags;

import org.xml.sax.Attributes;

public class LoggedOut extends XMLTag {
	private String message;

	/**
	 * Constructor for LoggedOut tags to be used outside of the XML Parser. 
	 * 	The parameters are REQUIRED for each tag so they must not be null.
	 * 
	 * @param message
	 *            - logout message
	 */
	public LoggedOut(String message) {
		super(TagType.LOGGEDOUT);
		this.message = message;
	}

	/**
	 * Constructor for LoggedOut tags to be created inside the XMLParser. 
	 * @param atts - SAX attribute list
	 */
	public LoggedOut(Attributes atts) {
		super(TagType.LOGGEDOUT);
		this.message = atts.getValue("message");

	}

	public String getMessage() {
		return this.message;
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder("<logged-out ");

		if (message != null)
			sb.append("message=\"" + this.message + "\"");

		sb.append("/></server>");

		return sb.toString();

	}

	@Override
	public boolean isLegal() {
		// The validation is already done by the parser.
		// The message must just follow the conventions for
		// a string of CDATA in XML.
		return true;
	}
}
