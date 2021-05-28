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
@Table(name = "Uom_Tab")
public class Uom {
	@Id
	@GeneratedValue(generator = "gen_uom")
	@SequenceGenerator(name = "gen_uom", sequenceName = "seq_uom")
	@Column(name = "u_id")
	private Integer uid;

	@Column(name = "u_type", nullable = false, length = 12)
	private String uomType;

	@Column(name = "u_model", nullable = false, length = 12, unique = true)
	private String uomModel;

	@Column(name = "u_desc", nullable = false, length = 100, unique = false)
	private String uomDesc;

}
