package com.sinosoft.undwrt.common.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * 
 * @author sinosoft
 * 
 * @created 2014-3-25
 */
public class DateUtil {

	/** 民國年開始時的西元年. */
	private static final int R_C_START_YEAR = 1911;
	
	public static String toDate(String strDate, String format){
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat(format);
		String rcDate = "";
		try {
			if("".equals(strDate) || null == strDate){
				return rcDate;
			}
			Date date = simpleDateFormat.parse(strDate);
			String dataPrint = simpleDateFormat.format(date);
			String rcyear = getRCYear(date);
			rcDate = format(format, rcyear, dataPrint);
			
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return rcDate;
	}
	
	/**
	 * 日期轉換.
	 * 
	 * @param date
	 *            日期格式
	 * @return 民國日期
	 */
	static String getRCYear(Date date) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		int rcyear = calendar.get(Calendar.YEAR) - R_C_START_YEAR;
		return String.valueOf(rcyear);
	}
	
	/**
	 * 格式化日期格式.
	 * 
	 * @param rcyear
	 *            the rcyear
	 * @param dataPrint
	 *            the data print
	 * @return the string
	 */
	static String format(String format, String rcyear, String dataPrint) {
		int yearPlace = format.lastIndexOf("y") + 1;
		return rcyear + dataPrint.substring(yearPlace);
	}
}
