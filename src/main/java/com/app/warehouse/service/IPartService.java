package com.app.warehouse.service;

import java.util.List;

import com.app.warehouse.model.Part;

public interface IPartService {

	// 1. Part Service
	public Integer savePart(Part part);

	// 2. Fetch All Part
	public List<Part> getAllParts();

	// 3. Delete Part
	public void deletePart(Integer id);

	// 4. Get Part Based On Id(Edit Part)
	public Part getPart(Integer id);

	// 5. Update Part
	public void updatePart(Part part);

	// 6. Validations PartCode Count
	public boolean getPartCount(String code);

	public boolean getPartCountForEdit(String code, Integer id);

	// 7. Generate Chart for Part Base Currency
	List<Object[]> generateChartForPartBaseCurrency();

}
