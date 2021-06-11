package com.app.warehouse.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.app.warehouse.Exception.PurchaseOrderException;
import com.app.warehouse.model.PurchaseDetails;
import com.app.warehouse.model.PurchaseOrder;
import com.app.warehouse.repository.PurchaseDetailsRepository;
import com.app.warehouse.repository.PurchaseOrderRepository;
import com.app.warehouse.service.IPurchaseOrderService;

@Service
public class PurchaseOrderServiceImpl implements IPurchaseOrderService {

	@Autowired
	private PurchaseOrderRepository repository;

	@Autowired
	private PurchaseDetailsRepository purchaseDtlRepo;

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

		return repository.findById(id).orElseThrow(() -> new PurchaseOrderException("PurchaseOrder Exit:"));
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

	// 5. ------------------for screen#2-------------------------------

	// 6. Save Purchase Details
	@Override
	public Integer savePurchaseDetails(PurchaseDetails purchaseDetails) {
		return purchaseDtlRepo.save(purchaseDetails).getId();
	}

	@Override
	public List<PurchaseDetails> getPurchaseDtlsByPurchaseOrderId(Integer id) {
		return purchaseDtlRepo.getPurchaseDtlsByPurchaseOrderId(id);
	}

	// 8. Remove
	@Override
	public void deletePurchaseDetails(Integer detailId) {

		if (purchaseDtlRepo.existsById(detailId)) {
			purchaseDtlRepo.deleteById(detailId);
		}
	}

	// 9. For Status
	@Override
	public String getCurrentStatusOfPurchaseOrder(Integer poId) {
		return repository.getCurrentStatusOfPurchaseOrder(poId);
	}

	@Override
	@Transactional
	public void updatePurchaseOrderStatus(Integer poId, String newStatus) {
		repository.updatePurchaseOrderStatus(poId, newStatus);
	}

	@Override
	public Integer getPurchaseDetailsCountByPurchaseOrderId(Integer poId) {
		return purchaseDtlRepo.getPurchaseDetailsCountByPurchaseOrderId(poId);
	}

	

}
