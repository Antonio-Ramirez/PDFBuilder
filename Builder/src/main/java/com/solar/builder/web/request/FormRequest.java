package com.solar.builder.web.request;

import java.io.Serializable;
import java.util.List;

import org.apache.commons.lang.builder.ToStringBuilder;

import com.solar.builder.client.bean.DropDownItem;

public class FormRequest implements Serializable {
	
	private static final long serialVersionUID = 311977076580380283L;
	private List<DropDownItem> options;
	private String formUrl;

	/**
	 * Creates the request.<br />
	 * This method may be required by the services framework.
	 */
	public FormRequest() {}
	
	public List<DropDownItem> getOptions() {
		return options;
	}

	public void setOptions(List<DropDownItem> options) {
		this.options = options;
	}

	public String getFormUrl() {
		return formUrl;
	}

	public void setFormUrl(String formUrl) {
		this.formUrl = formUrl;
	}

	@Override
	public String toString() {
		return new ToStringBuilder(this).append("formUrl", formUrl).toString();
	}
}
