package com.yigwoo.bean;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnectionManager {
	private Connection connection;
	public DBConnectionManager(String dbURL, String username, String password) 
			throws ClassNotFoundException, SQLException {
		Class.forName("com.mysql.jdbc.Driver");
		this.connection = DriverManager.getConnection(dbURL, username, password);
	}
	public Connection getConnection() {
		return this.connection;
	}
}
