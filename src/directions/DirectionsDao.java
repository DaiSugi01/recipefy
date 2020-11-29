package directions;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;


public class DirectionsDao {

	private final Connection conn;

	public DirectionsDao(Connection conn) {
		this.conn = conn;
	}
	
	/**
	 * get directions data by recipe id
	 * @param recipeId
	 * @return directions dto list filtered by given recipe id
	 * @throws SQLException 
	 */
    public ArrayList<DirectionsDto> selectDirectionsByRecipeId(int recipeId) throws SQLException {
    	
    	String query = 
    			"SELECT * FROM Directions WHERE recipe_id = ? ORDER BY dir_id";
    	System.out.println("[DirectionsDao] SQL: " + query + ", value=" + recipeId);
    	
    	PreparedStatement pstmt = null;
        ResultSet rs = null;
        ArrayList<DirectionsDto> directions = new ArrayList<>();

        try {
            pstmt = conn.prepareStatement(query);
            pstmt.setInt(1, recipeId);
            rs = pstmt.executeQuery();

            while(rs.next()) {
                int dirId = rs.getInt("dir_id");
                String dirName = rs.getString("direction");
                int returnedRecipeId = rs.getInt("recipe_id");
                Date createdDate = rs.getDate("created_date");
                directions.add(new DirectionsDto(dirId, dirName, returnedRecipeId, createdDate));
            }
            
            System.out.println("[DirectionsDao] selectDirectionsByRecipeId done");
        } catch (SQLException e) {
        	System.out.println("[DirectionsDao] selectDirectionsByRecipeId error: " + e.getMessage());
        } finally {
            if(pstmt != null) {
                pstmt.close();
            }
            if(rs != null) {
                rs.close();
            }
        }
    	
    	return directions;
    }
    
    
    /**
     * insert directions data
     * @param directions
     * @return 
     * 		true  : insert success
     * 		false : insert failed
     * @throws SQLException 
     */
    public boolean insertDirections(ArrayList<DirectionsDto> directions) throws SQLException{

    	PreparedStatement ppst = null;
        int result = 0;
        
        try{
        	conn.setAutoCommit(false);
        	String sql = "INSERT INTO Directions (direction, recipe_id) VALUES (?, ?)";
            
            for (DirectionsDto direction : directions) {
                System.out.println("[DirectionsDao] SQL: " + sql + ", value=" + direction.getDirection() + ", " + direction.getRecipeId());
                ppst = conn.prepareStatement(sql);
                ppst.setString(1, direction.getDirection());
                ppst.setInt(2, direction.getRecipeId());
                ppst.executeUpdate();
                result++;
            }
            
            System.out.println("[DirectionsDao] insertDirections done");
            conn.commit();
            
        }catch(Exception e){
        	System.out.println("[DirectionsDao] insertDirections error: " + e.getMessage());
        	conn.rollback();
        } finally {
			if (ppst != null) {
				ppst.close();
			}
		}
        return (result == directions.size());
    }
    
    
    
}
