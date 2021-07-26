package com.app.warehouse.impl;

import java.util.List;
import java.util.Map;

import org.apache.commons.collections4.map.HashedMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.app.warehouse.model.Role;
import com.app.warehouse.repository.RoleRepository;
import com.app.warehouse.service.IRoleService;

@Service
public class RoleServiceImpl implements IRoleService {

	@Autowired
	private RoleRepository repository;

	@Override
	public Map<Integer, String> getRolesMap() {
		List<Role> list = repository.findAll();
		Map<Integer, String> map = new HashedMap<>();
		for (Role role : list) {
			map.put(role.getId(), role.getRole().name());
		}
		return map;
	}
}
