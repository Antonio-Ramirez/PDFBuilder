package com.solar.builder.controller;

import java.io.IOException;
import java.text.MessageFormat;

import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.solar.builder.constant.AppConstants;


@Controller
@RequestMapping("/pdf/")
public class DownloadPDF implements IDownloadPDF {
	private static final Log log = LogFactory.getLog(DownloadPDF.class);
	
	@Autowired
	private AppConstants appContants;
	
	@Override
	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	public void getPDF(@PathVariable String id, HttpServletResponse response) {
		try {
			DownloadPDF.log.debug(MessageFormat.format("getPDF[id={0}]", id));
			String filepath = MessageFormat.format("{0}{1}.pdf", appContants.getHomePdfs(), id);
			
			FileIo.writeFileToServletStream(filepath, response,FileIo.RESPONSE_ENCODING_PDF_INLINE);
			
		} catch (Exception ex) {
			DownloadPDF.log.error(ex.getMessage(), ex);
			try {
				response.setContentType("text/plain");
				response.getOutputStream().print("The document does not exist.");
				response.flushBuffer();
			} catch (IOException e) {
				DownloadPDF.log.error(MessageFormat.format("Error writing file to output stream. [id={0}]", id));
				throw new RuntimeException(MessageFormat.format("Error writing file to output stream. [id={0}]", id), ex);
			}
		}
	}
}
