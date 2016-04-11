package com.putable.tilenet.Util.XMLTags;

import org.xml.sax.Attributes;

public class ServerTag extends XMLTag {
	private String version;
	private String group;
	private String name;

	private String status;

	/**
	 * Default Constructor for ServerTag use inside the server.
	 * 
	 * @param status
	 *            - accepts "busy", "open", "closed". Any other string is an
	 *            illegal tag by the TileNet DTD
	 */
	public ServerTag(String status) {
		super(TagType.SERVER);
		this.version = "1.0";
		this.group = "The Class Cast Exceptions";
		this.name = "TileNet";
		this.status = status;
	}

	public ServerTag(Attributes atts) {
		super(TagType.SERVER);
		this.version = atts.getValue("version");
		this.group = atts.getValue("group");
		this.name = atts.getValue("name");
		this.status = atts.getValue("status");
	}

	@Override
	public String toString() {
		return "<server version=\"" + version + "\" group=\"" + group
				+ "\" name=\"" + name + "\" status=\"" + status + "\">";

	}

	public String getVersion() {
		return version;
	}

	public String getGroup() {
		return group;
	}

	public String getName() {
		return name;
	}

	public String getStatus() {
		return status;
	}

	@Override
	public boolean isLegal() {
		// TODO: The Parser will handle the validations for these strings.
		return true;
	}

}
