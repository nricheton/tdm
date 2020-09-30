package org.tdm.core.dataset;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import org.tdm.core.Data;
import org.tdm.core.TdmDataset;

/**
 * @author Nicolas Richeton <nicolas.richeton@gmail.com>
 *
 */
public abstract class AbstractDataset implements TdmDataset {
	protected List<Map<String, Object>> objects;

	public AbstractDataset() {
	}

	protected abstract List<Map<String, Object>> readObjects() throws IOException;

	protected abstract List<Data> readObject(String type, String name) throws IOException;

	public List<Map<String, Object>> getObjects() {
		return objects;
	}

	public void init() throws IOException {
		objects = readObjects();
	}

	public List<Data> getData(String type) throws IOException {

		String requestedType = type;
		String requestedObject = null;
		if (type.contains("/")) {
			requestedType = type.split("/")[0];
			requestedObject = type.split("/")[1];
		}

		// Locate object
		for (Map<String, Object> o : getObjects()) {

			if (o.containsKey(requestedType)) {

				// Random object
				List<String> objectList = (List<String>) o.get(type);

				if (requestedObject == null) {
					requestedObject = objectList.get((int) (Math.random() * objectList.size()));

				}

				return readObject(requestedType, requestedObject);
			}
		}
		return null;
	}
}
