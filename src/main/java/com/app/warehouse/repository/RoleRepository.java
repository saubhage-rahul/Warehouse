package com.app.warehouse.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.app.warehouse.constant.RoleType;
import com.app.warehouse.model.Role;

@Repository
public interface RoleRepository extends JpaRepository<Role, Integer> {

	// SQL: (select count(role) from role tab where role=?)>0?true:false
	Boolean existsByRole(RoleType role);
}
