package org.tdm.core.impl.evaluator;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.tdm.core.TestData;
import org.tdm.core.ValueEvaluator;

/**
 * Handle runtime evaluation of tdm:date. <p>
 * 
 * <p>Examples
 * <ul>
 * <li>tdm:date:now</li>
 * <li>tdm:date:+1d</li>
 * <li>tdm:date:-5w</li>
 * <li>tdm:date:+6m</li>
 * <li>tdm:date:-1y</li>
 * </ul>
 * @author Nicolas Richeton <nicolas.richeton@gmail.com>
 *
 */
public class DateValueEval implements ValueEvaluator {
	private static String DATE = "tdm:date:";
	private Pattern pattern = Pattern.compile("([\\-\\+])([0-9]+)([dwmy])");

	private SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSXXX");

	public String evaluate(String value, TestData testData) {
		String dateExpression = ((String) value).substring(DATE.length());

		Calendar c = Calendar.getInstance();

		if ("now".equals(dateExpression)) {
			return sdf.format(c.getTime());
		}

		Matcher matcher = pattern.matcher(dateExpression);
		if (matcher.find()) {
			int sign = 1;

			if ("-".equals(matcher.group(1)))
				sign = -1;

			int nb = Integer.parseInt(matcher.group(2));

			int interval = Calendar.DAY_OF_MONTH;
			switch (matcher.group(3)) {
			case "w":
				interval = Calendar.WEEK_OF_YEAR;
				break;

			case "m":
				interval = Calendar.MONTH;
				break;

			case "y":
				interval = Calendar.YEAR;
				break;
			}

			c.add(interval, sign * nb);
			return sdf.format(c.getTime());

		}

		throw new IllegalArgumentException("Unsupported date expression: " + dateExpression);
	}

	public boolean match(String value) {
		return value.startsWith(DATE);
	}

}
