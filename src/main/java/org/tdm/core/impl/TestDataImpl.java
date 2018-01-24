package org.tdm.core.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.swing.event.ListSelectionEvent;

import org.tdm.core.TestData;

public class TestDataImpl implements TestData {
	private String[] stringArray = {};

	Map<String, String> data = new HashMap<String, String>();
	Map<String, Integer> dataCount= new HashMap<String, Integer>();

	public TestData add(String type, String id) {
		String typeName = type;
		int count = 1;
		if( dataCount.containsKey(type)) {
			count = dataCount.get(type);
			count++;
			typeName = typeName+count;
		}
		dataCount.put(type, count);
		
		data.put(typeName, id);
		
		return this;
	}

	public String id(String item) {
		return data.get(item);
	}

	public TestData addAll(TestData data) {
		for (String i : data.items()) {
			add(i, data.id(i));
		}
		return this;
	}

	public String[] items() {
		return data.keySet().toArray(stringArray);
	}

}
