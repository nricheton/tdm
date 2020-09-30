package org.tdm.core;

/**
 * Handler for attribute values => custom behavior
 *  @author Nicolas Richeton <nicolas.richeton@gmail.com>
 */
public interface AttributeEvaluator {

	/**
	 * Check if attribute can be processed by this evaluator
	 * 
	 * @param value
	 * @return true if evaluator can handle attribute
	 */
	public boolean match(Data data);

	/**
	 * Custom handling of attribute
	 * 
	 * @param data
	 * @param attribute
	 * @param testData
	 */
	public void evaluate(Data data, TestData testData);

	public void setManager(TdmManager manager);
}
