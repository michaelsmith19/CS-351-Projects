package com.putable.tilenet.Util;

import java.util.concurrent.BlockingQueue;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import com.putable.tilenet.Util.XMLTags.XMLTag;
import com.putable.tilenet.Util.XMLTags.XMLTag.TagType;
import com.putable.tilenet.connection.Connection;
import com.putable.tilenet.server.Pair;

public class TileNetHandler extends DefaultHandler {

	/** holds the tags being read in order to be executed by the server */
	private BlockingQueue<Pair> eventQ;
	private final Connection conn;

	public TileNetHandler(BlockingQueue<Pair> bq, Connection conn) {
		this.eventQ = bq;
		this.conn = conn;
	}

	/**
	 * This begins the XML Document. For us this will be the server tag for the
	 * server side and the client tag for the client. All other tags are
	 * embedded within the server and client tags.
	 * 
	 */
	public void startDocument() throws SAXException {
		// Was annoying. sorry
		// System.out.println("XML parsing initiated.");
	}

	/**
	 * When we get the end server and end client tags the endDocument will be
	 * called and the connection will be terminated.
	 */
	public void endDocument() throws SAXException {
		System.out.println("XML parsing complete.");
	}

	/**
	 * This begins each individual tag sent between the server or client tags it
	 * loads all attributes into the {@link Attributes} atts. It will make the
	 * full tags for those defined in the DTD. It cannot know how to handle the
	 * end of xrequest and xresponse.
	 * 
	 * @throws SAXException
	 *             thrown if the given data breaks the rules of XML or the DTD.
	 */
	public void startElement(String uri, String localName, String qName,
			Attributes atts) throws SAXException {

		if (qName.contains("-"))
			qName = loggFix(qName);

		qName = qName.toUpperCase();

		try {
			Pair newEvent = new Pair(XMLTag.makeTag(TagType.valueOf(qName),
					atts), conn);

			eventQ.put(newEvent);
		} catch (InterruptedException e) {
			System.out.println("fuck");
		}

	}

	/**
	 * Will be used to properly handle our implementations of the xrequest and
	 * xresponse.
	 * 
	 * @throws SAXException
	 *             thrown if the command is incompatible with the DTD
	 */
	public void endElement() throws SAXException {
		// TODO : End element will be Important for the xrequests and xresponses
	}

	/**
	 * This is used to be able to easily drop the - character from a logged-in
	 * or logged-out tag. This is done to easily set the value of the enum Type
	 * of the tag from its {@link String} name.
	 * 
	 * @param name
	 *            the {@link String} that denotes the name of the element
	 * @return the name without the - character.
	 */
	private String loggFix(String name) {
		return "logged" + name.substring(7);
	}
}
