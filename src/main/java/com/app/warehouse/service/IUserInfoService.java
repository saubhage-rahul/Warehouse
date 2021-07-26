package com.app.warehouse.service;

import java.util.List;
import java.util.Optional;

import com.app.warehouse.constant.UserMode;
import com.app.warehouse.model.UserInfo;

public interface IUserInfoService {

	public Integer saveUserInfo(UserInfo userInfo);

	public List<UserInfo> getAllUserInfo();

	Optional<UserInfo> getOneUserInfoByEmail(String email);

	void updateUserStatus(Integer id, UserMode mode);
	
	void updateUserPassword(String email, String password);
}
