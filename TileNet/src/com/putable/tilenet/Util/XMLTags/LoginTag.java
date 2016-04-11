package com.putable.tilenet.Util.XMLTags;

import org.xml.sax.Attributes;

public class LoginTag extends XMLTag {
	private String user;
	private String password;

	/**
	 * Constructor for loginTag inside our client. Should call this with input
	 * from initial dialogue asking username/pass
	 * 
	 * @param username
	 * @param password
	 */
	public LoginTag(String username, String password) {
		super(TagType.LOGIN);
		this.user = username;
		this.password = password;

	}

	public LoginTag(Attributes atts) {
		super(TagType.LOGIN);
		this.user = atts.getValue("user");
		this.password = atts.getValue("password");
	}

	public String getUser() {
		return user;
	}

	public String getPassword() {
		return password;
	}

	@Override
	public String toString() {
		return "<login user=\"" + user + "\" password=\"" + password + "\"/>";

	}

	@Override
	public boolean isLegal() {
		// TODO it may be best if we put in our rules for userNames and
		// passwords server side so that the reply can be in XMLTag form and
		// give a message as to why it was rejected.
		return true;
	}

}
