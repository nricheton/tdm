package org.tdm.demo.factories;

import static java.util.Arrays.asList;

import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.tdm.core.DataFactory;
import org.tdm.core.TestData;
import org.tdm.demo.app.Customer;
import org.tdm.demo.app.CustomerService;
import org.tdm.demo.app.OrderService;

public class CustomerUnitTestFactory implements DataFactory {

	CustomerService customerService;
	
	public void setCustomerService(CustomerService customerService) {
		this.customerService = customerService;
	}

	public List<String> getTypes() {
		return asList(new String[] { "customer" });
	}

	public void create(TestData data, String type, Map<String, Object> values) {

		Customer c = customerService.create(UUID.randomUUID() + "@test.org", (String)values.get("firstName"),
				(String)values.get("lastName"));
		
		Boolean enabled = null;
		if( values.get("enabled") instanceof Boolean ) {
			enabled = (Boolean) values.get("enabled");
		} else if(  values.get("enabled") instanceof String) {
			enabled = Boolean.valueOf((String) values.get("enabled"));
		}
		c.setEnabled(enabled);
		c.setPassword((String)values.get("password"));
		data.add(type, c.getEmail());
	}



}
