package org.tdm.core;

public interface TestData {
	String id(String item);
	TestData add(String type, String id);
	TestData addAll( TestData data);
	String[] items();
	
}
