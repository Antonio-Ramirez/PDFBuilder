package com.solar.builder.controller;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.beans.propertyeditors.StringTrimmerEditor;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.solar.builder.client.bean.Customer;
import com.solar.builder.pdf.bean.Form;
import com.solar.builder.pdf.bean.PdfSummary;
import com.solar.builder.pdf.service.IFormService;
import com.solar.builder.web.request.FileRequest;
import com.solar.builder.web.request.FormRequest;
import com.solar.builder.web.response.FormResponse;


@Controller
@RequestMapping("/ui/")
public class FormsUI implements IFormsUI {
	private static final Log log = LogFactory.getLog(FormsUI.class);
	@Autowired
	private IFormService formService;
	
	@InitBinder
	protected void initBinder(WebDataBinder binder) {
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd");
		dateFormat.setLenient(false);
		binder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat, true));
		binder.registerCustomEditor(String.class, new StringTrimmerEditor(true));
	}
	
	@Override
	@RequestMapping(method = RequestMethod.GET)
	public ModelAndView request() {
		FormsUI.log.debug("request");
		ModelAndView modelAndView = new ModelAndView("ui");

		FormRequest formRequest = new FormRequest();
		formRequest.setOptions(formService.findAdllForms());
		modelAndView.addObject("request", formRequest);
		
		FileRequest fileRequest = new FileRequest();
		fileRequest.setClients(formService.findAllClients());
		modelAndView.addObject("fileRequest", fileRequest);
		
		return modelAndView;
	}
	
	@Override
	@RequestMapping(value = "submit", method = RequestMethod.POST)
	public ModelAndView submit(@ModelAttribute("request") FormRequest request, BindingResult bindingResult) {
		FormsUI.log.debug("request " + request);
		
		ModelAndView modelAndView = new ModelAndView("ui");
		request.setOptions(formService.findAdllForms());
		modelAndView.addObject("request", request);
		
		Form inputForm = null;
		if (StringUtils.isBlank(request.getFormUrl())) {
			inputForm = new Form();
			inputForm.addError("Form is Required");
		} else {
			inputForm = formService.importForm(request.getFormUrl());
		}
		FormsUI.log.debug("# errors " + inputForm.getErrors().size());

		FileRequest fileRequest = new FileRequest();
		fileRequest.setClients(formService.findAllClients());
		modelAndView.addObject("fileRequest", fileRequest);
		FormResponse response = new FormResponse(inputForm);
		modelAndView.addObject("response", response);
		return modelAndView;
	}
	
	@Override
	@RequestMapping(value = "build", method = RequestMethod.POST)
	public ModelAndView build(@ModelAttribute("fileRequest") FileRequest request, BindingResult bindingResult) {
		FormsUI.log.debug("request " + request);
		ModelAndView modelAndView = new ModelAndView("ui");
		
		FormRequest formRequest = new FormRequest();
		formRequest.setOptions(formService.findAdllForms());
		modelAndView.addObject("request", formRequest);
		
		request.setClients(formService.findAllClients());
		modelAndView.addObject("fileRequest", request);

		PdfSummary pdfSummary = new PdfSummary();

		if (StringUtils.isBlank(request.getFormCustomerId())) {
			pdfSummary.addError("Customer is Required");
		} 
		if (pdfSummary.getErrors().isEmpty()) {
			Customer customer = new Customer(request.getFormCustomerId());
			try {
				pdfSummary = formService.buildPdf(customer);
			} catch (Exception e) {
				FormsUI.log.error(e.getMessage(), e);
				pdfSummary.addError(e.getMessage());
			}
		}
		FormsUI.log.debug("# errors " + pdfSummary.getErrors().size());
		
		FormResponse response = new FormResponse(pdfSummary);
		modelAndView.addObject("response", response);
		return modelAndView;
	}
	
}
