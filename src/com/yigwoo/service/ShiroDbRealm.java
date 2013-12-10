package com.yigwoo.service;

import java.io.Serializable;
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
import org.springframework.beans.factory.annotation.Autowired;

import com.yigwoo.entity.User;
import com.yigwoo.util.Encodes;

public class ShiroDbRealm extends AuthorizingRealm {
	

	protected AccountService accountService;
	
	@Autowired
	public void setAccountService(AccountService accountService) {
		this.accountService = accountService;
	}

	/**
	 * Do authorization bussiness
	 */
	@Override
	protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
		ShiroUser shiroUser = (ShiroUser) principals.getPrimaryPrincipal();
		User user = accountService.findUserByUsername(shiroUser.getUsername());
		SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
		info.addRoles(user.getRolesList());
		return info;
	}

	/**
	 * Authentication bussiness method
	 */
	@Override
	protected AuthenticationInfo doGetAuthenticationInfo(
			AuthenticationToken authToken) throws AuthenticationException {
		UsernamePasswordToken token = (UsernamePasswordToken) authToken;
		User user = accountService.findUserByUsername(token.getUsername());
		if (user != null) {
			byte[] salt = Encodes.decodeHex(user.getSalt());
			return new SimpleAuthenticationInfo(new ShiroUser(user.getId(),
					user.getUsername(), user.getEmail(), user.getRoles()), user.getPassword(),
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
		public String roles;

		public ShiroUser(Long id, String username, String email, String roles) {
			this.id = id;
			this.username = username;
			this.email = email;
			this.roles = roles;
		}

		public String getUsername() {
			return username;
		}
		
		public String getEmail() {
			return email;
		}
		
		public String getRoles() {
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
