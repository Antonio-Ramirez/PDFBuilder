package com.solar.builder.web.request;

import java.io.Serializable;
import java.util.List;

import org.apache.commons.lang.builder.ToStringBuilder;

import com.solar.builder.client.bean.DropDownItem;

public class FileRequest implements Serializable {
	
	private static final long serialVersionUID = 311977076580380283L;
	
	private List<DropDownItem> clients;
	private String formCustomerId;

	/**
	 * Creates the request.<br />
	 * This method may be required by the services framework.
	 */
	public FileRequest() {}
	
	public List<DropDownItem> getClients() {
		return clients;
	}

	public void setClients(List<DropDownItem> clients) {
		this.clients = clients;
	}

	public String getFormCustomerId() {
		return formCustomerId;
	}

	public void setFormCustomerId(String formCustomerId) {
		this.formCustomerId = formCustomerId;
	}

	@Override
	public String toString() {
		return new ToStringBuilder(this).append("formCustomerId", formCustomerId).toString();
	}
}
