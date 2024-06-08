package com.fathzer.launcher;

/** A class that can output messages when application launch fails.
 */
public interface Logger {
	/** Reports that java version is not supported.
	 * @param min The minimum java version required.
	 * @param current The current java version
	 */
	void wrongJavaVersion(Version min, String current);

	/** Reports a fatal error.
	 * @param exception The exception
	 */
	void fatalError(Exception exception);
}
