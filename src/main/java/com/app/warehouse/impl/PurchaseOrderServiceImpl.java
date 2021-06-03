package com.app.warehouse.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.app.warehouse.Exception.PurchaseOrderException;
import com.app.warehouse.model.PurchaseOrder;
import com.app.warehouse.repository.PurchaseOrderRepository;
import com.app.warehouse.service.IPurchaseOrderService;

@Service
public class PurchaseOrderServiceImpl implements IPurchaseOrderService {

	@Autowired
	private PurchaseOrderRepository repository;

	// 1. Save Purchase Order
	@Override
	public Integer savePurchaseOrder(PurchaseOrder purchaseOrder) {
		return repository.save(purchaseOrder).getId();
	}

	// 2. Fetch All Purchase Order
	@Override
	public List<PurchaseOrder> getAllPurchaseOrders() {
		return repository.findAll();
	}

	// 3. Get Purchase Order Based on Id
	@Override
	public PurchaseOrder getOnePurchaseOrder(Integer id) {

		return repository.findById(id).orElseThrow(() -> new PurchaseOrderException("Purchase Order Not Found"));
	}

	// 4. AJEX Validations for Purchase OrderCode
	@Override
	public boolean validateGetOrderCode(String code) {
		return repository.validateGetOrderCode(code) > 0;
	}

	@Override
	public boolean validateGetOrderCodeAndId(String code, Integer id) {
		return repository.validateGetOrderCodeAndId(code, id) > 0;
	}

}
