package org.tdm.core;

import java.io.IOException;
import java.util.List;
import java.util.Map;

/**
 * Interface for custom implementation of data injecting in YOUR test context. 
 * <p>
 * Depending of your context/applications, you can : 
 * <ul>
 * <li>Call your application code (unit tests with embedded db)</li>
 * <li>Create mocks (unit tests with mocking)</li>
 * <li>Call your application services (integration tests)</li>
 * <li>Perform SQL inserts (intergration tests on legacy applications)</li>
 * </ul>
 * @author Nicolas Richeton <nicolas.richeton@gmail.com>
 *
 */
public interface DataFactory {
	
	void create(TestData data, String type, String name, Map<String, Object> map) throws IOException; 
	/**
	 * Supported data types which can be created by this factory
	 * @return
	 */
	List<String> getTypes();
}
