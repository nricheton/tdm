package org.tdm.core.impl;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import org.slf4j.LoggerFactory;
import org.tdm.core.Data;
import org.tdm.core.DataFactory;
import org.tdm.core.TdmBuilder;
import org.tdm.core.TdmDataset;
import org.tdm.core.TdmManager;
import org.tdm.core.TestData;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jayway.jsonpath.DocumentContext;
import com.jayway.jsonpath.JsonPath;

public class Manager implements TdmManager {
	List<DataFactory> factories;
	TdmDataset[] datasets;
	ObjectMapper mapper = new ObjectMapper();

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

		String json = mapper.writeValueAsString(data);
		DocumentContext ctx = JsonPath.parse(json);

		for (String key : values.keySet()) {
			ctx.set("$.values." + key, values.get(key));
		}

		data = mapper.readValue(ctx.jsonString(), DataImpl.class);

		logger.info("Dataset {}", data);

		for (DataFactory f : factories) {
			if (f.getTypes().contains(data.getType())) {
				f.create(result, data.getType(), data.getValues());
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
