package com.app.warehouse.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.app.warehouse.model.PurchaseOrder;

@Repository
public interface PurchaseOrderRepository extends JpaRepository<PurchaseOrder, Integer> {

	// 1. AJEX Validations for Purchase OrderCode
	@Query("SELECT count(orderCode) FROM PurchaseOrder WHERE orderCode=:code")
	Integer validateGetOrderCode(String code);

	@Query("SELECT count(orderCode) FROM PurchaseOrder WHERE orderCode=:code AND id!=:id")
	Integer validateGetOrderCodeAndId(String code, Integer id);
}
