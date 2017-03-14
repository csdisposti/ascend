package com.ascend;
import java.io.InputStream;
import java.util.Properties;
/**
 * Created by mrkirkland on 2/27/2017.
 */
class PropertyReader {
    private String file;
    public PropertyReader(String s) {
        this.file = s;
    }
    public Properties getProperties()
    {
        Properties p = new Properties();
        InputStream in = this.getClass().getResourceAsStream("database.properties");
        try {
            p.load(in);
            in.close();
        } catch(Exception e)
        {
            e.printStackTrace();
            return null;
        }
        return p;
    }
}
