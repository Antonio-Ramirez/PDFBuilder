package com.solar.builder.controller;

import javax.servlet.http.HttpServletResponse;

public interface IDownloadPDF {
	
	void getPDF(String id, HttpServletResponse response);
	
}
