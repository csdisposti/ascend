import java.io.*;
import java.net.InetSocketAddress;
import com.sun.net.httpserver.*;
import java.sql.*;
import java.util.Scanner;
import java.util.Properties;
import java.io.IOException;
public class AscendTest {

	private static final String FILENAME = "filename.txt";

	public static void main(String[] args) throws Exception, IOException {


		HttpServer server = HttpServer.create(new InetSocketAddress(8000), 0);
		server.createContext("/main", new MyHandler());
		server.setExecutor(null);
		server.start();


		String username = "MasterAscend";
		String password = "AscendMasterKey";
		Properties prop = new Properties();
		prop.load(new FileInputStream("database.properties"));
		String url = prop.getProperty("jdbc.url");
		String driver = prop.getProperty("jdbc.driver");
		Class.forName(driver);

		Connection conn = DriverManager.getConnection(url, username, password);
		try {
			Statement stat = conn.createStatement();

			Scanner in = new Scanner(System.in);
			//int selection = getMenuSelection(in);

			//STEP 4: Execute a query
			System.out.println("Creating statement...");
			String sql;

			//print all from member table
			sql = "SELECT * FROM tblMember";
			ResultSet rs = stat.executeQuery(sql);

			ResultSetMetaData rsmd = rs.getMetaData();

			int columnsNumber = rsmd.getColumnCount();
			try (PrintWriter out = new PrintWriter(FILENAME)) {


				while (rs.next()) {
					//Print one row
					for (int i = 1; i <= columnsNumber; i++) {

						out.print(String.format("%-28s", rs.getString(i) + " ")); //Print one element of a row


					}

					out.println();//Move to the next line to print the next row.

				}

			} finally {
				conn.close();
			}
		} finally {
			conn.close();
		}
	}
	static String readFile(String fileName) throws IOException {
		BufferedReader br = new BufferedReader(new FileReader(fileName));
		try {
			StringBuilder sb = new StringBuilder();
			String line = br.readLine();

			while (line != null) {
				sb.append(line);
				sb.append("\n");
				line = br.readLine();
			}
			return sb.toString();
		} finally {
			br.close();
		}
	}

	static class MyHandler implements HttpHandler {
		@Override
		public void handle(HttpExchange t) throws IOException {

			String response = readFile(FILENAME);
			t.sendResponseHeaders(200, response.length());
			OutputStream os = t.getResponseBody();
			os.write(response.getBytes());
			os.close();
		}
	}


}

