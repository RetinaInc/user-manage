package com.yigwoo.simple.domain;

import java.io.Serializable;

public class AccountRole implements Serializable{

	private static final long serialVersionUID = -7434842300893474895L;
	private int accountId;
	private int roleId;
	
	public AccountRole() {}
	public AccountRole(int accountId, int roleId) {
		this.accountId = accountId;
		this.roleId = roleId;
	}
	
	public int getAccountId() {
		return accountId;
	}
	public void setAccountId(int accountId) {
		this.accountId = accountId;
	}
	public int getRoleId() {
		return roleId;
	}
	public void setRoleId(int roleId) {
		this.roleId = roleId;
	}
	

}
