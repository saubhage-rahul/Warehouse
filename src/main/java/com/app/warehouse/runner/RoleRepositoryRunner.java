package com.app.warehouse.runner;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import com.app.warehouse.constant.RoleType;
import com.app.warehouse.model.Role;
import com.app.warehouse.repository.RoleRepository;

@Component
@Order(10)
public class RoleRepositoryRunner implements CommandLineRunner {

	private static final Logger log = LoggerFactory.getLogger(RoleRepositoryRunner.class);

	@Autowired
	private RoleRepository repository;

	@Override
	public void run(String... args) throws Exception {

		RoleType[] roleType = RoleType.values();
		for (RoleType rt : roleType) {
			if (!repository.existsByRole(rt)) {
				Role role = new Role();
				role.setRole(rt);
				log.info("Role Data is Inserted into Role Table :" + rt.name());
				repository.save(role);
			}
		}
	}
}
