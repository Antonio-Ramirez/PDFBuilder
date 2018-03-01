package com.solar.builder.web.response;

import com.solar.builder.pdf.bean.Form;
import com.solar.builder.pdf.bean.PdfSummary;


public class FormResponse {
	
	private Form form;
	private PdfSummary pdfSummary;
	
	
	/**
	 * Creates the response.<br />
	 * This method may be required by the services framework.
	 */
	public FormResponse() {}

	public FormResponse(Form form) {
		this.form = form;
	}
	
	public FormResponse(PdfSummary pdfSummary) {
		this.pdfSummary = pdfSummary;
	}

	public Form getForm() {
		return form;
	}

	public void setForm(Form form) {
		this.form = form;
	}

	public PdfSummary getPdfSummary() {
		return pdfSummary;
	}

	public void setPdfSummary(PdfSummary pdfSummary) {
		this.pdfSummary = pdfSummary;
	}
}
