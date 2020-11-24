package dbutil;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

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
	
    
    /**
     * select data from Recipes table
     * @return data set of Recipe table
     * @throws SQLException
     */
    public ResultSet selectRecipes() throws SQLException {
    	String query = "SELECT * from Recipe order by created_date DESC LIMIT 5";
    	Statement stmt = null;
    	
    	try {
    		stmt =conn.createStatement();
	    	ResultSet result = stmt.executeQuery(query);
	    	return result;
    		
    	} catch (SQLException e) {
			System.out.println("Select recipe error: " + e.getMessage());
		}
    	
    	return null;
    }

}
