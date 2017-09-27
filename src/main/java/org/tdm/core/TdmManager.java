package org.tdm.core;

import java.util.List;
import java.util.Map;

public interface TdmManager {

	void setDataFactories(List<DataFactory> factories);
	
	void setDataset( TdmDataset dataset );
	
	TestData create( String type ); 
	TestData create( String type , Map<String,String> values); 
	
}
