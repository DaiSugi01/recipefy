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
	
	private void getConnection() {
		try {
			Class.forName(DBConfig.DRIVER);
			conn = DriverManager.getConnection(DBConfig.URL, DBConfig.USER, DBConfig.PASSWORD);
			System.out.println("Connected!");
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}
	
    public boolean insertUser(String firstName, String lastName, String email, String password){
        PreparedStatement preparedStatement = null;
        
        try{
            ResultSet resultSet;   
            String countEmails = "SELECT COUNT(*) FROM USERS WHERE email = ?";
            
            preparedStatement = conn.prepareStatement(countEmails);
            preparedStatement.setString(1, email);
            
            resultSet = preparedStatement.executeQuery();
            
            if(resultSet.next()){
                if(resultSet.getInt(1) > 0){
                    return false;
                }
            }
            
            String insertQuery = "INSERT INTO USERS (firstName, lastName, email, password) "
                    + "VALUES (?, ?, ?, ?)";
            
            preparedStatement = conn.prepareStatement(insertQuery);
            preparedStatement.setString(1, firstName);
            preparedStatement.setString(2, lastName);
            preparedStatement.setString(3, email);
            preparedStatement.setString(4, password);
            
            int result = preparedStatement.executeUpdate();
            return (result == 1);
            
        }catch(Exception e){
            System.out.println("Insert user error: " + e.getMessage());
        }
        return false;
    }
    
    public boolean checkCredentials(String email, String password){
        String query = "SELECT user_id FROM USERS WHERE email = ? AND pwd = ?";
        
        PreparedStatement preparedStatement = null;
        
        try{
            preparedStatement = conn.prepareStatement(query);
            preparedStatement.setString(1, email);
            preparedStatement.setString(2, password);

            System.out.println("OK");
            ResultSet resultSet = preparedStatement.executeQuery();
            System.out.println("OK1");
            
            if(resultSet.next()){
                return (resultSet.getInt(1) == 1);
            }
            System.out.println("OK2");
            
            
        }catch(Exception e){
            System.out.println("Check credentials error, " + e.getMessage());
        }
        
        return false;
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
