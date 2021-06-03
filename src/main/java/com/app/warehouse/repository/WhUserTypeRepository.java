package com.app.warehouse.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.app.warehouse.model.WhUserType;

@Repository
public interface WhUserTypeRepository extends JpaRepository<WhUserType, Integer> {

	// 1. WhUserType Code Count
	@Query("SELECT count(userCode) from WhUserType where userCode=:code")
	Integer validateWhUserCode(String code);

	@Query("SELECT count(userCode) from WhUserType where userCode=:code and id!=:id")
	Integer validateWhUserCodeAndId(String code, Integer id);

	// 2. WhUserType Email Count
	@Query("select count(userEmail) from WhUserType where userEmail=:email")
	Integer validateWhUserEmail(String email);

	@Query("select count(userEmail) from WhUserType where userEmail=:email and id!=:id")
	Integer validateWhUserEmailAndId(String email, Integer id);

	// 3. WhUserType IDNumber Count
	@Query("select count(userIdNum) from WhUserType where userIdNum=:number")
	Integer validateWhUserIdNumber(String number);

	@Query("select count(userIdNum) from WhUserType where userIdNum=:number and id!=:id")
	Integer validateWhUserIdNumberAndId(String number, Integer id);

	// 4. Generate Charts Data
	@Query("SELECT userIdType, count(userIdType) FROM WhUserType GROUP BY userIdType")
	List<Object[]> getWhUserTypUserIdTypeChart();
	
	// 5. Integration
	@Query("select id, userCode FROM WhUserType WHERE userType=:type")
	List<Object[]> getWhUserIdAndCodeByType(String type);


}
