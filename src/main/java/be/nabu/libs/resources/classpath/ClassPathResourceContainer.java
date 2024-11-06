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

import java.net.URL;
import java.util.ArrayList;
import java.util.Iterator;

import be.nabu.libs.resources.api.ResourceContainer;

public class ClassPathResourceContainer extends ClassPathResource implements ResourceContainer<ClassPathResource> {

	private String path;

	ClassPathResourceContainer(URL url, String path) {
		super(url);
		this.path = path == null ? null : path.replaceAll("[/]+$", "");
	}
	
	ClassPathResourceContainer(ClassPathResourceContainer parent, URL url, String path) {
		super(parent, url);
		this.path = path;
	}

	@Override
	public Iterator<ClassPathResource> iterator() {
		return new ArrayList<ClassPathResource>().iterator();
	}

	@SuppressWarnings("resource")
	@Override
	public ClassPathResource getChild(String name) {
		String childPath = path == null ? name : path + "/" + name;
		if (childPath.startsWith("/")) {
			childPath = childPath.substring(1);
		}
		URL url = Thread.currentThread().getContextClassLoader().getResource(childPath);
		if (url == null) {
			return null;
		}
		else {
			return ClassPathResourceResolver.isDirectory(url)
				? new ClassPathResourceContainer(this, url, childPath)
				: new ClassPathReadableResource(this, url);
		}
	}

	@Override
	public String toString() {
		return "classpath:" + getUri().toString();
	}
}
