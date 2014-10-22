package be.nabu.libs.resources.classpath;

import java.io.IOException;
import java.net.URL;

import be.nabu.libs.resources.api.ReadableResource;
import be.nabu.utils.io.ContentTypeMap;
import be.nabu.utils.io.IOUtils;
import be.nabu.utils.io.api.ByteBuffer;
import be.nabu.utils.io.api.ReadableContainer;

public class ClassPathReadableResource extends ClassPathResource implements ReadableResource {

	private URL url;
	
	public ClassPathReadableResource(URL url) {
		this(null, url);
	}
	
	ClassPathReadableResource(ClassPathResourceContainer parent, URL url) {
		super(parent, url);
		this.url = url;
	}

	@Override
	public String getContentType() {
		return ContentTypeMap.getInstance().getContentTypeFor(url.getPath());
	}

	@Override
	public ReadableContainer<ByteBuffer> getReadable() throws IOException {
		return IOUtils.wrap(url.openStream());
	}
}
