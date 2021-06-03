package servlets;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import models.User;

@WebServlet("/LoginServlet")
public class LoginServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    public LoginServlet() {
        super();
    }
    
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {		
		// Get parameters
		String user_id = request.getParameter("user_id");
		String password = request.getParameter("password");

		// Initialize attributes		
		String redirect = "Login.jsp"; // return to login by default
		String fullname = null;
		String error = null;
		
		// Check user and password are valid
		if (user_id != null && user_id.length() != 0
				&& password != null & password.length() != 0) {
			User user = new User();
			
			if (user.find(user_id, password) == true) {
				fullname = user.getFullName();
				if (fullname.isBlank()) fullname = user.getUserID(); // Use user ID as backup
				redirect = "Home.jsp"; // successful login, go to home page
			} else {
				error = "Incorrect username or password.";
			}
		} else {
			error = "Invalid username or password";
		}
		
		// Go to next page
		request.getSession().setAttribute("fullname", fullname);
		request.getSession().setAttribute("error", error);
		response.sendRedirect(redirect);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
