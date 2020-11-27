package recipe;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import dbutil.DbHandler;
import directions.DirectionsDao;
import directions.DirectionsDto;
import ingredients.IngredientsDao;
import ingredients.IngredientsDto;

/**
 * Servlet implementation class SearchDetail
 */
@WebServlet("/searchDetail/*")
public class SearchDetail extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public SearchDetail() {
        super();
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
		
		System.out.println("********** [SearchDetail-doGet] start **********");

		int recipeIdIdx = request.getRequestURI().lastIndexOf("/") + 1;
		int recipeId = Integer.parseInt(request.getRequestURI().substring(recipeIdIdx));
		
		Connection conn = DbHandler.getInstance().getConnection();
		RecipeDto recipeDto = null;
		ArrayList<IngredientsDto> ings = new ArrayList<>();
		ArrayList<DirectionsDto> dires = new ArrayList<>();
		
		try {
			
			// Get recipe data
			RecipeDao recipe = new RecipeDao(conn);
			recipeDto = recipe.selectRecipebyRecipeId(recipeId);
			System.out.println("[SearchDetail--doGet] selectRecipebyRecipeId run");
			
			// Get ingredients data
			IngredientsDao ingDao = new IngredientsDao(conn);
			ings = ingDao.selectIngredientsByRecipeId(recipeId);
			System.out.println("[SearchDetail--doGet] selectIngredients run");
			
			// Get direction data
			DirectionsDao dire = new DirectionsDao(conn);
			dires = dire.selectDirectionsByRecipeId(recipeId);
			System.out.println("[SearchDetail--doGet] selectDirectionsByRecipeId run");
						
		} catch (SQLException e) {
			System.out.println("[SearchDetail--doGet] failed: " + e.getMessage());
		}		
		
		System.out.println("[SearchDetail--doget] finish");
		HttpSession session = request.getSession();
		
		if (session.getAttribute("recipe") != null) {
			session.removeAttribute("recipe");
		}
		
		session.setAttribute("recipe", recipeDto);
		session.setAttribute("ings", ings);
		session.setAttribute("dires", dires);

		response.sendRedirect("searchDetail.jsp");
		
		System.out.println("********** [SearchDetail-doGet] finish **********");

	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
