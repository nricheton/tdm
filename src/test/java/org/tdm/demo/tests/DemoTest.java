package org.tdm.demo.tests;

import static org.tdm.core.Maps.map;

import java.io.IOException;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

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
	CustomerService customerService;
	AuthService authServ;
	OrderService orderService;

	@Before
	public void setup() throws JsonParseException, JsonMappingException, IOException {
		// Wiring
		customerService = new CustomerService();
		authServ = new AuthService();
		authServ.setCustomerService(customerService);
		orderService = new OrderService();

		CustomerUnitTestFactory cFact = new CustomerUnitTestFactory();
		cFact.setCustomerService(customerService);

		OrderUnitTestFactory oFact = new OrderUnitTestFactory();
		oFact.setOrderService(orderService);

		tdm = new Manager();
		tdm.setDataFactories(Arrays.asList(new DataFactory[] { cFact, oFact }));
		tdm.setDatasets(new JsonClasspathDataset("/demoDataset"));
		tdm.init();
	}

	/**
	 * Basic usage, no data customization.
	 * 
	 * @throws IOException
	 */
	@Test
	public void test1() throws IOException {
		TestData dataId = tdm.create().with("customer").perform();

		Assert.assertNotNull(customerService.get(dataId.id("customer")));
		Assert.assertTrue(customerService.get(dataId.id("customer")).getFirstName().length() > 0);
		Assert.assertTrue(customerService.get(dataId.id("customer")).getLastName().length() > 0);
	}

	@Test
	public void test2() throws IOException {
		TestData dataId = tdm.create().with("customer/john", map("firstName", "Mike")).perform();

		Assert.assertEquals("Mike", customerService.get(dataId.id("customer")).getFirstName());
	}

	@Test(expected = CredentialException.class)
	public void test3() throws CredentialException, IOException {
		TestData dataId = tdm.create().with("customer", map("enabled", "false", "password", "test")).perform();

		authServ.login(dataId.id("customer"), "test");
	}

	@Test
	public void test4() throws IOException {
		TestData dataId = tdm.create().with("customer", map("firstName", "Mike", "groups", "admin")).perform();

		Assert.assertEquals("Mike", customerService.get(dataId.id("customer")).getFirstName());
	}

	/**
	 * Creating multiple data at once.
	 * 
	 * @throws IOException
	 */
	@Test
	public void test5() throws IOException {
		TestData dataId = tdm.create()//
				.with("customer", map("firstName", "Mike", "groups", "admin"))//
				.with("customer")//
				.with("customer").perform();

		Assert.assertEquals("Mike", customerService.get(dataId.id("customer")).getFirstName());
		Assert.assertNotNull(customerService.get(dataId.id("customer2")));
		Assert.assertNotNull(customerService.get(dataId.id("customer3")));
	}

	/**
	 * Creating data with several lines, linking to objects of previous lines.
	 * 
	 * @throws IOException
	 */
	@Test
	public void test6() throws IOException {
		TestData dataId = tdm.create().with("customer/john").perform();
		dataId.addAll(tdm.create().with("order", map("customer", dataId.id("customer"))).perform());

		Assert.assertEquals(1, orderService.getOrders(dataId.id("customer")).size());
	}

	/**
	 * Ensure we can target a specific object.
	 * 
	 * @throws IOException
	 */
	@Test
	public void test7() throws IOException {
		TestData dataId = tdm.create().with("customer/mikeAdmin").perform();

		Assert.assertEquals("Mike", customerService.get(dataId.id("customer")).getFirstName());
	}

	/**
	 * Ensure any customer can be created (different values returned when creating
	 * 100x the same object)
	 * 
	 * @throws IOException
	 */
	@Test
	public void test8() throws IOException {

		Set<String> set = new HashSet<String>();

		for (int i = 0; i < 100; i++) {
			TestData dataId = tdm.create().with("customer").perform();
			set.add(customerService.get(dataId.id("customer")).getFirstName());
		}

		Assert.assertTrue(set.size() > 1);
	}

	@Test
	public void test9() throws IOException {

		TestData testData = tdm.create()//
				.with("customer", map("address.city", "Honolulu"))//
				.perform();

		Assert.assertEquals("Honolulu", customerService.get(testData.id("customer")).getAddress().getCity());
	}

	/**
	 * Test json "multi" format with multiple objects.
	 * <p>
	 * Also tests relation between objects with <i>tdm:name</i> and <i>tdm:id-of</i>.
	 * 
	 * @throws IOException
	 */
	@Test
	public void testMultiFormat() throws IOException {
		TestData testData = tdm.create().with("customer/withOrders").perform();

		// Access named object
		Assert.assertNotNull(customerService.get(testData.id("cust")));

		// Ensure object relation between customer and orders.
		Assert.assertEquals(3, orderService.getOrders(testData.id("cust")).size());
	}
	
	
	@Test
	public void testMultiFormatReplace() throws IOException {
		TestData testData = tdm.create().with("customer/withOrders",map("firstName", "Mike")).perform();

		// Access named object
		Assert.assertNotNull(customerService.get(testData.id("cust")));
		Assert.assertEquals("Mike", customerService.get(testData.id("cust")).getFirstName());
		
		// Ensure object relation between customer and orders.
		Assert.assertEquals(3, orderService.getOrders(testData.id("cust")).size());
	}
	
	@Test
	public void testMultiFormatTemplate() throws IOException {
		TestData testData = tdm.create().with("customer/withOrders-template").perform();

		// Access named object
		Assert.assertNotNull(customerService.get(testData.id("cust")));

		// Ensure object relation between customer and orders.
		Assert.assertEquals(2, orderService.getOrders(testData.id("cust")).size());
		Assert.assertEquals(1, orderService.getOrders(testData.id("cust2")).size());
	}
	
}
