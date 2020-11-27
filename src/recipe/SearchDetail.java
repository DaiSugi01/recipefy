package recipe;

import java.io.IOException;
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
		
		System.out.println("[SearchDetail--doget] run");
		int recipeIdIdx = request.getRequestURI().lastIndexOf("/") + 1;
		int recipeId = Integer.parseInt(request.getRequestURI().substring(recipeIdIdx));
		RecipeDto recipeDto = null;
		ArrayList<IngredientsDto> ings = new ArrayList<>();
		ArrayList<DirectionsDto> dires = new ArrayList<>();
		
		try {
			DbHandler handler = DbHandler.getInstance();
			
			// get recipe data
			RecipeDao recipe = new RecipeDao(handler.getConnection());
			recipeDto = recipe.selectRecipebyRecipeId(recipeId);
			System.out.println("[SearchDetail--doget] selectRecipebyRecipeId run");
			
			// get ingredients data
			IngredientsDao ingDao = new IngredientsDao(handler.getConnection());
			ings = ingDao.selectIngredients(recipeId);
			System.out.println("[SearchDetail--doget] selectIngredients run");
			for (IngredientsDto i : ings) {
				System.out.println(i.getIngName());
			}
			
			// get direction data
			DirectionsDao dire = new DirectionsDao(handler.getConnection());
			dires = dire.selectDirectionsByRecipeId(recipeId);
			System.out.println("[SearchDetail--doget] selectDirectionsByRecipeId run");
			
			for (DirectionsDto d : dires) {
				System.out.println(d.getDirection());
			}
			
		} catch (SQLException e) {
			System.out.println("[SearchDetail--doget] failed: " + e.getMessage());
		}		
		
		System.out.println("[SearchDetail--doget] finish");
		HttpSession session = request.getSession();
		if (session.getAttribute("recipe") != null) {
			session.removeAttribute("recipe");
		}
		session.setAttribute("recipe", recipeDto);
		session.setAttribute("ings", ings);
		session.setAttribute("dires", dires);

		response.sendRedirect("/java-group-project/searchDetail.jsp");
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
