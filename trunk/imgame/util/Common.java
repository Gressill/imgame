package util;

import java.text.MessageFormat;

/**
 * Common.java
 * 
 * @author zico this file is the common tool.
 */
public class Common {

	public static String formatNumber(double num, String formatStr) {

		MessageFormat mf = new MessageFormat(formatStr); // /{0,number,00}
		Object[] objs = { new Double(num) };
		return mf.format(objs);

	}

	/**
	 * 
	 * push a new value to the end of an array
	 * array[0]----head,array[length-1]---end
	 * 
	 * @param prices
	 * @param newPrice
	 */
	public static void push(double prices[], double priceDiff) {

		for (int i = 0; i < prices.length - 1; i++) {

			prices[i] = prices[i + 1];

		}

		prices[prices.length - 1] = prices[prices.length - 2] + priceDiff;

	}
}
