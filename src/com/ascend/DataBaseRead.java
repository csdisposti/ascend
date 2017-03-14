package com.ascend;

import java.io.IOException;
import java.sql.*;
import java.util.Properties;
import java.io.InputStream;

/**
 * Created by mrkirkland on 3/3/2017.
 */
public class DataBaseRead{

    private static Connection c;
    private StringBuilder sb = new StringBuilder();
    public DataBaseRead(Connection c)
    {
        this.c = c;
    }
    public String getTable(String s) {

            try {
                Statement stat = c.createStatement();
                ResultSet rs = stat.executeQuery("SELECT * FROM " + s);
                ResultSetMetaData rsmd = rs.getMetaData();
                int cc = rsmd.getColumnCount();
                System.out.println(cc);
                sb.append("<html><head><style type = 'text/css'>");
                sb.append("td{border: 1px solid black;}");
                sb.append("</style></head><body><table>");
                while (rs.next()) {
                    sb.append("<tr>");
                    for (int i = 1; i <= cc; i++) {
                        sb.append("<td>" + String.format("%-28s", rs.getString(i) + " ") + "</td>"); //Print one element of a row
                    }
                    sb.append("</tr>");
                }
                sb.append("</table>");
            } catch (Exception e)
            {
                e.printStackTrace();
                return e.getMessage();
            }
            sb.append("</body></html>");
            return sb.toString();
    }
}