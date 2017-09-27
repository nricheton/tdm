package org.tdm.demo.factories;

import static java.util.Arrays.asList;

import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.tdm.core.DataFactory;
import org.tdm.core.TestData;
import org.tdm.demo.app.Customer;
import org.tdm.demo.app.CustomerService;

public class CustomerUnitTestFactory implements DataFactory {

	CustomerService customerService;

	public void setCustomerService(CustomerService customerService) {
		this.customerService = customerService;
	}

	public List<String> getTypes() {
		return asList(new String[] { "customer" });
	}

	public void create(TestData data, String type, Map<String, String> values) {

		Customer c = customerService.create(UUID.randomUUID() + "@test.org", values.get("firstName"),
				values.get("lastName"));
		c.setEnabled(Boolean.getBoolean(values.get("enabled")));
		c.setPassword(values.get("password"));
		data.add(type, c.getEmail());
	}

}
