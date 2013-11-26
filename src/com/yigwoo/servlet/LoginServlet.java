package com.yigwoo.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.*;

import javax.servlet.*;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;

import com.yigwoo.bean.DBQuerier;
import com.yigwoo.bean.User;

@WebServlet(name = "login", urlPatterns = { "/login" })
public class LoginServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		String username = req.getParameter("username");
		String password = req.getParameter("password");
		Connection connection = (Connection) getServletContext().getAttribute("dbConnection");
		DBQuerier querier = new DBQuerier(connection);
		User user = querier.FindUserRecord(username, password);
		if (user != null) {
			HttpSession session = req.getSession();
			session.setAttribute("user", user);
			resp.sendRedirect("home.jsp");
		}
		else {
			RequestDispatcher rd = getServletContext().getRequestDispatcher("/login.html");
			PrintWriter out = resp.getWriter();
			out.println("<font color=red>Incorrect Username or Password.</font>");
			rd.include(req,resp);
		}
	}
}
