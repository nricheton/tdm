package org.tdm.core;

import java.util.List;
import java.util.Map;

public interface DataFactory {
	
	void create(TestData data, String type, Map<String, String> values); 
	List<String> getTypes();
}
