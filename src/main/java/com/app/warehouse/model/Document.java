package com.app.warehouse.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import lombok.Data;

@Data
@Entity
@Table(name = "doc_tab")
public class Document {

	@Id
	@GeneratedValue(generator = "gen_doc")
	@SequenceGenerator(name = "gen_doc", sequenceName = "gen_seq")
	@Column(name = "doc_id")
	private Integer docId;

	@Column(name = "doc_name")
	private String docName;

	@Column(name = "doc_data")
	@Lob
	private byte[] docData;

}
