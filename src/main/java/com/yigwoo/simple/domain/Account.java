package com.yigwoo.simple.domain;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.validation.constraints.Past;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotBlank;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.format.annotation.DateTimeFormat.ISO;

/**
 * Persistence entity, corresponds to 'account'
 * 
 * @author YigWoo
 * 
 */
public class Account implements Serializable{

	private static final long serialVersionUID = 6835146172519330564L;
	
	private int id;
	
	@Size(min=5, max=64)
	@NotBlank
	private String username;
	
	@NotBlank
	@Email
	private String email;
	
	@DateTimeFormat(iso=ISO.DATE)
	@Past
	private Date birthday;
	private int age;
	private String password;
	private String salt;
	private Date registerDate;
	
	@Size(min=6, max=64)
	private String plainPassword;
	private List<Role> roles = new ArrayList<Role>();

	public Account() {
	}

	public int getAge() {
		return age;
	}

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

	public List<Role> getRoles() {
		return roles;
	}

	public String getSalt() {
		return salt;
	}

	public String getUsername() {
		return username;
	}

	public void setAge(int age) {
		this.age = age;
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

	public void setRoles(List<Role> roles) {
		this.roles = roles;
	}

	public void setSalt(String salt) {
		this.salt = salt;
	}

	public void setUsername(String username) {
		this.username = username;
	}


	public Date getBirthday() {
		return birthday;
	}

	public void setBirthday(Date birthday) {
		this.birthday = birthday;
	}
}
