package org.tdm.core;

import java.io.IOException;
import java.util.List;
import java.util.Map;

public interface TdmManager {

	/**
	 * Data factories are custom code to create each type of data object in YOUR
	 * application. Usually uses code or services from application under tests.
	 * 
	 * @param factories
	 */
	void setDataFactories(List<DataFactory> factories);

	/**
	 * Data sources used to get data to be created.
	 * 
	 * @param datasets
	 */
	void setDatasets(TdmDataset... datasets);

	/**
	 * Perform tdm initialization.
	 * 
	 * Must be used after setting datasets and data factories, and before first
	 * create call.
	 * 
	 * @throws IOException
	 */
	void init() throws IOException;

	/**
	 * Get a builder to create a or several data objects.
	 * 
	 * @return
	 */
	TdmBuilder create();

	TestData create(String[] type, Map<String, Object>[] values) throws IOException;

	List<Data> getObjectFromDataset(String type, Map<String, Object> values) throws IOException;
}
