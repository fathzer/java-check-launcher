package com.fathzer.launcher;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class Parameters {
	private final float min;
	private final String className;
	private Output out;
	
	public Parameters(float min, String className) {
		this.min = min;
		this.className = className;
	}

	public static Parameters get() throws IOException {
		final Properties prop = new Properties();
		InputStream in = Parameters.class.getResourceAsStream("settings.properties");
		if (in==null) {
			throw new IOException("Can't read "+Utils.getPackageName(Parameters.class).replace('.', '/')+"/settings.properties resource");
		}
		try {
			prop.load(in);
		} finally {
			in.close();
		}
		final float min = Float.parseFloat(prop.getProperty("min.java.version"));
		final String className = prop.getProperty("main.class");
		return new Parameters(min, className);
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
