package com.app.warehouse.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.app.warehouse.model.GrnDtl;

@Repository
public interface GrnDetailRepository extends JpaRepository<GrnDtl, Integer> {

	@Modifying
	@Query("UPDATE GrnDtl SET status=:status WHERE id=:id")
	void updateGrnDtlStatus(Integer id, String status);

}
