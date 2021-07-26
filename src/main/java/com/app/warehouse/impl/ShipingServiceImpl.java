package com.app.warehouse.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.app.warehouse.Exception.ShipingNotFoundException;
import com.app.warehouse.model.Shiping;
import com.app.warehouse.repository.ShipingDetailRepository;
import com.app.warehouse.repository.ShipingRepository;
import com.app.warehouse.service.IShipingService;

@Service
public class ShipingServiceImpl implements IShipingService {

	@Autowired
	private ShipingRepository repository;

	@Autowired
	private ShipingDetailRepository dtlRepository;

	@Override
	public Integer saveShiping(Shiping shiping) {
		return repository.save(shiping).getId();
	}

	@Override
	public List<Shiping> getAllShiping() {
		return repository.findAll();
	}

	@Override
	public Shiping getOneShiping(Integer id) {
		return repository.findById(id).orElseThrow(() -> new ShipingNotFoundException("Shiping Not Exit:" + id));
	}

	@Override
	@Transactional
	public void updateShipingDtlStatus(Integer id, String status) {
		dtlRepository.updateShipingDtlStatus(id, status);
	}

}
