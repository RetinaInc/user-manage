package com.yigwoo.entity;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotBlank;

/**
 * Persistence entity, corresponds to 'account'
 * 
 * @author YigWoo
 * 
 */
@Entity
@Table(name = "account")
public class Account {

	private Long id;
	private String username;
	private String email;
	private String password;
	private String salt;
	private Date registerDate;
	private String plainPassword;
	private Set<Role> roles = new HashSet<Role>(0);

	public Account() {
	}

	public Account(Long id) {
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

	@NotBlank
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

	@ManyToMany(fetch = FetchType.LAZY)
	@JoinTable(
			name = "ACCOUNT_ROLE",
			joinColumns = {@JoinColumn(name = "account_id", referencedColumnName="id")},
			inverseJoinColumns = {@JoinColumn(name = "role_id", referencedColumnName="id")}
			)
	public Set<Role> getRoles() {
		return roles;
	}

	public void setRoles(Set<Role> roles) {
		this.roles = roles;
	}
}
