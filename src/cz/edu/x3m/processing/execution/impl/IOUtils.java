package cz.edu.x3m.processing.execution.impl;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

/**
 *
 *  @author Jan Hybs <x3mSpeedy@gmail.com>
 */
public class IOUtils {

    public static String readAll (String filePath) {
        BufferedReader reader = null;
        StringBuilder fileData = null;
        try {
            fileData = new StringBuilder ();
            reader = new BufferedReader (new FileReader (filePath));
            char[] buf = new char[1024];
            int numRead = 0;
            while ((numRead = reader.read (buf)) != -1) {
                String readData = String.valueOf (buf, 0, numRead);
                fileData.append (readData);
            }
            reader.close ();
            return fileData.toString ();
        } catch (Exception ex) {
            return null;
        } finally {
            if (reader != null)
                try {
                    reader.close ();
                } catch (IOException ex) {
                    //# oh, well
                }
        }
    }
}
