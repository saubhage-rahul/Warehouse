package com.app.warehouse.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.app.warehouse.model.ShipmentType;

@Repository
public interface ShipmentTypeRepository extends JpaRepository<ShipmentType, Integer> {

	@Query("SELECT count(shipCode) from ShipmentType where shipCode=:code")
	Integer getShipmentTypeCodeCount(String code);

	@Query("SELECT count(shipCode) from ShipmentType where shipCode=:code and id!=:id")
	Integer getShipmentTypeCodeCountForEdit(String code, Integer id);

	// Custom Query For Free Chart
	@Query("SELECT shipMode, count(shipMode) from ShipmentType GROUP BY shipMode")
	List<Object[]> getShipmentTypeModeAndCount();

	// Integration Dynamic DropDown
	@Query("SELECT shipId, shipCode FROM ShipmentType where enbleShip=:enable")
	List<Object[]> getShipIdAndShipCodeByEnable(String enable);
	
	
	
	
	

}
