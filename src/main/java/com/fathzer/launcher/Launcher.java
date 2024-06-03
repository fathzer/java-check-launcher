package com.fathzer.launcher;

import java.io.IOException;
import java.lang.reflect.Method;
import java.text.MessageFormat;

import javax.swing.JOptionPane;

public class Launcher {
	public static void main(String[] args) {
		new Launcher().doMain(args, new Console());
	}
	
	protected void doMain(String[] args, Output defaultOutput) {
		try {
			final Parameters params;
			params = Parameters.get();
			if (!launch(params, args)) {
				exit(-1);
			}
		} catch (IOException e) {
			defaultOutput.error(null, e);
			exit(-2);
		}
	}
	
	protected void exit(int code) {
		System.exit(code);
	}
	
	public boolean launch(Parameters params, String[] args) {
		final String current = System.getProperty("java.specification.version");
		if (Float.parseFloat(current)<params.getMinJavaVersion()) {
			String message = "<html>Your current java version is "+current+
					".<br>This application requires Java "+params.getMinJavaVersion()+" or more.<br>Please have a look at <a href=\"https://www.oracle.com/java\">https://www.oracle.com/java</a>";
			error(message);
		} else {
			try {
				// Warning: In java 1.2, getMethod and invoke requires arrays of arguments (the variable argument length notation, with ..., is not available in the java 1.2 language)
				Class[] argsClass = new Class[]{String[].class};
				Method method = Class.forName(params.getClassName()).getMethod("main", argsClass);
				Object[] arguments = new Object[]{args};
				method.invoke(null, arguments);
				return true;
			} catch (Exception e) {
				error(e);
			}
		}
		return false;
	}

	private static void error(Throwable e) {
		String pattern = "<html>A fatal error occurs ({0}).<br>Maybe a program file is corrupted.<br>" +
		"<br>You should try to install the application again in order to fix it.<br></html>";
		final Object[] args = new Object[]{e.toString()};
		error(MessageFormat.format(pattern, args));
	}

	static void error(String message) {
		JOptionPane.showMessageDialog(null, message, "Sorry, unable to launch application", JOptionPane.ERROR_MESSAGE);
	}
}
