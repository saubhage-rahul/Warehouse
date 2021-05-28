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
	@SequenceGenerator(name = "gen_shipmentType",sequenceName = "seq_shipmentType")
	@Column(name = "ship_id")
	private Integer shipId;

	@Column(name = "ship_mode")
	private String shipMode;

	@Column(name = "ship_code")
	private String shipCode;

	@Column(name = "enable_ship")
	private String enbleShip;

	@Column(name = "ship_grade")
	private String shipGrade;

	@Column(name = "ship_desc")
	private String shipDesc;

}
