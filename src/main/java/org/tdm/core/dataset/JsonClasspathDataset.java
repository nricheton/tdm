package org.tdm.core.dataset;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.slf4j.LoggerFactory;
import org.tdm.core.Data;
import org.tdm.core.impl.DataImpl;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * Dataset using json files from classpath.
 * 
 * <p>
 * Layout
 * <ul>
 * <li>dataset.json</li>
 * <ul>
 * <li>&lt;name1&gt;/
 * <ul>
 * <li>data1.json</li>
 * <li>data2.json</li>
 * </ul>
 * </li>
 * <li>&lt;name2&gt;/
 * <ul>
 * <li>data1.json</li>
 * <li>data2.json</li>
 * </ul>
 * </li>
 * </ul>
 * </li>
 * </ul>
 * 
 * @author Nicolas Richeton <nicolas.richeton@gmail.com>
 */
public class JsonClasspathDataset extends AbstractDataset {
	org.slf4j.Logger logger = LoggerFactory.getLogger(JsonClasspathDataset.class);

	String root;
	ObjectMapper mapper;

	public JsonClasspathDataset(String root) throws JsonParseException, JsonMappingException, IOException {
		this.root = root;
		mapper = new ObjectMapper();

	}

	@Override
	protected List<Map<String, Object>> readObjects() throws java.io.IOException {
		// Read index
		List<Map<String, Object>> index = mapper.readValue(
				JsonClasspathDataset.class.getResourceAsStream(root + "/dataset.json"),
				new TypeReference<List<Map<String, Object>>>() {
				});
		return (List<Map<String, Object>>) index;

	}

	@Override
	protected List<Data> readObject(String type, String name) throws IOException {

		String uri = root + "/objects/" + type + "/" + name + ".json";
		logger.info("Read dataset: {}", uri);

		Map<String, Object> dataset = mapper.readValue(JsonClasspathDataset.class.getResourceAsStream(uri),
				new TypeReference<Map<String, Object>>() {
				});

		if (dataset.containsKey("tdm:format") && "multi".equals(dataset.get("tdm:format"))) {
			return loadMultiFormat(type, name, dataset);
		}
		return loadSimpleFormat(type, name, dataset);

	}

	private List<Data> loadMultiFormat(String type, String name, Map<String, Object> dataset) {
		logger.debug("Loading multi dataset: {} {} ... ", type, name);
		dataset.remove("tdm:format");

		List<Data> data = new ArrayList<Data>();

		for (String t : dataset.keySet()) {

			List<Map<String, Object>> objects = (List<Map<String, Object>>) dataset.get(t);

			for (Map<String, Object> obj : objects) {

				DataImpl d = new DataImpl();
				d.setType(t);
				d.setDataName(name);
				d.setValues(obj);
				data.add(d);
			}

		}

		return data;
	}

	private List<Data> loadSimpleFormat(String type, String name, Map<String, Object> dataset) {
		logger.debug("Loading simple dataset: {} {} ... ", type, name);
		dataset.remove("tdm:format");
		DataImpl d = new DataImpl();
		d.setType(type);
		d.setDataName(name);
		d.setValues(dataset);

		List<Data> data = new ArrayList<Data>();
		data.add(d);

		return data;
	}

}
