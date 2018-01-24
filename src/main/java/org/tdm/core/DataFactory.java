package org.tdm.core;

import java.io.IOException;
import java.util.List;
import java.util.Map;

public interface DataFactory {
	
	void create(TestData data, String type, Map<String, Object> map) throws IOException; 
	List<String> getTypes();
}
