package com.putable.tilenet.Util.XMLTags;

import com.putable.tilenet.Util.XMLParser;

public class driver2 {

	public static void main(String[] args) throws Exception {

		XMLParser xmlp = new XMLParser();

		// xmlp.beginParse("/Users/Nick/Desktop/tester.txt");
		// xmlp.beginParse("src/com/putable/tilenet/Util/SampleText/client2server.txt");

		for (XMLTag x : xmlp.getBQueue()) {
			System.out.println(x.toString());
		}
	}

}