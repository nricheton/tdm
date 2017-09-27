package org.tdm.core.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.tdm.core.Data;
import org.tdm.core.DataFactory;
import org.tdm.core.TdmDataset;
import org.tdm.core.TdmManager;
import org.tdm.core.TestData;

public class Manager implements TdmManager {
	List<DataFactory> factories;
	TdmDataset dataset;

	public void setDataFactories(List<DataFactory> factories) {
		this.factories = factories;
	}

	public void setDataset(TdmDataset dataset) {
		this.dataset = dataset;
	}

	public TestData create(String type) {
		return create(type, new HashMap<String, String>());
	}

	public TestData create(String type, Map<String, String> values) {
		TestData result = new TestDataImpl();
		Data data = dataset.getData(type);
		data.getValues().putAll(values);

		for (DataFactory f : factories) {
			if (f.getTypes().contains(data.getType())) {
				f.create(result, type, data.getValues());
			}
		}

		return result;
	}

}
