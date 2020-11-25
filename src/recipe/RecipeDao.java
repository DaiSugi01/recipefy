package recipe;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class RecipeDao {

	private final Connection conn;

	public RecipeDao(Connection conn) {
		this.conn = conn;
	}

    /**
     * select data from Recipes table
     * @return data set of Recipe table
     * @throws SQLException
     */
    public ArrayList<RecipeDto> selectRecipes() throws SQLException {
    	String query = "SELECT * FROM Recipe order by created_date DESC LIMIT 5";
    	Statement stmt = null;
    	ResultSet rs = null;
    	ArrayList<RecipeDto> recipes = new ArrayList<>();
    	
    	try {
    		stmt =conn.createStatement();
    		rs = stmt.executeQuery(query);
    		
    		while (rs.next()) {
    			int recipeId = rs.getInt("recipe_id");
    			String recipeName = rs.getString("recipe_name");
    			int userId = rs.getInt("user_id");
    			recipes.add(new RecipeDto(recipeId, recipeName, userId));
    		}
    		
    	} catch (SQLException e) {
			System.out.println("Select recipe error: " + e.getMessage());
		} 
    	finally {
            try {
                if(stmt != null) {
                    stmt.close();
                }
                if(rs != null) {
                    rs.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
		}
    	
    	return recipes;
    }


}
