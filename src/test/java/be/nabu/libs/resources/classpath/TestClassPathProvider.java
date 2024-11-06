/*
* Copyright (C) 2014 Alexander Verbruggen
*
* This program is free software: you can redistribute it and/or modify
* it under the terms of the GNU Lesser General Public License as published by
* the Free Software Foundation, either version 3 of the License, or
* (at your option) any later version.
*
* This program is distributed in the hope that it will be useful,
* but WITHOUT ANY WARRANTY; without even the implied warranty of
* MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
* GNU Lesser General Public License for more details.
*
* You should have received a copy of the GNU Lesser General Public License
* along with this program. If not, see <https://www.gnu.org/licenses/>.
*/

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
import be.nabu.libs.resources.api.ResourceContainer;
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
	
	public void testRootDirectory() throws IOException, URISyntaxException {
		assertTrue(ResourceFactory.getInstance().resolve(new URI("classpath:/"), null) instanceof ResourceContainer);
	}
	
	public void testDirectory() throws IOException, URISyntaxException {
		Resource resourceRoot = ResourceFactory.getInstance().resolve(new URI("classpath:/testDir"), null);
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
