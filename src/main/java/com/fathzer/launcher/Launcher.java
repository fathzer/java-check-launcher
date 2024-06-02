package com.fathzer.launcher;

import java.io.IOException;
import java.lang.reflect.Method;
import java.text.MessageFormat;

import javax.swing.JOptionPane;

public class Launcher {
	public static void main(String[] args) {
		new Launcher().doMain(args, new Console());
	}
	
	void doMain(String[] args, Output defaultOutput) {
		try {
			final Parameters params;
			params = Parameters.get();
			if (launch(params, args)) {
				exit(-1);
			}
		} catch (IOException e) {
			defaultOutput.error(null, e);
		}
		exit(-2);
	}
	
	void exit(int code) {
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
				Method method = Class.forName(params.getClassName()).getMethod("main", new Class[]{String[].class});
				method.invoke(null, new Object[]{args});
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
		error(MessageFormat.format(pattern, new Object[]{e.toString()}));
	}

	static void error(String message) {
		JOptionPane.showMessageDialog(null, message, "Sorry, unable to launch application", JOptionPane.ERROR_MESSAGE);
	}
}
