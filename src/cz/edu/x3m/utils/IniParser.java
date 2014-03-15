package cz.edu.x3m.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Properties;

/**
 *
 * @author Jan Hybs <x3mSpeedy@gmail.com>
 */
public class IniParser {

    private final HashMap<String, String> map = new HashMap<> ();



    public IniParser (File outputFile) throws IOException {
        Properties properties = new Properties ();
        properties.load (new FileInputStream (outputFile));

        for (String key : properties.stringPropertyNames ()) {
            map.put (key.trim (), properties.getProperty (key).trim ());
        }
    }



    /**
     * @param key
     * @return value in ini file or null if value doesn't exist
     */
    public String get (String key) {
        if (map.containsKey (key))
            return map.get (key);
        return null;
    }
}
