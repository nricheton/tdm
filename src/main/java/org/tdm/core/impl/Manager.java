package org.tdm.core.impl;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import org.slf4j.LoggerFactory;
import org.tdm.core.AttributeEvaluator;
import org.tdm.core.Data;
import org.tdm.core.DataFactory;
import org.tdm.core.TdmBuilder;
import org.tdm.core.TdmDataset;
import org.tdm.core.TdmManager;
import org.tdm.core.TestData;
import org.tdm.core.ValueEvaluator;
import org.tdm.core.impl.evaluator.DateValueEval;
import org.tdm.core.impl.evaluator.IdOfValueEval;
import org.tdm.core.impl.evaluator.NameAttributeEvaluator;
import org.tdm.core.impl.evaluator.TemplateAttributeEvaluator;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jayway.jsonpath.DocumentContext;
import com.jayway.jsonpath.JsonPath;

/**
 * Test Data Manager main implementation.
 * 
 *
 */
public class Manager implements TdmManager {
	org.slf4j.Logger logger = LoggerFactory.getLogger(Manager.class);

	// Handlers for tdm: attributes
	List<AttributeEvaluator> attributeEvaluators = Arrays
			.asList(new AttributeEvaluator[] { new TemplateAttributeEvaluator(), new NameAttributeEvaluator() });
	// Handlers for tdm: values
	List<ValueEvaluator> valueEvaluators = Arrays
			.asList(new ValueEvaluator[] { new IdOfValueEval(), new DateValueEval() });

	List<DataFactory> factories;
	TdmDataset[] datasets;
	ObjectMapper mapper = new ObjectMapper();

	public void setDataFactories(List<DataFactory> factories) {
		this.factories = factories;
	}

	public void setDatasets(TdmDataset... datasets) {
		this.datasets = datasets;
	}

	public void init() throws IOException {

		for (AttributeEvaluator e : attributeEvaluators) {
			e.setManager(this);
		}

		for (TdmDataset d : datasets) {
			d.init();
		}
	}

	public List<Data> getObjectFromDataset(String type, Map<String, Object> values) throws IOException {
		logger.info("Looking for dataset: {}", type);

		// Get data
		List<Data> dataList = null;
		for (TdmDataset dataset : datasets) {
			dataList = dataset.getData(type);

			if (dataList != null)
				break;
		}


	
		
		if (dataList == null)
			throw new IllegalArgumentException("Dataset not found: type");
		
		String json = mapper.writeValueAsString(dataList.get(0));
		DocumentContext ctx = JsonPath.parse(json);

		for (String key : values.keySet()) {
			ctx.set("$.values." + key, values.get(key));
		}



		dataList.set(0, mapper.readValue(ctx.jsonString(), DataImpl.class));

		return dataList;
	}

	public TestData create(String type, Map<String, Object> values) throws IOException {
		TestData result = new TestDataImpl();

		List<Data> dataList = getObjectFromDataset(type, values);

		logger.info("Dataset {}", dataList);
		for (Data data : dataList) {
			evaluate(data, result);

			for (DataFactory f : factories) {
				if (f.getTypes().contains(data.getType())) {
					f.create(result, data.getType(), data.getDataName(), data.getValues());
				}
			}
		}

		return result;
	}

	/**
	 * Perform data value evaluation with all {@link ValueEvaluator}
	 * 
	 * @param data
	 * @param result
	 */
	private void evaluate(Data data, TestData result) {

		for (AttributeEvaluator e : attributeEvaluators) {
			if (e.match(data)) {
				e.evaluate(data, result);
			}
		}

		for (String key : data.getValues().keySet()) {
			Object value = data.getValues().get(key);

			// Only eval strings
			if (value instanceof String) {
				for (ValueEvaluator e : valueEvaluators) {
					if (e.match((String) value)) {
						data.getValues().put(key, e.evaluate((String) value, result));
					}

				}
			}

		}

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
