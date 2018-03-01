package com.solar.builder.pdf.service.test;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;

import org.junit.Test;

import com.solar.builder.client.bean.DropDownItem;
import com.solar.builder.pdf.bean.Field;
import com.solar.builder.pdf.bean.Form;
import com.solar.builder.pdf.bean.PdfBean;
import com.solar.builder.pdf.service.FormService;
import com.solar.builder.pdf.service.IFormService;


public class ClientsTest {

public IFormService service = new FormService("D:/PDFBuilder/");
	
	@Test
	public void testFindClients() {
		List<DropDownItem> clients = service.findAllClients();
		assertNotNull(clients);
		assertTrue("empty", clients.isEmpty());
	}
	
	@Test
	public void testBuildPdf() {
		Form inputForm = new Form();
		inputForm.setName("pta4.pdf");
		inputForm.setInputFileLocation("D:\\PDFBuilder\\clients\\");
		inputForm.setInputFileName("Walker_pta4.csv");
		
		buildDummyClient(inputForm);
		PdfBean pdfBean = service.translate(inputForm);
		assertNotNull(pdfBean);

		System.out.println(pdfBean);
		service.buildFinalForm(pdfBean);
		File file = new File(pdfBean.getOutputFileLocation(), pdfBean.getOutputFileName());
		assertTrue(file.exists());
		
	}
	
	private void buildDummyClient(Form inputForm){
		File file = null;
		BufferedReader br = null;
		try {
			
			file = new File(inputForm.getInputFileLocation(), inputForm.getInputFileName());
			br = new BufferedReader(new FileReader(file));
			 
			String line = null;
			Field inputField;
			String[] lines;
			while ((line = br.readLine()) != null) {
		
				lines = line.split(",");
				inputField = new Field(lines[0]);
				inputField.setValue(lines[1]);
				inputForm.addField(inputField, 1);
			}
			
		} catch (IOException e) {
			fail(e.getMessage());
		} finally {
			try {
				if(br != null){
					br.close();
				}
			} catch (IOException e) {
				fail(e.getMessage());
			}
		}
	}
}
