package be.nabu.libs.resources.classpath;

import java.net.URI;
import java.net.URL;
import java.security.Principal;
import java.util.Arrays;
import java.util.List;

import be.nabu.libs.resources.api.ResourceResolver;
import be.nabu.libs.resources.api.ResourceRoot;

public class ClassPathResourceResolver implements ResourceResolver {
	
	private static List<String> defaultSchemes = Arrays.asList(new String [] { "classpath" });

	@Override
	public ResourceRoot getResource(URI uri, Principal principal) {
		String path = uri.getPath();
		if (path.startsWith("/"))
			path = path.substring(1);
		URL url = Thread.currentThread().getContextClassLoader().getResource(path);
		if (url == null)
			return null;
		else
			return new ClassPathResource(url);
	}

	@Override
	public List<String> getDefaultSchemes() {
		return defaultSchemes;
	}

}
