package com.solar.builder.client.bean;

import java.io.Serializable;

public class DropDownItem implements Serializable {

	private static final long serialVersionUID = 1961384164878440611L;
	private String key;
	private String value;
	
	public DropDownItem(String key, String value){
		this.key = key;
		this.value = value;
	}
	public String getKey() {
		return key;
	}
	public void setKey(String key) {
		this.key = key;
	}
	public String getValue() {
		return value;
	}
	public void setValue(String value) {
		this.value = value;
	}
}
