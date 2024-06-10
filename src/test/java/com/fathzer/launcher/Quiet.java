package com.fathzer.launcher;

/** A logger that outputs nothing.
 */
public class Quiet implements Logger {

	public void wrongJavaVersion(Version min, String current) {
		// Nothing to do
	}

	@Override
	public void fatalError(Exception exception) {
		// Nothing to do
	}
}
