package com.app.warehouse.service;

import java.util.List;

import com.app.warehouse.model.OrderMethod;

public interface IOrderMethodService {

	// 1.Save Order Method
	public Integer saveOrderMethod(OrderMethod orderMethod);

	// 2.Fetch All Order Method
	public List<OrderMethod> getAllOrderMethods();

	// 3.Delete Order Method
	public void deleteOrderMethod(Integer id);

	// 4.Get Order Method based on id
	public OrderMethod getorderMethod(Integer id);

	// 5.Update Order Method
	public void updateOrderMethod(OrderMethod orderMethod);

	public boolean isOrderCountExit(String code);

	public boolean isOrderCountExitForEdit(String code, Integer id);

	// Custom Query for Generate Free Chart
	List<Object[]> generatechartForOrderMethodMode();

}
