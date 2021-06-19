package com.app.warehouse.service;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import com.app.warehouse.model.PurchaseDetails;
import com.app.warehouse.model.PurchaseOrder;

public interface IPurchaseOrderService {

	// 1. Save Purchase Order
	public Integer savePurchaseOrder(PurchaseOrder purchaseOrder);

	// 2. Fetch All Purchase Order
	public List<PurchaseOrder> getAllPurchaseOrders();

	// 3. Get Purchase Order Based on Id
	public PurchaseOrder getOnePurchaseOrder(Integer id);

	// 4. AJEX Validations For Purchase OrderCode
	boolean validateGetOrderCode(String code);

	boolean validateGetOrderCodeAndId(String code, Integer id);

	// 5. ------------------for screen#2-------------------------------

	// 6. Save Purchase Details
	public Integer savePurchaseDetails(PurchaseDetails purchaseDetails);

	// 7.
	public List<PurchaseDetails> getPurchaseDtlsByPurchaseOrderId(Integer id);

	// 8. Remove
	public void deletePurchaseDetails(Integer detailId);

	// 9. For Status
	String getCurrentStatusOfPurchaseOrder(Integer poId);
	
	public void updatePurchaseOrderStatus(Integer poId, String newStatus);
	
	public Integer getPurchaseDetailsCountByPurchaseOrderId(Integer poId);
	
	// Increase Part Quantity
	public Optional<PurchaseDetails> getPurchaseDetailsByPartIdAndPurchaseOrderId(Integer partId, Integer poId);
	
	public Integer updatePurchaseDetailsQtyByDetailId(Integer newQty, Integer dtlId);
	
	// Integration
	Map<Integer,String>findPurchaseOrderIdAndCodeByStatus(String status);

}
