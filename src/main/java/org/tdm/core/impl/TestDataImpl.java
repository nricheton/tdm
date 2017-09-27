package org.tdm.core.impl;

import java.util.HashMap;
import java.util.Map;

import org.tdm.core.TestData;

public class TestDataImpl implements TestData {

	Map<String, String> data = new HashMap<String, String>();

	public TestData add(String type, String id) {
		data.put(type, id);
		return this;
	}

	public String id(String item) {
		return data.get(item);
	}

}
