package com.yigwoo.simple.model;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.Validator;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;

import com.yigwoo.simple.domain.Account;

public class ValidatorTests {
	private Validator validator;
	private DateFormat df;
	
	@Before
	public void beforeTests() {
		LocalValidatorFactoryBean localValidatorFactoryBean = new LocalValidatorFactoryBean();
		localValidatorFactoryBean.afterPropertiesSet();
		validator = localValidatorFactoryBean;
		
		df = new SimpleDateFormat("yyyy-MM-dd");
	}

	@Test
	public void validAccountTest() throws ParseException {
		Account account = new Account();
		Date mockBirthday = df.parse("1990-01-01");
		account.setBirthday(mockBirthday);
		account.setEmail("test@gmail.com");
		account.setPlainPassword("123456");
		account.setUsername("test1");
		
		Set<ConstraintViolation<Account>> constraintViolations = validator.validate(account);
		
		Assert.assertEquals(0, constraintViolations.size());
	}
	
	@Test
	public void invalidAccountTest() throws ParseException {
		Account account = new Account();
		Date mockBirthday = df.parse("2014-01-05");
		account.setBirthday(mockBirthday);
		account.setEmail("test.com");
		account.setPlainPassword("123");
		account.setUsername("test");
		Set<ConstraintViolation<Account>> constraintViolations = validator.validate(account);
		
		Assert.assertEquals(4, constraintViolations.size());
	}
}
