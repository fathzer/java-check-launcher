package com.fathzer.launcher;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.InvocationTargetException;
import java.util.Properties;

import com.fathzer.launcher.Utils.InputStreamSupplier;

/** The parameters used to check if java version is compatible with the application.
 */
public class Parameters {
	private static final String CUSTOM_LOGGER_PREFIX = "class:";
	private static final Version MINIMUM_SUPPORTED_JAVA_VERSION = new Version("1.2");
	private final Version min;
	private final String className;
	private Logger logger;
	
	/** Constructor.
	 * @param min The minimum major java version (1.2, 1.3, ..., 1.8, 9, etc)
	 * @param className The main class of the application.
	 * @throws IllegalArgumentException if min &lt; 1.2 or className if null or blank.
	 */ 
	public Parameters(Version min, String className) {
		if (min.compareTo(MINIMUM_SUPPORTED_JAVA_VERSION)<0) {
			throw new IllegalArgumentException("Illegal Java min version "+min);
		}
		if (className==null || className.trim().length()==0) {
			throw new IllegalArgumentException("Main class is missing");
		}
		this.min = min;
		this.className = className;
		this.logger = new Console();
	}

	/** Reads a Parameters instance from an inputStream on a property file or resource.
	 * @param supplier The input stream supplier
	 * @return The Parameters that was read.
	 * @throws IOException if something when wrong while reading the input stream.
	 * @throws IllegalArgumentException if the content is illegal (java version &lt; 1.2, classname missing, etc...)  
	 */
	public static Parameters get(InputStreamSupplier supplier) throws IOException {
		final Properties prop = new Properties();
		InputStream in = supplier.get();
		try {
			prop.load(in);
		} finally {
			in.close();
		}
		final String min = prop.getProperty("min.java.version");
		if (min==null) {
			throw new IllegalArgumentException("Minimum java version is missing");
		}
		final String className = prop.getProperty("main.class");
		final Parameters parameters = new Parameters(new Version(min), className);
		final String out = prop.getProperty("logger","");
		if ("Swing".equalsIgnoreCase(out)) {
			parameters.setLogger(new Swing());
		} else if (out.startsWith(CUSTOM_LOGGER_PREFIX)) {
			// Custom logger
			parameters.setLogger(getCustomLogger(out.substring(CUSTOM_LOGGER_PREFIX.length())));
		}
		return parameters;
	}

	private static Logger getCustomLogger(String className) {
		try {
			final Class theClass = Class.forName(className);
			return (Logger) theClass.getDeclaredConstructor().newInstance();
		} catch (ReflectiveOperationException e) {
			throw new IllegalArgumentException("Unable to create class "+className+" with no argument constructor");
		} catch (ClassCastException e) {
			throw new IllegalArgumentException("Class "+className+" does not implements "+Logger.class.getName());
		} catch (SecurityException e) {
			throw new IllegalArgumentException("Unable to create class "+className+" with no argument constructor");
		}
	}

	/** Gets the java minimum major version required by the application.
	 * @return a version always &gt;= 1.2
	 */
	public Version getMinJavaVersion() {
		return min;
	}

	/** Gets the application's main class name.
	 * @return a non null String
	 */
	public String getClassName() {
		return className;
	}

	/** Gets the logger to be used to give information if application can't be launched.
	 * <br>The default value is an instance of {@link Console}
	 * @return a non null Logger instance.
	 */
	public Logger getLogger() {
		return logger;
	}

	/** Sets the logger to be used to give information if application can't be launched. 
	 * @param logger The logger to use.
	 * @throws IllegalArgumentException if logger is null.  
	 */
	public void setLogger(Logger logger) {
		if (this.logger==null) {
			throw new IllegalArgumentException("Output can't be null");
		}
		this.logger = logger;
	}
}
