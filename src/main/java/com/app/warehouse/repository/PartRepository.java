package com.app.warehouse.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.app.warehouse.model.Part;

@Repository
public interface PartRepository extends JpaRepository<Part, Integer> {

	// 1. Validations PartCode Count
	@Query("select count(partCode) from Part where partCode=:code")
	Integer getPartCount(String code);

	@Query("select count(partCode) from Part where partcode=:code and id!=:id")
	Integer getPartCountForEdit(String code, Integer id);

	// 2. Generate Chart for Part Base Currency
	@Query("select partCurrency,count(partCurrency) from Part GROUP BY partCurrency")
	List<Object[]> generateChartForPartBaseCurrency();

}
