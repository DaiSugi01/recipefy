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
	
	/**
	 * search ingredients by name
	 * @param ings
	 * @return list of ingredients searched by ingredients name
	 * @throws SQLException 
	 */
    public ArrayList<IngredientsDto> selectIngredientsbyName(ArrayList<IngredientsDto> ings) throws SQLException {
    	
    	// make ? for preparedStatement
    	StringBuilder ppsts = new StringBuilder();
    	for (int i = 0; i < ings.size(); i++) {
    		ppsts.append("?");
    		
    		if (i != ings.size()-1) {
    			ppsts.append(", ");
    		}
    	}
    	
    	String query = "SELECT * FROM Ingredients WHERE ing_name in (" + ppsts.toString() + ")";

    	
    	PreparedStatement ppsmt = null;
    	ResultSet rs = null;
    	ArrayList<IngredientsDto> newIngredients = new ArrayList<>();

    	try {
    		ppsmt = conn.prepareStatement(query);
        	for (int i = 0; i < ings.size(); i++) {
    			ppsmt.setString(i+1, ings.get(i).getIngName());
    		}
    		rs = ppsmt.executeQuery();
    		
    		while (rs.next()) {
                int ingId = rs.getInt("ing_id");
                String ingName = rs.getString("ing_name");
                Date createdDate = rs.getDate("created_date");

                System.out.println("[IngredientsDao] SQL: " + query + 
                		", values=ing_id:" + ingId + ", " + ingName + ", " + createdDate );
                newIngredients.add(new IngredientsDto(ingId, ingName, createdDate));
    		}
    		
    	} catch (SQLException e) {
    		System.out.println("[IngredientsDao] Select recipe by name error: " + e.getMessage());
    	} finally {

			if(rs != null) {
				rs.close();
			}

			if(ppsmt != null) {
				ppsmt.close();
			}
    	}
    	
    	return newIngredients;
    }
    
    /**
     * select ingredients by recipe id
     * @param recipeId
     * @return list of ingredients
     * @throws SQLException 
     */
    public ArrayList<IngredientsDto> selectIngredientsByRecipeId(int recipeId) throws SQLException {
    	String query = 
    			"SELECT I.* FROM Ingredients as I "
    			+ "INNER JOIN RecipeIngredients as RI on I.ing_id = Ri.ing_id "
    			+ "WHERE RI.recipe_id = ?";
    	System.out.println("[IngredientsDao] SQL: " + query + ", vallue=" + recipeId);

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
            
            System.out.println("[IngredientsDao] selectIngredients done");

        } catch (SQLException e) {
            System.out.println("[IngredientsDao] selectIngredients error: " + e.getMessage());
        } finally {
        	if(rs != null) {
                rs.close();
            }
            
            if(pstmt != null) {
            	pstmt.close();
            }
        }
    	
    	return ingredients;
    }
    
    /**
     * check if ingredients is exists
     * @param ings
     * @return list of ingredients
     * @throws SQLException
     */
    public ArrayList<IngredientsDto> checkIngredients(ArrayList<IngredientsDto> ings) throws SQLException {
    	
    	// make ? for preparedStatement
    	StringBuilder ppsts = new StringBuilder();
    	for (int i = 0; i < ings.size(); i++) {
    		ppsts.append("?");
    		
    		if (i != ings.size()-1) {
    			ppsts.append(", ");
    		}
    	}
    	
    	String query = "SELECT * FROM Ingredients WHERE ing_name in (" + ppsts.toString() + ")";

    	PreparedStatement ppsmt = null;
    	ResultSet rs = null;
    	ArrayList<IngredientsDto> returnedIngs = new ArrayList<>();

    	System.out.print("[IngredientsDao] SQL: " + query + ", value=");
        		

    	try {
    		ppsmt = conn.prepareStatement(query);
        	for (int i = 0; i < ings.size(); i++) {
        		System.out.print(ings.get(i).getIngName() + ", ");
    			ppsmt.setString(i+1, ings.get(i).getIngName());
    		}
    		rs = ppsmt.executeQuery();
    		
    		while (rs.next()) {
                int ingId = rs.getInt("ing_id");
                String ingName = rs.getString("ing_name");
                Date createdDate = rs.getDate("created_date");
                returnedIngs.add(new IngredientsDto(ingId, ingName, createdDate));
    		}
    		
    	} catch (SQLException e) {
    		System.out.println("[IngredientsDao Select Ingredients by kewyord error: " + e.getMessage());
    	} finally {
		
    		if(rs != null) {
				rs.close();
			}

			if(ppsmt != null) {
				ppsmt.close();
			}
    	}
    	
    	return returnedIngs;
    }
    
    /**
     * insert ingredients
     * @param ings
     * @return 
     * 		true  : insert success
     * 		false : insert failed
     * @throws SQLException 
     */
    public boolean insertIngredients(ArrayList<IngredientsDto> ings) throws SQLException {
        PreparedStatement ppst = null;
        int result = 0;

        try{
        	conn.setAutoCommit(false);
        	
        	String sql = "INSERT INTO Ingredients (ing_name) VALUES (?)";
        	System.out.println("");

            for (IngredientsDto ing : ings) {
                System.out.println(sql + ", value=" + ing.getIngName());
                ppst = conn.prepareStatement(sql);
                ppst.setString(1, ing.getIngName());
                ppst.executeUpdate();
                result++;
            }
            
            conn.commit();
            
        }catch(SQLException e){
            System.out.println("[IngredientsDao] Insert Ingredients error: " + e.getMessage());
            conn.rollback();
        } finally {
			if (ppst != null) {
				ppst.close();
			}
		}
        
        return (result == ings.size());
    }

}
