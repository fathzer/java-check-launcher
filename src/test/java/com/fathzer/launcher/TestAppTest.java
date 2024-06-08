package com.fathzer.launcher;

import static org.junit.Assert.*;

import org.junit.Test;

import com.fathzer.launcher.test.TestApp;

public class TestAppTest {

	@Test
	public void test() {
		TestApp.reset();
		assertFalse(TestApp.wasCalled());
		
		TestApp.main(new String[] {"A"});
		assertTrue(TestApp.wasCalled());
		TestApp.reset();
		assertFalse(TestApp.wasCalled());
	}
}
