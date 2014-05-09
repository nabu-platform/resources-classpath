package be.nabu.libs.resources.classpath;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;

import be.nabu.libs.resources.URIUtils;
import be.nabu.libs.resources.api.ReadableResource;
import be.nabu.libs.resources.api.ResourceContainer;
import be.nabu.libs.resources.api.ResourceRoot;
import be.nabu.utils.io.ContentTypeMap;
import be.nabu.utils.io.IOUtils;
import be.nabu.utils.io.api.ByteBuffer;
import be.nabu.utils.io.api.ReadableContainer;

public class ClassPathResource implements ReadableResource, ResourceRoot {

	private URL url;
	
	public ClassPathResource(URL url) {
		this.url = url;
	}
	
	@Override
	public void close() throws IOException {
		// do nothing
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
	public String getContentType() {
		return ContentTypeMap.getInstance().getContentTypeFor(url.getPath());
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
		return null;
	}

	@Override
	public ReadableContainer<ByteBuffer> getReadable() throws IOException {
		return IOUtils.wrap(url.openStream());
	}

}
