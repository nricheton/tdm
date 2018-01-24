package org.tdm.core.impl;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.LoggerFactory;
import org.tdm.core.Data;
import org.tdm.core.DataFactory;
import org.tdm.core.TdmBuilder;
import org.tdm.core.TdmDataset;
import org.tdm.core.TdmManager;
import org.tdm.core.TestData;

public class Manager implements TdmManager {
	List<DataFactory> factories;
	TdmDataset[] datasets;

	org.slf4j.Logger logger = LoggerFactory.getLogger(Manager.class);

	public void setDataFactories(List<DataFactory> factories) {
		this.factories = factories;
	}

	public void setDatasets(TdmDataset... datasets) {
		this.datasets = datasets;
	}

	public void init() throws IOException {
		for (TdmDataset d : datasets) {
			d.init();
		}
	}

	protected TestData create(String type, Map<String, Object> values) throws IOException {
		TestData result = new TestDataImpl();

		// Get data
		Data data = null;
		for (TdmDataset dataset : datasets) {
			data = dataset.getData(type);

			if (data != null)
				break;
		}

		// TODO: Support for hierarchy
		data.getValues().putAll(values);
		logger.info("Dataset {}", data);

		for (DataFactory f : factories) {
			if (f.getTypes().contains(data.getType())) {
				f.create(result, type, data.getValues());
			}
		}

		return result;
	}

	public TdmBuilder create() {
		return new TdmBuilder(this);
	}

	public TestData create(String[] type, Map<String, Object>[] values) throws IOException {

		TestDataImpl data = new TestDataImpl();
		for (int i = 0; i < type.length; i++) {
			data.addAll(create(type[i], values[i]));
		}
		return data;
	}

}
