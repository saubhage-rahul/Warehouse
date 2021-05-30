package com.app.warehouse.service;

import java.util.List;

import com.app.warehouse.model.Document;

public interface IDocumentService {

	// 1. Upload Document
	public void saveDocument(Document document);

	// 2. Show All Documents
	public List<Object[]> getDocumentIDAndName();

	// 3. Delete Document
	void deleteDocumentById(Integer id);

	// 4 Get Document Based on Id
	Document getDocumentById(Integer id);
}
