package be.nabu.libs.resources.classpath;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.net.URL;
import java.security.Principal;
import java.util.Arrays;
import java.util.List;

import be.nabu.libs.resources.api.Resource;
import be.nabu.libs.resources.api.ResourceResolver;

public class ClassPathResourceResolver implements ResourceResolver {
	
	private static List<String> defaultSchemes = Arrays.asList(new String [] { "classpath" });

	@SuppressWarnings("resource")
	@Override
	public Resource getResource(URI uri, Principal principal) {
		String path = uri.getPath();
		if (path.equals("/")) {
			return new ClassPathResourceContainer(null, null);
		}
		else if (path.startsWith("/")) {
			path = path.substring(1);
		}
		URL url = Thread.currentThread().getContextClassLoader().getResource(path);
		if (url == null) {
			return null;
		}
		else {
			return isDirectory(url) 
				? new ClassPathResourceContainer(url, path)
				: new ClassPathReadableResource(url);
		}
	}
	
	static boolean isDirectory(URL url) {
		// if it ends with a slash, it's always a directory
		if (url.getPath().endsWith("/")) {
			return true;
		}
		// In some cases a directory will also return a URL and it will even return a stream when asked...
		// the stream contains all the files in said directory
		// We however need to differentiate between directories and files, one (probably implementation-specific) way to do this is to check the type of inputstream
		// because the dir returns a listing built in memory, it is usually a byte array input stream, whereas an actual resource is streamed from the backend
		// while this is not bullet proof, it will likely not generate false positives either because that would assume the implementation loads everything into memory
		// we will assume in other implementations, the url.openstream might throw an exception for directories
		try {
			InputStream input = url.openStream();
			try {
				return input instanceof ByteArrayInputStream;
			}
			finally {
				try {
					input.close();
				}
				// streaming from a jar apparently throws a NPE (tested in java 7)
				catch (NullPointerException e) {
					return true;
				}
			}
		}
		catch(IOException e) {
			return false;
		}
	}

	@Override
	public List<String> getDefaultSchemes() {
		return defaultSchemes;
	}
}
