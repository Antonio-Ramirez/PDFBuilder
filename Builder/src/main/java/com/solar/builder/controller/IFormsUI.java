package com.solar.builder.controller;

import org.springframework.validation.BindingResult;
import org.springframework.web.servlet.ModelAndView;

import com.solar.builder.web.request.FileRequest;
import com.solar.builder.web.request.FormRequest;

public interface IFormsUI {
	
	ModelAndView request();
	
	ModelAndView submit(FormRequest request, BindingResult result);
	
	ModelAndView build(FileRequest request, BindingResult result);
	
}
