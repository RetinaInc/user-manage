package com.yigwoo.utils;

import java.util.Date;

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
