package com.fathzer.launcher;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import com.fathzer.launcher.Utils.InputStreamSupplier;

/** The parameters used to check if java version is compatible with the application.
 */
public class Parameters {
	private final float min;
	private final String className;
	private Output out;
	
	/** Constructor.
	 * @param min The minimum major java version (1.2, 1.3, ..., 1.8, 9, etc)
	 * @param className The main class of the application.
	 * @throw IllegalArgumentException if min<1.2 or className if null or blank.
	 */
	public Parameters(float min, String className) {
		if (min<1.2) {
			throw new IllegalArgumentException("Illegal Java min version "+min);
		}
		if (className==null || className.trim().length()==0) {
			throw new IllegalArgumentException("Main class is missing");
		}
		this.min = min;
		this.className = className;
		this.out = new Console();
	}

	/** Reads a Parameters instance from an inputStream on a property file or resource.
	 * @param supplier The input stream supplier
	 * @return The Parameters that was read.
	 * @throws IOException if something when wrong while reading the input stream.
	 * @throws IllegalArgumentException if the content is illegal (java version < 1.2, classname missing, etc...)  
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
		final Parameters parameters = new Parameters(Float.parseFloat(min), className);
		final String out = prop.getProperty("gui","");
		if ("Swing".equalsIgnoreCase(out)) {
			parameters.setOutput(new Swing());
		}
		return parameters;
	}

	/** Gets the java minimum major version required by the application.
	 * @return a float &gt;= 1.2
	 */
	public float getMinJavaVersion() {
		return min;
	}

	/** Gets the application's main class name.
	 * @return a non null String
	 */
	public String getClassName() {
		return className;
	}

	/** Gets the output to be used to give information if application can't be launched.
	 * <br>The default value is an instance of {@link Console}
	 * @return a non null Output instance.
	 */
	public Output getOutput() {
		return out;
	}

	/** Sets the output to be used to give information if application can't be launched. 
	 * @param out The output to use.
	 * @throws IllegalArgumentException if output is null.  
	 */
	public void setOutput(Output out) {
		if (this.out==null) {
			throw new IllegalArgumentException("Output can't be null");
		}
		this.out = out;
	}
}
