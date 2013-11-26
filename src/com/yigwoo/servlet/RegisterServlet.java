package com.yigwoo.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import javax.servlet.*;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;

import com.yigwoo.bean.DBQuerier;

@WebServlet(name="register", urlPatterns={"/register"})
public class RegisterServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	//private static final String INSERT_USER_QUERY = 
		//	"insert into Users(username,email, password) values (?,?,?)";

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		String email = req.getParameter("email");
		String username = req.getParameter("username");
		String password = req.getParameter("password1");
		
		Connection connection = (Connection) getServletContext().getAttribute("dbConnection");
		DBQuerier querier = new DBQuerier(connection);
		boolean isInserted = querier.InsertUserRecord(username, password, email);
		if (isInserted) {
			RequestDispatcher rd = getServletContext().getRequestDispatcher("/login.html");
			PrintWriter out = resp.getWriter();
			out.println("<font color=green>Registration successful, please login below.</font>");
			rd.include(req, resp); 
		} else {
			RequestDispatcher rd = getServletContext().getRequestDispatcher("/register.html");
			PrintWriter out = resp.getWriter();
			out.println("<font color=red>Registration failed, please change your email or username.</font>");
			rd.include(req, resp);
		}
	}
}
