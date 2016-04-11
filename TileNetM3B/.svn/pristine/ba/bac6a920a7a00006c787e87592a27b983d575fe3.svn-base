package com.putable.tilenet.Util.XMLTags;

import org.xml.sax.Attributes;

/**
 * The abstract root of the XMLTags.
 * 
 * @author Class Cast Exceptions
 * 
 */
public abstract class XMLTag {

	private TagType cType;

	public XMLTag(TagType type) {
		this.cType = type;
	}

	public enum TagType {
		SET, LOGGEDIN, LOGGEDOUT, SERVER, XREQUEST, XRESPONSE, HEAR, CLIENT, LOGIN, LOGOUT, CMD
	}

	public static XMLTag makeTag(TagType type, Attributes atts) {
		switch (type) {
		case LOGGEDIN: {
			return new LoggedIn(atts);
		}
		case SERVER: {
			return new ServerTag(atts);
		}
		case LOGGEDOUT: {
			return new LoggedOut(atts);
		}
		case SET: {
			return new SetTag(atts);
		}
		case HEAR: {
			return new HearTag(atts);
		}
		case CLIENT: {
			return new ClientTag(atts);
		}
		case LOGIN: {
			return new LoginTag(atts);
		}
		case LOGOUT: {
			return new LogoutTag(atts);
		}
		case CMD: {
			return new CMDTag(atts);
		}
		default: {
			System.out.println("Why did it default? Something is wrong.");
			break;
		}
		}
		return null;
	}

	/**
	 * TODO: implement this as an abstract method in each tag which tests
	 * attributes for everything that the parser does/can not do. Idea is that
	 * this will be called prior to execution/entering a queue so as to ensure
	 * that all attributes are to spec.
	 * 
	 * @return true if the tag is usable. Otherwise false.
	 */
	public boolean isLegal() {
		return true;
	}

	public TagType getTagType() {
		return this.cType;
	}

}
