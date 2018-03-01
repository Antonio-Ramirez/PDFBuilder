package com.solar.builder.pdf.bean;

import java.util.ArrayList;
import java.util.List;

public class PdfSummary {
	
	private List<String> errors = new  ArrayList<String>();
	private String url;
	private String id;
	
	public PdfSummary() {
	}
	
	public List<String> getErrors() {
		return errors;
	}
	public void addError(String error) {
		this.errors.add(error);
	}
	public void addErrors(List<String> errors) {
		this.errors.addAll(errors);
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	/**
	 * Constructs a <code>String</code> with all attributes in name = value format.
	 * @return a <code>String</code> representation of this object.
	 */
	public String toString() {
		final String TAB = "\n";
		
		StringBuffer retValue = new StringBuffer();
		
		retValue.append("PdfSummary ( ").append(super.toString()).append(TAB).append("url = ").append(this.url).append(TAB)
				.append("id = ").append(this.id).append(TAB).append(" )");
		
		return retValue.toString();
	}
}
