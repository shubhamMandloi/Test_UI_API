package Utility;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

public class PropertiesReader {
    /**
     * This method is used to load properties for the properties file provided as prop file
     * @param propFile
     * @return
     * @throws FileNotFoundException
     * @throws IOException
     */
    public synchronized  Properties loadProperties(String propFile) throws FileNotFoundException, IOException {
        Properties props = new Properties();
        props.load(new FileInputStream(propFile));
        return props;
    }
}
