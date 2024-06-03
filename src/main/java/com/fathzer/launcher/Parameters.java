package com.fathzer.launcher;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import com.fathzer.launcher.Utils.InputStreamSupplier;

public class Parameters {
	private final float min;
	private final String className;
	private Output out;
	
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

	public float getMinJavaVersion() {
		return min;
	}

	public String getClassName() {
		return className;
	}

	public Output getOutput() {
		return out;
	}

	public void setOutput(Output out) {
		this.out = out;
	}
}
