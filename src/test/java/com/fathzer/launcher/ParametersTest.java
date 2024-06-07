package com.fathzer.launcher;

import static org.junit.Assert.*;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import org.junit.Test;

public class ParametersTest {
	@SuppressWarnings("unused")
	private static class LoggerWithNoDefaultConstructor implements Logger {
		String name;
		LoggerWithNoDefaultConstructor(String name) {
			this.name = name;
		}

		@Override
		public void wrongJavaVersion(Version min, String current) {
		}

		@Override
		public void fatalError(Exception exception) {
		}
	}
	
	private static final String COOL_APP = "com.fathzer.launcher.test.TestApp";
	private static final Version TOO_SMALL = new Version("1");
	private static final Version COOL_VERSION = new Version("1.8");
	private static final Method CUSTOM_LOGGER_FROM_CLASS_NAME;
	
	static {
		try {
			CUSTOM_LOGGER_FROM_CLASS_NAME = Parameters.class.getDeclaredMethod("getCustomLogger", String.class);
			CUSTOM_LOGGER_FROM_CLASS_NAME.setAccessible(true);
		} catch (NoSuchMethodException | SecurityException e) {
			throw new RuntimeException(e);
		}
	}
	
	@Test
	public void test() throws IOException {
		Parameters params = new Parameters(new Version("17"), "myClass");
		assertTrue(params.getLogger() instanceof Console);
		
		params = Parameters.get(new Utils.ResourceStreamSupplier("settings.properties"));
		assertTrue(params.getLogger() instanceof Swing);
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void testNullVersion1() {
		new Parameters(null, COOL_APP);
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void testWrongVersion() {
		new Parameters(TOO_SMALL, COOL_APP);
	}

	@Test(expected = IllegalArgumentException.class)
	public void testWrongClass() {
		new Parameters(COOL_VERSION, " ");
	}

	@Test(expected = IllegalArgumentException.class)
	public void testNullClass() {
		new Parameters(COOL_VERSION, null);
	}

	@Test(expected = IllegalArgumentException.class)
	public void testNullLogger() {
		new Parameters(COOL_VERSION, COOL_APP).setLogger(null);
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void testUnkownLogger() throws Throwable {
		checkCustomLoggerError("unknown");
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void testNotALogger() throws Throwable {
		checkCustomLoggerError("java.lang.StringBuffer");
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void testLoggerWithNoConstructor() throws Throwable {
		checkCustomLoggerError(LoggerWithNoDefaultConstructor.class.getName());
	}

	private static void checkCustomLoggerError(String className) throws Throwable {
		try {
			CUSTOM_LOGGER_FROM_CLASS_NAME.invoke(null, className);
		} catch (IllegalAccessException | IllegalArgumentException e) {
			fail("Unable to call method");
		} catch (InvocationTargetException e) {
			throw e.getCause();
		}
	}
}
