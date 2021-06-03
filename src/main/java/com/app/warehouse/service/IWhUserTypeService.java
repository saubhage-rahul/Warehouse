package com.app.warehouse.service;

import java.util.List;
import java.util.Map;

import com.app.warehouse.model.WhUserType;

public interface IWhUserTypeService {

	// 1. Save WhUserType
	public Integer saveWhUserType(WhUserType whUserType);

	// 2. Fetch All WhUserType
	public List<WhUserType> getAllWhUserType();

	// 3. Delete WhUserType
	public void deleteWhUserType(Integer id);

	// 4. Get WhUserType Based On Id
	public WhUserType getWhUserType(Integer id);

	// 5. Update WhUserType
	public void updateWhUserType(WhUserType whUserType);

	// 6. WhUserType Code Count
	public boolean validateWhUserCode(String code);

	public boolean validateWhUserCodeAndId(String code, Integer id);

	// 7. WhUserType Email Count
	public boolean validateWhUserEmail(String email);

	public boolean validateWhUserEmailAndId(String email, Integer id);

	// 8. WhUserType IDNumber Count
	public boolean validateWhUserIdNumber(String number);

	public boolean validateWhUserIdNumberAndId(String number, Integer id);

	// 9. For Charts Data
	List<Object[]> getWhUserTypUserIdTypeChart();

	// 10. Integration
	public Map<Integer, String> getWhUserIdAndCodeByType(String type);

}
