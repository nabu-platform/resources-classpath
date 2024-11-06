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
