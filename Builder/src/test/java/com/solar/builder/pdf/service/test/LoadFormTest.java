package com.solar.builder.pdf.service.test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.junit.Test;

import com.solar.builder.client.bean.DropDownItem;
import com.solar.builder.pdf.bean.Field;
import com.solar.builder.pdf.bean.Form;
import com.solar.builder.pdf.service.FormService;
import com.solar.builder.pdf.service.IFormService;

public class LoadFormTest {

	public IFormService service = new FormService("D:/PDFBuilder/");
	
	@Test
	public void testDownloadForm() {
		File file;
		try {
			file = service.downloadForm("httxp://www.nyc.gov/html/dob/downloads/pdf/pta4.pdf", "pta4.pdf");
			assertNotNull(file);
			assertTrue(file.exists());
		} catch (Exception e) {
			fail(e.getMessage());
		}
	}
	
	
	@Test
	public void testReadFieldsFromForm(){
		Form form = new Form(); 
		service.getFieldsFromForm("pta4.pdf", form);
		assertNotNull(form);
		System.out.println(form);
		Map<Integer,List<Field>> fieldsByPage = form.getFields();
		for(List<Field> fieldByPage: fieldsByPage.values()){
			for(Field field: fieldByPage){
//				System.out.println(field);
				assertNotNull(field);
			}
		}
	}

	@Test
	public void testAvailableForms() {
		List<String> expectedFileNameList = new ArrayList<String>();
		expectedFileNameList.add("PTA4");
		
		List<DropDownItem> fileNameList = service.findAdllForms();
		
		assertNotNull(fileNameList);
		for(DropDownItem item : fileNameList){
			assertEquals("PTA4", item.getKey());
			assertTrue(expectedFileNameList.contains(item.getKey()));
		}
	}

	@Test
	public void testFindForm(){
		File file = service.findFormByName("pta4.pdf");
		assertNotNull(file);
		assertTrue(file.exists());
	}
}
