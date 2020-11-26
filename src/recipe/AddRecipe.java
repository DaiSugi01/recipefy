
package recipe;

import java.awt.image.BufferedImage;
import java.io.BufferedInputStream;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.sql.Date;
import java.sql.SQLException;
import java.util.ArrayList;

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
import ingredients.IngredientsDao;
import ingredients.IngredientsDto;
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
        // TODO Auto-generated constructor stub
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
		
		boolean isError = false;
		
		HttpSession session = request.getSession();
		UserDto user = (UserDto)session.getAttribute("user");
		
		String recipeName = request.getParameter("recipeName");		
		String category = request.getParameter("categories");
		String directions = request.getParameter("directions");
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
			int i = 0;
			Date d = null;
			ingList.add(new IngredientsDto(i, ingredient, d));
			ingCount++;
			
		} while (true);
		
		// Validation check
		if (!isEmpty(recipeName, category, ingList, directions, timeToCook)) {
			byte[] recipeImage = cahngeImageToBinery(request);

			DbHandler handler = DbHandler.getInstance();
			RecipeDao recipeDao = new RecipeDao(handler.getConnection());

			// Insert Recipe
			if (!recipeDao.insertRecipe(recipeName, recipeImage, category, timeToCook, user.getId())) {
				isError = true;
			}
			
			// Insert Ingredients if not exists.
			if (!isError) {
				isError = insertIngredients(handler, ingList);
			}
			
			if (!isError) {
				int recipeId = 0;
				try {
					recipeId = getRecipeId(handler, user.getId());
					isError = insertdirections(handler, directions, recipeId);
				} catch (SQLException e) {
					System.out.println("Insert Directions error: " + e.getMessage());
				}
			}
		}
		
		if (isError) {
			System.out.println("[AddRecipe--doPost] finish widhout error");
			RequestDispatcher rd = request.getRequestDispatcher("/add-recipe-success.jsp");
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
    public boolean isEmpty(String recipeName, String category, ArrayList<IngredientsDto> ingList, String directions, String timeToCook) {
    	if (recipeName == null || recipeName.isEmpty()) {
    		return true;
    	}
    	
    	if (category == null || category.isEmpty()) {
    		return true;
    	}
    	
    	if (ingList == null || ingList.size() == 0) {
    		return true;
    	}
    	
    	if (directions == null || directions.isEmpty()) {
    		return true;
    	}
    	
    	if (timeToCook == null || timeToCook.isEmpty()) {
    		return true;
    	}

    	return false;
    }
    
    
    public boolean insertIngredients(DbHandler handler, ArrayList<IngredientsDto> ingList) {
    	boolean isError = false;
    	
		IngredientsDao ingDao = new IngredientsDao(handler.getConnection());
		
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
    	return false;
    }

    public int getRecipeId(DbHandler handler, int userId) throws SQLException {
    	
    	RecipeDao recipeDao = new RecipeDao(handler.getConnection());
    	RecipeDto recipe = recipeDao.selectRecipebyId(userId);
    	return recipe.getRecipeId();
    }
    
    public boolean insertdirections(DbHandler handler, String directions, int recipeId) {
    	DirectionsDao directionsDao = new DirectionsDao(handler.getConnection());
    	return directionsDao.insertDirections(directions, recipeId);
    }
    	
}
