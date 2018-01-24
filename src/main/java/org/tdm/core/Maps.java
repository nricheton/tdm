package org.tdm.core;

import java.util.HashMap;
import java.util.Map;

public class Maps {
	public static Map<String, Object> map(String... keyvalues) {
		Map<String,Object> result = new HashMap<String, Object>();
		for( int i=0; i< keyvalues.length; i=i+2){
			result.put(keyvalues[i], keyvalues[i+1]);
		}
		return result;

	}
}
