package recipe;

import java.awt.image.BufferedImage;
import java.io.BufferedInputStream;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.imageio.ImageIO;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dbutil.DbHandler;

/**
 * Servlet implementation class SearchResult
 */
@WebServlet("/searchResult")
public class SearchResult extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public SearchResult() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		System.out.println("[SearchResult -- doGet] run");
		
		String keyword = request.getParameter("search");
		DbHandler hander = DbHandler.getInstance();
		ArrayList<RecipeDto> searchedRecipe = null;


		
		try {
			RecipeDao recipe = new RecipeDao(hander.getConnection());
			searchedRecipe = recipe.selectRecipesbyKeyword(keyword);

			for (RecipeDto recipeDto : searchedRecipe) {
//				response.setContentType("image/png");
				OutputStream outputStream = response.getOutputStream();
				BufferedImage img = deserializeImage(recipeDto.getRecipeImage());
				ImageIO.write(img, "png", outputStream);
				request.setAttribute("outputStream", outputStream);
//				outputStream.flush();
			}
			
		} catch (SQLException e) {
			System.out.println("[SearchResult--doget] failed: " + e.getMessage());
		}
		
		request.setAttribute("searchedRecipes", searchedRecipe);
		
		for(RecipeDto r : searchedRecipe) {
			System.out.println(r.getRecipeName());
		}
		
		System.out.println(searchedRecipe != null && searchedRecipe.size() > 0);
		System.out.println("[SearchResult -- doGet] finish");
		
		RequestDispatcher rd = request.getRequestDispatcher("/searchResult.jsp");
		rd.forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

	public BufferedImage deserializeImage(byte[] recipeImage) throws IOException {
      InputStream inputStream = new ByteArrayInputStream(recipeImage);

      BufferedInputStream bufferedInputStream = new BufferedInputStream(inputStream);
      BufferedImage img = ImageIO.read(bufferedInputStream);
      
      return img;

	}
}
