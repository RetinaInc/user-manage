package com.yigwoo.simple.service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.yigwoo.simple.domain.Account;
import com.yigwoo.simple.domain.AccountRole;
import com.yigwoo.simple.domain.Role;
import com.yigwoo.simple.persistence.AccountMapper;
import com.yigwoo.simple.persistence.AccountRoleMapper;
import com.yigwoo.simple.persistence.RoleMapper;
import com.yigwoo.simple.service.ShiroDbRealm.ShiroUser;
import com.yigwoo.simple.util.DateProvider;
import com.yigwoo.simple.util.Digests;
import com.yigwoo.simple.util.Encodes;

/**
 * This a class which utilize UserDao to provide services that the logging and
 * registering procedures needs
 * 
 * @author YigWoo
 * 
 */

@Component
public class AccountService {
	public static final int HASH_ITERATION_COUNT = 2324;
	public static final String HASH_ALGORITHM = "SHA-1";
	private static final String ROLENAME_COMMON_USER = "common user";
	private static final String ROLENAME_ADMIN = "admin";
	private static final List<String> COMMON_USER_ROLES = Arrays
			.asList(ROLENAME_COMMON_USER);
	private static final List<String> ADMIN_ROLES = Arrays.asList(
			ROLENAME_ADMIN, ROLENAME_COMMON_USER);
	private static final int SALT_SIZE = 8;
	private static Logger logger = LoggerFactory
			.getLogger(AccountService.class);
	private DateProvider dateProvider = DateProvider.DATE_PROVIDER;
	private AccountMapper accountMapper;
	private AccountRoleMapper accountRoleMapper;
	private RoleMapper roleMapper;
	
	@Autowired
	public void setAccountMapper(AccountMapper accountMapper) {
		this.accountMapper = accountMapper;
	}

	@Autowired
	public void setAccountRoleMapper(AccountRoleMapper accountRoleMapper) {
		this.accountRoleMapper = accountRoleMapper;
	}
	
	@Autowired
	public void setRoleMapper(RoleMapper roleMapper) {
		this.roleMapper = roleMapper;
	}
	
	@Transactional
	public void deleteAccount(int id) {
		if (id == 1) {
			throw new ServiceException("Cannot Delete A Superuser Account");
		} else {
			accountMapper.deleteAccountById(id);
			accountRoleMapper.deleteAccountRoleByAccountId(id);
		}
	}

	private void encryptPassword(Account account) {
		byte[] salt = Digests.generateSalt(SALT_SIZE);
		account.setSalt(Encodes.encodeHex(salt));
		byte[] hashedPassword = Digests.sha1(account.getPlainPassword()
				.getBytes(), salt, HASH_ITERATION_COUNT);
		account.setPassword(Encodes.encodeHex(hashedPassword));
	}

	public Account getAccountById(int id) {
		return accountMapper.getAccountById(id);
	}

	public Account getAccountByEmail(String email) {
		return accountMapper.getAccountByEmail(email);
	}

	public Account getAccountByUsername(String username) {
		Account account = accountMapper.getAccountByUsername(username);
		logger.debug("owner's name {}, owner's age {}", account.getUsername(), account.getAge());
		return account;
	}
	
	public List<ShiroUser> getAllShiroUsers(int pageNumber, int pageSize,
			String sortColumn, String sortDirection) {
		List<Account> accounts = accountMapper.getAllAccounts();
		List<ShiroUser> shiroUsers = buildShiroUsersFromAccounts(accounts);
		return shiroUsers;
	}
	
	private List<ShiroUser> buildShiroUsersFromAccounts(List<Account> accounts) {
		List<ShiroUser> shiroUsers = new ArrayList<ShiroUser>();
		for (Account account : accounts) {
			List<String> roles =   extractStringRoleList(account.getRoles());
			ShiroUser user = new ShiroUser(account.getId(), account.getUsername(), account.getEmail(), roles, account.getRegisterDate());
			shiroUsers.add(user);
		}
		return shiroUsers;
	}

	@Transactional
	public void createAccount(Account account, List<String> roleList) {
		/* First we want to encrypt the plain password with
		 * the predefined one-way algorithm; then set the register date
		 * of the account;
		 * Next, for each role in `roleList`, we add an entry to ACCOUNT_ROLE
		 * table, with the `ROLE_ID` found in `ROLE` table and the account.id;
		 * Finally, we insert the account information into `ACCOUNT` table.
		 */
		encryptPassword(account);
		account.setRegisterDate(dateProvider.getDate());
		accountMapper.insertAccount(account);
		for (String rolename : roleList) {
			Role role = roleMapper.getRoleByRolename(rolename);
			AccountRole accountRole = new AccountRole(account.getId(), role.getId());
			logger.debug("create account accountId:{}, roleId:{}", account.getId(), role.getId());
			accountRoleMapper.insertAccountRole(accountRole);
		}
	}

	public void createAdminAccount(Account account) {
		createAccount(account, ADMIN_ROLES);
	}

	public void createCommonUserAccount(Account account) {
		createAccount(account, COMMON_USER_ROLES);
	}

	@Transactional
	public Account updateAccount(Account accountModel) {
		/*
		 * Since the the form in "editProfile" will generate a new Account
		 * object, and the form just contains partial information of the old
		 * Account object, So we want to keep the username infomation in the new
		 * Account object in editProfile.jsp then in this function, retrieve the
		 * old Account object, combine it with the new Account object, to form
		 * the Account object we want to save!
		 */
		Account account = getAccountByUsername(accountModel.getUsername());
		account.setEmail(accountModel.getEmail());
		account.setPlainPassword(accountModel.getPlainPassword());
		account.setBirthday(accountModel.getBirthday());
		if (StringUtils.isNotBlank(account.getPlainPassword())) {
			encryptPassword(account);
		}
		logger.debug("birthday {}", account.getBirthday());
		accountMapper.updateAccount(account);
		return account;
	}

	public List<String> extractStringRoleList(List<Role> roles) {
		ArrayList<String> roleList = new ArrayList<String>();
		for (Role role : roles) {
			ShiroDbRealm.logger.debug("{}", role.getRolename());
			roleList.add(role.getRolename());
		}
		return roleList;
	}
}
