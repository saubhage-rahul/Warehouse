package com.app.warehouse.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
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

	// For Status
	@Query("SELECT status FROM PurchaseOrder WHERE id=:poId")
	String getCurrentStatusOfPurchaseOrder(Integer poId);

	@Modifying
	@Query("UPDATE PurchaseOrder SET status=:newStatus WHERE id=:poId")
	void updatePurchaseOrderStatus(Integer poId, String newStatus);

	// Integration
	@Query("SELECT id,orderCode FROM PurchaseOrder WHERE status=:status")
	List<Object[]> findPurchaseOrderIdAndCodeByStatus(String status);
}
