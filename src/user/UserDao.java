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
	
    public boolean selectUserCount(String firstName, String lastName, String email, String password) throws SQLException{
        PreparedStatement ppst = null;
        ResultSet resultSet = null;
        boolean isDuplicate = false;
        
        try{
            conn.setAutoCommit(false);
            String countEmails = "SELECT COUNT(*) FROM USERS WHERE email = ?";
            System.out.println("[UserDao] SQL: " + countEmails + 
            		", value=" + firstName + ", " + lastName + ", " + email + ", " + password);

            ppst = conn.prepareStatement(countEmails);
            ppst.setString(1, email);
            
            resultSet = ppst.executeQuery();
            
            if(resultSet.next()){
                if(resultSet.getInt(1) > 0){
                	isDuplicate = true;
                } else {
                	isDuplicate = false;
                }
            }
            
        } catch (Exception e) {
            System.out.println("[UserDao]: selectUserCount error: " + e.getMessage());
		} finally {
			if (resultSet != null) {
				resultSet.close();
			}
			
			if (ppst != null) {
				ppst.close();
			}
		}
        
        return isDuplicate;
    }
	
    /**
     * 
     * @param firstName
     * @param lastName
     * @param email
     * @param password
     * @return 
     * 		true  : insert success 
     * 		false : insert failed
     * @throws SQLException
     */
    public boolean insertUser(String firstName, String lastName, String email, String password) throws SQLException{
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        int result = -1;
        
        try{
            conn.setAutoCommit(false);

            String insertQuery = "INSERT INTO USERS (first_name, last_name, email, pwd) "
                    + "VALUES (?, ?, ?, SHA(?))";
            
            System.out.println("[UserDao] SQL: " + insertQuery +
            		", value=" + firstName + ", " + lastName + ", " + email + ", " + password);

            preparedStatement = conn.prepareStatement(insertQuery);
            preparedStatement.setString(1, firstName);
            preparedStatement.setString(2, lastName);
            preparedStatement.setString(3, email);
            preparedStatement.setString(4, password);
            
            result = preparedStatement.executeUpdate();
            conn.commit();
            
        } catch(Exception e) {
        	
            System.out.println("Insert user error: " + e.getMessage());
            conn.rollback();
            
        } finally {
        	if (resultSet != null) {
        		resultSet.close();
        	}
        	
        	if (preparedStatement != null) {
        		preparedStatement.close();
        	}
		}
        
        return (result == 1);
    }
    
    
    /**
     * 
     * @param email
     * @param password
     * @return 
     * 		true  : credential OK
     * 		false : credential failed
     * @throws SQLException
     */
    public boolean checkCredentials(String email, String password) throws SQLException{
        String query = "SELECT count(*) FROM USERS WHERE email = ? AND pwd = SHA(?)";
        System.out.println("[UserDao] SQL: " + query + ", value=" + email + ", " + password);

        boolean isOk = false;
        PreparedStatement ppst = null;
        ResultSet resultSet = null;

        try {
        	ppst = conn.prepareStatement(query);
            ppst.setString(1, email);
            ppst.setString(2, password);

            resultSet = ppst.executeQuery();
            
            if(resultSet.next()){
            	isOk = (resultSet.getInt(1) == 1);
            }
            
            
        } catch(Exception e) {
            System.out.println("[UserDao] Check credentials error, " + e.getMessage());
        } finally {
			if (resultSet != null) {
				resultSet.close();
			}
			
			if (ppst != null) {
				ppst.close();
			}
		}
        
        return isOk;
    }
    
    
    public UserDto selectUser(String email, String password) throws SQLException {
    	PreparedStatement pstmt = null;
        ResultSet rs = null;
        UserDto user = null;
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
            System.out.println("[UserDao] selectUser error, " + e.getMessage());
            e.printStackTrace();
        } finally {
        	
            if(pstmt != null) {
                pstmt.close();
            }

            if(rs != null) {
                rs.close();
            }

        }
    	
    	return user;
    }
}
