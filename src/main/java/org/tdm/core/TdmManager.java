package org.tdm.core;

import java.io.IOException;
import java.util.List;
import java.util.Map;

public interface TdmManager {

	void setDataFactories(List<DataFactory> factories);
	
	void setDatasets( TdmDataset... datasets );
	void init() throws IOException;
	
	TdmBuilder create();
	
	
	TestData create( String[] type , Map<String,Object>[] values) throws IOException; 
	
}
