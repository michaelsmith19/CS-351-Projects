package com.putable.siteriter.server;

import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.io.Writer;
import java.net.Socket;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import com.putable.siteriter.SDLParseException;
import com.putable.siteriter.SDLParser;

/**
 * A concrete extension of {@link AbstractConnection} which gives its connecting
 * client a String generated from its {@link SDLParser}.
 *
 * @author Trent Small
 *
 */
public class SDLConnection extends AbstractConnection
{
	/**
	 * A reference to the {@link SDLParser} to use for generating the response
	 * to this connection.
	 */
	private SDLParser parser;

	/**
	 * A ballroom for ensuring that threads perform their duties correctly.
	 */
	private static Ballroom ballroom = new BallroomImpl();

	/**
	 * This is the path of the originally loaded SDL file, used when reloading
	 * the parser.
	 */
	private String sdlPath = null;

	/**
	 * Constructs an SDLConnection .
	 *
	 * @param server
	 *            The Server which is currently handling this connection.
	 * @param socket
	 *            The connection Socket which will be read from and written to.
	 * @param parser
	 *            The SDLParser which contains the loaded SDL file.
	 * @param sdlPath
	 *            The path of the originally loaded SDL file.
	 */
	public SDLConnection(Server server, Socket socket, SDLParser parser,
			String sdlPath)
	{
		super(server, socket);
		this.sdlPath = sdlPath;
		this.parser = parser;
	}

	/**
	 * Milliseconds in one day.
	 */
	private static final long MS_PER_DAY = 1000 * 60 * 60 * 24;

	/**
	 * Writes an HTTP response to this thread's Socket with a correct HTTP
	 * header.
	 *
	 * @param response
	 *            The HTML message to send through the Socket.
	 * @param type
	 *            The type to send. This goes directly into the header.
	 * @param expires
	 *            The amount of milliseconds until this page expires. This goes
	 *            directly into the header.
	 * @throws IOException
	 *             If the {@link Writer} at {@link #getWriter()} cannot be
	 *             written to for any reason.
	 */
	private void writeResponse(String response, String type, long expires)
			throws IOException
	{
		Date now = new Date();
		if (expires <= 0)
			expires = now.getTime();
		else
			expires += now.getTime();
		byte[] bytes = response.getBytes();
		String header = "HTTP/1.0 200 OK\r\n" + "Date: "
				+ formatAsRFC1123(now.getTime()) + "\r\n"
				+ "Server: SiteRiter Server/8.0\r\n" + "Content-Type: " + type
				+ "\r\n" + "Expires: " + formatAsRFC1123(expires) + "\r\n"
				+ "Content-Length: " + bytes.length + "\r\n"
				+ "Last-modified: " + formatAsRFC1123(now.getTime()) + "\r\n"
				+ "\r\n";
		getWriter().write(header);
		getWriter().write(response);
	}

	/**
	 * A default robots.txt response. This will be sent through the Socket if
	 * the SDLParser does not have a ROBOTS_TXT rule loaded.
	 */
	private static final String ROBOTS_TXT = "# Default robots.txt for the SiteRiter SDL server\n"
			+ "User-agent: *\n"
			+ "Disallow: /  # Don't say we didn't warn you..\n";

	/**
	 * Blocks all threads after this is called from generating any pages, and
	 * waits for all threads currently generating pages to finish. Then, reloads
	 * {@link #parser} with a new {@link FileReader}, which reads the file at
	 * {@link #sdlPath}. After reloading finishes, Threads are allowed to
	 * generate pages again.
	 *
	 * @throws InterruptedException
	 *             If anything happens to this thread while it is waiting at
	 *             {@link Ballroom#lock()} for any generating threads to
	 *             complete.
	 *
	 * @throws IOException
	 *             If {@link SDLParser#load(Reader)} fails for any reason
	 */
	private void reload() throws InterruptedException, IOException
	{
		/*
		 * Lock, and therefore wait for all other critical threads to call
		 * ballroom.CheckOut()
		 */
		ballroom.lock();
		{
			Reader r = new FileReader(sdlPath);
			parser.load(r);
			r.close(); /* Close this to let the user edit it again */
			System.err.println("Reloading complete!");
		}
		ballroom.unlock(); /*
							 * Unlock, and therefore allow any other waiting
							 * threads to finish calling ballroom.CheckIn()
							 */
	}

	/**
	 * Loads the selector table with information from the request. This is done
	 * just like using PHP's {@code $_GET} array to get variables out of the
	 * URL, where the syntax looks like:<br>
	 * <br>
	 * {@code http://HOST_NAME:PORT/SOME/PATH?value1=key1&value2=key2}<br>
	 * <br>
	 * The user may insert as many keys and values as they wish with this
	 * syntax. Unfortunately, this is only going to be useful if the user knows
	 * what the names of the selectors are in the SDL file that is currently
	 * loaded.
	 *
	 * @param request
	 *            The request to grab selector information from.
	 * @param selectors
	 *            The table of selectors to insert into.
	 */
	private void insertSelectorsFromRequest(String request,
			Map<String, Integer> selectors)
	{
		if (request.contains("?"))
		{
			request = request.substring(request.indexOf("?") + 1);
			String[] selectorArgs = request.split("&");

			for (int i = 0; i < selectorArgs.length; i++)
			{
				String[] parts = selectorArgs[i].split("=");
				String key = parts[0];
				if (parts.length == 2)
				{
					try
					{
						int value = Integer.parseInt(parts[1]);
						selectors.put(key, value);
					} catch (NumberFormatException e)
					{} /*
						 * This argument wasn't formed correctly, but meh, let's
						 * just throw it away.
						 */
				}
			}
		}
	}

	private void sendGeneratedPage(String request,
			Map<String, Integer> selectors, long expires) throws InterruptedException,
			IOException
	{
		/*
		 * Check in to the critical section. This blocks any reload requests
		 * until all threads in this section exit.
		 */
		ballroom.checkIn();
		{
			if (request.equals("/robots.txt"))
			{
				String r = parser.makePage(request, selectors);
				if (r == null)
				{
					r = ROBOTS_TXT;
				}
				writeResponse(r, "text/plain", expires);
			}
			else
			{
				String b = parser.makePage(request, selectors);
				writeResponse(b, "text/HTML", expires);
			}
		}
		ballroom.checkOut();
		/*
		 * Make sure to check out of the critical section so any reload requests
		 * are able to block access
		 */
	}

	@Override
	public void respond(String request) throws IOException
	{

		// DONE: Extra credit component: Design and implement a way to load
		// selectors based on the URL!
		Map<String, Integer> selectors = new HashMap<String, Integer>();
		insertSelectorsFromRequest(request, selectors);

		if (request.equals("/reload"))
		{
			try
			{
				reload();
				sendGeneratedPage(request, selectors, 0);
				/*
				 *  Get a new page, but set the timeout to 0 so it can
				 *  reload at any time.
				 */
			} catch (InterruptedException e)
			{
				writeResponse("Thread interrupted. Try again later.",
						"text/plain", 0);
			} catch (SDLParseException e)
			{
				writeResponse("Parser could not load: " + e.getMessage()
						+ "... Exiting.", "text/plain", 0);
				System.err.println("Parser could not load from reload request!"
						+ e.getMessage());
				System.exit(1);
			}
		}
		else
		{
			try
			{
				sendGeneratedPage(request, selectors, MS_PER_DAY);
			} catch (InterruptedException e)
			{
				writeResponse(
						"Thread interrupted during page creation. Try again later.",
						"text/plain", 0);
				return;
			}
		}
	}
}
