package com.fathzer.launcher;

import java.text.MessageFormat;
import java.util.ResourceBundle;

import javax.swing.JOptionPane;

public class Swing implements Logger {
	private static final String DIALOG_TITLE;
	private static final String WRONG_JAVA_VERSION_PATTERN;
	private static final String FATAL_ERROR_PATTERN;
	
	static {
		final ResourceBundle bundle = Localization.MESSAGES;
		DIALOG_TITLE = bundle.getString("swing.dialog.title");
		WRONG_JAVA_VERSION_PATTERN = bundle.getString("swing.unsupported.java.version.pattern");
		FATAL_ERROR_PATTERN = bundle.getString("swing.fatal.error.pattern");
	}
	
	public void fatalError(Exception exception) {
		final Object[] args = new Object[]{exception.toString()};
		error(MessageFormat.format(FATAL_ERROR_PATTERN, args));
	}
	
	private void error(String message) {
		JOptionPane.showMessageDialog(null, message, DIALOG_TITLE, JOptionPane.ERROR_MESSAGE);
	}

	public void wrongJavaVersion(Version min, String current) {
		final Object[] args = new Object[] {min, current};
		error(MessageFormat.format(WRONG_JAVA_VERSION_PATTERN, args));
	}
}
