package com.fathzer.launcher;

import java.math.BigDecimal;

public class Version implements Comparable {
	private final String version;
	private final BigDecimal asBigDecimal;
	
	public Version(String version) {
		this.version = version;
		asBigDecimal = new BigDecimal(version);
	}

	public int compareTo(Object o) {
		return asBigDecimal.compareTo(((Version)o).asBigDecimal);
	}

	public String toString() {
		return version;
	}
}
