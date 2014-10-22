package be.nabu.libs.resources.classpath;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.net.URISyntaxException;

import be.nabu.libs.resources.ResourceFactory;
import be.nabu.libs.resources.ResourceReadableContainer;
import be.nabu.libs.resources.ResourceUtils;
import be.nabu.libs.resources.api.ReadableResource;
import be.nabu.libs.resources.api.Resource;
import be.nabu.libs.resources.api.ResourceRoot;
import be.nabu.utils.io.IOUtils;
import be.nabu.utils.io.api.ByteBuffer;
import be.nabu.utils.io.api.ReadableContainer;
import junit.framework.TestCase;

public class TestClassPathProvider extends TestCase {
	public void testSimple() throws UnsupportedEncodingException, IOException, URISyntaxException {
		ReadableContainer<ByteBuffer> bytes = ResourceUtils.toReadableContainer(new URI("classpath:/test.txt"), null);
		try {
			assertEquals("this is a test", new String(IOUtils.toBytes(bytes), "ASCII"));
		}
		finally {
			bytes.close();
		}
	}
	
	public void testDirectory() throws IOException, URISyntaxException {
		ResourceRoot resourceRoot = ResourceFactory.getInstance().resolve(new URI("classpath:/testDir"), null);
		Resource resource = ResourceUtils.resolve(resourceRoot, "test.txt");
		ReadableContainer<ByteBuffer> bytes = new ResourceReadableContainer((ReadableResource) resource);
		try {
			assertEquals("this is another test", new String(IOUtils.toBytes(bytes), "ASCII"));
		}
		finally {
			bytes.close();
		}
	}
}
