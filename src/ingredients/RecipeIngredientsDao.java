package ingredients;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;


public class RecipeIngredientsDao {

	private final Connection conn;

	public RecipeIngredientsDao(Connection conn) {
		this.conn = conn;
	}

	/**
	 * Insert RecipeIngredients
	 * @param recipeId
	 * @param ings
	 * @return
     * 		true  : insert success
     * 		false : insert failed
	 * @throws SQLException 
	 */
    public boolean insertRecipeIngredients(int recipeId, ArrayList<IngredientsDto> ings) throws SQLException{
    	
    	String sql = "INSERT INTO RecipeIngredients (recipe_id, ing_id) VALUES (?,?)";
        int result = 0;

    	PreparedStatement ppst = null;
        
        try{
        	conn.setAutoCommit(false);
            for (IngredientsDto ing : ings) {
                System.out.println("[RecipeIngredientsDao] SQL: " + sql + ", value=" + recipeId + ", " +ing.getIngName());
                ppst = conn.prepareStatement(sql);
                ppst.setInt(1, recipeId);
                ppst.setInt(2, ing.getIngId());
                ppst.executeUpdate();
                result++;
            }
            
            conn.commit();
            
        } catch(SQLException e) {
            System.out.println("Insert Recipe error: " + e.getMessage());
            conn.rollback();
        } finally {
			
        	if (ppst != null) {
        		ppst.close();
        	}
		}
        
        return (result == ings.size());
    }
    
}
