


import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

/**
 * Singleton to manage DB Connection
 *
 * @author jparks
 *
 */
public class DbConnection {

	private static final String DB_PROPERTIES_FILE = "database.properties";

	private static DbConnection me = null;

	private Connection dbConn = null;

	private DbConnection(String dbPropertiesFile) throws FileNotFoundException, IOException,
			SQLException {

		Properties p = new Properties();
		p.load(new FileInputStream(dbPropertiesFile));

		String username = "";
		String password = "";
		String url = "";
		String driver = "";
		url = p.getProperty("jdbc.url");
		driver = p.getProperty("jdbc.driver");
		// Class.forName(driver);

		dbConn = DriverManager.getConnection(url, username, password);
	}

	public Connection getConnection() {
		return dbConn;
	}

	public static DbConnection accessDbConnection()
			throws FileNotFoundException, IOException, SQLException {
		return accessDbConnection(DB_PROPERTIES_FILE);
	}

	public static DbConnection accessDbConnection(String propertiesFilename) throws FileNotFoundException, IOException, SQLException {
		if (me == null) {
			me = new DbConnection(propertiesFilename);
		}
		return me;
	}

	public void disconnect() {
		me = null;
		if (dbConn != null) {
			try {
				dbConn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			} finally {
				dbConn = null;
			}
		}
	}

}
