package org.tdm.core.impl;

import java.util.HashMap;
import java.util.Map;

import org.tdm.core.TestData;

/**
 * @author Nicolas Richeton <nicolas.richeton@gmail.com>
 *
 */
public class TestDataImpl implements TestData {
	private String[] stringArray = {};

	Map<String, String> data = new HashMap<String, String>();
	Map<String, String> dataAlt = new HashMap<String, String>();

	Map<String, Integer> dataCount = new HashMap<String, Integer>();
	Map<String, String> dataType = new HashMap<String, String>();
	Map<String, String> dataCustomName = new HashMap<String, String>();
	Map<String, String> dataTypeName = new HashMap<String, String>();

	public TestData add(String type, String name, String id) {

		String typeName = type;
		int count = 1;
		if (dataCount.containsKey(type)) {
			count = dataCount.get(type);
			count++;
			typeName = type + count;
		}
		dataCount.put(type, count);

		data.put(typeName, id);
		dataType.put(typeName, type);

		if (name != null) {
			dataCustomName.put(typeName, name);
			dataAlt.put(name, id);
		}

		return this;
	}

	public String id(String item) {

		String result = data.get(item);
		if (result == null)
			result = dataAlt.get(item);

		return result;
	}

	public String customName(String item) {
		return dataCustomName.get(item);
	}

	public String type(String item) {
		return dataType.get(item);
	}

	public TestData addAll(TestData data) {
		for (String i : data.items()) {
			add(data.type(i), data.customName(i), data.id(i));
		}
		return this;
	}

	public String[] items() {
		return data.keySet().toArray(stringArray);
	}

}
