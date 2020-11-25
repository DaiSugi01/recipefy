package user;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UserDao {
	private final Connection conn;

	public UserDao(Connection conn) {
		this.conn = conn;
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
            
            String insertQuery = "INSERT INTO USERS (first_name, last_name, email, pwd) "
                    + "VALUES (?, ?, ?, SHA(?))";
            
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
        String query = "SELECT count(*) FROM USERS WHERE email = ? AND pwd = SHA(?)";
        
        PreparedStatement preparedStatement = null;
        
        try{
            preparedStatement = conn.prepareStatement(query);
            preparedStatement.setString(1, email);
            preparedStatement.setString(2, password);

            System.out.println("OK");
            ResultSet resultSet = preparedStatement.executeQuery();
            System.out.println("OK1");
            
            System.out.println(email + " " + password);
            
            if(resultSet.next()){
                return (resultSet.getInt(1) == 1);
            }
            System.out.println("OK2");
            
            
        }catch(Exception e){
            System.out.println("Check credentials error, " + e.getMessage());
        }
        
        return false;
    }
    
    
    public UserDto selectUser(String email, String password) {
    	PreparedStatement pstmt = null;
        ResultSet rs = null;
        UserDto user= null;
    	try {
    		String query = "SELECT * FROM Users  WHERE email = ? AND pwd = SHA(?)";
            pstmt = conn.prepareStatement(query);
            pstmt.setString(1, email);
            pstmt.setString(2, password);
            rs = pstmt.executeQuery();

            while(rs.next()) {
                int id = rs.getInt("user_id");
                String user_firstName = rs.getString("first_name");
                String user_lastName = rs.getString("last_name");
                String user_email = rs.getString("email");
                String user_password = rs.getString("pwd");
                user = new UserDto(id, user_firstName, user_lastName, user_email, user_password);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if(pstmt != null) {
                    pstmt.close();
                }
                if(rs != null) {
                    rs.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    	
    	return user;
    }
}
