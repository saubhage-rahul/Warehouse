package com.app.warehouse.runner;

import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import com.app.warehouse.constant.UserMode;
import com.app.warehouse.model.UserInfo;
import com.app.warehouse.repository.RoleRepository;
import com.app.warehouse.repository.UserInfoRepository;
import com.app.warehouse.service.IUserInfoService;

@Component
@Order(20)
public class UserMasterDataRunner implements CommandLineRunner {

	@Autowired
	private IUserInfoService service;

	@Autowired
	private UserInfoRepository repository;

	@Autowired
	private RoleRepository repo;

	@Override
	public void run(String... args) throws Exception {

		if (!repository.existsByEmail("nit@gmail.com")) {

			UserInfo user = new UserInfo();

			user.setName("NIT-MASTER");
			user.setEmail("nit@gmail.com");

			user.setPassword("nit");
			user.setMode(UserMode.ENABLED);

			user.setRoles(repo.findAll().stream().collect(Collectors.toSet()));

			service.saveUserInfo(user);
		}
	}
}
