package recipe;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class RecipeDao {

	private final Connection conn;

	public RecipeDao(Connection conn) {
		this.conn = conn;
	}

	public RecipeDto getColumn(ResultSet rs) throws SQLException {
		int recipeId = rs.getInt("recipe_id");
		String recipeName = rs.getString("recipe_name");
		byte[] recipeImage = rs.getBytes("recipe_image");
		String recipeCategory = rs.getString("recipe_category");
		String timeToCook = rs.getString("time_to_cook");
		int userId = rs.getInt("user_id");
		Date updatedDate = rs.getDate("updated_date");
		Date createdDate = rs.getDate("created_date");
		return new RecipeDto(recipeId, recipeName, recipeImage, 
				recipeCategory, timeToCook, userId, updatedDate, createdDate);

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
    			recipes.add(getColumn(rs));
    		}
    		
    	} catch (SQLException e) {
			System.out.println("Select recipe error: " + e.getMessage());
		} finally {
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

    
    /**
     * select data from Recipes table by keyword
     * @return data set of Recipe table
     * @throws SQLException
     */
    public ArrayList<RecipeDto> selectRecipesbyKeyword(String keyword) throws SQLException {
    	String query = "SELECT * FROM Recipe WHERE recipe_name like ?";
    	keyword = "%" + keyword + "%";

    	PreparedStatement ppsmt = null;
    	ResultSet rs = null;
    	ArrayList<RecipeDto> recipes = new ArrayList<>();
    	
    	try {
    		ppsmt =conn.prepareStatement(query);
    		ppsmt.setString(1, keyword);
    		rs = ppsmt.executeQuery();
    		
    		while (rs.next()) {
    			recipes.add(getColumn(rs));
    		}
    		
    	} catch (SQLException e) {
    		System.out.println("Select recipe by kewyord error: " + e.getMessage());
    	} finally {
    		try {
    			if(ppsmt != null) {
    				ppsmt.close();
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

    
    public RecipeDto selectRecipebyId(int userId) throws SQLException {
    	String query = "SELECT * FROM Recipe WHERE user_id = ? ORDER BY created_date LIMIT 1";
    	
    	PreparedStatement ppsmt = null;
    	ResultSet rs = null;
    	RecipeDto recipe = null;
    	
    	try {
    		ppsmt =conn.prepareStatement(query);
    		ppsmt.setInt(1, userId);
    		rs = ppsmt.executeQuery();
    		
    		while (rs.next()) {
    			recipe = (getColumn(rs));
    		}
    		
    	} catch (SQLException e) {
    		System.out.println("Select recipe by kewyord error: " + e.getMessage());
    	} finally {
    		try {
    			if(ppsmt != null) {
    				ppsmt.close();
    			}
    			if(rs != null) {
    				rs.close();
    			}
    		} catch (SQLException e) {
    			e.printStackTrace();
    		}
    	}
    	
    	return recipe;
    	
    }

    /**
     * insert recipe data
     * @return data set of Recipe table
     * @throws SQLException
     */
    public boolean insertRecipe(String recipeName, byte[] recipeImage, String category, String timeToCook, int userId){
        PreparedStatement preparedStatement = null;
        
        try{
      
            String insertQuery = "INSERT INTO Recipe (recipe_name, recipe_image, recipe_category, time_to_cook, user_id) VALUES (?, ?, ?, ?, ?)";
            
            preparedStatement = conn.prepareStatement(insertQuery);
            preparedStatement.setString(1, recipeName);
            preparedStatement.setBytes(2, recipeImage);
            preparedStatement.setString(3, category);
            preparedStatement.setString(4, timeToCook);
            preparedStatement.setInt(5, userId);
            
            int result = preparedStatement.executeUpdate();
            return (result == 1);
            
        }catch(Exception e){
            System.out.println("Insert Recipe error: " + e.getMessage());
        }
        return false;
    }

}
