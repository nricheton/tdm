package org.tdm.core.dataset;

import java.util.HashMap;
import java.util.Map;

import org.tdm.core.Data;
import org.tdm.core.TdmDataset;
import org.tdm.core.impl.DataImpl;

public class DummyDataset implements TdmDataset {

	public Data getData(String type) {
		DataImpl result = new DataImpl();

		if (type.equals("customer"))
			result.setType("customer");
		Map<String, Object> values = new HashMap<String, Object>();
		values.put("firstName", "John");
		values.put("lastName", "Doe");

		result.setValues(values);
		return result;

	}

	public void init() {
	}

}
