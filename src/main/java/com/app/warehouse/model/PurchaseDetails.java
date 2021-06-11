package com.app.warehouse.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import lombok.Data;

@Data
@Entity
@Table(name = "purchase_details")
public class PurchaseDetails {

	@Id
	@GeneratedValue(generator = "purchase_dtl_gen")
	@SequenceGenerator(name = "purchase_dtl_gen", sequenceName = "purchase_dtl_seq")
	@Column(name = "purchase_dtl_id")
	private Integer id;

	@Column(name = "purchase_dtl_qty")
	private Integer qty;

	@ManyToOne
	@JoinColumn(name = "part_id_fk")
	private Part part;

	@ManyToOne
	@JoinColumn(name = "purchase_order_id_fk")
	private PurchaseOrder purchaseOrder;

}
