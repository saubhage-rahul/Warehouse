package com.app.warehouse.impl;

import java.util.List;
import java.util.Map;

import org.apache.commons.collections4.map.HashedMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.app.warehouse.Exception.OrderMethodNotFoundException;
import com.app.warehouse.model.OrderMethod;
import com.app.warehouse.repository.OrderMethodRepository;
import com.app.warehouse.service.IOrderMethodService;

@Service
public class OrderMethodServiceImpl implements IOrderMethodService {

	@Autowired
	private OrderMethodRepository repository;

	// 1.Save Order Method
	@Override
	public Integer saveOrderMethod(OrderMethod orderMethod) {

		orderMethod = repository.save(orderMethod);

		return orderMethod.getId();
	}

	// 2.Fetch All Order Method
	@Override
	public List<OrderMethod> getAllOrderMethods() {
		List<OrderMethod> list = repository.findAll();
		return list;
	}

	// 3.Delete Order Method
	@Override
	public void deleteOrderMethod(Integer id) {
		repository.delete(getorderMethod(id));

	}

	// 4.Get Order Method based on id
	@Override
	public OrderMethod getorderMethod(Integer id) {

		return repository.findById(id)
				.orElseThrow(() -> new OrderMethodNotFoundException("OrderMethod Not Exit!! : " + id));
	}

	// 5.Update Order Method
	@Override
	public void updateOrderMethod(OrderMethod orderMethod) {
		if (orderMethod.getId() == null || !repository.existsById(orderMethod.getId())) {
			throw new OrderMethodNotFoundException("Order Method not Exist: " + orderMethod.getId());
		} else {
			repository.save(orderMethod);
		}
	}

	@Override
	public boolean isOrderCountExit(String code) {
		return repository.getOrderMethodcount(code) > 0;
	}

	@Override
	public boolean isOrderCountExitForEdit(String code, Integer id) {
		return repository.getOrderMethodcountForEdit(code, id) > 0;
	}

	// Custom Query for Generate Free Chart
	@Override
	public List<Object[]> generatechartForOrderMethodMode() {
		return repository.generatechartForOrderMethodMode();
	}

	// Integration
	@Override
	public Map<Integer, String> getOrderMethodIdAndMode() {
		List<Object[]> list = repository.getOrderMethodIdAndMode();
		Map<Integer, String> map = new HashedMap<>();
		for (Object[] obj : list) {

			map.put((Integer) obj[0], (String) obj[1]);
		}

		return map;
	}
}
