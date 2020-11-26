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
	
    public ArrayList<IngredientsDto> selectIngredientsbyName(ArrayList<IngredientsDto> ings) {
    	
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
    			System.out.println("ing_id:" + ingId + ", ing_name:" + ingName + "craeted_date" + createdDate );
                newIngredients.add(new IngredientsDto(ingId, ingName, createdDate));
    		}
    		
    	} catch (SQLException e) {
    		System.out.println("Select recipe by name error: " + e.getMessage());
    	} finally {
    		try {
    			if(ppsmt != null) {
    				ppsmt.close();
    			}
    			if(rs != null) {
    				rs.close();
    			}
    		} catch (SQLException e) {
                System.out.println("selectIngredientsbyName error: " + e.getMessage());
    		}
    	}
    	
    	return newIngredients;
    }
    
    public ArrayList<IngredientsDto> selectIngredients(int recipeId) {
    	String query = 
    			"SELECT I.ing_id, I.ing_name, I.created_date FROM Ingredients as I "
    			+ "INNER JOIN RecipeIngredients as RI on I.ing_id = Ri.ing_id "
    			+ "WHERE RI.recipe_id = ?";
    	System.out.println("SQL: " + query + ", vallue=" + recipeId);

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
                returnedIngs.add(new IngredientsDto(ingId, ingName, createdDate));
    		}
    		
    	} catch (SQLException e) {
    		System.out.println("Select Ingredients by kewyord error: " + e.getMessage());
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
    	
    	return returnedIngs;
    }
    
    public boolean insertIngredients(ArrayList<IngredientsDto> ings){
        PreparedStatement ppst = null;
        
        try{
        	
        	String sql = "INSERT INTO Ingredients (ing_name) VALUES (?)";
            
            int result = 0;
            for (IngredientsDto ing : ings) {
                System.out.println(sql + ", value=" + ing.getIngName());
                ppst = conn.prepareStatement(sql);
                ppst.setString(1, ing.getIngName());
                ppst.executeUpdate();
                result++;
                ppst = null;
            }
            
            return (result == ings.size());
            
        }catch(Exception e){
            System.out.println("Insert Ingredients error: " + e.getMessage());
        }
        return false;
    }

}
