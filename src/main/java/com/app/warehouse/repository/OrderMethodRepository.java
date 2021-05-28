package com.app.warehouse.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.app.warehouse.model.OrderMethod;

@Repository
public interface OrderMethodRepository extends JpaRepository<OrderMethod, Integer> {

	@Query("select count(orderCode) from OrderMethod where orderCode=:code")
	Integer getOrderMethodcount(String code);

	@Query("select count(orderCode) from OrderMethod where orderCode=:code and id!=:id")
	Integer getOrderMethodcountForEdit(String code, Integer id);

	// Custom Query for Generate Free Chart
	@Query("select orderMode,count(orderMode) from OrderMethod Group By orderMode")
	List<Object[]> generatechartForOrderMethodMode();

}
