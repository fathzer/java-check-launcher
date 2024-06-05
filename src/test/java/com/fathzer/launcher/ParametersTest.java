package com.fathzer.launcher;

import static org.junit.Assert.*;

import java.io.IOException;
import java.math.BigDecimal;

import org.junit.Test;

public class ParametersTest {
	@Test
	public void test() throws IOException {
		Parameters params = new Parameters(new Version("17"), "myClass");
		assertTrue(params.getLogger() instanceof Console);
		
		params = Parameters.get(new Utils.ResourceStreamSupplier("settings.properties"));
		assertTrue(params.getLogger() instanceof Swing);
	}
}
