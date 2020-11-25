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
	
    public ArrayList<IngredientsDto> selectDirections(int recipeId) {
    	String query = 
    			"SELECT D.dir_id, D.direction, D.created_date FROM Directions as D "
    			+ "INNER JOIN RecipeDirections as RD on D.dir_id = RD.dir_id "
    			+ "WHERE RD.recipe_id = ? ORDER BY D.dir_id";
    	PreparedStatement pstmt = null;
        ResultSet rs = null;
        ArrayList<IngredientsDto> directions = new ArrayList<>();

        try {
            pstmt = conn.prepareStatement(query);
            pstmt.setInt(1, recipeId);
            rs = pstmt.executeQuery();

            while(rs.next()) {
                int ingId = rs.getInt("dir_id");
                String ingName = rs.getString("direction");
                Date createdDate = rs.getDate("created_date");
                directions.add(new IngredientsDto(ingId, ingName, createdDate));
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
    	
    	return directions;
    }
    
}
