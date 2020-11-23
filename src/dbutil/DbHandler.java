package dbutil;

import java.sql.Connection;
import java.sql.DriverManager;

public class DbHandler {
	
	private final String URL = "jdbc:mysql://localhost/recipe";
	private final String USER = "root";
	private final String PASSWORD = "zhpv3925";
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
			conn = DriverManager.getConnection(URL, USER, PASSWORD);
			System.out.println("Connected!");
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}

}
