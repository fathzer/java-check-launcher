package com.fathzer.launcher;

import java.util.ResourceBundle;

class Localization {
	static final ResourceBundle MESSAGES = ResourceBundle.getBundle(Utils.getPackageName(Localization.class)+".Resources");

	private Localization() {
		super();
	}
}
