package com.yigwoo.simple.persistence;

import java.util.List;

import com.yigwoo.simple.domain.Account;

/**
 * This interface use the mapped SQL queries in AccountMapper.xml
 * to manage ACCOUNT table
 * @author YigWoo
 */

public interface AccountMapper {
	public void insertAccount(Account account);

	public void updateAccount(Account account);

	public void deleteAccountById(int id);
	
	public List<Account> getAllAccounts();

	public Account getAccountById(int id);

	public Account getAccountByUsername(String username);

	public Account getAccountByEmail(String email);

}
