package org.tdm.core.impl.evaluator;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;

import org.tdm.core.AttributeEvaluator;
import org.tdm.core.Data;
import org.tdm.core.TdmManager;
import org.tdm.core.TestData;

/**
 * Allow to create an object from another dataset : "tdm:from-template" : "myobject" or  "tdm:from-template" : "myobject/dataset"
 * 
 * <p>
 * Only the first object of the dataset is used.
 * 
 * tdm:name
 *
 */
public class TemplateAttributeEvaluator implements AttributeEvaluator {
	private static String TEMPLATE = "tdm:from-template";
	private TdmManager manager = null;

	@Override
	public void setManager(TdmManager manager) {
		this.manager = manager;
	}

	@Override
	public boolean match(Data data) {
		return data.getValues().containsKey(TEMPLATE);
	}

	@Override
	public void evaluate(Data data, TestData testData) {
		String templateName = (String) data.getValues().get(TEMPLATE);
		List<Data> data2;
		try {
			data2 = manager.getObjectFromDataset(templateName, new HashMap<String, Object>());
			if (data2.size() != 1) {
				throw new IllegalArgumentException("No template found, or not a single template");
			}

			data.getValues().putAll(data2.get(0).getValues());
		} catch (IOException e) {
			throw new IllegalArgumentException("No template found.");
		}

	}

}
