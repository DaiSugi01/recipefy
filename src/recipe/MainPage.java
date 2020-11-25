package recipe;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import dbutil.DbHandler;

/**
 * Servlet implementation class MainPage
 */
@WebServlet("/home")
public class MainPage extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public MainPage() {
        super();
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		System.out.println("[MainPage -- doGet] start");
		
		ArrayList<RecipeDto> recipes = new ArrayList<>();

		try {
			DbHandler hander = DbHandler.getInstance();
			RecipeDao recipeDao = new RecipeDao(hander.getConnection());
			recipes = recipeDao.selectRecipes();
			

		} catch (SQLException e) {
			System.out.println("[MainPage - doget()] Couldn't get data from recipes table");
		}

		request.setAttribute("recipes", recipes);
		RequestDispatcher rd = request.getRequestDispatcher("/index.jsp");

		HttpSession session = request.getSession();
		System.out.println(session.getAttribute("user"));
		System.out.println("[MainPage -- doGet] finish");
		rd.forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
