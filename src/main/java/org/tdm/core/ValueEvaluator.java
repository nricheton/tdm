package org.tdm.core;

/**
 * Handler for expression values, resolved at runtime. (Dates, links, ...)
 */
public interface ValueEvaluator {

	/**
	 * Check if value can be processed by this evaluator
	 * @param value
	 * @return true if evaluator can handle expression
	 */
	public boolean match(String value);

	/**
	 * Evaluate expression value and return new value.
	 * 
	 * @param value Value to be evaluated
	 * @param testData Current context
	 * @return Evaluated value.
	 */
	public String evaluate(String value, TestData testData);
}
