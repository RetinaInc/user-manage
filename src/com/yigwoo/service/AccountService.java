package com.yigwoo.service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.yigwoo.entity.Account;
import com.yigwoo.entity.AccountToRole;
import com.yigwoo.entity.Role;
import com.yigwoo.repository.AccountDao;
import com.yigwoo.repository.AccountToRoleDao;
import com.yigwoo.repository.RoleDao;
import com.yigwoo.service.ShiroDbRealm.ShiroUser;
import com.yigwoo.util.DateProvider;
import com.yigwoo.util.Digests;
import com.yigwoo.util.Encodes;

/**
 * This a class which utilize UserDao to provide services that the logging and
 * registering procedures needs
 * 
 * @author YigWoo
 * 
 */

@Component
@Transactional
public class AccountService {
	public static final int HASH_ITERATION_COUNT = 2324;
	public static final String HASH_ALGORITHM = "SHA-1";
	public static final String ROLENAME_COMMON_USER = "common user";
	public static final String ROLENAME_ADMIN = "admin";
	public static final String ROLENAME_SUPER_ADMIN = "super admin";
	public static final List<String> COMMON_USER_ROLES = Arrays
			.asList(ROLENAME_COMMON_USER);
	public static final List<String> ADMIN_ROLES = Arrays.asList(
			ROLENAME_ADMIN, ROLENAME_COMMON_USER);
	public static final List<String> SUPER_ADMIN_ROLES = Arrays.asList(
			ROLENAME_ADMIN, ROLENAME_COMMON_USER, ROLENAME_SUPER_ADMIN);
	private static final int SALT_SIZE = 8;
	private static Logger logger = LoggerFactory
			.getLogger(AccountService.class);
	private DateProvider dateProvider = DateProvider.DATE_PROVIDER;
	private AccountDao accountDao;
	private RoleDao roleDao;
	private AccountToRoleDao accountToRoleDao;

	@Autowired
	public void setAccountDao(AccountDao accountDao) {
		this.accountDao = accountDao;
	}

	@Autowired
	public void setRoleDao(RoleDao roleDao) {
		this.roleDao = roleDao;
	}

	@Autowired
	public void setAccountToRoleDao(AccountToRoleDao accountToRoleDao) {
		this.accountToRoleDao = accountToRoleDao;
	}

	public List<String> findAccountRoles(Account account) {
		List<String> roles = new ArrayList<String>();
		List<AccountToRole> accountToRoles = accountToRoleDao
				.findByAccountId(account.getId());
		for (AccountToRole accountToRole : accountToRoles) {
			roles.add(accountToRole.getRole().getRolename());
		}
		return roles;
	}

	public Account findAccount(Long id) {
		return accountDao.findOne(id);
	}

	public Account findAccountByUsername(String username) {
		return accountDao.findByUsername(username);
	}

	public Account findAccountByEmail(String email) {
		return accountDao.findByEmail(email);
	}

	public List<Account> findAllAccounts() {
		return accountDao.findAll();
	}

	public void updateAccount(Account account) {
		if (StringUtils.isNotBlank(account.getPlainPassword())) {
			encryptPassword(account);
		}
		accountDao.save(account);
	}

	public boolean deleteAccount(Long id) {
		/**
		 * The order of deleting the `account` and the `account_to_role` is
		 * important. Since if you delete `account` first, in account_to_role
		 * table, the entry couldn't find the corresponding reference to the
		 * `account`. So, before you delete the `account`, delete the
		 * corresponding `account_to_role`s first.
		 */
		if (id == 1) {
			return false;
		} else {
			logger.debug("{}", id);
			List<AccountToRole> accountToRoles = accountToRoleDao
					.findByAccountId(id);
			logger.debug("{}", accountToRoles.size());
			for (AccountToRole accountToRole : accountToRoles) {
				logger.debug("{}", accountToRole.getId());
				accountToRoleDao.delete(accountToRole);
			}
			accountDao.delete(id);
			return true;
		}
	}

	public void registerAccount(Account account, List<String> roles) {
		encryptPassword(account);
		account.setRegisterDate(dateProvider.getDate());
		accountDao.save(account);
		for (String rolename : roles) {
			Role role = roleDao.findByRolename(rolename);
			AccountToRole accountToRole = new AccountToRole();
			accountToRole.setAccount(account);
			accountToRole.setRole(role);
			accountToRoleDao.save(accountToRole);
		}
	}

	public Account updateAccountProfile(Account accountModel) {
		/*
		 * Since the the form in "editProfile" will generate a new Account
		 * object, and the form just contains partial information of the old
		 * Account object, So we want to keep the username infomation in the new
		 * Account object in editProfile.jsp then in this function, retrieve the
		 * old Account object, combine it with the new Account object, to form
		 * the Account object we want to save!
		 */
		Account account = findAccountByUsername(accountModel.getUsername());
		account.setEmail(accountModel.getEmail());
		account.setPlainPassword(accountModel.getPlainPassword());
		updateAccount(account);
		return account;
	}

	private void encryptPassword(Account account) {
		byte[] salt = Digests.generateSalt(SALT_SIZE);
		account.setSalt(Encodes.encodeHex(salt));
		byte[] hashedPassword = Digests.sha1(account.getPlainPassword()
				.getBytes(), salt, HASH_ITERATION_COUNT);
		account.setPassword(Encodes.encodeHex(hashedPassword));
	}

	public void registerCommonUserAccount(Account account) {
		registerAccount(account, COMMON_USER_ROLES);
	}

	public void registerAdminAccount(Account account) {
		registerAccount(account, ADMIN_ROLES);
	}

	public List<ShiroUser> findShiroUsersByRole(String rolename) {
		Long id = roleDao.findByRolename(rolename).getId();
		logger.debug("find shiro users by role id {}", id);
		List<AccountToRole> accountToRoles = accountToRoleDao.findByRoleId(id);
		List<ShiroUser> users = new ArrayList<ShiroUser>();
		for (AccountToRole accountToRole : accountToRoles) {
			Account account = accountToRole.getAccount();
			ShiroUser shiroUser = new ShiroUser(account.getId(),
					account.getUsername(), account.getEmail(),
					findAccountRoles(account), account.getRegisterDate());
			users.add(shiroUser);
		}
		return users;
	}
}
