package com.solar.builder.dao.businesslayer;

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
@Table(name = "customer")
public class Customer {
	@Id
	@GenericGenerator(name="autoGen" ,strategy="increment")
    @GeneratedValue(generator="autoGen")
	@Column(name = "customer_id")
	private Long id;
	@Column(name = "customer_firm_name")
	private String firmName;
	@Column(name = "customer_first_name")
	private String firstName;
	@Column(name = "customer_last_name")
	private String lastName;
	
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "customer")
	private Set<FormUrlFieldValue> formUrlFields = new HashSet<FormUrlFieldValue>(0);
	
	protected Customer() {}
	
	public Customer(String firmName, String firstName, String lastName) {
		this.firmName = firmName;
		this.firstName = firstName;
		this.lastName = lastName;
	}
	
	public Long getId() {
		return id;
	}

	public String getFirmName() {
		return firmName;
	}

	public String getFirstName() {
		return firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public Set<FormUrlFieldValue> getFormUrlFields() {
		return formUrlFields;
	}

	public void setFormUrlFields(Set<FormUrlFieldValue> formUrlFields) {
		this.formUrlFields = formUrlFields;
	}
}
