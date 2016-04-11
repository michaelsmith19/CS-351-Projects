package com.putable.tilenet.Util.XMLTags;

import org.xml.sax.Attributes;

public class LoggedIn extends XMLTag {
	private String objid;
	private String message;

	/**
	 * Constructor for use inside server.
	 * 
	 * @param objid
	 *            - ID of the agent logged in
	 * @param message
	 */
	public LoggedIn(String objid, String message) {
		super(TagType.LOGGEDIN);
		this.objid = objid;
		this.message = message;
	}

	public LoggedIn(Attributes atts) {
		super(TagType.LOGGEDIN);
		this.objid = atts.getValue("objid");
		this.message = atts.getValue("message");
	}

	public String getObjid() {
		return objid;
	}

	public String getMessage() {
		return message;
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder("<logged-in ");
		if (objid != null)
			sb.append("objid=\"" + this.objid + "\"");
		if (message != null)
			sb.append(" message=\"" + this.message + "\"");

		sb.append("/>");

		return sb.toString();

	}

	@Override
	public boolean isLegal() {
		if (checkObjid())
			return true;

		return false;
	}

	private boolean checkObjid() {
		// If the beginning letter is not among the 4 possible return false.
		char objType = this.objid.charAt(0);
		if (objType != 'm' || objType != 't' || objType != 'i'
				|| objType != 'a' || objType != 'k')
			return false;
		// If the number after the letter is negative it is an invalid objid.
		if (this.objid.charAt(1) == '-')
			return false;
		// Try to parse the integer. If it gives the NumberFormatException
		// than it was not a valid number so the objid was not valid either.
		try {
			Integer.parseInt(objid.substring(1));
		} catch (NumberFormatException e) {
			return false;
		}
		return true;
	}

}
