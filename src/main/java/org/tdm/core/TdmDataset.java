package org.tdm.core;

import java.io.IOException;
import java.util.List;

/**
 * Interface for custom dataset loading. Allows to store dataset your way.
 * 
 * @author Nicolas Richeton <nicolas.richeton@gmail.com>
 *
 */
public interface TdmDataset {

	void init() throws IOException;

	List<Data> getData(String type) throws IOException;
}
