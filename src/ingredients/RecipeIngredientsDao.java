package ingredients;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import directions.DirectionsDto;

public class RecipeIngredientsDao {

	private final Connection conn;

	public RecipeIngredientsDao(Connection conn) {
		this.conn = conn;
	}

    public boolean insertRecipeIngredients(int recipeId, ArrayList<IngredientsDto> ings){
        PreparedStatement ppst = null;
        
        try{
        	
        	String sql = "INSERT INTO RecipeIngredients (recipe_id, ing_id) VALUES (?,?)";
            
            int result = 0;
            for (IngredientsDto ing : ings) {
                System.out.println(sql + ", value=" + recipeId + ", " +ing.getIngName());
                ppst = conn.prepareStatement(sql);
                ppst.setInt(1, recipeId);
                ppst.setInt(2, ing.getIngId());
                ppst.executeUpdate();
                result++;
                ppst = null;
            }
            
            return (result == ings.size());
            
        }catch(Exception e){
            System.out.println("Insert Recipe error: " + e.getMessage());
        }
        return false;
    }
    

}
