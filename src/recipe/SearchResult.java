package recipe;

import java.awt.image.BufferedImage;
import java.io.BufferedInputStream;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Base64;

import javax.imageio.ImageIO;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

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
//			searchedRecipe = recipe.tempSelectRecipesbyKeyword(keyword);
			
		} catch (SQLException e) {
			System.out.println("[SearchResult--doget] failed: " + e.getMessage());
		}
		
		for(RecipeDto r : searchedRecipe) {
			System.out.println(r.getRecipeName());
		}
		
		System.out.println("[SearchResult -- doGet] finish");
		HttpSession session = request.getSession();
		session.setAttribute("searchedRecipes", searchedRecipe);
		response.sendRedirect("searchResult.jsp");
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

	public BufferedImage deserializeImage(byte[] recipeImage) throws IOException {
      InputStream inputStream = new ByteArrayInputStream(recipeImage);

      BufferedInputStream bufferedInputStream = new BufferedInputStream(inputStream);
      BufferedImage img = ImageIO.read(bufferedInputStream);
      
      return img;

	}
}
