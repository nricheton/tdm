package org.tdm.core.impl.evaluator;

import org.tdm.core.AttributeEvaluator;
import org.tdm.core.Data;
import org.tdm.core.TdmManager;
import org.tdm.core.TestData;

public class NameAttributeEvaluator implements AttributeEvaluator {
	private static String NAME = "tdm:name";
	private TdmManager manager =null;

	@Override
	public void setManager(TdmManager manager) {
		this.manager = manager;
	}

	@Override
	public boolean match(Data data) {
		return data.getValues().containsKey(NAME);
	}

	@Override
	public void evaluate(Data data, TestData testData) {

		data.setDataName((String) data.getValues().get(NAME));
		data.getValues().remove(NAME);

	}

}
