
package recipe;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Date;
import java.sql.SQLException;
import java.util.ArrayList;
import java.sql.Connection;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.servlet.http.Part;

import dbutil.DbHandler;
import directions.DirectionsDao;
import directions.DirectionsDto;
import ingredients.IngredientsDao;
import ingredients.IngredientsDto;
import ingredients.RecipeIngredientsDao;
import user.UserDto;

/**
 * Servlet implementation class AddRecipe
 */
@MultipartConfig
@WebServlet("/addRecipe")
public class AddRecipe extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public AddRecipe() {
        super();
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		HttpSession session = request.getSession();
		UserDto user = (UserDto)session.getAttribute("user");

		if (user == null) {
			response.sendRedirect("login");
		} else {
			RequestDispatcher rd = request.getRequestDispatcher("/add-recipe.jsp");
			rd.forward(request, response);			
		}

	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		System.out.println("[AddRecipe--doPost] run");
		
		boolean isError = true;
		
		HttpSession session = request.getSession();
		UserDto user = (UserDto)session.getAttribute("user");
		
		String recipeName = request.getParameter("recipeName");		
		String category = request.getParameter("categories");
		String timeToCook = request.getParameter("times");

		ArrayList<IngredientsDto> ingList = new ArrayList<>();
		int ingCount = 1;
		do {
			String ingredient = request.getParameter("ingredients" + ingCount);
			if (ingredient == null) {
				System.out.println("break!!");
				break;
			}
			System.out.println("ingredient" + ingCount + ": " + ingredient);
			int tempId = 0;
			Date tempDate = null;
			ingList.add(new IngredientsDto(tempId, ingredient, tempDate));
			ingCount++;
			
		} while (true);
		
		ArrayList<DirectionsDto> directionList = new ArrayList<>();
		int dirCount = 1;
		do {
			String direction = request.getParameter("directions" + dirCount);
			if (direction == null) {
				break;
			}
			System.out.println("directions" + dirCount + ": " + direction);
			int defaultId = 0;
			Date tempDate = null;
			directionList.add(new DirectionsDto(defaultId, direction, defaultId, tempDate));
			dirCount++;
			
		} while (true);
		
		System.out.println(recipeName);
		System.out.println(category);
		System.out.println(ingList);
		System.out.println(timeToCook);
		
		for (DirectionsDto dire : directionList) {
			System.out.println(dire.getDirection());
		}
		
		// Validation check
		if (!isEmpty(recipeName, category, ingList, directionList, timeToCook)) {
			byte[] recipeImage = cahngeImageToBinery(request);
			Connection conn = DbHandler.getInstance().getConnection();
			
			try {
				conn.setAutoCommit(false);
			
				RecipeDao recipeDao = new RecipeDao(conn);
	
				// Insert Recipe
				if (!recipeDao.insertRecipe(recipeName, recipeImage, category, timeToCook, user.getId())) {
					System.out.println("[AddRecipe] Insert Recipe error");
					isError = true;
				} else {
					isError = false;
				}
				
				System.out.println("insertRecipe run " + !isError);
				
				// Insert Ingredients if not exists.
				IngredientsDao ingDao = new IngredientsDao(conn);
				if (!isError) {
					isError = insertIngredients(conn, ingList, ingDao);
				}
				System.out.println("insertIngredients run " + isError);
							
				int recipeId = 0;
	
				// Insert directions
				if (!isError) {
					try {
						// get recipe id
						recipeId = getRecipeId(conn, user.getId());
						System.out.println("getRecipeId run " + isError);
						
						// set recipe id for each direction
						for (DirectionsDto d : directionList) {
							d.setRecipeId(recipeId);
						}
	
						isError = insertdirections(conn, directionList);
						System.out.println("insertdirections run " + isError);
					} catch (SQLException e) {
						isError = true;
						System.out.println("Insert Directions error: " + e.getMessage());
					}
				}
				
				// Insert RecipeIngredients
				if (!isError) {
					try {
						RecipeIngredientsDao riDao = new RecipeIngredientsDao(conn);
						ArrayList<IngredientsDto> ingNames = ingDao.selectIngredientsbyName(ingList);
						if(!riDao.insertRecipeIngredients(recipeId, ingNames)) {
							System.out.println("insertRecipeIngredients error");
						}
						System.out.println("insertRecipeIngredients run " + isError);
	
					} catch (Exception e) {
						isError = true;
						System.out.println("Insert RecipeIngredients error: " + e.getMessage());
					}
				}
				
				if (isError) {
					conn.rollback();
				} else {
					conn.commit();					
				}
				
			} catch (SQLException e1) {
				System.out.println("[AddRecipe] error: " + e1.getMessage());
				e1.printStackTrace();
				try {
					conn.rollback();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			} finally {
				try {
					if (conn != null) {
						conn.close();
					}
				} catch (SQLException e) {
						System.out.println("Connection close error: " + e.getMessage());
				}
			}

		}
		
		if (!isError) {
			System.out.println("[AddRecipe--doPost] finish widhout error");
			RequestDispatcher rd = request.getRequestDispatcher("/added-recipe.jsp");
			rd.forward(request, response);
		} else {
			System.out.println("[AddRecipe--doPost] finish widh error");
			
			RequestDispatcher rd = request.getRequestDispatcher("/add-recipe-failed.jsp");
			rd.forward(request, response);
		}
	}

	
	public byte[] cahngeImageToBinery(HttpServletRequest request) throws IOException, ServletException {
		Part part = request.getPart("recipeImage");
		InputStream inputStream = part.getInputStream();

        byte[] byteImage = convertInputStreamToByteArray(inputStream);
        System.out.println(byteImage.length);
        return byteImage;
	}
	
