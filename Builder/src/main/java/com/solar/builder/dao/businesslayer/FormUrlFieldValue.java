package com.solar.builder.dao.businesslayer;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name = "form_url_fields_value")
public class FormUrlFieldValue {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "form_url_fields_Value_id")
	private Long id;
	@Column(name = "form_url_fields_Value")
	private String fieldValue;
	
	@OneToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	@JoinColumn(name = "form_url_fields_id", nullable = false)
	private FormUrlField formUrlField;
	
	@ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	@JoinColumn(name = "form_url_fields_customer_id", nullable = false)
	private Customer customer;
	
	protected FormUrlFieldValue() {}
	
	public FormUrlFieldValue(Long id, String fieldValue, FormUrlField formUrlField) {
		this.id = id;
		this.fieldValue = fieldValue;
		this.formUrlField = formUrlField;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Customer getCustomer() {
		return customer;
	}

	public void setCustomer(Customer customer) {
		this.customer = customer;
	}

	public String getFieldValue() {
		return fieldValue;
	}

	public void setFieldValue(String fieldValue) {
		this.fieldValue = fieldValue;
	}

	public FormUrlField getFormUrlField() {
		return formUrlField;
	}

	public void setFormUrlField(FormUrlField formUrlField) {
		this.formUrlField = formUrlField;
	}
	
}
