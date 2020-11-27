package recipe;

import java.io.IOException;
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
	
	public RecipeDto getColumn(ResultSet rs) throws SQLException, IOException {
		
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
     * this method is deleted and replaced with selectRecipesbyKeyword in the future
     * @return data set of Recipe table
     * @throws SQLException
     */
    public ArrayList<RecipeDto> tempSelectRecipesbyKeyword(String keyword) throws SQLException {
    	
    	String query = "SELECT * FROM Recipe WHERE recipe_name like ? ORDER BY created_date DESC LIMIT 1";
    	keyword = "%" + keyword + "%";

    	PreparedStatement ppst = null;
    	ResultSet rs = null;
    	ArrayList<RecipeDto> recipes = new ArrayList<>();
    	
    	try {
    		ppst =conn.prepareStatement(query);
    		ppst.setString(1, keyword);
    		rs = ppst.executeQuery();
    		
    		while (rs.next()) {
    			recipes.add(getColumn(rs));
    		}
    		
    	} catch (SQLException | IOException e) {
    		System.out.println("Select recipe by kewyord error: " + e.getMessage());
    	} finally {
    		try {
    			if(ppst != null) {
    				ppst.close();
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
     * select data from Recipes table
     * @return data set of Recipe table
     * @throws SQLException
     */
    public ArrayList<RecipeDto> selectRecipes() throws SQLException {
    	
    	String query = "SELECT * FROM Recipe order by created_date DESC LIMIT 5";
        System.out.println("[RecipeDao] SQL: " + query);

    	Statement stmt = null;
    	ResultSet rs = null;
    	ArrayList<RecipeDto> recipes = new ArrayList<>();
    	
    	try {
    		stmt =conn.createStatement();
    		rs = stmt.executeQuery(query);
    		
    		while (rs.next()) {
    			recipes.add(getColumn(rs));
    		}
    		
    	} catch (SQLException | IOException e) {
			System.out.println("[RecipeDao]Select recipe error: " + e.getMessage());
		} finally {

            if(stmt != null) {
                stmt.close();
            }
            
            if(rs != null) {
                rs.close();
            }
		}
    	
    	return recipes;
    }

    
    /**
     * select data from Recipes table by keyword
     * @return data set of Recipe table
     * @throws SQLException
     */
    public ArrayList<RecipeDto> selectRecipesByKeyword(String keyword) throws SQLException {
    	
    	String query = "SELECT * FROM Recipe WHERE recipe_name like ? ORDER BY created_date DESC";
    	keyword = "%" + keyword + "%";
    	System.out.println("[RecipeDao] SQL: " + query + ", values=" + keyword);
    	
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
    		
    	} catch (SQLException | IOException e) {
    		System.out.println("[RecipeDao] Select recipe by kewyord error: " + e.getMessage());
    	} finally {

    		if(ppsmt != null) {
				ppsmt.close();
			}
    		
			if(rs != null) {
				rs.close();
			}
    	}
    	
    	return recipes;
    }

    
    /**
     * select data from Recipes table by keyword
     * @return data set of Recipe table
     * @throws SQLException
     */
    public ArrayList<RecipeDto> selectRecipesByIngredients(String ingName) throws SQLException {
    	
    	StringBuilder query = new StringBuilder();
    	
    	query.append("SELECT ");
    	query.append("    R.* ");
    	query.append("FROM ");
    	query.append("    RecipeIngredients as RI ");
    	query.append("INNER JOIN Recipe as R on ");
    	query.append("	RI.recipe_id = R.recipe_id ");
    	query.append("INNER JOIN Ingredients as I on ");
    	query.append("	RI.ing_id = I.ing_id ");
    	query.append("WHERE");
    	query.append("	I.ing_name = ? ");
    	
    	PreparedStatement ppsmt = null;
    	ResultSet rs = null;
    	ArrayList<RecipeDto> recipes = new ArrayList<>();
    	
    	try {
    		ppsmt =conn.prepareStatement(query.toString());
    		ppsmt.setString(1, ingName);
    		
    		System.out.println("[RecipeDao] SQL: " + query.toString() + ", value=" + ingName);
    		rs = ppsmt.executeQuery();
    		
    		while (rs.next()) {
    			recipes.add(getColumn(rs));
    		}
    		
    	} catch (SQLException | IOException e) {
    		System.out.println("[RecipeDao] Select recipe by ingredients error: " + e.getMessage());
    	} finally {
			if(rs != null) {
				rs.close();
			}

			if(ppsmt != null) {
				ppsmt.close();
			}
    	}
    	
    	return recipes;
    }
    
    
    /**
     * select recipe data by user id
     * @param userId
     * @return recipe data
     * @throws SQLException
     */
    public RecipeDto selectRecipebyUserId(int userId) throws SQLException {
    	
    	String query = "SELECT * FROM Recipe WHERE user_id = ? ORDER BY created_date DESC LIMIT 1";
    	System.out.println("[RecipeDao] SQL: " + query + ", value=" + userId);
    	PreparedStatement ppst = null;
    	ResultSet rs = null;
    	RecipeDto recipe = null;
    	
    	try {
    		ppst = conn.prepareStatement(query);
    		ppst.setInt(1, userId);
    		rs = ppst.executeQuery();
    		
    		while (rs.next()) {
    			recipe = (getColumn(rs));
    		}
    		
    	} catch (SQLException | IOException e) {
    		System.out.println("[RecipeDao] Select recipe by kewyord error: " + e.getMessage());
    	} finally {
    		try {
    			if(ppst != null) {
    				ppst.close();
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
    public boolean insertRecipe(String recipeName, byte[] recipeImage, String category, String timeToCook, int userId) throws SQLException{
        
    	PreparedStatement ppst = null;
    	int result = -1;
    	
        try{
        	conn.setAutoCommit(false);
        	
            String insertQuery = "INSERT INTO Recipe (recipe_name, recipe_image, recipe_category, time_to_cook, user_id) VALUES (?, ?, ?, ?, ?)";
            System.out.println("[RecipeDao] SQL" + insertQuery);
            
            ppst = conn.prepareStatement(insertQuery);
            ppst.setString(1, recipeName);
            ppst.setBytes(2, recipeImage);
            ppst.setString(3, category);
            ppst.setString(4, timeToCook);
            ppst.setInt(5, userId);
            
            result = ppst.executeUpdate();            
            
            conn.commit();
            
        }catch(Exception e){
            System.out.println("[RecipeDao] Insert Recipe error: " + e.getMessage());
            conn.rollback();
        } finally {
        	
			if (ppst != null) {
				ppst.close();
			}
			
		}
        
        return (result == 1);
    }

    public RecipeDto selectRecipebyRecipeId(int recipeId) throws SQLException {
    	
    	String query = "SELECT * FROM Recipe WHERE recipe_id = ?";
    	System.out.println("[RecipeDao] SQL: "  + query + ", values=" + recipeId);
    	
    	PreparedStatement ppst = null;
    	ResultSet rs = null;
    	RecipeDto recipe = null;
    	
    	try {
    		
    		ppst =conn.prepareStatement(query);
    		ppst.setInt(1, recipeId);
    		rs = ppst.executeQuery();
    		
    		while (rs.next()) {
    			recipe = getColumn(rs);
    		}
    		
    	} catch (SQLException | IOException e) {
    		System.out.println("[RecipeDao] Select recipe by kewyord error: " + e.getMessage());
    	} finally {

    		if(ppst != null) {
				ppst.close();
			}
			if(rs != null) {
				rs.close();
			}
			
    	}
    	
    	return recipe;
    	
    }
}
