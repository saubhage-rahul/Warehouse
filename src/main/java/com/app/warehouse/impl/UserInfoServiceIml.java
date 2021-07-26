package com.app.warehouse.impl;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.app.warehouse.constant.UserMode;
import com.app.warehouse.model.UserInfo;
import com.app.warehouse.repository.UserInfoRepository;
import com.app.warehouse.service.IUserInfoService;

@Service
public class UserInfoServiceIml implements IUserInfoService, UserDetailsService {

	@Autowired
	private UserInfoRepository repository;

	@Autowired
	private BCryptPasswordEncoder encoder;

	@Override
	public Integer saveUserInfo(UserInfo userInfo) {

		String pwd = userInfo.getPassword();
		String encodePwd = encoder.encode(pwd);
		userInfo.setPassword(encodePwd);
		return repository.save(userInfo).getId();
	}

	@Override
	public List<UserInfo> getAllUserInfo() {
		return repository.findAll();
	}

	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		Optional<UserInfo> opt = repository.findByEmail(username);
		if (!opt.isPresent() || opt.get().getMode().equals(UserMode.DISABLED)) {
			throw new UsernameNotFoundException("User Not Exit!");
		}
		UserInfo info = opt.get();
		return new User(info.getEmail(), info.getPassword(), info.getRoles().stream()
				.map(r -> new SimpleGrantedAuthority(r.getRole().name())).collect(Collectors.toSet()));
	}

	@Override
	public Optional<UserInfo> getOneUserInfoByEmail(String email) {

		return repository.findByEmail(email);
	}

	@Override
	@Transactional
	public void updateUserStatus(Integer id, UserMode mode) {
		repository.updateUserStatus(id, mode);
	}

	@Override
	@Transactional
	public void updateUserPassword(String email, String password) {

		repository.updateUserPassword(email, password);
	}
}
