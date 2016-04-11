package com.putable.tilenet.Util.XMLTags;

import org.xml.sax.Attributes;

import com.putable.tilenet.Util.AttributeTransformer;

public class CMDTag extends XMLTag {
	private String type;
	private String objid;
	private String modifiers;
	private String text;

	/**
	 * Constructor for a cmd generation inside client.
	 * 
	 * @param type
	 *            - Type of command, accepts "press", "click", "say".
	 * @param objid
	 *            - The object the command is interacting with.
	 */
	public CMDTag(String type, String objid) {
		super(TagType.CMD);
		this.type = type;
		this.objid = objid;
	}

	public CMDTag(Attributes atts) {
		super(TagType.CMD);
		this.type = atts.getValue("type");
		this.objid = atts.getValue("objid");
		this.modifiers = atts.getValue("modifiers");
		this.text = atts.getValue("text");
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder("<cmd type=\"" + type
				+ "\" objid=\"" + objid + "\"");
		if (modifiers != null)
			sb.append(" modifiers=\"" + modifiers + "\"");
		if (text != null)
			sb.append(" text=\"" + AttributeTransformer.stringToAttribute(text)
					+ "\"");
		return sb.toString() + "/>";

	}

	public void setType(String type) {
		this.type = type;
	}

	public void setModifiers(String modifiers) {
		this.modifiers = modifiers;
	}

	public void setText(String text) {
		this.text = text;
	}

	public String getCMDType() {
		return type;
	}

	public String getObjid() {
		return objid;
	}

	public String getModifiers() {
		return modifiers;
	}

	public String getText() {
		return text;
	}

	@Override
	public boolean isLegal() {
		// if either the objid or modifier is invalid it will return false.
		if (checkObjid() && checkModifiers())
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

	private boolean checkModifiers() {
		if (modifiers != null) {
			try {
				Integer.parseInt(modifiers);
			} catch (NumberFormatException e) {
				return false;
			}
		}
		// Must return true not only if the integer is correct
		// but if there is no modifier since it is optional.
		return true;
	}
}
