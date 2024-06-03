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

	@Test
	public void unknownResource() {
		TestApp.reset();
		final NoExitLauncher launcher = new NoExitLauncher();
		launcher.doMain(new String[]{"A","B"}, new Console(), new Utils.ResourceStreamSupplier("unknown.properties"));
		assertFalse(TestApp.wasCalled());
		assertEquals(-2, (int)launcher.getExitCode());
	}
	
	@Test
	public void unknownClass() {
		TestApp.reset();
		final NoExitLauncher launcher = new NoExitLauncher();
		launcher.doMain(new String[]{"A","B"}, new Console(), new Utils.ResourceStreamSupplier("unknownClassSettings.properties"));
		assertFalse(TestApp.wasCalled());
		assertEquals(-2, (int)launcher.getExitCode());
	}

	@Test
	public void classWithNoMainMethod() {
		TestApp.reset();
		final NoExitLauncher launcher = new NoExitLauncher();
		launcher.doMain(new String[]{"A","B"}, new Console(), new Utils.ResourceStreamSupplier("classWithNoMainMethodSettings.properties"));
		assertFalse(TestApp.wasCalled());
		assertEquals(-2, (int)launcher.getExitCode());
	}

	@Test
	public void noClass() {
		TestApp.reset();
		final NoExitLauncher launcher = new NoExitLauncher();
		launcher.doMain(new String[]{"A","B"}, new Console(), new Utils.ResourceStreamSupplier("noClassSettings.properties"));
		assertFalse(TestApp.wasCalled());
		assertEquals(-2, (int)launcher.getExitCode());
	}

	@Test
	public void noJavaMinVersion() {
		TestApp.reset();
		final NoExitLauncher launcher = new NoExitLauncher();
		launcher.doMain(new String[]{"A","B"}, new Console(), new Utils.ResourceStreamSupplier("noJavaMinVersionSettings.properties"));
		assertFalse(TestApp.wasCalled());
		assertEquals(-2, (int)launcher.getExitCode());
	}
	
	@Test
	public void IllegalJavaMinVersion() {
		TestApp.reset();
		final NoExitLauncher launcher = new NoExitLauncher();
		launcher.doMain(new String[]{"A","B"}, new Console(), new Utils.ResourceStreamSupplier("IllegalJavaMinVersionSettings.properties"));
		assertFalse(TestApp.wasCalled());
		assertEquals(-2, (int)launcher.getExitCode());
	}
	
	@Test
	public void tooSmallJavaMinVersion() {
		TestApp.reset();
		final NoExitLauncher launcher = new NoExitLauncher();
		launcher.doMain(new String[]{"A","B"}, new Console(), new Utils.ResourceStreamSupplier("tooSmallJavaMinVersionSettings.properties"));
		assertFalse(TestApp.wasCalled());
		assertEquals(-1, (int)launcher.getExitCode());
	}
}
