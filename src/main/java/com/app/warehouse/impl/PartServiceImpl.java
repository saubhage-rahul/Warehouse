package com.app.warehouse.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.app.warehouse.Exception.PartNotFoundException;
import com.app.warehouse.model.Part;
import com.app.warehouse.repository.PartRepository;
import com.app.warehouse.service.IPartService;

@Service
public class PartServiceImpl implements IPartService {

	@Autowired
	private PartRepository repository;

	// 1. Part Service
	@Override
	public Integer savePart(Part part) {
		return repository.save(part).getId();
	}

	// 2. Fetch All Part
	@Override
	public List<Part> getAllParts() {
		return repository.findAll();
	}

	@Override
	public void deletePart(Integer id) {

		repository.delete(getPart(id));
	}

	@Override
	public Part getPart(Integer id) {
		return repository.findById(id).orElseThrow(() -> new PartNotFoundException("Part Exit :" + id));
	}

	@Override
	public void updatePart(Part part) {
		repository.save(part);
	}

	@Override
	public boolean getPartCount(String code) {
		return repository.getPartCount(code) > 0;
	}

	@Override
	public boolean getPartCountForEdit(String code, Integer id) {
		return repository.getPartCountForEdit(code, id) > 0;
	}
}