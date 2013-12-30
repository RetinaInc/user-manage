package com.yigwoo.simple.persistence;


import com.yigwoo.simple.domain.AccountRole;

/**
 * This interface user AccountRoleMapper.xml
 * to manage ACCOUNT_ROLE table
 * @author YigWoo
 *
 */
public interface AccountRoleMapper {
	public void insertAccountRole(AccountRole accountRole);
	public void deleteAccountRoleByAccountId(int accountId);
}
