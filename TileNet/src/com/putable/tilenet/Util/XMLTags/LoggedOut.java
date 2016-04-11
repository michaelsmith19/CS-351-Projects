package com.putable.tilenet.Util.XMLTags;

import org.xml.sax.Attributes;

public class LoggedOut extends XMLTag {
	private String message;

	/**
	 * LoggedOut constructor for use in Server.
	 * 
	 * @param message
	 *            - logout message
	 */
	public LoggedOut(String message) {
		super(TagType.LOGGEDOUT);
		this.message = message;
	}

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
		// TODO the validation is already done by the parser.
		// The message must just follow the conventions for
		// a string of CDATA in XML.
		return true;
	}
}
