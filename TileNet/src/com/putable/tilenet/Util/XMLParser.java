package com.putable.tilenet.Util;

import java.io.IOException;
import java.io.InputStream;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;

import com.putable.tilenet.connection.Connection;
import com.putable.tilenet.server.Pair;

public class XMLParser implements Runnable {
	//private final Connection conn;
	private XMLReader xmlReader;
	private BlockingQueue<Pair> bQ;

	public XMLParser(BlockingQueue<Pair> serverBQ, Connection conn)
			throws ParserConfigurationException, SAXException {
		bQ = serverBQ;
		

		SAXParserFactory spf = SAXParserFactory.newInstance();
		spf.setNamespaceAware(true);
		spf.setValidating(true);

		SAXParser saxParser = spf.newSAXParser();
		xmlReader = saxParser.getXMLReader();

		TileNetHandler tnh = new TileNetHandler(bQ, conn);
		xmlReader.setContentHandler(tnh);
		xmlReader.setErrorHandler(new XMLErrorHandler(System.err));
		

	}

	public XMLParser() throws SAXException, ParserConfigurationException {
		bQ = new LinkedBlockingQueue<Pair>();

		SAXParserFactory spf = SAXParserFactory.newInstance();
		spf.setNamespaceAware(true);
		spf.setValidating(true);

		SAXParser saxParser = spf.newSAXParser();
		xmlReader = saxParser.getXMLReader();

		System.err.println("making Pair queue with a null connection");
		TileNetHandler tnh = new TileNetHandler(bQ, null);
		xmlReader.setContentHandler(tnh);
		xmlReader.setErrorHandler(new XMLErrorHandler(System.err));

	}

	private InputSource input;

	public void beginParse() throws IOException, SAXException {
		xmlReader.parse(input);
	}

	public void setInputSource(InputStream streamIn) {
		this.input = new InputSource(streamIn);
	}

	public BlockingQueue<Pair> getBQueue() {
		return this.bQ;
	}

	public Pair nextEvent() {
		return bQ.poll();
	}	

	@Override
	public void run() {
		/*
		 * The only way to stop a SAX parser.....
		 * is to kill it with an exception
		 * No, really.
		 */
		try {
			beginParse();
		} catch (IOException e) {
			System.err.println("Parser Thread died.");
			Thread.currentThread().interrupt();
		} catch (SAXException e) {
			System.err.println("Parser Thread died.");
			Thread.currentThread().interrupt();
		}
	}

}