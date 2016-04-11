package com.putable.tilenet.Util;

import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

import javax.imageio.ImageIO;

import com.putable.tilenet.Util.XMLTags.ClientTag;
import com.putable.tilenet.Util.XMLTags.LoginTag;
import com.putable.tilenet.Util.XMLTags.SetTag;
import com.putable.tilenet.Util.XMLTags.XMLTag;
import com.putable.tilenet.Util.XMLTags.XMLTag.TagType;
import com.putable.tilenet.blueprints.Element;
import com.putable.tilenet.blueprints.Element.ElemType;
import com.putable.tilenet.blueprints.ElementBuilder;
import com.putable.tilenet.blueprints.TileNetElementBuilder;
import com.putable.tilenet.tests.AutoBot;

/**
 * A Class that holds common utility methods in a static forum
 * 
 * @author ClassCastExceptions
 */
public class Common {
	private static long uniqueCounter;
	private static final long botTimeoutMilli = 8000;
	// Courtesy of StackOverFlow for the idea
	private static final char[] inHex = "0123456789ABCDEF".toCharArray();

	public final static String clientHeader = "<?xml version='1.0' encoding='utf-8'?><!DOCTYPE client SYSTEM \"http://putable.com/TileNet/TileNet1.0.dtd\">";
	public static final List<String> botcmd_ConnectDisconnect = Arrays.asList(
			"WAIT_RESPONCE", "SEND:" + clientHeader + new ClientTag(), "SEND:"
					+ new LoginTag("gladios", "cake"), "WAIT_RESPONCE",
			"EXPECTED:T:LOGGEDIN", "DISCONNECT");
	public static final List<String> botcmd_Connect = Arrays.asList(
			"WAIT_RESPONCE", "SEND:" + clientHeader + new ClientTag(), "SEND:"
					+ new LoginTag("gladios", "cake"), "WAIT_RESPONCE",
			"EXPECTED:T:LOGGEDIN");
	public static final List<String> botcmd_ConnectTalk = Arrays.asList(
			"WAIT_RESPONCE", "SEND:" + clientHeader + new ClientTag(), "SEND:"
					+ new LoginTag("gladios", "cake"), "WAIT_RESPONCE",
			"EXPECTED:T:LOGGEDIN",
			"Z:I'm going to kill you, and all the cake is gone.");

	public static long getID() {
		uniqueCounter++;
		return uniqueCounter;
	}

	public static BufferedReader getReaderFromSock(Socket sock)
			throws IOException {
		return new BufferedReader(new InputStreamReader(sock.getInputStream()));
	}

	public static InputStream getInputStreamFromSock(Socket sock)
			throws IOException {
		return sock.getInputStream();
	}

	public static PrintWriter getWriterFromSock(Socket sock) throws IOException {
		return new PrintWriter(sock.getOutputStream(), true);
	}

	public static boolean sendTheBots(int port, int count, List<String> commands)
			throws UnknownHostException, IOException, InterruptedException,
			ExecutionException {
		boolean success = true;
		ExecutorService botSwarm = Executors.newCachedThreadPool();
		List<Future<Boolean>> backToThe = new ArrayList<Future<Boolean>>();
		// Create count AutoAgents

		for (int i = 0; i < count; i++) {
			AutoBot jones = new AutoBot(null, port, commands);
			backToThe.add(botSwarm.submit(jones));
		}
		for (Future<Boolean> f : backToThe)
			success &= f.get();

		// Tell thread pool to shutdown when there's nothing left.
		botSwarm.shutdown();
		try {
			botSwarm.awaitTermination(botTimeoutMilli, TimeUnit.MILLISECONDS);
		} catch (InterruptedException e) {
			return false;
		}

		return success;

	}

	/**
	 * Creates a {@link BufferedImage} from a given {@link String} value.
	 * 
	 * @param s
	 *            The {@link String} that contains image hex data.
	 * @return The {@link BufferedImage} that is created from the String of
	 *         data.
	 */
	public static BufferedImage toBufferedImage(String s) {
		// TODO error checking
		byte[] bytes = new byte[s.length() / 2];
		for (int i = 0; i < s.length(); i += 2)
			bytes[i / 2] = (byte) ((Character.digit(s.charAt(i), 16) << 4) + Character
					.digit(s.charAt(i + 1), 16));
		InputStream in = new ByteArrayInputStream(bytes);
		BufferedImage bi = null;
		try {
			bi = ImageIO.read(in);
		} catch (IOException e) {
			System.err.println("error parsinf image hex data");
		}
		return bi;
	}

