package be.nabu.libs.resources.classpath;

import java.io.Closeable;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;

import be.nabu.libs.resources.URIUtils;
import be.nabu.libs.resources.api.LocatableResource;
import be.nabu.libs.resources.api.Resource;
import be.nabu.libs.resources.api.ResourceContainer;

/**
 * It can not list children and it can not point to an actual directory
 * But it can resolve specific children if asked
 * For example you could build a resource container around "test", 
 * then request "example.txt" and you would get the resource "test/example.txt" 
 */
abstract public class ClassPathResource implements Resource, Closeable, LocatableResource {

	private ClassPathResourceContainer parent;
	private URL url;

	public ClassPathResource(URL url) {
		this.url = url;
	}
	
	ClassPathResource(ClassPathResourceContainer parent, URL url) {
		this.parent = parent;
		this.url = url;
	}
	
	@Override
	public String getContentType() {
		return Resource.CONTENT_TYPE_DIRECTORY;
	}

	@Override
	public String getName() {
		try {
			return URIUtils.getName(url.toURI());
		}
		catch (URISyntaxException e) {
			throw new RuntimeException(e);
		}
	}

	@Override
	public ResourceContainer<?> getParent() {
		return parent;
	}

	@Override
	public URI getURI() {
		try {
			return url.toURI();
		}
		catch (URISyntaxException e) {
			throw new RuntimeException(e);
		}
	}

	@Override
	public void close() throws IOException {
		// do nothing
	}
	
	URL getURL() {
		return url;
	}
}
