package dbutil;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

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
			System.out.println("Connected!");
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		return  conn;
	}
	

}
