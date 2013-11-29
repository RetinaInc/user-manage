package com.yigwoo.servlet.listener;

import java.sql.Connection;
import java.sql.SQLException;
import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;
import com.yigwoo.bean.DBConnectionManager;

@WebListener
public class AppContextListener implements ServletContextListener {

	@Override
	public void contextDestroyed(ServletContextEvent event) {
		Connection connection = (Connection) event.getServletContext().
				getAttribute("dbConnection");
		try {
			connection.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void contextInitialized(ServletContextEvent event) {
		ServletContext ctx = event.getServletContext();
		//initilize DB connection
		String dbURL = ctx.getInitParameter("dbURL");
		String dbUser = ctx.getInitParameter("dbUser");
		String dbPassword = ctx.getInitParameter("dbPassword");
		try {
			DBConnectionManager connectionManager = 
					new DBConnectionManager(dbURL, dbUser, dbPassword);
			ctx.setAttribute("dbConnection", connectionManager.getConnection());
		} catch (ClassNotFoundException e){
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}
