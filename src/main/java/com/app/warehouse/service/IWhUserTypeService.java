package com.app.warehouse.service;

import java.util.List;

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

}
