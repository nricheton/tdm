package org.tdm.core;

import java.io.IOException;
import java.util.List;

public interface TdmDataset {

	void init() throws IOException;

	List<Data> getData(String type) throws IOException;
}
