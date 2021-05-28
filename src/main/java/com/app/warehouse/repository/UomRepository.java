package com.app.warehouse.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.app.warehouse.model.Uom;

@Repository
public interface UomRepository extends JpaRepository<Uom, Integer> {

	@Query("SELECT count(uomModel) from Uom where uomModel=:model")
	Integer getUomCount(String model);

	@Query("SELECT count(uomModel) from Uom where uomModel=:model and id!=:id")
	Integer getUomCountForEdit(String model, Integer id);

	// Custom Query For Free Chat
	@Query("select uomType, count(uomType) from Uom Group By uomType")
	List<Object[]> getUomTypeChart();

	// For Integration
	@Query("select uid,uomModel from Uom")
	List<Object[]> getUomIdAndModel();

}
