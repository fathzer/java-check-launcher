package com.fathzer.launcher;

import static org.junit.Assert.*;

import java.util.Arrays;
import java.util.Collection;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import com.fathzer.launcher.test.TestApp;

@RunWith(Parameterized.class)
public class LauncherFailTest {
	private String resourcePath;
	private Integer expectedExitCode;
	private NoExitLauncher launcher;
	
	@Before
	public void initialize() {
		TestApp.reset();
		launcher = new NoExitLauncher();
	}
	
	public LauncherFailTest(String resourcePath, Integer expectedExitCode) {
		this.resourcePath = resourcePath;
		this.expectedExitCode = expectedExitCode;
	}
	
	@Parameterized.Parameters
	public static Collection<Object[]> primeNumbers() {
		return Arrays.asList(new Object[][] {
			{ "unknown.properties", -2 },
			{ "unknownClassSettings.properties", -2 },
			{ "classWithNoMainMethodSettings.properties", -2 },
			{ "noClassSettings.properties", -2 },
			{ "noJavaMinVersionSettings.properties", -2 },
			{ "IllegalJavaMinVersionSettings.properties", -2 },
			{ "UnknownLogger.properties", -2 },
			{ "UnknownLoggerClass.properties", -2 },
			{ "tooSmallJavaMinVersionSettings.properties", -1 }
		});
	}

	@Test
	public void test() {
		launcher.doMain(new String[]{"A","B"}, new Quiet(), new Utils.ResourceStreamSupplier(resourcePath));
		assertFalse(TestApp.wasCalled());
		assertEquals(expectedExitCode, launcher.getExitCode());
	}
}