	/**
	 * Takes in a {@link File} and attempts to parse it into a
	 * {@link BufferedImage}. Then it converts the image into a Byte array to be
	 * returned as a {@link String}.
	 * 
	 * @param f
	 *            The file to be parsed into a {@link BufferedImage}
	 * @return The {@link String} representation of the images hex data.
	 */
	public static String toHexData(File f) {
		BufferedImage bi;
		ByteArrayOutputStream bin = null;
		try {
			bi = ImageIO.read(f);
			bin = new ByteArrayOutputStream();
			ImageIO.write(bi, "png", bin);
		} catch (IOException e) {
			System.err.println("error reading file");
		}
		byte[] bytes = bin.toByteArray();
		char[] hex = new char[bytes.length * 2];
		for (int i = 0; i < bytes.length; i++) {
			int x = bytes[i] & 0xFF;
			hex[i * 2] = inHex[x >>> 4];
			hex[i * 2 + 1] = inHex[x & 0x0F];
		}
		return new String(hex);
	}

	/**
	 * Takes an XMLTag and attempts to turn the tag into a {@link Element} If
	 * the {@link XMLTag} is not a {@link SetTag} then it will give an error
	 * message.
	 * 
	 * @param xml
	 *            The {@link XMLTag} to be converted to an {@link Element}.
	 * @return The {@link Element} that represents the {@link SetTag}.
	 */
	public static Element toElement(XMLTag xml) {
		if (xml.getTagType() != TagType.SET) {
			System.err
					.println("You cannot convert from a non SET XMLTag to element");
			return null;
		}
		SetTag tag = (SetTag) xml;
		ElementBuilder makeElements = new TileNetElementBuilder();
		Element ele = null;
		switch (tag.getObjid().charAt(0)) {
		case 'a':
			ele = makeElements.orderElement(ElemType.AGENT);
			ele.type = ElemType.AGENT;
			break;
		case 'k':
			ele = makeElements.orderElement(ElemType.KEY);
			ele.type = ElemType.KEY;
			break;
		case 't':
			ele = makeElements.orderElement(ElemType.TOKEN);
			ele.type = ElemType.TOKEN;
			break;
		case 'i':
			ele = makeElements.orderElement(ElemType.IMAGE);
			ele.type = ElemType.IMAGE;
			break;
		case 'm':
			ele = makeElements.orderElement(ElemType.MATRIX);
			ele.type = ElemType.MATRIX;
			break;
		default:
			System.err.println("Cannot create Element from " + tag);
			// TODO disconnect from the server. Say that the server
			// was IN ERROR since it sent a tag that could not be
			// turned into an element.
		}

		ele.tag = tag;
		ele.setId(tag.getObjid());
		ele.setName(tag.getName());

		return ele;
	}

	/**
	 * Tells whether the given {@link String} conforms to the definition of a
	 * whisper.
	 * 
	 * @param sayCmd
	 *            The {@link String} to be examined.
	 * @return True only if the {@link String} has a '(' as a first character
	 *         and ')' as a last character after leading and trailing whitespace
	 *         is trimmed.
	 */
	public static boolean isWhisper(String sayCmd) {

		String cmdLessWhitespace = sayCmd.trim();

		if (cmdLessWhitespace.charAt(0) == '('
				&& cmdLessWhitespace.charAt(cmdLessWhitespace.length() - 1) == ')')
			return true;

		return false;
	}

	/**
	 * Takes the message as a {@link String} and changes it to the proper
	 * message for the {@link HearTag}.
	 * 
	 * @param message
	 *            The {@link String} message from the {@link CMDTag} say.
	 * @return The {@link String} with (whisper) added to the front and the name
	 *         of the {@link Agent} it is going to removed.
	 */
	public static String changeToWhisperMessage(String message) {
		String whisper = "(whisper)";
		message = message.trim();
		int i = 1;
		while (Character.isWhitespace(message.charAt(i))) {
			i++;
		}
		while (!(Character.isWhitespace(message.charAt(i)))) {
			i++;
		}
		while (message.charAt(i) != ')') {
			whisper += message.charAt(i);
			i++;
		}
		return whisper;
	}

	/**
	 * Gets the {@link Agent} that the whisper should be sent to.
	 * 
	 * @param message
	 *            The full {@link String} from the say {@link CMDTag}.
	 * @return The {@link String} of the receiving agents name.
	 */
	public static String getToFromWhisper(String message) {
		String to = "";
		message = message.trim();
		int i = 1;
		while (Character.isWhitespace(message.charAt(i))) {
			i++;
		}
		while (!(Character.isWhitespace(message.charAt(i)))) {
			to += message.charAt(i);
			i++;
		}
		return to;
	}

}
