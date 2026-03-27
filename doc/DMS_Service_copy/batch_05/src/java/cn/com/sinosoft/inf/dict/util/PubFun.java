package cn.com.sinosoft.inf.dict.util;

import java.io.ByteArrayInputStream;
import java.io.UnsupportedEncodingException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.wutka.jox.JOXBeanInputStream;

public class PubFun {
	// public static Date strToDate(String strD) throws Exception {
	// try {
	// String strFormat = "yyyy-MM-dd";
	// SimpleDateFormat sdf = new SimpleDateFormat(strFormat);
	// return sdf.parse(strD);
	// } catch (java.text.ParseException e) {
	// throw new Exception(
	// "Can   not   convert   string   to   date   object:" + strD);
	// }
	// }

	/**
	 * 日期转换成字符串
	 * 
	 * @param date
	 * @return str
	 */
	public static String DateToStr(Date date) {

		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		String str = format.format(date);
		return str;
	}

	/**
	 * 字符串转换成日期
	 * 
	 * @param str
	 * @return date
	 */
	public static Date StrToDate(String str) {

		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		Date date = null;
		try {
			date = format.parse(str);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return date;
	}
	
	public static JOXBeanInputStream generateJox(String xml) throws UnsupportedEncodingException {
		return new JOXBeanInputStream(new ByteArrayInputStream(xml.getBytes("UTF-8")));
	}
}
