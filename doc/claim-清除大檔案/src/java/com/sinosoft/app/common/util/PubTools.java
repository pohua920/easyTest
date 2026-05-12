package com.sinosoft.app.common.util;

import java.text.SimpleDateFormat;
import java.util.Date;

public class PubTools {
	/**
	 * 将一个com.sinosoft.utility.string.Date按format类型转化为String
	 * @author 中科软
	 * @param iDate
	 * @param format 如："yyyy-MM-dd HH:mm:ss"; "yyyy-MM-dd"; "yyyy年MM月dd日"等等
	 * @return format类型的字符串
	 */
	public static String getDateToFormat(Date iDate, String format) {
		return new SimpleDateFormat(format).format(iDate);
	}

	public static Double formatDouble(Object s) {
		if (s == null || "".equals(s)) {
			return null;
		}
		double z = Double.parseDouble((String) s);
		return z;
	}

	public static Integer formatInteger(Object s) {
		if (s == null || "".equals(s)) {
			return null;
		}
		double z = Double.parseDouble((String) s);

		return (int) z;
	}
}
