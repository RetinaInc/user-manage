package com.yigwoo.service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

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

import com.yigwoo.entity.Account;
import com.yigwoo.entity.Role;
import com.yigwoo.repository.AccountDao;
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
			Set<Role> roles = account.getRoles();
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

	public void deleteAccount(Long id) {
		if (id == 1) {
			throw new ServiceException("Cannot Delete A Superuser Account");
		} else {
			accountDao.delete(id);
		}
	}

	private void encryptPassword(Account account) {
		byte[] salt = Digests.generateSalt(SALT_SIZE);
		account.setSalt(Encodes.encodeHex(salt));
		byte[] hashedPassword = Digests.sha1(account.getPlainPassword()
				.getBytes(), salt, HASH_ITERATION_COUNT);
		account.setPassword(Encodes.encodeHex(hashedPassword));
	}

	public Account findAccount(Long id) {
		return accountDao.findOne(id);
	}

	public Account findAccountByEmail(String email) {
		return accountDao.findByEmail(email);
	}

	public Account findAccountByUsername(String username) {
		/* Since account.getRoles() use LAZY fetch strategy,
		 * and AccountService is @Transactional
		 * we must set the roles to the account now.
		 * Or the account's roles would be empty!
		 */
		Account account = accountDao.findByUsername(username);
		Set<Role> roles = account.getRoles();
		logger.debug("roles {}", roles.size());
		account.setRoles(roles);
		return account;
	}

	public Page<ShiroUser> findAllShiroUsers(int pageNumber, int pageSize,
			String sortColumn, String sortDirection) {
		PageRequest pageRequest = buildPageRequest(pageNumber, pageSize,
				sortColumn, sortDirection);
		Page<Account> accountPage = accountDao.findAll(pageRequest);
		Page<ShiroUser> users = buildShiroUserPage(accountPage, pageRequest);
		return users;
	}

	public void registerAccount(Account account, List<String> roleList) {
		encryptPassword(account);
		account.setRegisterDate(dateProvider.getDate());
		Set<Role> roles = new HashSet<Role>(0);
		for (String rolename : roleList) {
			Role role = roleDao.findByRolename(rolename);
			roles.add(role);
		}
		account.setRoles(roles);
		accountDao.save(account);
	}

	public void registerAdminAccount(Account account) {
		registerAccount(account, ADMIN_ROLES);
	}

	public void registerCommonUserAccount(Account account) {
		registerAccount(account, COMMON_USER_ROLES);
	}

	@Autowired
	public void setAccountDao(AccountDao accountDao) {
		this.accountDao = accountDao;
	}

	@Autowired
	public void setRoleDao(RoleDao roleDao) {
		this.roleDao = roleDao;
	}

	public void updateAccount(Account account) {
		if (StringUtils.isNotBlank(account.getPlainPassword())) {
			encryptPassword(account);
		}
		accountDao.save(account);
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
}
