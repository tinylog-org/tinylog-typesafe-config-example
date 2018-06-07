package demo;

import java.util.Map;
import java.util.Map.Entry;
import java.util.function.Function;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.pmw.tinylog.Configurator;
import org.pmw.tinylog.Logger;

import com.typesafe.config.ConfigFactory;
import com.typesafe.config.ConfigValue;

public class Application {

	private static final Pattern PATTERN = Pattern.compile("(tinylog\\.writer\\w*)(\\.type)");

	public static void main(final String[] args) {
		/* Load the type-safe configuration as stream */
		Stream<Entry<String, ConfigValue>> stream = ConfigFactory.load("tinylog.conf").resolve().withOnlyPath("tinylog").entrySet().stream();

		/* Map keys 'tinylog.writer*.type' to 'tinylog.writer*' (see reason in tinylog.conf) */
		Function<String, String> keyMapper = key -> { Matcher matcher = PATTERN.matcher(key); return matcher.matches() ? matcher.group(1) : key; };

		/* Convert the type-safe configuration to a tinylog compatible map */
		Map<String, ?> map = stream.collect(Collectors.toMap(entry -> keyMapper.apply(entry.getKey()), entry -> entry.getValue().unwrapped()));

		/* Activate the new configuration */
		Configurator.fromMap(map).activate();

		/* Happy logging :) */
		Logger.info("Hello World!");
	}

}
