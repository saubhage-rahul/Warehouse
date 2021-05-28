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
@Table(name = "Part_tab")
public class Part {

	@Id
	@GeneratedValue(generator = "part_gen")
	@SequenceGenerator(name = "part_gen", sequenceName = "part_seq")
	@Column(name = "part_id")
	private Integer id;

	@Column(name = "part_code")
	private String partCode;

	@Column(name = "part_currency")
	private String partCurrency;

	@Column(name = "part_cost")
	private Double partBaseCost;

	@Column(name = "part_wid")
	private Double partWid;

	@Column(name = "part_ht")
	private Double partHt;

	@Column(name = "part_len")
	private Double partLen;

	@Column(name = "part_desc")
	private String partDesc;
	
	//HAS-A Relationship
	@ManyToOne
	@JoinColumn(name = "uom_id_fk")
	private Uom uom;

}
