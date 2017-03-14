package com.ascend;

import java.io.StringWriter;
import java.sql.Connection;
import java.io.IOException;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.util.Scanner;

import com.sun.net.httpserver.*;
import sun.misc.IOUtils;

/**
 * Created by mrkirkland on 2/27/2017.
 */
public class Ascend{
        private static Connection c;
        private static DataBaseRead dba;

    public static void main(String[] args) throws Exception {
        c = Utils.getConnection();
        System.out.println(c + " Created");
        dba = new DataBaseRead(c);
        System.out.println(dba + " Created");
        HttpServer server = HttpServer.create(new InetSocketAddress(8000), 0);
        server.createContext("/main", new MyHandler());
        server.setExecutor(null);
        server.start();
    }

    static class MyHandler implements HttpHandler {
        String response;
        @Override
        public void handle(HttpExchange t) throws IOException {
            Scanner s = new Scanner(t.getRequestBody()).useDelimiter("\\A");
            String requestB = s.hasNext() ? s.next() : null;
            System.out.println(requestB);
            if (requestB == null)
            {
                response = Ascend.getMainPage();
            } else
            {
                response = getDynamicPage(requestB);
            }
            t.sendResponseHeaders(200, response.length());
            OutputStream os = t.getResponseBody();
            os.write(response.getBytes());
            os.close();
        }
    }
    public static final String getMainPage()
    {

        return "<html>"
                + "<head>"
                + "</head>"
                + "<body>"
                + "<header>"
                + "<img src = \"ascend.png\" alt=\" ASCEND INC\" />"
                + "</header>"
                + "<section>"
                + "<p> Welcome to ASCEND </p>"
                + "<form method = \"post\" >"
                + "Username:<br /> <input type=\"text\" name=\"firstname\" /><br />"
                + "Password:<br /> <input type=\"password\" name=\"password\" /><br />"
                + "<input type=\"submit\" />"
                + "</form>"
                + "</section>"
                + "</body>"
                + "</html>";
    }
    public static final String getDynamicPage(String request)
    {
        return dba.getTable("tblMember");
    }
}
