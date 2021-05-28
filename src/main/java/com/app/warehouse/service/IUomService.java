package com.app.warehouse.service;

import java.util.List;
import java.util.Map;

import com.app.warehouse.model.Uom;

public interface IUomService {

	// Save UOM
	public Integer saveUom(Uom uom);

	// Fetch All UOM'S
	public List<Uom> fetchAllUom();

	// Delete UOM
	public void deleteUom(Integer id);

	// Get UOM
	public Uom getUom(Integer id);

	// Update UOM
	public void updateUom(Uom uom);

	public boolean isUomModelExit(String model);

	public boolean isUomModelExitForEdit(String model, Integer id);

	// Generate Free Chart
	List<Object[]> getUomTypeChart();

	// For Integration
	Map<Integer, String> getUomIdAndModel();

}
