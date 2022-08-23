package utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.Properties;


public class PropertiesReader {

    private static final Logger LOGGER = LoggerFactory.getLogger(PropertiesReader.class);

    private PropertiesReader() {
    }

    public static String get(String property) {
        Properties properties = new Properties();
        String env = Environment.getCurrentEnv();
        try {
            properties.load(PropertiesReader.class.getClassLoader().getResourceAsStream("application-" + env + ".properties"));
        } catch (IOException | NullPointerException e) {
            LOGGER.error("Property " + property + " not found. ", e);
        }
        return properties.getProperty(property);
    }
}
