package com.solar.builder.client.bean;

import java.text.MessageFormat;

public class Customer {
	private Long customerId;
	private Long formId;
	private String formName;
	private String customerFirstName;
	private String customerLastName;
	
	public Customer(Long customerId, Long formId, String formName, String firstName, String lastName){
		this.customerId = customerId;
		this.formId = formId;
		this.formName = formName;
		this.customerFirstName = firstName;
		this.customerLastName = lastName; 
	}
	public Customer(String key){
		String[] head = key.split("-");
		String[] ids = head[1].split(" ");
		this.customerId = Long.valueOf(head[0]);
		this.formId = Long.valueOf(ids[0]);
		this.formName = ids[1];
		this.customerFirstName = ids[2]; 
		this.customerLastName = ids[3];
	}
	
	public Long getCustomerId() {
		return customerId;
	}
	public void setCustomerId(Long customerId) {
		this.customerId = customerId;
	}
	public Long getFormId() {
		return formId;
	}
	public void setFormId(Long formId) {
		this.formId = formId;
	}
	public String getFormName() {
		return formName;
	}
	public void setFormName(String formName) {
		this.formName = formName;
	}
	public String getCustomerFirstName() {
		return customerFirstName;
	}
	public void setCustomerFirstName(String customerFirstName) {
		this.customerFirstName = customerFirstName;
	}
	public String getCustomerLastName() {
		return customerLastName;
	}
	public void setCustomerLastName(String customerLastName) {
		this.customerLastName = customerLastName;
	}
	public String getKey(){
		return MessageFormat.format("{0,number,#}-{1,number,#} {2} {3} {4}", customerId, formId, formName, customerFirstName, customerLastName);
	}
}
