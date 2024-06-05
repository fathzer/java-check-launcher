package com.fathzer.launcher;

import java.math.BigDecimal;
import java.util.Objects;

public class Version implements Comparable {
	private final String asString;
	private final BigDecimal asBigDecimal;
	
	public Version(String version) {
		this.asString = version;
		asBigDecimal = new BigDecimal(version);
	}

	public int compareTo(Object o) {
		return asBigDecimal.compareTo(((Version)o).asBigDecimal);
	}
	
	@Override
	public int hashCode() {
		return asBigDecimal.hashCode();
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null || getClass() != obj.getClass()) {
			return false;
		}
		Version other = (Version) obj;
		return Objects.equals(asBigDecimal, other.asBigDecimal);
	}

	public String toString() {
		return asString;
	}
}
