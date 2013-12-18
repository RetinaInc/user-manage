package com.yigwoo.simple.domain;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotBlank;

/**
 * Persistence entity, corresponds to 'account'
 * 
 * @author YigWoo
 * 
 */
public class Account {

	private int id;
	private String username;
	private String email;
	private String password;
	private String salt;
	private Date registerDate;
	private String plainPassword;
	private List<Role> roles = new ArrayList<Role>();

	public Account() {
	}

	@NotBlank
	@Email
	public String getEmail() {
		return email;
	}

	public int getId() {
		return id;
	}

	public String getPassword() {
		return password;
	}

	public String getPlainPassword() {
		return plainPassword;
	}

	public Date getRegisterDate() {
		return registerDate;
	}

	public String getSalt() {
		return salt;
	}

	@NotBlank
	public String getUsername() {
		return username;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public void setId(int id) {
		this.id = id;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public void setPlainPassword(String plainPassword) {
		this.plainPassword = plainPassword;
	}

	public void setRegisterDate(Date registerDate) {
		this.registerDate = registerDate;
	}

	public void setSalt(String salt) {
		this.salt = salt;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public List<Role> getRoles() {
		return roles;
	}

	public void setRoles(List<Role> roles) {
		this.roles = roles;
	}
}
