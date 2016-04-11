package com.putable.siteriter.server;

import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;

import com.putable.siteriter.tsmall1.SDLParserImpl;

/**
 * A web server which hosts an {@link SDLParser}. This server handles requests
 * through a web browser over HTTP. If you are looking for a class called
 * ExampleServer, ((S.3.6)) states that this it is required to change.
 *
 * @author Trent Small
 *
 */
public class SDLServer
{
	/**
	 * The instance of an SDLParser we will use to generate pages.
	 */
	private SDLParser		parser;

	/**
	 * The actual web server object that will listen on the network and receive
	 * connection requests.
	 */
	private Server			server;

	/**
	 * The path originally used to load into the parser. This is stored in order
	 * to reload the page later.
	 */
	private static String	sdlFilePath	= null;

	/**
	 * Build an SDLServer.
	 *
	 * @param portNumber
	 *            What port number to listen on. Should be greater than 1000 and
	 *            must not be in use by any other program (including any other
	 *            instance of an SDLServer).
	 */
	public SDLServer(int portNumber)
	{
		parser = new SDLParserImpl();
		server = new Server(new SDLConnectionFactory(parser, sdlFilePath),
				portNumber);
	}

	/**
	 * Load the given grammar and then start serving pages based on it.
	 *
	 * @param reader
	 *            The source of the SDL Rules File for this SiteRiter server
	 * @throws IOException
	 *             If anything goes wrong.
	 */
	public void loadAndRun(Reader reader) throws IOException
	{
		parser.load(reader);

		/* Close the reader so we can edit the page for reload processing */
		reader.close();

		server.run();
	}

	/**
	 * A driver for starting an SDLServer from the OS.
	 *
	 * @param args
	 *            args[0] is required. It is the path to the SDL file that will
	 *            be loaded into the parser.
	 *
	 *            args[1] is optional. It is the port number to host on. If
	 *            unspecified, the default port 8000 is picked. If specified, it
	 *            must be an int under 65535. Trying to host on a port under
	 *            1000 may not work.
	 *
	 * @throws IOException
	 *             If basically anything blows up during processing.
	 */
	public static void main(String[] args) throws IOException
	{
		int newPort = argsValid(args);
		if (newPort == -1)
		{
			throw new IllegalArgumentException("Arguments given are illegal.\n");
		}
		sdlFilePath = args[0];
		Reader r = new FileReader(sdlFilePath);
		SDLServer es = new SDLServer(newPort);
		es.loadAndRun(r);
	}

	/**
	 * Verifies the arguments given to main. In this case, They need to be any
	 * String in args[0] and an int in args[1].
	 *
	 * @param args
	 *            The arguments to verify.
	 * @return The port read by the verification. If there are invalid
	 *         arguments, This returns -1. If there is no args[1], the default
	 *         port 8000 is used.
	 */
	private static int argsValid(String[] args)
	{
		int usedPort = -1;
		if (args.length == 1)
		{
			return 8000; /*
						 * An IOException will be thrown later if this path is
						 * invalid.
						 */
		}
		else if (args.length == 2)
		{
			try
			{
				usedPort = Integer.parseInt(args[1]);
			} catch (NumberFormatException e)
			{
				usedPort = -1;
			}
		}
		return usedPort;
	}
}
