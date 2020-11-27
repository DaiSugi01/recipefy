package login;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import dbutil.DbHandler;
import user.UserDao;
import user.UserDto;

/**
 * Servlet implementation class Login2
 */
@WebServlet("/login")
public class Login extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Login() {
        super();
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		System.out.println("********** [Login-doGet] start **********");

		RequestDispatcher rd = request.getRequestDispatcher("/login.jsp");
		rd.forward(request, response);	
		
		System.out.println("********** [Login-doGet] finish **********");
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		System.out.println("********** [Login-doPost] start **********");

		response.getWriter().append("Served at: ").append(request.getContextPath());
		
		String email = request.getParameter("email");
		String password = request.getParameter("password");
	
		Connection conn = DbHandler.getInstance().getConnection();
		UserDao user = new UserDao(conn);
		
		boolean isError = false;
		
		try {
			
			if (user.checkCredentials(email, password)) {
				
				UserDto userDto = user.selectUser(email, password);

				HttpSession session = request.getSession();
				session.setAttribute("user", userDto);
				
			} else {
				isError = true;
			}
			
		} catch (SQLException e) {
			System.out.println("[Login--doPost] error: " + e.getMessage());
		} finally {
			if (conn != null) {
				try {
					conn.close();
				} catch (SQLException e) {
					System.out.println("[Login--doPost] connection close error: " + e.getMessage());
					e.printStackTrace();
				}
			}
		}
		
		if (isError) {			
			RequestDispatcher rd = request.getRequestDispatcher("/login-error.jsp");
			rd.forward(request, response);	
		} else {
			response.sendRedirect("home");
		};
		
		System.out.println("********** [Login-doPost] finish **********");


	}

}
