package com.app.warehouse.impl;

import java.util.List;
import java.util.Map;

import org.apache.commons.collections4.map.HashedMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.app.warehouse.Exception.ShipmentTypeNotFoundException;
import com.app.warehouse.model.ShipmentType;
import com.app.warehouse.repository.ShipmentTypeRepository;
import com.app.warehouse.service.IShipmentTypeService;

@Service
public class ShipmentTypeServiceImpl implements IShipmentTypeService {

	@Autowired
	private ShipmentTypeRepository repository;

	// Save ShipmentType
	@Override
	public Integer saveShipmentType(ShipmentType shipmentType) {
		shipmentType = repository.save(shipmentType);
		return shipmentType.getShipId();
	}

	// Fetch All Shipment Type
	@Override
	public List<ShipmentType> fetchAllShipmentType() {
		List<ShipmentType> list = repository.findAll();
		return list;
	}

	// Delete ShipmentType
	@Override
	public void deleteShipmentType(Integer id) {
		repository.delete(getShipmentType(id));
	}

	// Get ShipmentType based on Id
	@Override
	public ShipmentType getShipmentType(Integer id) {
		return repository.findById(id)
				.orElseThrow(() -> new ShipmentTypeNotFoundException("ShipmentType Not Exit!! :" + id));
	}

	// Update ShipmentType
	@Override
	public void updateShipmentType(ShipmentType shipmentType) {
		repository.save(shipmentType);

	}

	@Override
	public boolean isShipmentTypecodeExist(String code) {
		return repository.getShipmentTypeCodeCount(code) > 0;
	}

	public boolean isShipmentTypeCodeExistForEdit(String code, Integer id) {
		return repository.getShipmentTypeCodeCountForEdit(code, id) > 0;
	}

	// Free Chart
	@Override
	public List<Object[]> getShipmentTypeModeAndCount() {
		return repository.getShipmentTypeModeAndCount();
	}

	// Integration
	@Override
	public Map<Integer, String> getShipIdAndShipCodeByEnable(String enable) {
		List<Object[]> list = repository.getShipIdAndShipCodeByEnable(enable);
		Map<Integer, String> map = new HashedMap<>();
		for (Object[] obj : list) {
			map.put((Integer) obj[0], (String) obj[1]);

		}
		return map;
	}
}
