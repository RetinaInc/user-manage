package com.yigwoo.simple.persistence;

import com.yigwoo.simple.domain.Role;

public interface RoleMapper{
	public Role getRoleByRolename(String rolename);
}
