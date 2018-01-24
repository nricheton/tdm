package org.tdm.core.dataset;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import org.tdm.core.Data;
import org.tdm.core.TdmDataset;
import org.tdm.core.impl.DataImpl;

public abstract class AbstractDataset implements TdmDataset {
	protected List<Map<String, Object>> objects;

	public AbstractDataset() {
	}

	protected abstract List<Map<String, Object>> readObjects() throws IOException;

	protected abstract Map<String, Object> readObject(String type, String name) throws IOException;

	public List<Map<String, Object>> getObjects() {
		return objects;
	}

	public void init() throws IOException {
		objects = readObjects();
	}

	public Data getData(String type) throws IOException {

		// Locate object
		for (Map<String, Object> o : getObjects()) {

			if (o.containsKey(type)) {

				// Random object

				List<String> objectList = (List<String>) o.get(type);

				// Get first object
				String objectName = objectList.get(0);

				DataImpl d = new DataImpl();
				d.setType(type);
				d.setDataName(objectName);
				d.setValues(readObject(type, objectName));
				return d;

			}
		}
		return null;
	}
}
