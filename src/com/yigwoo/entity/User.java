package com.yigwoo.entity;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotBlank;


@Entity
@Table(name="users_table")
public class User {
	
	private Long id;
	private String username;
	private String email;
	private String password;
	private String salt;
	private Date registerDate;
	private String plainPassword;
	
	public User() {}
	
	public User(Long id) {
		this.id = id;
	}
	
	@NotBlank
	@Email
	public String getEmail() {
		return email;
	}

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	public Long getId() {
		return id;
	}

	public String getPassword() {
		return password;
	}
	
	@Transient
	public String getPlainPassword() {
		return plainPassword;
	}

	public Date getRegisterDate() {
		return registerDate;
	}

	public String getSalt() {
		return salt;
	}

	public String getUsername() {
		return username;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public void setId(Long id) {
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
}
