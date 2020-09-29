package org.tdm.core;

public interface TestData {
	String id(String item);

	TestData add(String type, String customName, String id);

	TestData addAll(TestData data);

	String[] items();

	String customName(String item);

	String type(String item);
}
