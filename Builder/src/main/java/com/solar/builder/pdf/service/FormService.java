package com.solar.builder.pdf.service;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;
import java.nio.channels.Channels;
import java.nio.channels.ReadableByteChannel;
import java.text.MessageFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.solar.builder.client.bean.Customer;
import com.solar.builder.client.bean.DropDownItem;
import com.solar.builder.dao.broker.IFormDAO;
import com.solar.builder.dao.businesslayer.FormUrl;
import com.solar.builder.dao.businesslayer.FormUrlFieldValue;
import com.solar.builder.pdf.bean.Field;
import com.solar.builder.pdf.bean.Form;
import com.solar.builder.pdf.bean.PdfBean;
import com.solar.builder.pdf.bean.PdfSummary;
import com.solar.builder.pdf.bean.PdfUtil;

public class FormService implements IFormService {
	
	private static final long serialVersionUID = -8906920083214319438L;
	private static final Log log = LogFactory.getLog(FormService.class);
	private String home;
	private String homeForms;
	private String homePdfs;
	private IFormDAO formDAO;
	
	public FormService (String home) {
		this.home = home;
		this.homeForms = home + "templates/";
		this.homePdfs = home + "pdfs/";
	}
	
	public Form importForm(String plainUrl) {
		Form form = new Form();
		try {
			String name = getNameFormFromUrl(plainUrl);
			form.setName(name);
			form.setUrl(plainUrl);
			File file = downloadForm(plainUrl, name);
			getFieldsFromForm(file.getName(), form);
		} catch (Exception e) {
			FormService.log.error(e.getMessage(), e);
			form.addError(e.getMessage());
		}
		return form;
	}
	
	private String getNameFormFromUrl(String plainUrl){
		return plainUrl.substring(plainUrl.lastIndexOf("/") + 1, plainUrl.length());
	}
	
	public File downloadForm(String plainUrl, String formName) throws IOException, Exception {
		URL url = new URL(plainUrl);
		ReadableByteChannel rbc = Channels.newChannel(url.openStream());

		File file = new File(homeForms, formName);
		FileOutputStream fos = new FileOutputStream(file);
		
		fos.getChannel().transferFrom(rbc, 0, Long.MAX_VALUE);
		fos.close();
		
		return file;
	}

	public void getFieldsFromForm(String formName, Form form) {
		try {
			form.setInputFileLocation(homeForms);
			form.setInputFileName(formName);
			form.setOutputFileLocation(home);
			PdfUtil.analizeForm(form);
			formDAO.storeFormInputField(form);
			
		} catch (Exception e) {
			FormService.log.error(e.getMessage(), e);
			form.addError(e.getMessage());
		}
	}

	public File findFormByName(String formName) {
		File file = new File(homeForms + formName);
		return file;
	}
	
	public List<DropDownItem> findAdllForms() {
		List<DropDownItem> formList = new ArrayList<DropDownItem>();
		List<FormUrl> forms = formDAO.findAllForm();
		for(FormUrl form : forms) {
			formList.add(new DropDownItem(form.getName(), form.getFormUrl()));
		}
		return formList;
	}

	@Override
	public List<DropDownItem> findAllClients() {
		List<DropDownItem> cusomerList = new ArrayList<DropDownItem>();
		List<Customer> customers = formDAO.findAllCustomer();
		for(Customer customer : customers){
			cusomerList.add(new DropDownItem(customer.getKey(), customer.getKey()));
		}
		return cusomerList;
	}

	@Override
	public PdfSummary buildPdf(Customer customer){
		PdfSummary pdfSummary = new PdfSummary();
		try {
			pdfSummary = buildFinalForm(translate(processFile(customer)));
		} catch (Exception e) {
			FormService.log.error(e.getMessage(), e);
			pdfSummary.addError(e.getMessage());
		}
		return pdfSummary;
	}
	
	@Override
	public Form processFile(Customer customer) throws Exception {
		Form form = new Form();
		FormUrl formUrl = formDAO.findFormByName(customer.getFormName());
		form.setName(getNameFormFromUrl(formUrl.getFormUrl()));
		Field inputField;
		List<FormUrlFieldValue> values = formDAO.getFormValuesByCustomerId(customer.getCustomerId(), customer.getFormId());
		for(FormUrlFieldValue value : values){
			inputField = new Field(value.getFormUrlField().getFieldName());
			inputField.setValue(value.getFieldValue());
			form.addField(inputField, value.getFormUrlField().getPage());
		}
		return form;
	}

	@Override
	public PdfBean translate(Form inputForm) {

		SimpleDateFormat todayFormat = new SimpleDateFormat("yyMMddHHmmSS");
		PdfBean pdfBean = new PdfBean();
		pdfBean.setInputFileLocation(homeForms);
		pdfBean.setInputFileName(inputForm.getName());
		pdfBean.setOutputFileLocation(homePdfs);
		pdfBean.setId(inputForm.getFileName() + "-" + todayFormat.format(Calendar.getInstance().getTime()));
		pdfBean.setOutputFileName(pdfBean.getId()+".pdf");
		pdfBean.setUrl(MessageFormat.format("/FormsService/pdf/{0}", pdfBean.getId()));
		
		Map<Integer, List<Field>> fieldsByPages = inputForm.getFields();
		
		for(List<Field> fieldsByPage : fieldsByPages.values()) {
			for(Field field : fieldsByPage) {
				pdfBean.addField(field.getKey(), field.getValue());
			}
		}
		FormService.log.debug(pdfBean);
		return pdfBean;
	}

	@Override
	public PdfSummary buildFinalForm(PdfBean pdfBean) {
		PdfSummary pdfSummary = new PdfSummary();
		try {
			PdfUtil.generatePdfFile(pdfBean);
			pdfSummary.setId(pdfBean.getId());
			pdfSummary.setUrl(pdfBean.getUrl());
		} catch (Exception e) {
			FormService.log.error(e.getMessage(), e);
			pdfSummary.addError(e.getMessage());
		}
		FormService.log.debug(pdfSummary);
		return pdfSummary;
	}

	public IFormDAO getFormDAO() {
		return formDAO;
	}

	public void setFormDAO(IFormDAO formDAO) {
		this.formDAO = formDAO;
	}
	
}
