package com.solar.builder.pdf.bean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Form implements Serializable {

	private static final long serialVersionUID = 6707192689597989331L;
	private Long id;
	private String inputFileLocation = null, inputFileName = null, outputFileLocation = null, outputFileName = null;
	private String name;
	private String url;
	private Map<Integer,List<Field>> fields = new HashMap<Integer, List<Field>>();
	private List<String> errors = new  ArrayList<String>();
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
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
	public String getName() {
		return name;
	}
	public Long getCustomerId() {
		return 1L;
	}
	public String getFileName() {
		if(name != null){
			return name.replace(".pdf", "");
		}
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public Map<Integer,List<Field>> getFields() {
		for(List<Field> row : this.fields.values()){
			Collections.sort(row);
		}
		return fields;
	}
	public void addField(Field field, Integer page) {
		List<Field> list = fields.get(page);
		if(list == null){
			list = new ArrayList<Field>();
		}
		list.add(field);
		this.fields.put(page, list);
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
	@Override
	public String toString() {
		final String TAB = "\n";
		StringBuffer sb = new StringBuffer();
		sb.append("inputFileLocation = ").append(this.inputFileLocation).append(TAB)
			.append("inputFileName = ").append(this.inputFileName).append(TAB)
			.append("outputFileLocation = ").append(this.outputFileLocation).append(TAB)
			.append("outputFileName = ").append(this.outputFileName).append(TAB);
		return sb.toString();		
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		return result;
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Form other = (Form) obj;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		return true;
	}
}
