package org.tdm.demo.app;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class OrderService {

	private Map<String, List<Order>> orders = new HashMap<String, List<Order>>();
	private int orderCount = 0;

	public Order create(String customer, String product, int count, Date dateOfPurchase) {

		orderCount++;
		Order c = new Order();
		c.setId(orderCount);
		c.setCount(count);
		c.setDateOfPurchase(dateOfPurchase);
		c.setProduct(product);

		if (!orders.containsKey(customer)) {
			orders.put(customer, new ArrayList<Order>());
		}
		orders.get(customer).add(c);

		return c;

	}

	public List<Order> getOrders(String customer) {
		return orders.get(customer);
	}
}
