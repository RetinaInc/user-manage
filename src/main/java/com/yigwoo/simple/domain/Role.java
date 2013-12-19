package com.yigwoo.simple.domain;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.hibernate.validator.constraints.NotBlank;

public class Role implements Serializable{
	private static final long serialVersionUID = 3177289562555454870L;
	
	private int id;
	private String rolename;
	private List<Account> accounts = new ArrayList<Account>();
	
	public Role() {}
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	
	@NotBlank
	public String getRolename() {
		return rolename;
	}
	
	public void setRolename(String rolename) {
		this.rolename = rolename;
	}

	public List<Account> getAccounts() {
		return accounts;
	}

	public void setAccounts(List<Account> accounts) {
		this.accounts = accounts;
	}
}
