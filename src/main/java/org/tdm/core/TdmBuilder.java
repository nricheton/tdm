package org.tdm.core;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TdmBuilder {
	private final String[] arrayString = {};
	private final Map<String, Object>[] arrayMap = new HashMap[0];

	private List<String> dataNames;
	private List<Map<String, Object>> dataValues;

	private TdmManager manager;
	private TestData data;

	public TdmBuilder(TdmManager manager) {
		this.manager = manager;
		dataNames = new ArrayList<String>();
		dataValues = new ArrayList<Map<String, Object>>();
	}

	public TdmBuilder with(String name, Map<String, Object> values) {
		dataNames.add(name);
		dataValues.add(values);
		return this;
	}

	public TdmBuilder with(String name) {
		dataNames.add(name);
		dataValues.add(new HashMap<String, Object>());
		return this;
	}

	public TdmBuilder use(TestData data) {
		this.data = data;
		return this;
	}

	public TestData perform() throws IOException {
		TestData result = manager.create(dataNames.toArray(arrayString), dataValues.toArray(arrayMap));
		if (data != null) {
			data.addAll(result);
			result = data;
		}
		return result;
	}
}
