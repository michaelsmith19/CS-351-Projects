package com.putable.siteriter.abstractserver;

import java.io.IOException;

public class ServerException extends IOException {
	private static final long serialVersionUID = 1L;

	public ServerException(String s) {
		super(s);
	}
}
