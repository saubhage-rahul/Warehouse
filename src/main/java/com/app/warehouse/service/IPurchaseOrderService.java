package com.app.warehouse.service;

import java.util.List;

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

}
