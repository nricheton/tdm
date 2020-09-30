package org.tdm.demo.factories;

import static java.util.Arrays.asList;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.tdm.core.DataFactory;
import org.tdm.core.TestData;
import org.tdm.demo.app.Customer;
import org.tdm.demo.app.CustomerService;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jayway.jsonpath.JsonPath;
import com.jayway.jsonpath.ReadContext;

public class CustomerUnitTestFactory implements DataFactory {

	CustomerService customerService;
	ObjectMapper mapper = new ObjectMapper();

	public void setCustomerService(CustomerService customerService) {
		this.customerService = customerService;
	}

	public List<String> getTypes() {
		return asList(new String[] { "customer" });
	}

	public void create(TestData data, String type, Map<String, Object> values) throws IOException {
		String json = mapper.writeValueAsString(values);
		ReadContext ctx = JsonPath.parse(json);

		Customer c = customerService.create(UUID.randomUUID() + "@test.org", ctx.read("$.firstName", String.class),
				ctx.read("$.lastName", String.class));

		Boolean enabled = null;
		if (values.get("enabled") instanceof Boolean) {
			enabled = (Boolean) values.get("enabled");
		} else if (values.get("enabled") instanceof String) {
			enabled = Boolean.valueOf((String) values.get("enabled"));
		}
		c.setEnabled(enabled);
		c.setPassword(ctx.read("$.password", String.class));
		data.add(type, c.getEmail());

		customerService.setAddress(c.getEmail(), ctx.read("$.address.street", String.class),
				ctx.read("$.address.postalCode", String.class), ctx.read("$.address.city", String.class));

	}

}
