package org.pmw.tinylog;

import java.util.Map;
import java.util.Properties;
import java.util.Map.Entry;

/**
 * As {@link Configurator} doesn't provide any method for loading configurations from maps or properties and
 * {@link PropertiesLoader} is package private, we need a helper class in the same package as {@link PropertiesLoader}.
 */
public final class ExternalConfigurator {

	private ExternalConfigurator() {
	}

	public static Configurator fromMap(final Map<String, ?> map) {
		Properties properties = new Properties();
		
		for (Entry<String, ?> entry : map.entrySet()) {
			Object value = entry.getValue();
			if (value != null) {
				properties.put(entry.getKey(), value.toString());
			}
		}
		
		return PropertiesLoader.readProperties(properties);
	}

}
