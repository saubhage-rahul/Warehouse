package com.app.warehouse.service;

import java.util.List;

import com.app.warehouse.model.Grn;

public interface IGrnService {

	public Integer saveGrn(Grn grn);

	public List<Grn> getAllGrns();

	public Grn getOneGrn(Integer id);

}
