package com.ascend;

import java.io.FileInputStream;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.util.Properties;

/**
 * Created by mrkirkland on 3/14/2017.
 */
public class Utils {

    public static Connection getConnection()
    {
        Properties p = new PropertyReader("databse.properties").getProperties();
        Connection c;
        try {
            String drivers = p.getProperty("jdbc.drivers");
            String connectionURL = p.getProperty("jdbc.host");
            String username = p.getProperty("jdbc.user");
            String password = p.getProperty("jdbc.password");
            Class.forName(drivers);
            c = DriverManager.getConnection(connectionURL, username, password);
            System.out.println("Connection Successful" + c);
            return c;

        } catch (Exception e)
        {
            e.printStackTrace();
            return null;
        }
    }
}
