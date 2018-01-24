package org.tdm.core.dataset;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import org.tdm.core.Data;
import org.tdm.core.TdmDataset;
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
 * 	<li>dataset.json</li>
 * 	<li>objects/
 * 		<ul>
 * 			<li>&lt;name1&gt;/
 * 				<ul>
 * 					<li>data1.json</li>
 * 					<li>data2.json</li>
 * 				</ul>
 * 			</li>
 * 			<li>&lt;name2&gt;/
 * 				<ul>
 * 					<li>data1.json</li>
 * 					<li>data2.json</li>
 * 				</ul>
 * 			</li>
 * 		</ul>
 * 	</li>
 * </ul>
 * @author nricheton
 */
public class JsonClasspathDataset extends AbstractDataset {
	String root;
	ObjectMapper mapper;

	public JsonClasspathDataset(String root) throws JsonParseException, JsonMappingException, IOException {
		this.root = root;
		mapper = new ObjectMapper();

	}

	@Override
	protected List<Map<String, Object>> readObjects() throws java.io.IOException {
		// Read index
		Map<String, Object> index = mapper.readValue(
				JsonClasspathDataset.class.getResourceAsStream(root + "/dataset.json"),
				new TypeReference<Map<String, Object>>() {
				});
		return (List<Map<String, Object>>) index.get("objects");

	}

	@Override
	protected Map<String, Object> readObject(String type, String name) throws IOException {
		Map<String, Object> object;

		object = mapper.readValue(
				JsonClasspathDataset.class.getResourceAsStream(root + "/objects/" + type + "/" + name + ".json"),
				new TypeReference<Map<String, Object>>() {
				});

		return object;

	}

}
