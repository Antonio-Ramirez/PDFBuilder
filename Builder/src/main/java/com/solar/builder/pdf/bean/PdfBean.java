package com.solar.builder.pdf.bean;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;

public class PdfBean {
	
	private String inputFileLocation = null, inputFileName = null, outputFileLocation = null, outputFileName = null;
	
	private Map<String, String> fields = null;
	private Map<String, PdfTable> tablefields = null;
	private List<String> errors = new  ArrayList<String>();
	private String url;
	private String id;
	
	public PdfBean() {
		this.fields = new Hashtable<String, String>();
		this.tablefields = new Hashtable<String, PdfTable>();
	}
	
	public void addField(String name, String value) {
		if (value == null)
			value = "";
		this.fields.put(name, value);
	}
	
	public void addField(String name, Object value) {
		if (value == null)
			value = "";
		this.fields.put(name, value.toString());
	}
	
	public void addField(String name, BigDecimal value) {
		this.fields.put(name, value.toString());
	}
	
	public void addTable(String name, PdfTable tab) {
		this.tablefields.put(name, tab);
	}
	
	public void addTableCell(String name, PdfTableCell cell) {
		if (!tablefields.containsKey(name)) {
			this.tablefields.put(name, new PdfTable());
		}
		this.tablefields.get(name).addCell(cell);
	}
	
	public void addTableHeader(String name, PdfTableHeader header) {
		if (!tablefields.containsKey(name)) {
			this.tablefields.put(name, new PdfTable());
		}
		this.tablefields.get(name).addHeader(header);
	}
	
	public void nextRow(String name) {
		if (tablefields.containsKey(name)) {
			this.tablefields.get(name).nextRow();
		}
	}
	
	public Map<String, String> getFields() {
		return fields;
	}
	
	public Map<String, PdfTable> getTablefields() {
		return tablefields;
	}
	
	public String getInputFileLocation() {
		return inputFileLocation;
	}
	
	public void setInputFileLocation(String inputFileLocation) {
		this.inputFileLocation = inputFileLocation;
	}
	
	public String getInputFileName() {
		return inputFileName;
	}
	
	public void setInputFileName(String inputFileName) {
		this.inputFileName = inputFileName;
	}
	
	public String getOutputFileLocation() {
		return outputFileLocation;
	}
	
	public void setOutputFileLocation(String outputFileLocation) {
		this.outputFileLocation = outputFileLocation;
	}
	
	public String getOutputFileName() {
		return outputFileName;
	}
	
	public void setOutputFileName(String outputFileName) {
		this.outputFileName = outputFileName;
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
		
		retValue.append("PdfBean ( ").append(super.toString()).append(TAB).append("inputFileLocation = ").append(this.inputFileLocation).append(TAB)
				.append("inputFileName = ").append(this.inputFileName).append(TAB).append("outputFileLocation = ").append(this.outputFileLocation).append(TAB)
				.append("outputFileName = ").append(this.outputFileName).append(TAB).append("fields = ").append(this.fields).append(TAB)
				.append("tablefields = ").append(this.tablefields).append(TAB).append(" )");
		
		return retValue.toString();
	}
}
