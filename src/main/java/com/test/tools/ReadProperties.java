package com.test.tools;

public class ReadProperties {
	private static ReadProperties instance = null;

	private ReadProperties() {
		
	}

	public static ReadProperties getInstance() {
		if (instance == null) {
			instance = new ReadProperties();
		}

		return instance;
	}
}
