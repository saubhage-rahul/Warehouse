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
@Table(name = "wh_usr_tab")
public class WhUserType {

	@Id
	@GeneratedValue(generator = "whusr_gen")
	@SequenceGenerator(name = "whusr_gen", sequenceName = "whusr_seq")
	@Column(name = "wh_usr_id")
	private Integer id;

	@Column(name = "wh_usr_type", nullable = false, length = 10)
	private String userType;

	@Column(name = "wh_usr_code", nullable = false, length = 10, unique = true)
	private String userCode;

	@Column(name = "wh_usr_for",nullable = false, length = 10)
	private String userFor;

	@Column(name = "wh_usr_email", nullable = false, unique = true)
	private String userEmail;

	@Column(name = "wh_usr_contact", nullable = false, length = 20)
	private String userContact;

	@Column(name = "wh_usr_id_type", nullable = false, length = 10)
	private String userIdType;

	@Column(name = "wh_usr_if_other", nullable = false, length = 10)
	private String ifOther;

	@Column(name = "wh_usr_id_number", nullable = false, length = 15, unique = true)
	private String userIdNum;

}
