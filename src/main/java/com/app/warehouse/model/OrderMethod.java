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

	@Column(name = "order_mode")
	private String orderMode;

	@Column(name = "order_code")
	private String orderCode;

	@Column(name = "order_type")
	private String orderType;

	@Column(name = "order_desc")
	private String orderDesc;

	@ElementCollection
	@CollectionTable(name = "order_accept_tab", joinColumns = @JoinColumn(name = "order_id"))
	@Column(name = "order_accept")
	private Set<String> orderAccept;

}
