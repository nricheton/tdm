package org.tdm.demo.factories;

import static java.util.Arrays.asList;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Map;

import org.tdm.core.DataFactory;
import org.tdm.core.TestData;
import org.tdm.demo.app.Order;
import org.tdm.demo.app.OrderService;

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
					((Integer) values.get("count")).intValue(), sdf.parse((String) values.get("dateOfPurchase")));
		} catch (ParseException e) {
			throw new IOException(e);
		}

		data.add(type, String.valueOf(c.getId()));
	}

}
