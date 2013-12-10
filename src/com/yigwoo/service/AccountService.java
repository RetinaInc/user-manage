package com.yigwoo.service;

import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.yigwoo.entity.User;
import com.yigwoo.repository.UserDao;
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
	private static final int SALT_SIZE = 8;
	private static Logger logger = LoggerFactory.getLogger(AccountService.class);
	private UserDao userDao;
	private DateProvider dateProvider = DateProvider.DATE_PROVIDER;

	@Autowired
	public void setUserDao(UserDao userDao) {
		this.userDao = userDao;
	}

	public User getUser(Long id) {
		return userDao.findOne(id);
	}

	public User findUserByUsername(String username) {
		return userDao.findByUsername(username);
	}

	public User findUserByEmail(String email) {
		return userDao.findByEmail(email);
	}
	
	public List<User> findUserByRoles(String roles) {
		return userDao.findByRoles(roles);
	}
	
	public List<User> findAllUsers() {
		return userDao.findAll();
	}
	
	public void updateUser(User user) {
		if (StringUtils.isNotBlank(user.getPlainPassword())) {
			encryptPassword(user);
		}
		userDao.save(user);
	}

	public void deleteUser(Long id) {
		userDao.delete(id);
	}

	public void registerUser(User user, String roles) {
		encryptPassword(user);
		user.setRoles(roles);
		user.setRegisterDate(dateProvider.getDate());
		userDao.save(user);
	}

	private void encryptPassword(User user) {
		byte[] salt = Digests.generateSalt(SALT_SIZE);
		user.setSalt(Encodes.encodeHex(salt));
		byte[] hashedPassword = Digests.sha1(
				user.getPlainPassword().getBytes(), salt, HASH_ITERATION_COUNT);
		user.setPassword(Encodes.encodeHex(hashedPassword));
	}

}
