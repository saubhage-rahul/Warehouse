package com.app.warehouse.model;

import java.util.Set;

import javax.persistence.CollectionTable;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import lombok.Data;

@Data
@Entity
@Table(name = "order_method_tab")
public class OrderMethod {

	@Id
	@GeneratedValue(generator = "order_gen")
	@SequenceGenerator(name = "order_gen", sequenceName = "order_seq")
	@Column(name = "order_id")
	private Integer id;

	@Column(name = "order_mode", nullable = false, length = 10)
	private String orderMode;

	@Column(name = "order_code", nullable = false, length = 13, unique = true)
	private String orderCode;

	@Column(name = "order_type", nullable = false, length = 5)
	private String orderType;

	@Column(name = "order_desc", nullable = false, length = 100)
	private String orderDesc;

	@ElementCollection
	@CollectionTable(name = "order_accept_tab", joinColumns = @JoinColumn(name = "order_id"))
	@Column(name = "order_accept", nullable = false, length = 20)
	private Set<String> orderAccept;

}
