package ingredients;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;


public class IngredientsDao {

	private final Connection conn;

	public IngredientsDao(Connection conn) {
		this.conn = conn;
	}
	
    public ArrayList<IngredientsDto> selectIngredients(int recipeId) {
    	String query = 
    			"SELECT * FROM Ingredients as I "
    			+ "INNER JOIN RecipeIngredients as RI on I.ing_id = Ri.ing_id "
    			+ "WHERE RI.recipe_id = ?";

    	PreparedStatement pstmt = null;
        ResultSet rs = null;
        ArrayList<IngredientsDto> ingredients = new ArrayList<>();

        try {
            pstmt = conn.prepareStatement(query);
            pstmt.setInt(1, recipeId);
            rs = pstmt.executeQuery();

            while(rs.next()) {
                int ingId = rs.getInt("ing_id");
                String ingName = rs.getString("ing_name");
                Date createdDate = rs.getDate("created_date");
                ingredients.add(new IngredientsDto(ingId, ingName, createdDate));
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
    	
    	return ingredients;
    }
    
}
