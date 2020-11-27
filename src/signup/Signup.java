package signup;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;

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
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		System.out.println("********** [Signup-doGet] start **********");
		RequestDispatcher rd = request.getRequestDispatcher("/signup.jsp");
		rd.forward(request, response);
		System.out.println("********** [Signup-doGet] finish **********");
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		System.out.println("********** [Signup-doPost] start **********");
		response.getWriter().append("Served at: ").append(request.getContextPath());

		String firstName = request.getParameter("firstName");
		String lastName = request.getParameter("lastName");
		String email = request.getParameter("email");
		String password = request.getParameter("password");
	
		Connection conn = DbHandler.getInstance().getConnection();
		boolean isError = false;

		try {
			
			if (!isEmpty(firstName, lastName, email, password)) {

				UserDao user = new UserDao(conn);
				
				// Check if user is exists
				if (user.selectUserCount(firstName, lastName, email, password)) {
					isError = true;
				}
				
				// Insert user
				if (!isError) {
					if (!user.insertUser(firstName, lastName, email, password)){
						isError = true;
					}
				}
			}
			
		} catch (SQLException e) {
			System.out.println("[Signup--diPost] error: " + e.getMessage());
		} finally {
			if (conn != null) {
				try {
					conn.close();
				} catch (SQLException e) {
					System.out.println("[Signup] connection close error: " + e.getMessage());
					e.printStackTrace();
				}
			}
		}
		
		
		if (isError) {
			RequestDispatcher rd = request.getRequestDispatcher("/signup-error.jsp");
			rd.forward(request, response);
		} else {
			response.sendRedirect("login");
		}
	
		System.out.println("********** [Signup-doPost] finish **********");
	}

	
	/**
	 * validation check
	 * @param firstName
	 * @param lastName
	 * @param email
	 * @param password
	 * @return
	 * 		true  : validation passed
	 * 		false : validation failed
	 */
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