    //InputStreamをByte配列にする
    public byte[] convertInputStreamToByteArray(InputStream inputStream) throws IOException {
        ByteArrayOutputStream buffer = new ByteArrayOutputStream();
        int nRead;
        byte[] data = new byte[16777215];
        while ((nRead = inputStream.read(data, 0, data.length)) != -1) {
            buffer.write(data, 0, nRead);
        }
        return buffer.toByteArray();
    }
    
    /**
     * validation check for form
     * @param recipeName
     * @param category
     * @param ingList
     * @param directions
     * @param timeToCook
     * @return boolean value
     */
    public boolean isEmpty(String recipeName, String category, ArrayList<IngredientsDto> ingList, 
    		ArrayList<DirectionsDto> directionList, String timeToCook) {
    	if (recipeName == null || recipeName.isEmpty()) {
    		return true;
    	}
    	
    	System.out.println("recipeName ok");
    	
    	if (category == null || category.isEmpty()) {
    		return true;
    	}

    	System.out.println("category ok");
    	
    	if (ingList == null || ingList.size() == 0) {
    		return true;
    	}
    	
    	System.out.println("ingList ok");

    	if (directionList == null || directionList.size() == 0) {
    		return true;
    	}
    	
    	System.out.println("directions ok");

    	if (timeToCook == null || timeToCook.isEmpty()) {
    		return true;
    	}
    	
    	System.out.println("timeToCook ok");
    	
    	System.out.println("Validation ok");
    	
    	return false;
    }
    
    
    public boolean insertIngredients(Connection conn, ArrayList<IngredientsDto> ingList, IngredientsDao ingDao) {
    	System.out.println("***********[ADDRecipe] insertIngredients run***********");
    	boolean isError = false;
    			
		try {
			ArrayList<IngredientsDto> newIngs = ingDao.checkIngredients(ingList);
			
			if (ingList.size() != newIngs.size()) {
				
				ArrayList<IngredientsDto> ingsForCheck = new ArrayList<>();
				for (int i =0; i < ingList.size(); i++) {
					if (!newIngs.contains(ingList.get(i))) {
						ingsForCheck.add(ingList.get(i));
					}
				}
				
				if (!ingDao.insertIngredients(ingsForCheck)) {
					isError = true;
					System.out.println("[AddRecipe--doGet insert ingredients error");
				}
			}

		} catch (SQLException e) {
			isError = true;
			System.out.println("[AddRecipe--doGet insert ingredients error: " + e.getMessage());
		}	
    	return isError;
    }

    public int getRecipeId(Connection conn, int userId) throws SQLException {
    	
    	RecipeDao recipeDao = new RecipeDao(conn);
    	RecipeDto recipe = recipeDao.selectRecipebyId(userId);
    	return recipe.getRecipeId();
    }
    
    public boolean insertdirections(Connection conn, ArrayList<DirectionsDto> directions) {
    	DirectionsDao directionsDao = new DirectionsDao(conn);
    	return !directionsDao.insertDirections(directions);
    }
    	
}
