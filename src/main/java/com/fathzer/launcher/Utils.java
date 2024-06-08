package com.fathzer.launcher;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

/** Some utilities.
 */
public abstract class Utils {
	private Utils() {
		super();
	}

	/** An interface that can create an InputStream.
	 */
	public interface InputStreamSupplier {
		/** Gets an InputStream. 
		 * @return A non null InputStream
		 * @throws IOException If something went wrong
		 */
		InputStream get() throws IOException;
	}
	
	/** An {@link InputStream} that creates a Stream from a resource.
	 */
	public static class ResourceStreamSupplier implements InputStreamSupplier {
		private final String path;
		
		/** Constructor.
		 * @param path The resource path. Please note that the stream is created with <i>ResourceStreamSupplier.class.getResourceAsStream(path)</i>,
		 * so resource path should be absolute or relative to this class.
		 */
		public ResourceStreamSupplier(String path) {
			this.path = path;
		}
		
		/** {@inheritDoc}
		 * @throws IOException if the resource is not found
		 */
		public InputStream get() throws IOException {
			final InputStream stream = ResourceStreamSupplier.class.getResourceAsStream(path);
			if (stream==null) {
				final char c = '/';
				final String absPath = path.startsWith("/") ? path : c+Utils.getPackageName(Utils.class).replace('.', '/')+c+path;
				throw new FileNotFoundException("Can't read "+absPath+" resource");
			}
			return stream;
		}
	}
	
	/** Gets the package name of a class.
	 * @param aClass a class.
	 * @return The name of the package the class belongs to.
	 */
	public static String getPackageName(Class aClass) {
		// Warning, the Class.getPackageName method is not available before java 9
		String name = aClass.getName();
		int index = name.lastIndexOf('.');
		return name.substring(0,index);
	}
}
