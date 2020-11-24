package recipe;

import java.io.IOException;
import java.sql.ResultSet;
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
		
		ArrayList<String> recipes = new ArrayList<>();

		try {
			
			DbHandler hander = DbHandler.getInstance();
			ResultSet result = hander.selectRecipes();
			
			while (result.next()) {
				recipes.add(result.getString("recipe_name"));
			}

		} catch (SQLException e) {
			System.out.println("[MainPage - doget()] Couldn't get data from recipes table");
		}

		System.out.println("run");

		for (String string : recipes) {
			System.out.println(string);
		}
		String a = "aaa";
		request.setAttribute("a", a);
		request.setAttribute("recipes", recipes);
		RequestDispatcher rd = request.getRequestDispatcher("/index.jsp");
		rd.forward(request, response);
		
		
		
		
		
		
		
		
//		RequestDispatcher rd = request.getRequestDispatcher("");
//		request.setAttribute("recipes", recipes);
//		getServletConfig().getServletContext().getRequestDispatcher("/index.jsp").forward(request,response);
//		rd.forward(request, response);
//		response.sendRedirect("recipeList");
//		response.sendRedirect("index.jsp");
		
//		RequestDispatcher rd = request.getRequestDispatcher("/index.jsp");
//		rd.forward(request, response);
		
		
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
