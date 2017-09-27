package org.tdm.demo.app;

import javax.security.auth.login.CredentialException;

public class AuthService {

	CustomerService CustomerService;

	public void setCustomerService(CustomerService customerService) {
		CustomerService = customerService;
	}

	public Customer login(String email, String password) throws CredentialException {
		Customer c = CustomerService.get(email);
		if (c != null && password.equals(c.getPassword())) {

			if (c.isEnabled())
				return c;
		}

		throw new CredentialException();
	}
}
