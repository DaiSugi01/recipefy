package recipe;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

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

		System.out.println("********** [MainPage-doGet] start **********");
		
		ArrayList<RecipeDto> recipes = new ArrayList<>();

		Connection conn = DbHandler.getInstance().getConnection();
		try {

			RecipeDao recipeDao = new RecipeDao(conn);
			recipes = recipeDao.selectRecipes();
			
		} catch (SQLException e) {
			System.out.println("[MainPage--doGet] Couldn't get data from recipes table");
		} finally {
			if (conn != null) {
				try {
					conn.close();
				} catch (SQLException e) {
					System.out.println("[MainPage--doget] connection close error: " + e.getMessage());
					e.printStackTrace();
				}
			}
		}

		request.setAttribute("recipes", recipes);

		RequestDispatcher rd = request.getRequestDispatcher("index.jsp");
		rd.forward(request, response);

		System.out.println("********** [MainPage-doGet] finish **********");
}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
