package com.putable.tilenet.blueprints;

import com.putable.tilenet.layout.HomeLayout;


public class SplashGridFactory implements GridFactory {

	@Override
	public Layout addLayout() {
		return new HomeLayout();
	}	
}
