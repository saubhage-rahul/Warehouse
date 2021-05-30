package com.app.warehouse.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.app.warehouse.model.Document;

@Repository
public interface DocumentRepository extends JpaRepository<Document, Integer> {

	// 1. Show All Documents
	@Query("select docId,docName from Document")
	List<Object[]> getDocumentIDAndName();

}
