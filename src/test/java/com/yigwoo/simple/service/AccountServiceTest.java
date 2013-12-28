package com.yigwoo.simple.service;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import org.hamcrest.CoreMatchers;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.Assert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import com.yigwoo.simple.domain.Account;
import com.yigwoo.simple.domain.Role;
import com.yigwoo.simple.service.AccountService;
import com.yigwoo.simple.service.ServiceException;

@ContextConfiguration(locations={"classpath:applicationContext.xml"})
@RunWith(SpringJUnit4ClassRunner.class)
public class AccountServiceTest {
	private static DateFormat dateFormat;
	
	@Autowired
	private AccountService accountService;
	
	@BeforeClass
	public static void setUp() {
		dateFormat = new SimpleDateFormat("yyyy-MM-dd");
	}
	
	@Test
	public void testFindSingleAccount() {
		Account account = accountService.getAccountById(1);
		Assert.assertEquals(account.getUsername(), "superuser");
		Assert.assertEquals(3, account.getRoles().size());
		
		account = accountService.getAccountByEmail("2");
		Assert.assertNull(account);
		
		account = accountService.getAccountByUsername("superuser");
		Assert.assertEquals(1, account.getId());
	}
	
	@Test
	@Transactional
	public void testFindAllAccounts() {
		List<Account> accounts = accountService.getAllAccounts();
		Assert.assertEquals(4, accounts.size());
		Assert.assertEquals("superuser", accounts.get(0).getUsername());
	}
	
	@Test
	@Transactional
	public void testCreateCommonUser() throws ParseException {
		Account account = buildAccount();
		accountService.createCommonUserAccount(account);
		
		Account accountFromDb = accountService.getAccountByUsername(account.getUsername());
		createAccountAssertions(account, accountFromDb, Arrays.asList("common user"));
	}
	
	@Test
	@Transactional
	public void testCreateAdmin() throws ParseException {
		Account account = buildAccount();
		accountService.createAdminAccount(account);
		
		Account accountFromDb = accountService.getAccountByUsername(account.getUsername());
		createAccountAssertions(account, accountFromDb, Arrays.asList("admin", "common user"));
	}
	
	@Test(expected=ServiceException.class)
	@Transactional
	public void testDeleteSuperuser() {
		accountService.deleteAccount(1);
	}
	
	@Test
	@Transactional
	public void testDeleteAccount() {
		accountService.deleteAccount(43);
		Account account = accountService.getAccountById(43);
		Assert.assertNull(account);
	}
	
	@Test
	@Transactional
	public void testUpdateAccount() throws ParseException {
		String mockEmail = "test@test.com";
		Date mockBirthday = dateFormat.parse("2010-02-01");
		Account account = accountService.getAccountByUsername("yigwoo");
		String oldPassword = account.getPassword();
		String oldSalt = account.getSalt();
		
		account.setEmail(mockEmail);
		account.setBirthday(mockBirthday);
		account.setPlainPassword("asdfghjkl");
		accountService.updateAccount(account);
		
		Account accountFromDb = accountService.getAccountByUsername("yigwoo");
		Assert.assertEquals(mockEmail, accountFromDb.getEmail());
		Assert.assertEquals(mockBirthday, accountFromDb.getBirthday());
		Assert.assertNotEquals(oldPassword, accountFromDb.getPassword());
		Assert.assertNotEquals(oldSalt, accountFromDb.getSalt());
	}

	private void createAccountAssertions(Account account, Account accountFromDb, List<String> roleListExpected)
			throws ParseException {
		Assert.assertNotNull(accountFromDb);
		Assert.assertEquals("test@test.com", accountFromDb.getEmail());
		Assert.assertEquals(dateFormat.parse("1980-02-03"), account.getBirthday());
		Assert.assertNotNull(accountFromDb.getPassword());
		Assert.assertNotNull(accountFromDb.getSalt());
		Assert.assertNotNull(accountFromDb.getRegisterDate());
		List<String> roleList = new ArrayList<String>();
		for (Role role : accountFromDb.getRoles()) {
			roleList.add(role.getRolename());
		}
		Assert.assertThat(roleList, CoreMatchers.is(roleListExpected));
	}

	private Account buildAccount() {
		Account account = new Account();
		try {
			account.setBirthday(dateFormat.parse("1980-02-03"));
			account.setEmail("test@test.com");
			account.setPlainPassword("testPassword");
			account.setUsername("testUsername");

		} catch (ParseException e) {
			e.printStackTrace();
		} 
		return account;
	}
}
