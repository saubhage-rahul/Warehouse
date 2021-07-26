package com.app.warehouse.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.app.warehouse.constant.UserMode;
import com.app.warehouse.model.UserInfo;

@Repository
public interface UserInfoRepository extends JpaRepository<UserInfo, Integer> {
	Boolean existsByEmail(String email);

	Optional<UserInfo> findByEmail(String email);

	@Modifying
	@Query("UPDATE UserInfo SET mode=:mode WHERE id=:id")
	void updateUserStatus(Integer id, UserMode mode);

	@Modifying
	@Query("UPDATE UserInfo SET password=:password WHERE email=:email")
	void updateUserPassword(String email, String password);
}
