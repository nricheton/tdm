package org.tdm.core;

import java.util.HashMap;
import java.util.Map;

/**
 * Helper class to create Kay/values maps.
 * 
 * 
 * @author Nicolas Richeton <nicolas.richeton@gmail.com>
 *
 */
public class Maps {
	/**
	 * Create a map by associating key/values by pairs : the 2 first parameters will
	 * be the first pair, the 3rd and 4th parameters will be the second pair, ...
	 * 
	 * @param keyvalues
	 * @return
	 */
	public static Map<String, Object> map(String... keyvalues) {
		Map<String, Object> result = new HashMap<String, Object>();
		for (int i = 0; i < keyvalues.length; i = i + 2) {
			result.put(keyvalues[i], keyvalues[i + 1]);
		}
		return result;

	}
}
