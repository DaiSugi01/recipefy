package login;

import java.io.IOException;

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
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.getWriter().append("Served at: ").append(request.getContextPath());
		DbHandler handler = DbHandler.getInstance();
		
		System.out.println("Success!");
		
		String email = request.getParameter("email");
		String password = request.getParameter("password");
	
		System.out.println(email);
		System.out.println(password);
		UserDao user = new UserDao(handler.getConnection());
		
		if (user.checkCredentials(email, password)) {
			
			UserDto userDto = user.selectUser(email, password);

			HttpSession session = request.getSession();
			session.setAttribute("user", userDto);
			
			response.sendRedirect("home");

		} else {
			// go to error
			RequestDispatcher rd = request.getRequestDispatcher("/login-error.html");
			rd.forward(request, response);	
		};
		
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
