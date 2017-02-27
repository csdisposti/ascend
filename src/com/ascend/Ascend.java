package com.ascend;

import java.util.Properties;

/**
 * Created by mrkirkland on 2/27/2017.
 */
public class Ascend {
    public static void main(String[] argv)
    {
        PropertyReader p = new PropertyReader("dba.properties");
        Properties prop;
        try
        {
            prop = p.readProperties();
        } catch (Exception e)
        {
            e.printStackTrace();
            return;
        }
        //start database connection
        //check if database is connected and working
        //start web connection
        //check if sockets are open

        //loop for socket connections

        //close web connections
        //close database connections
    }
}
