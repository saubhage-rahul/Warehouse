package com.app.warehouse.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.app.warehouse.model.SaleOrderDetails;

@Repository
public interface SaleOrderDetailsRepository extends JpaRepository<SaleOrderDetails, Integer> {

	@Query("select saleOrderDetails from SaleOrderDetails saleOrderDetails JOIN saleOrderDetails.saleOrder as saleOrder where saleOrder.id=:poId")
	public List<SaleOrderDetails> getSaleDtlsBySaleOrderId(Integer poId);

	// For Status
	@Query("SELECT count(saleOrderDetails) FROM SaleOrderDetails saleOrderDetails JOIN saleOrderDetails.saleOrder as saleOrder WHERE saleOrder.id=:soId")
	public Integer getSaleDtlsCountBySaleOrderId(Integer soId);

}
