package com.yigwoo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.yigwoo.entity.User;
import com.yigwoo.repository.UserDao;
import com.yigwoo.utils.DateProvider;

/**
 * This a class which utilize UserDao to provide
 * services that the logging and registering procedures needs
 * @author YigWoo
 *
 */

@Component
@Transactional
public class AccountService {
	public static final int HASH_ITERATION_COUNT = 2324;
	public static final String HASH_ALGORITHM = "SHA-1";
	private static final int SALT_SIZE = 8;
	
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
	
	public void registerUser(User user) {
		encryptPassword(user);
		user.setRegisterDate(dateProvider.getDate());
		userDao.save(user);
	}
	
	private void encryptPassword(User user) {
		
	}
}
