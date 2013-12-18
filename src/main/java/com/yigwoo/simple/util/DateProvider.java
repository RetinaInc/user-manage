package com.yigwoo.simple.util;

import java.util.Date;

/**
 * A simple date provider class
 * @author YigWoo
 *
 */

public interface DateProvider {
	Date getDate();
	public static final DateProvider DATE_PROVIDER = new CurrentDateProvider();  

	/**
	 * Return the current Date.
	 * @author YigWoo
	 *
	 */
	public static class CurrentDateProvider implements DateProvider {
		@Override
		public Date getDate() {
			return new Date();
		}
	}
}
