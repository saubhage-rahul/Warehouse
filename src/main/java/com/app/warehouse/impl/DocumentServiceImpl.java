package com.app.warehouse.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.app.warehouse.Exception.DocumentNotFoundException;
import com.app.warehouse.model.Document;
import com.app.warehouse.repository.DocumentRepository;
import com.app.warehouse.service.IDocumentService;

@Service
public class DocumentServiceImpl implements IDocumentService {

	@Autowired
	private DocumentRepository repository;

	// 1. Upload Document
	@Override
	public void saveDocument(Document document) {
		repository.save(document);

	}

	// 2. Show All Documents
	@Override
	public List<Object[]> getDocumentIDAndName() {
		List<Object[]> list = repository.getDocumentIDAndName();
		return list;
	}

	// 3. Delete Document
	@Override
	public void deleteDocumentById(Integer id) {
		/*
		 * if (repository.existsById(id)) repository.deleteById(id); else throw new
		 * RuntimeException("Document Not exist:");
		 */

		repository.delete(getDocumentById(id));
	}

	// 4 Get Document Based on Id
	@Override
	public Document getDocumentById(Integer id) {

		return repository.findById(id).orElseThrow(() -> new DocumentNotFoundException("Document Exist:"));
	}
}
