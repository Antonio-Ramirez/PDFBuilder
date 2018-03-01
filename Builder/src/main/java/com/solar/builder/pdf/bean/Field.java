package com.solar.builder.pdf.bean;

import java.io.Serializable;

public class Field implements Comparable<Field>, Serializable {

	private static final long serialVersionUID = -682019908911885859L;
	private String key;
	private String type;
	private String value;
	private String alias;
	private String tableColumn;
	private int page;
	private int top;
	private int right;

	public Field(String key) {
		this.key = key;
	}
	public String getKey() {
		return key;
	}
	public void setKey(String key) {
		this.key = key;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getValue() {
		return value;
	}
	public void setValue(String value) {
		this.value = value;
	}
	public String getAlias() {
		return alias;
	}
	public void setAlias(String alias) {
		this.alias = alias;
	}
	public String getTableColumn() {
		return tableColumn;
	}
	public void setTableColumn(String tableColumn) {
		this.tableColumn = tableColumn;
	}
	public int getPage() {
		return page;
	}
	public void setPage(int page) {
		this.page = page;
	}
	public int getTop() {
		return top;
	}
	public void setTop(int top) {
		this.top = top;
	}
	public int getRight() {
		return right;
	}
	public void setRight(int right) {
		this.right = right;
	}
	@Override
	public String toString() {
		StringBuffer sb = new StringBuffer();
		sb.append("KEY: ").append(key).append(" TYPE: ").append(type);
		if(value != null){
			sb.append(" VALUE: ").append(value);
		}
		return sb.toString();
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + page;
		result = prime * result + top;
		result = prime * result + right;
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
		Field other = (Field) obj;
		if (page != other.page)
			return false;
		if (top != other.top)
			return false;
		if (right != other.right)
			return false;
		return true;
	}
	
	@Override
	public int compareTo(Field o) {
		Integer value = (this.page*1000) + (this.top*10) + (this.right);
		Integer value2 = (o.getPage()*1000) + (o.getTop()*10) + (o.getRight());
		return value.compareTo(value2);
	}
	
}
