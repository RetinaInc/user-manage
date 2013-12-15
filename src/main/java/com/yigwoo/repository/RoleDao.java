package com.yigwoo.repository;

import java.util.List;

import org.springframework.data.repository.Repository;
import com.yigwoo.entity.Role;

public interface RoleDao extends Repository<Role, Long>{
	public Role save(Role role);
	public void delete(Role role);
	public void delete(Long id);
	public Role findOne(Long id);
	public Role findByRolename(String rolename);
	public List<Role> findAll();
}
