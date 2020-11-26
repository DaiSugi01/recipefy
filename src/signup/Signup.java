package signup;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dbutil.DbHandler;
import user.UserDao;

/**
 * Servlet implementation class Signup
 */
@WebServlet("/signup")
public class Signup extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Signup() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		System.out.println("[Signup-doget] run");
		RequestDispatcher rd = request.getRequestDispatcher("/signup.jsp");
		rd.forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		System.out.println("[Signup-doPost] run");
		response.getWriter().append("Served at: ").append(request.getContextPath());
		DbHandler handler = DbHandler.getInstance();
		
		System.out.println("Success!");
		
		String firstName = request.getParameter("firstName");
		String lastName = request.getParameter("lastName");
		String email = request.getParameter("email");
		String password = request.getParameter("password");
	
		System.out.println(firstName);
		System.out.println(lastName);
		System.out.println(email);
		System.out.println(password);
		
		if (isEmpty(firstName, lastName, email, password)) {
			RequestDispatcher rd = request.getRequestDispatcher("/signup-error.jsp");
			rd.forward(request, response);
		} else {
			
			UserDao user = new UserDao(handler.getConnection());
			
			if (user.insertUser(firstName, lastName, email, password)) {
				response.sendRedirect("login");
			} else {
				// error
				RequestDispatcher rd = request.getRequestDispatcher("/signup-error.jsp");
				rd.forward(request, response);
			}
		}
	
	}

	
	public boolean isEmpty(String firstName, String lastName, String email, String password) {
		
		if (firstName == null || firstName.isEmpty()) {
			return true;
		}

		if (lastName == null || lastName.isEmpty()) {
			return true;
		}
		
		if (email == null || email.isEmpty()) {
			return true;
		}
		
		if (password == null || password.isEmpty()) {
			return true;
		}
		
		return false;
	}
}
