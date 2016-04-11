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

import com.putable.tilenet.Util.XMLTags.XMLTag;

public class XMLParser implements Runnable {
	private XMLReader xmlReader;
	private BlockingQueue<XMLTag> bQ;

	public XMLParser(BlockingQueue<XMLTag> serverBQ)
			throws ParserConfigurationException, SAXException {
		bQ = serverBQ;

		SAXParserFactory spf = SAXParserFactory.newInstance();
		spf.setNamespaceAware(true);
		spf.setValidating(true);

		SAXParser saxParser = spf.newSAXParser();
		xmlReader = saxParser.getXMLReader();

		TileNetHandler tnh = new TileNetHandler(bQ);
		xmlReader.setContentHandler(tnh);
		xmlReader.setErrorHandler(new XMLErrorHandler(System.err));

	}

	public XMLParser() throws SAXException, ParserConfigurationException {
		bQ = new LinkedBlockingQueue<XMLTag>();

		SAXParserFactory spf = SAXParserFactory.newInstance();
		spf.setNamespaceAware(true);
		spf.setValidating(true);

		SAXParser saxParser = spf.newSAXParser();
		xmlReader = saxParser.getXMLReader();

		TileNetHandler tnh = new TileNetHandler(bQ);
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

	public BlockingQueue<XMLTag> getBQueue() {
		return this.bQ;
	}

	public XMLTag nextEvent() {
		return bQ.poll();
	}

	@Override
	public void run() {
		try {
			beginParse();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SAXException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

}