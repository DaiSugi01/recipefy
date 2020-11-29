package recipe;

import java.awt.image.BufferedImage;
import java.io.BufferedInputStream;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.imageio.ImageIO;
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
	private final String INGREDIENTS = "ingredients";
       
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

		System.out.println("********** [SearchResult--doGet] start **********");
		
		DbHandler hander = DbHandler.getInstance();
		Connection conn = hander.getConnection();

		String keyword = request.getParameter("search");
		ArrayList<RecipeDto> searchedRecipe = new ArrayList<>();

		try {
			RecipeDao recipe = new RecipeDao(hander.getConnection());
			
			String opt = request.getParameter("filter");
			
			switch (opt) {
				case INGREDIENTS:
					searchedRecipe = recipe.selectRecipesByIngredients(keyword);					
					break;
				default:
					searchedRecipe = recipe.selectRecipesByKeyword(keyword);
			}			
		
			for(RecipeDto r : searchedRecipe) {
				System.out.println(r.getRecipeName());
			}

		} catch (SQLException e) {
			System.out.println("[SearchResult--doget] failed: " + e.getMessage());
		} finally {
			if (conn != null) {
				try {
					conn.close();
				} catch (SQLException e) {
					System.out.println("[SearchResult--doget] Conneciton close failed: " + e.getMessage());
					e.printStackTrace();
				}				
			}
		}
		
		System.out.println("********** [SearchResult--doGet] done **********");
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

	/**
	 * 
	 * @param recipeImage
	 * @return
	 * @throws IOException
	 */
//	public BufferedImage deserializeImage(byte[] recipeImage) throws IOException {
//      InputStream inputStream = new ByteArrayInputStream(recipeImage);
//
//      BufferedInputStream bufferedInputStream = new BufferedInputStream(inputStream);
//      BufferedImage img = ImageIO.read(bufferedInputStream);
//      
//      return img;
//
//	}
}
