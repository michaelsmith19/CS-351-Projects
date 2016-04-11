package com.putable.siteriter.server;

import java.net.Socket;

import com.putable.siteriter.SDLParser;
import com.putable.siteriter.server.AbstractConnection;
import com.putable.siteriter.server.ConnectionFactory;
import com.putable.siteriter.server.Server;

/**
 * Used by a {@link Server} to build a new {@link SDLConnection}.
 * 
 * @author Trent Small
 */
public class SDLConnectionFactory implements ConnectionFactory
{
	/**
	 * The parser used to generate a page, which will be sent to this
	 * connection.
	 */
	private SDLParser	parser;

	/**
	 * The path of the originally loaded SDL file. This is passed into the
	 * connection for reload processing to work.
	 */
	private String		sdlPath	= null;

	/**
	 * Creates a new SDLConnectionFactory.
	 * 
	 * @param parser
	 *            The parser for every connection using
	 *            {@link #make(Server, Socket) make} to use.
	 * @param sdlPath
	 *            The path of the originally loaded SDL file.
	 */
	public SDLConnectionFactory(SDLParser parser, String sdlPath)
	{
		this.sdlPath = sdlPath;
		this.parser = parser;
	}

	@Override
	public AbstractConnection make(Server server, Socket socket)
	{
		return new SDLConnection(server, socket, parser, sdlPath);
	}

}
