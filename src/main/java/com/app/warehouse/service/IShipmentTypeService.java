package com.app.warehouse.service;

import java.util.List;

import com.app.warehouse.model.ShipmentType;

public interface IShipmentTypeService {

	// Save ShipmentType
	public Integer saveShipmentType(ShipmentType shipmentType);

	// Fetch All ShipmentTypes
	public List<ShipmentType> fetchAllShipmentType();

	// Delete ShipmentType
	public void deleteShipmentType(Integer id);

	// Get ShipmentType based on Id
	public ShipmentType getShipmentType(Integer id);

	// Update ShipmentType
	public void updateShipmentType(ShipmentType shipmentType);

	public boolean isShipmentTypecodeExist(String code);

	public boolean isShipmentTypeCodeExistForEdit(String code, Integer id);

	// Free Chart
	List<Object[]> getShipmentTypeModeAndCount();

}
