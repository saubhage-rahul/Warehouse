package com.app.warehouse.model;

import java.util.Set;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table(name = "grn_tab")
public class Grn {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "grn_id")
	private Integer id;

	@Column(name = "grn_code")
	private String grnCode;

	@Column(name = "grn_type")
	private String grnType;

	@Column(name = "grn_desc")
	private String grnDescription;

	// 1...1
	@ManyToOne
	@JoinColumn(name = "po_id_fk", unique = true)
	private PurchaseOrder po;

	// 1...*
	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	@JoinColumn(name = "grn_id_fk")
	private Set<GrnDtl> dtls;

}
