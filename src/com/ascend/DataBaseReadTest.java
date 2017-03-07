package com.ascend;

import java.sql.*;
import java.util.Properties;
import java.io.InputStream;

/**
 * Created by mrkirkland on 3/3/2017.
 */
public class DataBaseReadTest {

    public DataBaseReadTest()
    {

    }
    public String getTables() {
        Properties prop = new Properties();
        try {

            InputStream in = this.getClass().getResourceAsStream("database.properties");
            prop.load(in);
            in.close();
            String drivers = prop.getProperty("jdbc.drivers");
            String connectionURL = prop.getProperty("jdbc.host");
            String username = prop.getProperty("jdbc.user");
            String password = prop.getProperty("jdbc.password");

            Class.forName(drivers);

            Connection con = DriverManager.getConnection(connectionURL, username, password);

            System.out.println("Connection Successful");

            Statement s = con.createStatement();

            ResultSet rs = s.executeQuery("SELECT * FROM tblMember");

            StringBuilder sb = new StringBuilder();

            ResultSetMetaData rsmd = rs.getMetaData();

            int cc = rsmd.getColumnCount();

            System.out.println(cc);

            while (rs.next()) {
                for (int i = 1; i <= cc; i++) {

                    sb.append(String.format("%-28s", rs.getString(i) + " ")); //Print one element of a row
                }
            }

            return sb.toString();
        } catch(Exception e) {
            e.printStackTrace();
            System.err.println(prop.getProperty("jdbc.drivers"));
            return e.getMessage();
        } finally
        {
        }
    }
}