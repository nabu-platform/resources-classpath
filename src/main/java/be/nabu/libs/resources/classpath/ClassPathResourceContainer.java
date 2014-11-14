package be.nabu.libs.resources.classpath;

import java.net.URL;
import java.util.ArrayList;
import java.util.Iterator;

import be.nabu.libs.resources.api.ResourceContainer;

public class ClassPathResourceContainer extends ClassPathResource implements ResourceContainer<ClassPathResource> {

	private String path;

	ClassPathResourceContainer(URL url, String path) {
		super(url);
		this.path = path.replaceAll("[/]+$", "");
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
		return "classpath:" + getURI().toString();
	}
}
