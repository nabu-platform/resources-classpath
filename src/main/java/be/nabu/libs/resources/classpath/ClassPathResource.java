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
	public URI getUri() {
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
