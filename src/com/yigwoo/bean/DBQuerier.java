package com.yigwoo.bean;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class DBQuerier {
	private static final String INSERT_USER_QUERY = 
			"insert into Users(username,email, password) values (?,?,?)";
	private final String FIND_USER_QUERY = "select username, password, email "
			+ "from users where username=? and password=?";
	private Connection connection;
	private PreparedStatement prepStmt;

	public DBQuerier(Connection connection) {
		this.connection = connection;
	}

	public User FindUserRecord(String username, String password) {
		User user = null;
		ResultSet resultSet = null;
		try {
			PreparedStatement prepStmt = null;
			prepStmt = connection.prepareStatement(FIND_USER_QUERY);
			prepStmt.setString(1, username);
			prepStmt.setString(2, password);
			resultSet = prepStmt.executeQuery();
			if (resultSet != null && resultSet.next()) {
				user = new User(resultSet.getString("username"), 
						resultSet.getString("email"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				if (resultSet != null)
					resultSet.close();
				if (prepStmt != null)
					prepStmt.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return user;
	}
	
	public boolean InsertUserRecord(String username, String password, String email) {
		boolean res = false;
		try {
			prepStmt = connection.prepareStatement(INSERT_USER_QUERY);
			prepStmt.setString(1, username);
			prepStmt.setString(2, email);
			prepStmt.setString(3, password);
			res = prepStmt.execute();
		} catch (SQLException e){
			e.printStackTrace();
		} finally {
			try {
				prepStmt.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return res;
	}
}
