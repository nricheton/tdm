# tdm - Test Data Manager


For tests like this : 

	/**
	 * Basic usage, no data customization.
	 * 
	 * @throws IOException
	 */
	@Test
	public void test1() throws IOException {
    		// Create a customer, doesn't matter
		TestData dataId = tdm.create().with("customer").perform();

   		 // Test ensure a a customer exists in the application, with data (customer service)
		Assert.assertNotNull(cServ.get(dataId.id("customer")));
		Assert.assertTrue(cServ.get(dataId.id("customer")).getFirstName().length() > 0);
		Assert.assertTrue(cServ.get(dataId.id("customer")).getLastName().length() > 0);
	}

	@Test
	public void test2() throws IOException {
  		  // Create a customer, any data but ensure firstName is Mike, this is important for the following test
		TestData dataId = tdm.create().with("customer", map("firstName", "Mike")).perform();

		    // Test has a condition triggered by the firstName. Here we just check the value. 
		Assert.assertEquals("Mike", cServ.get(dataId.id("customer")).getFirstName());
	}
 
  
Data are based on logical objects, not technical implementation, expressed by json or project specific language (plug your data provider).
  

	{  
		"firstName" : "John",
		"lastName" : "Doe",
		"enabled" : true,  
		"password" : "password", 
		"groups" : [ "user" ]
	}
	

Daja injection is up to you : plug your own code to call classes, remote soap/rest services, browser GUI to create each type of object. 

	public class OrderUnitTestFactory implements DataFactory {

		OrderService orderService;

		public void setOrderService(OrderService orderService) {
			this.orderService = orderService;
		}

		public List<String> getTypes() {
			return asList(new String[] { "order" });
		}

		public void create(TestData data, String type, Map<String, Object> values) throws IOException {
			SimpleDateFormat sdf = new SimpleDateFormat("YYY-MM-DD");
			Order c;
			try {
				c = orderService.create((String) values.get("customer"), (String) values.get("product"),
						((Integer) values.get("count")).intValue(), sdf.parse((String) 	values.get("dateOfPurchase")));
			} catch (ParseException e) {
				throw new IOException(e);
			}

			data.add(type, String.valueOf(c.getId()));
		}

	}

