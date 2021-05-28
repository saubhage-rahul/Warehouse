package com.app.warehouse.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.app.warehouse.Exception.WhUserTypeNotFoundException;
import com.app.warehouse.model.WhUserType;
import com.app.warehouse.repository.WhUserTypeRepository;
import com.app.warehouse.service.IWhUserTypeService;

@Service
public class WhUserTypeServiceImpl implements IWhUserTypeService {

	@Autowired
	private WhUserTypeRepository repository;

	// 1. Save WhUserType
	@Override
	public Integer saveWhUserType(WhUserType whUserType) {

		whUserType = repository.save(whUserType);

		return whUserType.getId();
	}

	// 2. Fetch All WhUserType
	@Override
	public List<WhUserType> getAllWhUserType() {
		List<WhUserType> list = repository.findAll();
		return list;
	}

	// 3. Delete WhUserType
	@Override
	public void deleteWhUserType(Integer id) {

		/* repository.deleteById(id); */

		repository.delete(getWhUserType(id));

	}

	// 4. Get WhUserType Based On Id
	@Override
	public WhUserType getWhUserType(Integer id) {

		return repository.findById(id)
				.orElseThrow(() -> new WhUserTypeNotFoundException("WhUserType not Exit!! :" + id));
	}

	// 5. Update WhUserType
	@Override
	public void updateWhUserType(WhUserType whUserType) {
		repository.save(whUserType);
	}
}
