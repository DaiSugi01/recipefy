package recipe;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dbutil.DbHandler;
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
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		int recipeIdIdx = request.getRequestURI().lastIndexOf("/") + 1;
		int recipeId = Integer.parseInt(request.getRequestURI().substring(recipeIdIdx));

		DbHandler handler = DbHandler.getInstance();
		RecipeDao recipe = new RecipeDao(handler.getConnection());
		
		
		IngredientsDao ingDao = new IngredientsDao(handler.getConnection());
		ArrayList<IngredientsDto> ings = ingDao.selectIngredients(recipeId);
		
		
		
		
		RequestDispatcher rd = request.getRequestDispatcher("/searchDetail.jsp");
		rd.forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
