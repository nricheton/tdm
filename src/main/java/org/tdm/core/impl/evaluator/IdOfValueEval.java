package org.tdm.core.impl.evaluator;

import org.tdm.core.TestData;
import org.tdm.core.ValueEvaluator;

/**
 * tdm:id-of:xxx : Id reference from a generated object.
 * 
 * @author Nicolas Richeton <nicolas.richeton@gmail.com>
 *
 */
public class IdOfValueEval implements ValueEvaluator {

	private static String ID_OF = "tdm:id-of:";

	public String evaluate(String value, TestData testData) {
		String id = ((String) value).substring(ID_OF.length());
		return testData.id(id);
	}

	public boolean match(String value) {
		return value.startsWith(ID_OF);
	}

}
