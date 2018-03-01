package com.solar.builder.dao.businesslayer;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name = "form_url_fields")
public class FormUrlField {
	@Id
	@GenericGenerator(name="autoGen" ,strategy="increment")
    @GeneratedValue(generator="autoGen")
	@Column(name = "form_url_fields_id")
	private Long id;
	@Column(name = "form_url_fields_name")
	private String fieldTextName;
	@Column(name = "form_url_fields_type")
	private String fieldType;
	@Column(name = "form_url_fields_form_name")
	private String fieldName;
	@Column(name = "form_url_page")
	private Integer page;
	@Column(name = "form_url_row")
	private Integer row;
	@Column(name = "form_url_column")
	private Integer column;
	
	@ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	@JoinColumn(name = "form_url_id", nullable = false)
	private FormUrl formUrl;
	
	protected FormUrlField() {}
	
	public FormUrlField(String fieldTextName, String fieldType, String fieldName, Integer page, 
			Integer row, Integer column) {
		this.fieldTextName = fieldTextName;
		this.fieldType = fieldType;
		this.fieldName = fieldName;
		this.page = page;
		this.row = row;
		this.column = column;
	}
	
	public FormUrlField(String fieldTextName, String fieldType, String fieldName, Integer page, 
			Integer row, Integer column, FormUrl formUrl) {
		this.fieldTextName = fieldTextName;
		this.fieldType = fieldType;
		this.fieldName = fieldName;
		this.page = page;
		this.row = row;
		this.column = column;
		this.formUrl = formUrl;
	}
	

	public Long getId() {
		return id;
	}

	public String getFieldName() {
		return fieldName;
	}

	public String getFieldType() {
		return fieldType;
	}

	public String getFieldTextName() {
		return fieldTextName;
	}

	public Integer getPage() {
		return page;
	}

	public Integer getRow() {
		return row;
	}

	public Integer getColumn() {
		return column;
	}

	public FormUrl getFormUrl() {
		return formUrl;
	}

}
