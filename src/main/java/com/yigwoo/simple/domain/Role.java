package com.yigwoo.simple.domain;

import java.util.ArrayList;
import java.util.List;

import javax.validation.constraints.NotNull;

public class Role {
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
	
	@NotNull
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
