package org.tdm.core.impl;

import java.util.Map;

import org.tdm.core.Data;

public class DataImpl implements Data {
	String type;
	Map<String, String> values;

	public String getType() {
		return type;
	}

	public Map<String, String> getValues() {
		return values;
	}

	public void setType(String type) {
		this.type = type;
	}

	public void setValues(Map<String, String> values) {
		this.values = values;
	}

}
