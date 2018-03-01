package com.solar.builder.dao.broker.hibernate;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

import com.solar.builder.client.bean.Customer;
import com.solar.builder.controller.FormsUI;
import com.solar.builder.dao.broker.IFormDAO;
import com.solar.builder.dao.businesslayer.FormUrl;
import com.solar.builder.dao.businesslayer.FormUrlField;
import com.solar.builder.dao.businesslayer.FormUrlFieldValue;
import com.solar.builder.pdf.bean.Field;
import com.solar.builder.pdf.bean.Form;


public class FormDAO extends HibernateDaoSupport implements IFormDAO {
	private static final Log log = LogFactory.getLog(FormsUI.class);
	
	@SuppressWarnings("unchecked")
	@Override
	public void storeFormInputField(Form form) {
		List<FormUrl> formUrls = (List<FormUrl>) getHibernateTemplate().find("from FormUrl where formUrl = ? ", form.getUrl());
		if(formUrls != null){
			FormUrl formUrl = formUrls.get(0);
			FormDAO.log.debug(MessageFormat.format("Form Name: {0} pages: {1,number,#}" , form.getName(), form.getFields().size()));
			for(List<Field> fields : form.getFields().values()){
				for(Field field : fields){
					getHibernateTemplate().save(new FormUrlField(field.getAlias(), field.getType(), field.getKey(),
							field.getPage(), field.getTop(), field.getRight(), formUrl));
				}
			}
		}
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<FormUrl> findAllForm() {
		List<FormUrl> forms = (List<FormUrl>)getHibernateTemplate().find("from FormUrl");
		FormDAO.log.debug(MessageFormat.format("Retrieve forms from DB: {0,number,#} ", forms.size()));
		return forms;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public FormUrl findFormByName(String formName) {
		FormUrl form = null;
		List<FormUrl> forms = (List<FormUrl>)getHibernateTemplate().find("from FormUrl where name = ?", formName);
		if(forms != null){
			form = forms.get(0);
		} 
		FormDAO.log.debug(MessageFormat.format("Retrieve form from DB: {0} ", form.getName()));
		return form;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Customer> findAllCustomer() {
		List<Customer> customers = new  ArrayList<Customer>();
		
		List<Object[]> objects = (List<Object[]>) getHibernateTemplate()
			.find("select distinct customer.id, formUrlField.formUrl.id, formUrlField.formUrl.name, customer.firstName, customer.lastName from FormUrlFieldValue ");
		if(objects != null){
			for(Object[] obj : objects){
				customers.add(new Customer(Long.valueOf(obj[0].toString()), Long.valueOf(obj[1].toString()), obj[2].toString(), obj[3].toString(), obj[4].toString()));
			}
		}
		FormDAO.log.debug(MessageFormat.format("Retrieve customers from DB: {0,number,#} ", customers.size()));
		return customers;
	}


	@SuppressWarnings("unchecked")
	@Override
	public List<FormUrlFieldValue> getFormValuesByCustomerId(Long customerId, Long formId) {
		List<FormUrlFieldValue> values = (List<FormUrlFieldValue>) getHibernateTemplate().find("from FormUrlFieldValue where customer.id = ? and formUrlField.formUrl.id = ? ", customerId, formId);
		FormDAO.log.debug(MessageFormat.format("Retrieve values from DB for Customer: {0,number,#} values: {1,number,#}", customerId, values.size()));
		return values;
	}
	
}

