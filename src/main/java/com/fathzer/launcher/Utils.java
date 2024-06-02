package com.fathzer.launcher;

public abstract class Utils {
	private Utils() {
		super();
	}

	public static String getPackageName(Class aClass) {
		// Warning, the Class.getPackageName method is not available before java 9
		String name = aClass.getName();
		int index = name.lastIndexOf('.');
		return name.substring(0,index);
	}
}
