package org.tdm.demo.tests;

import static org.tdm.core.Maps.map;

import java.io.IOException;
import java.util.Arrays;

import javax.security.auth.login.CredentialException;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.tdm.core.DataFactory;
import org.tdm.core.TdmManager;
import org.tdm.core.TestData;
import org.tdm.core.dataset.JsonClasspathDataset;
import org.tdm.core.impl.Manager;
import org.tdm.demo.app.AuthService;
import org.tdm.demo.app.CustomerService;
import org.tdm.demo.app.OrderService;
import org.tdm.demo.factories.CustomerUnitTestFactory;
import org.tdm.demo.factories.OrderUnitTestFactory;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;

public class DemoTest {

	TdmManager tdm;
	CustomerService cServ;
	AuthService authServ;
	OrderService orderServ;

	@Before
	public void setup() throws JsonParseException, JsonMappingException, IOException {
		// Wiring
		cServ = new CustomerService();
		authServ = new AuthService();
		authServ.setCustomerService(cServ);
		orderServ = new OrderService();

		CustomerUnitTestFactory cFact = new CustomerUnitTestFactory();
		cFact.setCustomerService(cServ);

		OrderUnitTestFactory oFact = new OrderUnitTestFactory();
		oFact.setOrderService(orderServ);

		tdm = new Manager();
		tdm.setDataFactories(Arrays.asList(new DataFactory[] { cFact, oFact }));
		tdm.setDatasets(new JsonClasspathDataset("/demoDataset"));
		tdm.init();
	}

	@Test
	public void test1() throws IOException {
		TestData dataId = tdm.create().with("customer").perform();

		Assert.assertNotNull(cServ.get(dataId.id("customer")));
		Assert.assertTrue(cServ.get(dataId.id("customer")).getFirstName().length() > 0);
		Assert.assertTrue(cServ.get(dataId.id("customer")).getLastName().length() > 0);
	}

	@Test
	public void test2() throws IOException {
		TestData dataId = tdm.create().with("customer", map("firstName", "Mike")).perform();

		Assert.assertEquals("Mike", cServ.get(dataId.id("customer")).getFirstName());
	}

	@Test(expected = CredentialException.class)
	public void test3() throws CredentialException, IOException {
		TestData dataId = tdm.create().with("customer", map("enabled", "false", "password", "test")).perform();

		authServ.login(dataId.id("customer"), "test");
	}

	@Test
	public void test4() throws IOException {
		TestData dataId = tdm.create().with("customer", map("firstName", "Mike", "groups", "admin")).perform();

		Assert.assertEquals("Mike", cServ.get(dataId.id("customer")).getFirstName());
	}

	@Test
	public void test5() throws IOException {
		TestData dataId = tdm.create()//
				.with("customer", map("firstName", "Mike", "groups", "admin"))//
				.with("customer")//
				.with("customer").perform();

		Assert.assertEquals("Mike", cServ.get(dataId.id("customer")).getFirstName());
		Assert.assertNotNull(cServ.get(dataId.id("customer2")));
		Assert.assertNotNull(cServ.get(dataId.id("customer3")));

	}

	@Test
	public void test6() throws IOException {
		TestData dataId = tdm.create().with("customer").perform();
		dataId.addAll(tdm.create().with("order", map("customer", dataId.id("customer"))).perform());

		Assert.assertEquals(1, orderServ.getOrders(dataId.id("customer")).size());

	}

	@Test
	public void testMulti() throws IOException {
		TestData dataId = tdm.create().with("customer/withOrders").perform();

		Assert.assertEquals(3, orderServ.getOrders(dataId.id("cust")).size());

	}
}
