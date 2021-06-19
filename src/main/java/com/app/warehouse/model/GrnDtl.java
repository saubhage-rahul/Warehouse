package com.app.warehouse.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table(name = "grn_tab")
public class GrnDtl {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "grn_dtl_id")
	private Integer id;

	@Column(name = "grn_dtl_code")
	private String partCode;

	@Column(name = "grn_dtl_cost")
	private Double baseCost;

	@Column(name = "grn_dtl_qty")
	private Integer qty;

	@Column(name = "grn_dtl_status")
	private String status;

}
