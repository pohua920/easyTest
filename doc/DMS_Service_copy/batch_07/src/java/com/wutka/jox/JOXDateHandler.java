package com.wutka.jox;

import java.text.DateFormat;
import java.text.SimpleDateFormat;

/**
 * JOXDateHandler handles date parsing and formatting. Set system property
 * com.oce.wutka.dateFormat to control default date parsing.
 * 建议：
 * "yyyy-MM-dd'T'HH:mm:ss.SSSZ"  2001-07-04T12:08:56.235-0700  
 * 
 * @see java.text.SimpleDateFormat
 * @version 2.0 7007-01-19 李子扬 check ok!
 */
class JOXDateHandler {
	//final static String DATE_FORMAT = "dd-MM-yyyy hh:mm:ss z";
	final static String DATE_FORMAT = "yyyy-MM-dd HH:mm:ss";

	static DateFormat determineDateFormat() {
		String dateFormat = System.getProperty("com.oce.wutka.dateFormat");
		if (dateFormat != null && !dateFormat.equals("")) {
			return new SimpleDateFormat(dateFormat);
		} else {
			// default format includes all necessary fields
			return new SimpleDateFormat(DATE_FORMAT);
		}
	}
}
