package org.tdm.core;

import java.util.Map;

/**
 * One data object, with all attributes.
 * 
 * @author Nicolas Richeton <nicolas.richeton@gmail.com>
 *
 */
public interface Data {
	String TYPE_OBJECT = "object";
	String TYPE_SET = "set";

	String getType();

	String getDataName();

	void setDataName(String name);

	Map<String, Object> getValues();
}
