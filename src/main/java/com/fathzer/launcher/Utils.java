package com.fathzer.launcher;

import java.io.IOException;
import java.io.InputStream;

public abstract class Utils {
	private Utils() {
		super();
	}
	
	public interface InputStreamSupplier {
		InputStream get() throws IOException;
	}
	
	public static class ResourceStreamSupplier implements InputStreamSupplier {
		private final String path;
		
		public ResourceStreamSupplier(String path) {
			this.path = path;
		}
		
		public InputStream get() throws IOException {
			final InputStream stream = Parameters.class.getResourceAsStream(path);
			if (stream==null) {
				final String absPath = path.startsWith("/") ? path : "/"+Utils.getPackageName(Utils.class).replace('.', '/')+"/"+path;
				throw new IOException("Can't read "+absPath+" resource");
			}
			return stream;
		}
		
	}
	
	public static String getPackageName(Class aClass) {
		// Warning, the Class.getPackageName method is not available before java 9
		String name = aClass.getName();
		int index = name.lastIndexOf('.');
		return name.substring(0,index);
	}
}
