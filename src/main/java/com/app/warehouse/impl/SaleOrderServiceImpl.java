package com.app.warehouse.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.app.warehouse.Exception.SaleOrderNotFoundExceptio;
import com.app.warehouse.model.SaleOrder;
import com.app.warehouse.model.SaleOrderDetails;
import com.app.warehouse.repository.SaleOrderDetailsRepository;
import com.app.warehouse.repository.SaleOrderRepository;
import com.app.warehouse.service.ISaleOrderService;

@Service
public class SaleOrderServiceImpl implements ISaleOrderService {

	@Autowired
	private SaleOrderRepository repository;

	@Autowired
	private SaleOrderDetailsRepository saleRepository;

	// 1. Save SaleOrder
	@Override
	public Integer saveSaleOrder(SaleOrder saleOrder) {
		return repository.save(saleOrder).getId();
	}

	// 2. Fetch All SaleOrder
	@Override
	public List<SaleOrder> getAllSaleOrder() {
		return repository.findAll();
	}

	// 3. Get SaleOrder based on Id
	@Override
	public SaleOrder getOneSaleOrder(Integer id) {

		return repository.findById(id).orElseThrow(() -> new SaleOrderNotFoundExceptio("Sale Order Exit"));
	}

	// 4. Validate OrderCode(AJEX Validation)
	@Override
	public boolean validateOrderCode(String code) {
		return repository.validateOrderCode(code) > 0;
	}

	@Override
	public boolean validateOrderCodeAndId(String code, Integer id) {
		return repository.validateOrderCodeAndId(code, id) > 0;
	}

	// ------------------- Screen#2---------------------

	@Override
	public Integer savePurchaseDetails(SaleOrderDetails saleOrderDetails) {
		return saleRepository.save(saleOrderDetails).getId();
	}

	@Override
	public List<SaleOrderDetails> getSaleDtlsBySaleOrderId(Integer id) {
		return saleRepository.getSaleDtlsBySaleOrderId(id);
	}

	// 7. Remove Part
	@Override
	public void deleteSaleDetails(Integer detailId) {
		if (saleRepository.existsById(detailId)) {
			saleRepository.deleteById(detailId);
		}
	}

	// 8. For Status
	@Override
	public String getCurrentStatusOfSaleOrder(Integer soId) {
		return repository.getCurrentStatusOfSaleOrder(soId);
	}

	@Override
	@Transactional
	public void updateSaleOrderStatus(Integer soId, String newStatus) {
		repository.updateSaleOrderStatus(soId, newStatus);
	}

	@Override
	public Integer getSaleDtlsCountBySaleOrderId(Integer soId) {
		return saleRepository.getSaleDtlsCountBySaleOrderId(soId);
	}

	// Increase Part Quantity
	@Override
	public Optional<SaleOrderDetails> getSaleDetailByPartIdAndSaleOrderId(Integer partId, Integer soId) {
		return saleRepository.getSaleDetailByPartIdAndSaleOrderId(partId, soId);
	}

	@Transactional
	@Override
	public Integer updateSaleOrderDetailQtyByDetailId(Integer newQty, Integer dtlId) {
		return saleRepository.updateSaleOrderDetailQtyByDetailId(newQty, dtlId);
	}

}
