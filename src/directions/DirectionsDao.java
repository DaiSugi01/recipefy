package directions;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import ingredients.IngredientsDto;

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
        	System.out.println("[DirectionsDao]selectDirectionsByRecipeId error: " + e.getMessage());
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
    
    public boolean insertDirections(String directions, int recipe_id){
        PreparedStatement preparedStatement = null;
        System.out.println("[DirectionsDao] insertDirections run");
        
        try{
            
            String insertQuery = "INSERT INTO Directions (direction, recipe_id) VALUES (?, ?)";
            
            preparedStatement = conn.prepareStatement(insertQuery);
            preparedStatement.setString(1, directions);
            preparedStatement.setInt(2, recipe_id);
            
            int result = preparedStatement.executeUpdate();
            System.out.println("SQL: "+ insertQuery + ", "
            		+ "value: directions=" + directions + ", recipe_id=" + recipe_id);
            return (result == 1);
            
        }catch(Exception e){
            System.out.println("Insert Directions error: " + e.getMessage());
        }
        return false;
    }
    
    
    
}
