package com.fathzer.launcher.test;

public class TestApp {
	private static boolean called;
	
	public static void main(String[] args) {
		called = true;
	}
	
	public static boolean wasCalled() {
		return called;
	}
	
	public static void reset() {
		called = false;
	}
}
