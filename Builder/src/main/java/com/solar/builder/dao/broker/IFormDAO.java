package com.solar.builder.dao.broker;

import java.util.List;

import com.solar.builder.client.bean.Customer;
import com.solar.builder.dao.businesslayer.FormUrl;
import com.solar.builder.dao.businesslayer.FormUrlFieldValue;
import com.solar.builder.pdf.bean.Form;

public interface IFormDAO {
	
	void storeFormInputField(Form form);
	
	List<FormUrl> findAllForm();
	
	FormUrl findFormByName(String formName);
	
	List<Customer> findAllCustomer();
	
	List<FormUrlFieldValue> getFormValuesByCustomerId(Long customerId, Long formId);
}
