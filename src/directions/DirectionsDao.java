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
	
    public ArrayList<DirectionsDto> selectDirectionsByRecipeId(int recipeId) {
    	String query = 
    			"SELECT * FROM Directions WHERE recipe_id = ? ORDER BY dir_id";
    	System.out.println("SQL" + ": " + query + ", value=" + recipeId);
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
    	
    	return directions;
    }
    
    public boolean insertDirections(ArrayList<DirectionsDto> directions){
        PreparedStatement ppst = null;
        
        try{
        	
        	String sql = "INSERT INTO Directions (direction, recipe_id) VALUES (?, ?)";
            
            int result = 0;
            for (DirectionsDto direction : directions) {
                System.out.println(sql + ", value=" + direction.getDirection() + ", " + direction.getRecipeId());
                ppst = conn.prepareStatement(sql);
                ppst.setString(1, direction.getDirection());
                ppst.setInt(2, direction.getRecipeId());
                ppst.executeUpdate();
                result++;
                ppst = null;
            }
            
            System.out.println("[DirectionsDao] insertDirections done");
            return (result == directions.size());
            
        }catch(Exception e){
        	System.out.println("[DirectionsDao] insertDirections error: " + e.getMessage());
        }
        return false;
    }
    
    
    
}
