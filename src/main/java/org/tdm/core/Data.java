package org.tdm.core;

import java.util.Map;

public interface Data {
	String TYPE_OBJECT = "object";
	String TYPE_SET = "set";

	String getType();
	String getDataName();

	Map<String, Object> getValues();
}
