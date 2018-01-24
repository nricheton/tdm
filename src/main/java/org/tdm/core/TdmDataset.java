package org.tdm.core;

import java.io.IOException;

public interface TdmDataset {

	void init() throws IOException;

	Data getData(String type) throws IOException;
}
