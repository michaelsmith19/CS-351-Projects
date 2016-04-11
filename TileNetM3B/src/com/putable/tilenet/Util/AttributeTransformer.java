package com.putable.tilenet.Util;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

import javax.imageio.ImageIO;

/**
 * Serves to translate strings into and out of proper TileNet protocol.
 * 
 * @author ClassCastExceptions
 * 
 */

public class AttributeTransformer {

/**
     * Converts all instances of '&', '<', and '"' to proper TileNet form
     * so that they may be transmitted as attribute values.
     * 
     * @param unformatted - the string requiring a transformation. 
     * @return The 'transformed' string
     */
	public static String stringToAttribute(String unformatted) {
		// First, all instance of the '&' character
		// Then, all instance of '<' and '"'
		String transformed = unformatted.replace("&", "&amp;")
				.replace("<", "&lt;").replace("\"", "&quot;");

		return transformed;
	}

	/**
	 * Converts all instances of "amp;", "&lt;", and "&quot;" to their
	 * respective English counterparts.
	 * 
	 * @param formatted
	 *            - the attribute value to be transformed.
	 * @return The transformed string
	 */
	public static String attributeToString(String formatted) {
		String transformed = formatted.replace("&amp;", "&")
				.replace("&lt;", "<").replace("&quot;", "\"");

		return transformed;
	}

	/**
	 * Converts a hex string representing a byte[] representing an image into a
	 * BufferedImage. Assumes .png for all images.
	 * 
	 * @param hex
	 *            - The hex string containing image data.
	 * @return BufferedImage generated from given hex string.
	 * @throws IOException
	 */
	public static BufferedImage hex2Image(String hex) throws IOException {
		byte[] newbytes = new byte[hex.length() / 2];
		for (int i = 0; i < newbytes.length; i++) {
			String sub = hex.substring(2 * i, 2 * i + 2);
			newbytes[i] = (byte) Integer.parseInt(sub, 16);
		}
		BufferedImage img = ImageIO.read(new ByteArrayInputStream(newbytes));
		return img;
	}

	/**
	 * Takes a BufferedImage and converts it into a hex string representing a
	 * byte[] of image data.
	 * 
	 * @param img
	 *            - The BufferedImage to be converted to hex.
	 * @return A hex string representing image data
	 * @throws IOException
	 */
	public static String image2Hex(BufferedImage img) throws IOException {
		ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
		ImageIO.write(img, "png", outputStream);
		StringBuilder sb = new StringBuilder();
		byte[] ba = outputStream.toByteArray();
		for (byte b : ba) {
			String s = Integer.toHexString(unsignThis(b));
			if (s.length() == 1)
				s = "0" + s;
			sb.append(s);
		}
		return sb.toString();
	}

	private static int unsignThis(byte b) {
		return b & 0xFF;
	}
}
