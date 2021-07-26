package com.app.warehouse.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.app.warehouse.Exception.DataNotFoundException;
import com.app.warehouse.model.Grn;
import com.app.warehouse.repository.GrnDetailRepository;
import com.app.warehouse.repository.GrnRepository;
import com.app.warehouse.service.IGrnService;

@Service
public class GrnServiceImpl implements IGrnService {

	@Autowired
	private GrnRepository repository;

	@Autowired
	private GrnDetailRepository dtlRepository;

	@Override
	public Integer saveGrn(Grn grn) {
		return repository.save(grn).getId();

	}

	@Override
	public List<Grn> getAllGrns() {
		return repository.findAll();
	}

	@Override
	public Grn getOneGrn(Integer id) {
		return repository.findById(id).orElseThrow(() -> new DataNotFoundException("GRN Not Exit:" + id));
	}

	@Override
	@Transactional
	public void updateGrnDtlStatus(Integer id, String status) {
		dtlRepository.updateGrnDtlStatus(id, status);
	}
}
