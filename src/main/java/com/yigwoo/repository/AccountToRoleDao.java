package com.yigwoo.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.Repository;

import com.yigwoo.entity.Account;
import com.yigwoo.entity.AccountToRole;

public interface AccountToRoleDao extends Repository<AccountToRole, Long>{

	public AccountToRole save(AccountToRole accountToRole);
	public void delete(AccountToRole accountToRole);
	public void delete(Long id);
	public List<AccountToRole> findAll();
	public List<AccountToRole> findByAccountId(Long id);
	public List<AccountToRole> findByRoleId(Long id);

}
