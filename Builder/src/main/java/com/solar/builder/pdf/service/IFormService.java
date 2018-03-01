package com.solar.builder.pdf.service;

import java.io.File;
import java.io.IOException;
import java.io.Serializable;
import java.util.List;

import com.solar.builder.client.bean.Customer;
import com.solar.builder.client.bean.DropDownItem;
import com.solar.builder.pdf.bean.Form;
import com.solar.builder.pdf.bean.PdfBean;
import com.solar.builder.pdf.bean.PdfSummary;

public interface IFormService extends Serializable {
	
	Form importForm(String plainUrl);
	
	File downloadForm(String plainUrl, String formName) throws IOException, Exception;
	
	void getFieldsFromForm(String templateName, Form form);
	
	File findFormByName(String templateName);

	List<DropDownItem> findAdllForms();
	
	List<DropDownItem> findAllClients();
	
	PdfSummary buildPdf(Customer customer) throws Exception;
	
	Form processFile(Customer customer) throws Exception;
		
	PdfBean translate(Form inputForm);
	
	PdfSummary buildFinalForm(PdfBean pdfBean);
}
