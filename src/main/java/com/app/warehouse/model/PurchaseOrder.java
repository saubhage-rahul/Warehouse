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
@Table(name = "purchase_order_tab")
public class PurchaseOrder {

	@Id
	@GeneratedValue(generator = "purchase_order_gen")
	@SequenceGenerator(name = "purchase_order_gen", sequenceName = "purchase_order_seq")
	@Column(name = "purchase_order_id")
	private Integer id;

	@Column(name = "purchase_order_code")
	private String orderCode;

	@Column(name = "purchase_order_refno")
	private String refNum;

	@Column(name = "purchase_order_qltycheck")
	private String qltyChck;

	@Column(name = "purchase_order_status")
	private String status;

	@Column(name = "purchase_order_desc")
	private String description;

	// Integration
	@ManyToOne
	@JoinColumn(name = "ship_id_fk")
	private ShipmentType st;

	// Integration
	@ManyToOne
	@JoinColumn(name = "wh_usr_id_fk")
	private WhUserType vendor;

}
