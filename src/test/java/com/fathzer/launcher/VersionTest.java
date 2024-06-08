package com.fathzer.launcher;

import static org.junit.Assert.*;

import org.junit.Test;

public class VersionTest {

	@Test
	@SuppressWarnings("java:S5785")
	public void test() {
		final Version v1 = new Version("17");
		final Version v2 = new Version("17");
		assertEquals (v1, v2);
		assertEquals (v1.hashCode(), v2.hashCode());
		assertFalse (v1.equals(null));
		assertFalse (((Object)v1).equals("version"));
		assertTrue (v1.equals(v1));
		assertEquals (0, v1.compareTo(v2));
		
		final Version v3 = new Version("21");
		assertTrue(v3.compareTo(v1)>0);
		assertTrue(v1.compareTo(v3)<0);
	}

}
