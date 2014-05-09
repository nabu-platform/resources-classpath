package be.nabu.libs.resources.classpath;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.net.URISyntaxException;

import be.nabu.libs.resources.ResourceUtils;
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
}
