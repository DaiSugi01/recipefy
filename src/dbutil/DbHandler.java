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
	
	private void getConnection() {
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			conn = DriverManager.getConnection(DBConfig.URL, DBConfig.USER, DBConfig.PASSWORD);
			System.out.println("Connected!");
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}

}
