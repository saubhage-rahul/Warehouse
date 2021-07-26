package com.app.warehouse.impl;

import java.util.List;
import java.util.Map;

import org.apache.commons.collections4.map.HashedMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.app.warehouse.Exception.UomNotFoundException;
import com.app.warehouse.model.Uom;
import com.app.warehouse.repository.UomRepository;
import com.app.warehouse.service.IUomService;

@Service
public class UomServiceImpl implements IUomService {

	@Autowired
	private UomRepository repository;

	// Save UOM
	@Override
	public Integer saveUom(Uom uom) {
		uom = repository.save(uom);
		return uom.getUid();
	}

	// Fetch All UOM'S
	@Override
	public List<Uom> fetchAllUom() {
		List<Uom> list = repository.findAll();
		return list;
	}

	// Delete UOM
	@Override
	public void deleteUom(Integer id) {
		repository.delete(getUom(id));
	}

	// Get UOM
	@Override
	public Uom getUom(Integer id) {

		return repository.findById(id).orElseThrow(() -> new UomNotFoundException("Uom Not Exit!! : + id"));
	}

	// Update UOM
	@Override
	public void updateUom(Uom uom) {
		if (uom.getUid() == null || !repository.existsById(uom.getUid())) {
			throw new UomNotFoundException("Uom Not Exit:" + uom.getUid());
		} else {
			repository.save(uom);
		}
	}

	@Override
	public boolean isUomModelExit(String model) {

		return repository.getUomCount(model) > 0;
	}

	@Override
	public boolean isUomModelExitForEdit(String model, Integer id) {
		return repository.getUomCountForEdit(model, id) > 0;
	}

	// Generate Free Chart
	@Override
	public List<Object[]> getUomTypeChart() {
		return repository.getUomTypeChart();
	}

	// For Integration
	@Override
	public Map<Integer, String> getUomIdAndModel() {
		List<Object[]> list = repository.getUomIdAndModel();
		Map<Integer, String> map = new HashedMap<>();
		for (Object[] obj : list) {
			map.put((Integer) obj[0], (String) obj[1]);

		}
		return map;
	}
}
