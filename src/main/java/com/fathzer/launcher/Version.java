package com.fathzer.launcher;

import java.math.BigDecimal;

/** A java version.
 */
public class Version implements Comparable {
	private final String asString;
	private final BigDecimal asBigDecimal;
	
	/** Constructor.
	 * @param version The java main version in the same format as the <i>java.specification.version</i> system property. 
	 */
	public Version(String version) {
		this.asString = version;
		asBigDecimal = new BigDecimal(version);
	}

	public int compareTo(Object o) {
		return asBigDecimal.compareTo(((Version)o).asBigDecimal);
	}
	
	public int hashCode() {
		return asBigDecimal.hashCode();
	}

	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null || getClass() != obj.getClass()) {
			return false;
		}
		Version other = (Version) obj;
		return asBigDecimal.equals(other.asBigDecimal);
	}

	public String toString() {
		return asString;
	}
}
