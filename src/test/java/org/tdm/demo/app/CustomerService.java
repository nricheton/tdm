package org.tdm.demo.app;

import java.util.ArrayList;
import java.util.List;

public class CustomerService {

	private List<Customer> customers = new ArrayList<Customer>();

	public Customer create(String email, String firstName, String lastName) {
		Customer c = new Customer();
		c.setEmail(email);
		c.setFirstName(firstName);
		c.setLastName(lastName);
		customers.add(c);
		return c;

	}

	public void enable(String email, boolean enabled) {
		get(email).setEnabled(enabled);
	}

	public void setPassword(String email, String password) {
		get(email).setPassword(password);
	}

	public Customer get(String email) {
		for (Customer c : customers) {
			if (c.getEmail().equals(email))
				return c;
		}

		return null;
	}
}
