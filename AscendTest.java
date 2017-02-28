import java.sql.*;
import java.util.Scanner;
import java.io.FileInputStream;
import java.util.Properties;

public class AscendTest {


	public static void main(String[] args) throws Exception {

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
			sql ="SELECT * FROM tblMember";
			ResultSet rs = stat.executeQuery(sql);

			ResultSetMetaData rsmd = rs.getMetaData();

			int columnsNumber = rsmd.getColumnCount();

			while (rs.next()) {
			//Print one row
				for(int i = 1 ; i <= columnsNumber; i++){

					System.out.print(String.format("%-28s", rs.getString(i) + " ")); //Print one element of a row

				}

				System.out.println();//Move to the next line to print the next row.

			}

		} finally {
			conn.close();
		}

	}

}
