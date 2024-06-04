package com.fathzer.launcher;

import java.lang.reflect.Method;

import com.fathzer.launcher.Utils.InputStreamSupplier;
import com.fathzer.launcher.Utils.ResourceStreamSupplier;

/** The main class that checks the java version and launch the application if it's requirements are fulfilled.
 */
public class Launcher {
	private static final String DEFAULT_PARAM_SOURCE_PATH = "settings.properties";
	
	/** The main method, that reads the parameters, checks the java version and runs the application if requirements are fulfilled.
	 * <br>If the java version is not supported, the process exits with a -1 code. If an error occurs (for instance if parameters are invalid), the process exits with a -2 code.
	 * <br>Have a look at <a href="https://github.com/fathzer/java-check-launcher/">the documentation</a> to known how to pass the parameters.
	 * @param args The application arguments (passed to the main method of application).
	 */
	public static void main(String[] args) {
		new Launcher().doMain(args, new Console());
	}
	
	void doMain(String[] args, Output defaultOutput) {
		doMain(args, defaultOutput, new ResourceStreamSupplier(DEFAULT_PARAM_SOURCE_PATH));
	}

	/** Checks the java version, then run the application if requirements are fulfilled.
	 * The exit codes in case of problem are the same as in the main method.
	 * @param args The arguments to pass to the application's main method.
	 * @param defaultOutput The Output to use if an error occurs while reading the parameters.
	 * @param paramSource The source where to read the parameters.
	 * @see #main(String[])
	 */
	protected void doMain(String[] args, Output defaultOutput, InputStreamSupplier paramSource) {
		try {
			final Parameters params = Parameters.get(paramSource);
			defaultOutput = params.getOutput();
			if (!launch(params, args)) {
				error(-1);
			}
		} catch (Exception e) {
			defaultOutput.error(null, e);
			error(-2);
		}
	}
	
	/** This method is called when the {@link #doMain(String[], Output, InputStreamSupplier)} method does not launch the application.
	 * <br>The default implementation calls System.exit with the code passed to the method.
	 * <br>One can override this method to prevent the process from exiting.
	 * @param code The problem's code: -1 -> java version is not compatible with the application, -2 -> A misconfiguration was detected 
	 */
	protected void error(int code) {
		System.exit(code);
	}
	
	/** Checks if application can be launched, and launch it if requirements are fulfilled.
	 * @param params The parameters of the check.
	 * @param args The arguments of the application's main method.
	 * @return true if the application was launched, false if the java version is not compatible with the application.
	 * @throws ReflectiveOperationException If the java version meets the requirements but we were unable to launch the application
	 * (typically because an unknown class was passed in the parameters or a class with no main method).
	 */
	public boolean launch(Parameters params, String[] args) throws ReflectiveOperationException {
		final String current = System.getProperty("java.specification.version");
		if (Float.parseFloat(current)<params.getMinJavaVersion()) {
			String message = "<html>Your current java version is "+current+
					".<br>This application requires Java "+params.getMinJavaVersion()+" or more.<br>Please have a look at <a href=\"https://www.oracle.com/java\">https://www.oracle.com/java</a>";
			params.getOutput().error(message, null);
		} else {
			// Warning: In java 1.2, getMethod and invoke requires arrays of arguments (the variable argument length notation, with ..., is not available in the java 1.2 language)
			Class[] argsClass = new Class[]{String[].class};
			Method method = Class.forName(params.getClassName()).getMethod("main", argsClass);
			Object[] arguments = new Object[]{args};
			method.invoke(null, arguments);
			return true;
		}
		return false;
	}
/*
	private void error(Throwable e) {
		String pattern = "<html>A fatal error occurs ({0}).<br>Maybe a program file is corrupted.<br>" +
		"<br>You should try to install the application again in order to fix it.<br></html>";
		final Object[] args = new Object[]{e.toString()};
		error(MessageFormat.format(pattern, args));
	}

	private void error(String message) {
		JOptionPane.showMessageDialog(null, message, "Sorry, unable to launch application", JOptionPane.ERROR_MESSAGE);
	}*/
}
