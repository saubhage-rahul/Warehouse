package com.app.warehouse.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import lombok.Data;

@Data
@Entity
@Table(name = "shipment_type")
public class ShipmentType {

	@Id
	@GeneratedValue(generator = "gen_shipmentType")
	@SequenceGenerator(name = "gen_shipmentType", sequenceName = "seq_shipmentType")
	@Column(name = "ship_id")
	private Integer shipId;

	@Column(name = "ship_mode", nullable = false, length = 10)
	private String shipMode;

	@Column(name = "ship_code", nullable = false, length = 10, unique = true)
	private String shipCode;

	@Column(name = "enable_ship", nullable = false, length = 5)
	private String enbleShip;

	@Column(name = "ship_grade", nullable = false, length = 3)
	private String shipGrade;

	@Column(name = "ship_desc", nullable = false, length = 100, unique = false)
	private String shipDesc;

}
