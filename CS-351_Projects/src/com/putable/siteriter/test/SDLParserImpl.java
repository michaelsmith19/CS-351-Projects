package com.putable.siteriter.test;

import java.io.IOException;
import java.io.Reader;
import java.util.Map;

import com.putable.siteriter.SDLParser;

public class SDLParserImpl implements SDLParser{

	@Override
	public void load(Reader reader) throws IOException {
		throw new IOException();
	}

	@Override
	public String makePage(String key, Map<String, Integer> selectors) {
		return null;
	}

	
}
