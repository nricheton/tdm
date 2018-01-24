package org.tdm.core.impl;

import java.util.Map;

import org.tdm.core.Data;

public class DataImpl implements Data {
	String type;
	String dataName;
	Map<String, Object> values;

	public String getDataName() {
		return dataName;
	}

	public void setDataName(String name) {
		this.dataName = name;
	}

	public String getType() {
		return type;
	}

	public Map<String, Object> getValues() {
		return values;
	}

	public void setType(String type) {
		this.type = type;
	}

	public void setValues(Map<String, Object> values) {
		this.values = values;
	}

	@Override
	public String toString() {
		return "[type=" + type + ", dataName=" + dataName + ", values=" + values + "]";
	}
	

}
