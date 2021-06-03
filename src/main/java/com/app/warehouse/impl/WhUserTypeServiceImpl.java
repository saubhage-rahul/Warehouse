package com.app.warehouse.impl;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

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

		return repository.findById(id).orElseThrow(() -> new WhUserTypeNotFoundException("WhUserType Exit!! :" + id));
	}

	// 5. Update WhUserType
	@Override
	public void updateWhUserType(WhUserType whUserType) {
		repository.save(whUserType);
	}

	// 6. WhUserType Code Count
	@Override
	public boolean validateWhUserCode(String code) {
		return repository.validateWhUserCode(code) > 0;
	}

	@Override
	public boolean validateWhUserCodeAndId(String code, Integer id) {
		return repository.validateWhUserCodeAndId(code, id) > 0;
	}

	// 7. WhUserType Email Count
	@Override
	public boolean validateWhUserEmail(String email) {
		return repository.validateWhUserEmail(email) > 0;
	}

	@Override
	public boolean validateWhUserEmailAndId(String email, Integer id) {
		return repository.validateWhUserEmailAndId(email, id) > 0;
	}

	// 8. WhUserType IDNumber Count

	@Override
	public boolean validateWhUserIdNumber(String number) {
		return repository.validateWhUserIdNumber(number) > 0;
	}

	@Override
	public boolean validateWhUserIdNumberAndId(String number, Integer id) {
		return repository.validateWhUserIdNumberAndId(number, id) > 0;
	}

	// 9. For Charts Data
	@Override
	public List<Object[]> getWhUserTypUserIdTypeChart() {
		return repository.getWhUserTypUserIdTypeChart();
	}

	// 10. Integration
	@Override
	public Map<Integer, String> getWhUserIdAndCodeByType(String type) {
		List<Object[]> list = repository.getWhUserIdAndCodeByType(type);
		Map<Integer, String> map = new LinkedHashMap<>();
		for (Object[] obj : list) {
			map.put((Integer) obj[0], (String) obj[1]);

		}
		return map;
	}

}
