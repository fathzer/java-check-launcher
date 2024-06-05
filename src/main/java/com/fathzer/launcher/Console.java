package com.fathzer.launcher;

import java.text.MessageFormat;
import java.util.ResourceBundle;

public class Console implements Logger {
	private static final String WRONG_JAVA_VERSION_PATTERN;
	private static final String FATAL_ERROR_MESSAGE;

	static {
		final ResourceBundle bundle = Localization.MESSAGES;
		WRONG_JAVA_VERSION_PATTERN = bundle.getString("console.unsupported.java.version.pattern");
		FATAL_ERROR_MESSAGE = bundle.getString("console.fatal.error.message");
	}

	public void fatalError(Exception exception) {
		System.err.println(FATAL_ERROR_MESSAGE);
		exception.printStackTrace();
	}

	public void wrongJavaVersion(Version min, String current) {
		final Object[] args = new Object[] {min, current};
		System.err.println(MessageFormat.format(WRONG_JAVA_VERSION_PATTERN, args));
	}
}
