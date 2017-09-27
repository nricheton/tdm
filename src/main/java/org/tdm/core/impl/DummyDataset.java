package org.tdm.core.impl;

import java.util.HashMap;
import java.util.Map;

import org.tdm.core.Data;
import org.tdm.core.TdmDataset;

public class DummyDataset implements TdmDataset {

	public Data getData(String type) {
		DataImpl result = new DataImpl();
		
		if( type.equals( "customer"))
		result.setType( "customer");
		Map<String,String> values = new HashMap<String, String>();
		values.put( "firstName", "John");
		values.put( "lastName", "Doe");
		
		result.setValues(values);
		return result;
		
	}

}
