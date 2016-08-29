package demo;

import java.util.Map;
import java.util.Map.Entry;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.pmw.tinylog.ExternalConfigurator;
import org.pmw.tinylog.Logger;

import com.typesafe.config.Config;
import com.typesafe.config.ConfigFactory;
import com.typesafe.config.ConfigValue;

public class Application {

	public static void main(String[] args) {
		Config config = ConfigFactory.load("tinylog.conf").resolve().withOnlyPath("tinylog");
		Stream<Entry<String, ConfigValue>> stream = config.entrySet().stream();
		Map<String, ?> map = stream.collect(Collectors.toMap(Entry::getKey, entry -> entry.getValue().unwrapped()));
		
		ExternalConfigurator.fromMap(map).activate();
		
		Logger.info("Hello World!");
	}

}
