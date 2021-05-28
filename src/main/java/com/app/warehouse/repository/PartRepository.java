package com.app.warehouse.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.app.warehouse.model.Part;

@Repository
public interface PartRepository extends JpaRepository<Part, Integer> {
	
	@Query("select count(partCode) from Part where partCode=:code")
	Integer getPartCount(String code );
	
	@Query("select count(partCode) from Part where partcode=:code and id!=:id")
	Integer getPartCountForEdit(String code,Integer id);

}
