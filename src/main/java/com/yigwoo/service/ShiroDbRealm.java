package com.yigwoo.service;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import java.util.Objects;

import javax.annotation.PostConstruct;

import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authc.credential.HashedCredentialsMatcher;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.util.ByteSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.yigwoo.entity.Account;
import com.yigwoo.util.Encodes;

public class ShiroDbRealm extends AuthorizingRealm {
	private static Logger logger = LoggerFactory.getLogger(ShiroDbRealm.class);
	protected AccountService accountService;

	@Autowired
	public void setAccountService(AccountService accountService) {
		this.accountService = accountService;
	}

	/**
	 * Do authorization bussiness
	 */
	@Override
	protected AuthorizationInfo doGetAuthorizationInfo(
			PrincipalCollection principals) {
		ShiroUser shiroUser = (ShiroUser) principals.getPrimaryPrincipal();
		Account account = accountService.findAccountByUsername(shiroUser
				.getUsername());
		SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
		List<String> roles = accountService.findAccountRoles(account);
		logger.debug("Current User roles {}", roles.toString());
		info.addRoles(roles);
		return info;
	}

	/**
	 * Authentication bussiness method
	 */
	@Override
	protected AuthenticationInfo doGetAuthenticationInfo(
			AuthenticationToken authToken) throws AuthenticationException {
		UsernamePasswordToken token = (UsernamePasswordToken) authToken;
		Account account = accountService.findAccountByUsername(token
				.getUsername());
		if (account != null) {
			List<String> roles = accountService.findAccountRoles(account);
			byte[] salt = Encodes.decodeHex(account.getSalt());
			return new SimpleAuthenticationInfo(new ShiroUser(account.getId(),
					account.getUsername(), account.getEmail(), roles,
					account.getRegisterDate()), account.getPassword(),
					ByteSource.Util.bytes(salt), getName());
		} else {
			return null;
		}
	}

	@PostConstruct
	public void initCredentialsMatcher() {
		HashedCredentialsMatcher matcher = new HashedCredentialsMatcher(
				AccountService.HASH_ALGORITHM);
		matcher.setHashIterations(AccountService.HASH_ITERATION_COUNT);
		setCredentialsMatcher(matcher);
	}

	public static class ShiroUser implements Serializable {
		private static final long serialVersionUID = -7499871275405557055L;
		public Long id;
		public String username;
		public String email;
		public List<String> roles;
		public Date registerDate;

		public ShiroUser(Long id, String username, String email,
				List<String> roles, Date registerDate) {
			this.id = id;
			this.username = username;
			this.email = email;
			this.roles = roles;
			this.registerDate = registerDate;
		}

		public Date getRegisterDate() {
			return registerDate;
		}

		public Long getId() {
			return id;
		}

		public String getUsername() {
			return username;
		}

		public String getEmail() {
			return email;
		}

		public List<String> getRoles() {
			return roles;
		}

		@Override
		public String toString() {
			return username;
		}

		@Override
		public int hashCode() {
			return Objects.hashCode(username);
		}

		@Override
		public boolean equals(Object obj) {
			if (this == obj) {
				return true;
			}
			if (obj == null) {
				return false;
			}
			if (getClass() != obj.getClass()) {
				return false;
			}
			ShiroUser other = (ShiroUser) obj;
			if (username == null) {
				if (other.username != null) {
					return false;
				}
			} else if (!username.equals(other.username)) {
				return false;
			}
			return true;
		}
	}
}
