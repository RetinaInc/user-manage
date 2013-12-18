package com.yigwoo.simple.service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.yigwoo.simple.domain.Account;
import com.yigwoo.simple.domain.Role;
import com.yigwoo.simple.persistence.AccountMapper;
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
	private AccountMapper accountMapper;
	private RoleMapper roleMapper;

	private PageRequest buildPageRequest(int pageNumber, int pageSize,
			String sortColumn, String sortDirection) {
		Sort sort = null;
		Direction direction = null;
		if (sortDirection.equals("ASC")) {
			direction = Direction.ASC;
		} else if (sortDirection.equals("DESC")) {
			direction = Direction.DESC;
		}
		sort = new Sort(direction, sortColumn);
		return new PageRequest(pageNumber - 1, pageSize, sort);
	}

	private Page<ShiroUser> buildShiroUserPage(Page<Account> accountPage,
			Pageable pageable) {
		List<Account> accountList = accountPage.getContent();
		List<ShiroUser> shiroUserList = new ArrayList<ShiroUser>();
		for (Account account : accountList) {
			List<Role> roles = account.getRoles();
			List<String> roleList = new ArrayList<String>();
			for (Role role : roles) {
				roleList.add(role.getRolename());
			}
			ShiroUser shiroUser = new ShiroUser(account.getId(),
					account.getUsername(), account.getEmail(), roleList,
					account.getRegisterDate());
			shiroUserList.add(shiroUser);
		}
		Page<ShiroUser> shiroUserPage = new PageImpl<ShiroUser>(shiroUserList,
				pageable, accountPage.getTotalElements());
		return shiroUserPage;
	}

	public void deleteAccount(int id) {
		if (id == 1) {
			throw new ServiceException("Cannot Delete A Superuser Account");
		} else {
			accountMapper.deleteAccountById(id);
		}
	}

	private void encryptPassword(Account account) {
		byte[] salt = Digests.generateSalt(SALT_SIZE);
		account.setSalt(Encodes.encodeHex(salt));
		byte[] hashedPassword = Digests.sha1(account.getPlainPassword()
				.getBytes(), salt, HASH_ITERATION_COUNT);
		account.setPassword(Encodes.encodeHex(hashedPassword));
	}

	public Account getAccount(int id) {
		return accountMapper.getAccountById(id);
	}

	public Account getAccountByEmail(String email) {
		return accountMapper.getAccountByEmail(email);
	}

	public Account getAccountByUsername(String username) {
		/* Since account.getRoles() use LAZY fetch strategy,
		 * and AccountService is @Transactional
		 * we must set the roles to the account now.
		 * Or the account's roles would be empty!
		 */
		Account account = accountMapper.getAccountByUsername(username);
		List<Role> roles = account.getRoles();
		logger.debug("roles {}", roles.size());
		account.setRoles(roles);
		return account;
	}

	public Page<ShiroUser> findAllShiroUsers(int pageNumber, int pageSize,
			String sortColumn, String sortDirection) {
		//PageRequest pageRequest = buildPageRequest(pageNumber, pageSize,
		//sortColumn, sortDirection);
		//Page<Account> accountPage = accountMapper.findAll(pageRequest);
		//Page<ShiroUser> users = buildShiroUserPage(accountPage, pageRequest);
		//return users;
		return null;
	}

	public void createAccount(Account account, List<String> roleList) {
		encryptPassword(account);
		account.setRegisterDate(dateProvider.getDate());
		List<Role> roles = new ArrayList<Role>();
		for (String rolename : roleList) {
			//Role role = roleMapper.findByRolename(rolename);
			//roles.add(role);
		}
		account.setRoles(roles);
		accountMapper.insertAccount(account);
	}

	public void createAdminAccount(Account account) {
		createAccount(account, ADMIN_ROLES);
	}

	public void createCommonUserAccount(Account account) {
		createAccount(account, COMMON_USER_ROLES);
	}

	@Autowired
	public void setAccountMapper(AccountMapper accountMapper) {
		this.accountMapper = accountMapper;
	}

	@Autowired
	public void setRoleMapper(RoleMapper roleMapper) {
		this.roleMapper = roleMapper;
	}

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
		if (StringUtils.isNotBlank(account.getPlainPassword())) {
			encryptPassword(account);
		}
		accountMapper.updateAccount(account);
		return account;
	}
}
