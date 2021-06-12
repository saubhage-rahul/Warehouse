package com.app.warehouse.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.app.warehouse.model.PurchaseDetails;

@Repository
public interface PurchaseDetailsRepository extends JpaRepository<PurchaseDetails, Integer> {

	@Query("SELECT purchaseDetails FROM PurchaseDetails purchaseDetails JOIN purchaseDetails.purchaseOrder as purchaseOrder WHERE purchaseOrder.id=:poId")
	public List<PurchaseDetails> getPurchaseDtlsByPurchaseOrderId(Integer poId);

	// For Status
	@Query("SELECT count(purchaseDetails) FROM PurchaseDetails purchaseDetails JOIN purchaseDetails.purchaseOrder as purchaseOrder WHERE purchaseOrder.id=:poId")
	public Integer getPurchaseDetailsCountByPurchaseOrderId(Integer poId);

	// Increase Part Quantity
	@Query("SELECT purchaseDetails FROM PurchaseDetails  purchaseDetails JOIN purchaseDetails.part as part JOIN purchaseDetails.purchaseOrder  as purchaseOrder WHERE part.id=:partId and purchaseOrder.id=:poId")
	public Optional<PurchaseDetails> getPurchaseDetailsByPartIdAndPurchaseOrderId(Integer partId, Integer poId);

	@Modifying
	@Query("UPDATE PurchaseDetails SET qty = qty + :newQty WHERE id=:dtlId")
	public Integer updatePurchaseDetailsQtyByDetailId(Integer newQty, Integer dtlId);
}
