package com.putable.siteriter.server;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;

import com.putable.siteriter.SDLParser;
import com.putable.siteriter.abstractserver.Server;
import com.putable.siteriter.msmith19.SDLParserImpl;

/**
 * An example web server based on the com.putable.siteriter.server classes,
 * available as a example, model, and starting point for modification by UNM
 * CS351 S'10 students to build their own server code.
 * 
 * @author ackley
 * 
 */
public class SiteRiterServer
{
	/**
	 * The instance of an SDLParser we will use to generate pages.
	 */
	private SDLParser	parser;

	/**
	 * The actual web server object that will listen on the network and receive
	 * connection requests.
	 */
	private Server		server;

	/**
	 * Build an SiteRiterServer.
	 * 
	 * @param portNumber
	 *            What port number to listen on. Should be greater than 1000 and
	 *            must not be in use by any other program (including any other
	 *            instance of an ExampleServer).
	 */
	public SiteRiterServer(int portNumber)
	{
		parser = new SDLParserImpl();
		server = new Server(new SDLConnectionFactory(parser), 8000);
	}

	/**
	 * Load the given grammar and then start serving pages based on it.
	 * 
	 * @param reader
	 *            The source of the SDL Rules File for this SiteRiter server
	 * @throws IOException
	 *             If anything goes wrong.
	 */
	private void loadAndRun(Reader reader) throws IOException
	{
		parser.load(reader);
		server.run();
	}

	/**
	 * A minimal test driver for the ExampleServer. Note that because the
	 * {@link ExampleSDLParserImpl#load(Reader)} method doesn't actually read
	 * from its input, the contents of the reader provided here doesn't actually
	 * matter. In a real implementation things would be different.
	 * 
	 * FIXME: Accept an SDL Rules File filename as args[0], arrange for it to be
	 * loaded at startup.
	 * 
	 * TODO: Accept an optional port number as args[1], and listen on that if
	 * provided (else default to 8000)
	 * 
	 * @param args
	 *            Not used in this demo
	 * @throws IOException
	 *             If basically anything blows up during processing.
	 */
	// XXX These FiXME and ToDO tags in your javadoc show up in eclipse's task
	// window. Make sure you remove them when you do them!
	public static void main(String[] args) throws IOException
	{
		int port = 8000;
		if (args.length < 1)
			throw new IOException("There was no rules file.");
		Reader r = new InputStreamReader(new FileInputStream(new File(args[0])));
		if (args.length == 2)
			port = Integer.parseInt(args[1]);

		SiteRiterServer es = new SiteRiterServer(port);
		es.loadAndRun(r);
	}
}
