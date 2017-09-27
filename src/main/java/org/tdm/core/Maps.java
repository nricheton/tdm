package org.tdm.core;

import java.util.HashMap;
import java.util.Map;

public class Maps {
	public static Map<String, String> map(String... keyvalues) {
		Map<String,String> result = new HashMap<String, String>();
		for( int i=0; i< keyvalues.length; i=i+2){
			result.put(keyvalues[i], keyvalues[i+1]);
		}
		return result;

	}
}
