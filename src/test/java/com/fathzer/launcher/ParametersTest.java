package com.fathzer.launcher;

import static org.junit.Assert.*;

import java.io.IOException;

import org.junit.Test;

public class ParametersTest {
	@Test
	public void test() throws IOException {
		Parameters params = new Parameters(17f, "myClass");
		assertTrue(params.getOutput() instanceof Console);
		
		params = Parameters.get(new Utils.ResourceStreamSupplier("settings.properties"));
		assertTrue(params.getOutput() instanceof Swing);
	}
}
