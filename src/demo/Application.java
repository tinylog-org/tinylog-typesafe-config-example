package demo;

import java.util.Map;
import java.util.Map.Entry;
import java.util.function.Function;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.tinylog.Logger;
import org.tinylog.configuration.Configuration;

import com.typesafe.config.ConfigFactory;
import com.typesafe.config.ConfigValue;

public class Application {

	private static final Pattern PATTERN = Pattern.compile("(writer\\w*)(\\.type)");

	public static void main(final String[] args) {
		/* Load type-safe configuration as stream */
		Stream<Entry<String, ConfigValue>> stream = ConfigFactory.load("tinylog.conf").resolve().entrySet().stream();

		/* Map keys 'writer*.type' to 'writer*' (see reason in tinylog.conf) */
		Function<String, String> keyMapper = key -> { Matcher matcher = PATTERN.matcher(key); return matcher.matches() ? matcher.group(1) : key; };

		/* Convert type-safe configuration into a tinylog compatible map */
		Map<String, String> map = stream.collect(Collectors.toMap(entry -> keyMapper.apply(entry.getKey()), entry -> entry.getValue().unwrapped().toString()));

		/* Activate the new configuration */
		Configuration.replace(map);

		/* Happy logging :) */
		Logger.info("Hello World!");
	}

}
