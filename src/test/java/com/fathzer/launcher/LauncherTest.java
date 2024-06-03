package com.fathzer.launcher;

import static org.junit.Assert.*;

import org.junit.Test;

import com.fathzer.launcher.test.TestApp;

public class LauncherTest {

	@Test
	public void test() {
		TestApp.reset();
		final NoExitLauncher launcher = new NoExitLauncher();
		launcher.doMain(new String[]{"A","B"}, new Console());
		
		assertTrue(TestApp.wasCalled());
		assertNull(launcher.getExitCode());
	}
}
