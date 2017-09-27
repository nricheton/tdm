package org.tdm.demo.tests;

import java.util.Arrays;

import javax.security.auth.login.CredentialException;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.tdm.core.DataFactory;
import static org.tdm.core.Maps.*;
import org.tdm.core.TdmManager;
import org.tdm.core.TestData;
import org.tdm.core.impl.DummyDataset;
import org.tdm.core.impl.Manager;
import org.tdm.demo.app.AuthService;
import org.tdm.demo.app.CustomerService;
import org.tdm.demo.factories.CustomerUnitTestFactory;

public class DemoTest {

	TdmManager tdm;
	CustomerService cServ;
	AuthService authServ;

	@Before
	public void setup() {
		// Wiring
		cServ = new CustomerService();
		authServ = new AuthService();
		authServ.setCustomerService(cServ);
		CustomerUnitTestFactory cFact = new CustomerUnitTestFactory();
		cFact.setCustomerService(cServ);

		tdm = new Manager();
		tdm.setDataFactories(Arrays.asList(new DataFactory[] { cFact }));
		tdm.setDataset(new DummyDataset());
	}

	@Test
	public void test1() {
		TestData dataId = tdm.create("customer");

		Assert.assertNotNull(cServ.get(dataId.id("customer")));
		Assert.assertTrue(cServ.get(dataId.id("customer")).getFirstName().length() > 0);
		Assert.assertTrue(cServ.get(dataId.id("customer")).getLastName().length() > 0);
	}

	@Test
	public void test2() {
		TestData dataId = tdm.create("customer", map("firstName", "Mike"));

		Assert.assertEquals("Mike", cServ.get(dataId.id("customer")).getFirstName());
	}

	@Test(expected = CredentialException.class)
	public void test3() throws CredentialException {
		TestData dataId = tdm.create("customer", map("enabled", "false", //
				"password", "test"));

		authServ.login(dataId.id("customer"), "test");
	}

}
