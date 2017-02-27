package com.ascend;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
/**
 * Created by mrkirkland on 2/27/2017.
 */
class PropertyReader {
    private String file;
    PropertyReader(String s)
    {
        this.file = s;
    }
    Properties readProperties() throws IOException {
        Properties p = new Properties();
        InputStream io = getClass().getClassLoader().getResourceAsStream(this.file);
        if (io != null) {
            p.load(io);
        } else {
            throw new FileNotFoundException("property file: " + this.file + " not found");
        }
        return p;
    }
}
