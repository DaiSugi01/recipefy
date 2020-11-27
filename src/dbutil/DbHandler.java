package dbutil;

import java.sql.Connection;
import java.sql.DriverManager;


public class DbHandler {
	
	Connection conn = null;
	
	private static DbHandler handler = null;
	
	private DbHandler() {
		getConnection();
	}
	
	public static DbHandler getInstance() {
		if (handler == null) {
			handler = new DbHandler();
		}
		return handler;
	}
	
	public Connection getConnection() {
		try {

			Class.forName(DBConfig.DRIVER);
			conn = DriverManager.getConnection(DBConfig.URL, DBConfig.USER, DBConfig.PASSWORD);

		} catch (Exception e) {
			System.out.println("[DBHandler] connection failed: " + e.getMessage());
		}
		
		return  conn;
	}
	
}
