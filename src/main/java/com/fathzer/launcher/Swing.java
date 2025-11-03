package com.fathzer.launcher;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.text.MessageFormat;
import java.util.ResourceBundle;

import javax.swing.JOptionPane;

/** A {@link Logger} that outputs its messages to a Swing dialog.
 */
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
		error(MessageFormat.format(FATAL_ERROR_PATTERN, new String[] {getHtmlStackTrace(exception)}));
	}

	public void wrongJavaVersion(Version min, String current) {
		final Object[] args = new Object[] {current, min};
		error(MessageFormat.format(WRONG_JAVA_VERSION_PATTERN, args));
	}

	private void error(String message) {
		JOptionPane.showMessageDialog(null, message, DIALOG_TITLE, JOptionPane.ERROR_MESSAGE);
	}
	
	private static String getHtmlStackTrace(Throwable e) {
		String htmlTrace = Utils.replaceAll(getStackTrace(e), "\t","&nbsp;&nbsp;");
		return Utils.replaceAll(htmlTrace,System.getProperty("line.separator"), "<br>");
	}
	
    private static String getStackTrace(Throwable t) {
        StringWriter sw = new StringWriter();
        PrintWriter pw = new PrintWriter(sw);
        t.printStackTrace(pw);
        pw.flush();
        return sw.toString();
    }
}
