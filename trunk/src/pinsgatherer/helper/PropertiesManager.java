package pinsgatherer.helper;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class PropertiesManager {
	
	private static final String PROPERTIES_FILENAME = "pinsgatherer.properties";
	
	private static Properties properties;
	
	static {
		properties = read();
	}

	private static Properties read() {
		// Read properties file.
		Properties properties = new Properties();
		try {
			properties.load(new FileInputStream(PROPERTIES_FILENAME));
		} catch (IOException e) {
			e.printStackTrace();
		}
		return properties;
	}
	
	public static String getProperty(String property) {
		return properties.getProperty(property);
	}
}
