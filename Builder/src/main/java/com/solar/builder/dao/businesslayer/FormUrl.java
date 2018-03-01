package com.solar.builder.dao.businesslayer;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name = "form_url")
public class FormUrl {
	@Id
	@GenericGenerator(name="autoGen" ,strategy="increment")
    @GeneratedValue(generator="autoGen")
	@Column(name = "form_url_id")
	private Long id;
	@Column(name = "form_url_name")
	private String name;
	@Column(name = "form_url")
	private String formUrl;
	@Column(name = "form_url_date")
	private Date formDate;
	
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "formUrl")
	private Set<FormUrlField> formUrlFields = new HashSet<FormUrlField>(0);
	
	protected FormUrl() {}
	
	public FormUrl(String name, String formUrl, Date formDate) {
		this.name = name;
		this.formUrl = formUrl;
		this.formDate = formDate;
	}
	
	public FormUrl(String name, String formUrl, Date formDate, Set<FormUrlField> formUrlFields) {
		this.name = name;
		this.formUrl = formUrl; 
		this.formDate = formDate;
		this.formUrlFields = formUrlFields;
	}

	public Long getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public String getFormUrl() {
		return formUrl;
	}

	public Date getFormDate() {
		return formDate;
	}

	public Set<FormUrlField> getFormUrlFields() {
		return formUrlFields;
	}

	public void setFormUrlFields(Set<FormUrlField> formUrlFields) {
		this.formUrlFields = formUrlFields;
	}
	
}
