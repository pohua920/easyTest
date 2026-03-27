package com.sinosoft.app.common.util;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 字符串工具类
 * @Company: sinosig
 * @author: wangyi
 * @Date: 2011-07-28
 */
public class StringUtil extends org.apache.commons.lang.StringUtils {

	/**
	 * 判断字符串是否为null或空串
	 * @param str
	 * @return
	 */
	public static boolean isBlank(final String str) {

		return (str == null) || (str.trim().length() <= 0);
	}

	/**
	 * 判断字符串数祖是否为null或空串
	 * @param str
	 * @return
	 */
	public static boolean isBlank(final String[] str) {
		return (str == null) || (str.length <= 0);
	}

	/**
	 * 重写isNumeric方法，判断字符串是否为数字
	 * @return String
	 */
	public static boolean isNumeric(String str) {
		try {
			Float.parseFloat(str);
		} catch (NumberFormatException ne) {
			return false;
		}
		return true;
	}

	/**
	 * 判断字符串是否可以转换为整数
	 * @param str String
	 * @return boolean
	 */
	public static boolean canIntegerDeal(String str) {
		try {
			Integer.parseInt(str);
		} catch (NumberFormatException ne) {
			return false;
		}
		return true;
	}

	/**
	 * 防止出现空值的情况
	 * @param value String
	 * @return String
	 */
	public static String nullToString(String value) {
		if (value == null || value.trim().equals("null") || value.trim().equals("") ) {
			return "0";
		} else {
			return value;
		}
	}

	/**
	 * 防止出现空值的情况
	 * @param value String
	 * @return String
	 */
	public static String PrtendNull(String value) {
		if (value == null || value.trim().equals("null") || value.trim().equals("") ) {
			return "";
		} else {
			return value.trim();
		}
	}

	/**
	 * 防止出现空值的情况
	 * @param value String
	 * @return String
	 */
	public static String PrtendNullToBlank(String value) {
		if (value == null || value.trim().equals("null") || value.trim().equals("") ) {
			return "&nbsp;";
		} else {
			return value.trim();
		}
	}

	/**
	 * 防止出现空值的情况
	 * @param value String
	 * @return String
	 */
	public static String NullToStrinNull(String value) {
		if (value == null || value.trim().equals("null") || value.trim().equals("") ) {
			return null;
		} else {
			return value;
		}
	}

	// 替换字符串的方法
	public static String strreplace(String source, String oldString, String newString) {
		StringBuffer output = new StringBuffer();

		int lengthOfSource = source.length(); // 源字符串长度
		int lengthOfOld = oldString.length(); // 老字符串长度

		int posStart = 0; // 开始搜索位置
		int pos; // 搜索到老字符串的位置

		while ((pos = source.indexOf(oldString, posStart)) >= 0) {
			output.append(source.substring(posStart, pos));

			output.append(newString);
			posStart = pos + lengthOfOld;
		}

		if (posStart < lengthOfSource) {
			output.append(source.substring(posStart));
		}
		return output.toString();
	}

	// String转Date
	public static Date StringToDate(String str) {
		Date date = null;
		// 只保留日期部分，返回的是java.sql.Date 2007-01-18
		date = java.sql.Date.valueOf(str);
		return date;
	}

	// String转Date
	public static Date simpleStringToDate(String str) {
		SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMdd");
		Date date = null;
		// String转Date
		try {
			date = formatter.parse(str);
		} catch (ParseException e) {
			return new Date();
		}
		return date;
	}

	// String转BigDecimal
	public static BigDecimal StringToBigDecimal(String str) {
		try {
			return new BigDecimal(str);
		} catch (Exception e) {
			return BigDecimal.ZERO;
		}
	}

	// dateTOString
	public static String DateToString(Date date) {
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
		return formatter.format(date);
	}

	// dateTOSimpleString
	public static String DateToSimpleString(Date date) {
		SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMdd");
		return formatter.format(date);
	}

	// 截取字符串
	public static String subStr(String source, String startStr, String endStr) {
		if (source.indexOf(startStr) < 0) {
			return source;
		}
		if (source.indexOf(endStr) < 0) {
			return source;
		}
		int startPos = source.indexOf(startStr);
		int endPos = source.indexOf(endStr) + endStr.length();
		if (startPos > endPos) {
			return source;
		}
		return source.substring(startPos, endPos);

	}

	// 截取<></>中字符串
	public static String subXmlStr(String source, String startStr, String endStr) {
		if (source.indexOf(startStr) < 0) {
			return source;
		}
		if (source.indexOf(endStr) < 0) {
			return source;
		}
		int startPos = source.indexOf(startStr) + startStr.length();
		int endPos = source.indexOf(endStr);
		if (startPos > endPos) {
			return source;
		}
		return source.substring(startPos, endPos);

	}

	// 是否符合规范的输入 不能包含特殊字符
	public static boolean isStandard(String str) {
		// 清除掉所有特殊字符
		String regEx = "[\"`~!@#$%^&*()+=|{}':;',\\[\\]<>/?~！@#￥%……&*（）——+|{}【】‘；：”“’。，、？]";
		Pattern p = Pattern.compile(regEx);
		Matcher m = p.matcher(str);
		return m.find(0);
	}

	// 判断字符串中是否包含小写字母
	public static boolean isLowerCase(String str) {
		Pattern q = Pattern.compile("[a-z]+");
		Matcher m2 = q.matcher(str); // 判断是否含有小写字符
		return m2.find(0);
	}

	// string to int
	public static int stringToInt(String str) {
		try {
			return Integer.parseInt(str);
		} catch (Exception e) {
			return 0;
		}
	}

	// oracle语句查询结果集List转换成String类型，並去掉开头“,”
	public static String listTostring(List<String> strLists) {
		String comCodes = "";
		try {
			for (String newStr : strLists) {
				comCodes = comCodes + ",'" + newStr + "'";
			}
			if (comCodes.startsWith(",")) {
				comCodes = comCodes.substring(1, comCodes.length());
			}
			return comCodes;
		} catch (Exception e) {
			return "";
		}
	}

	public static String listTostr(List<String> strLists) {
		String comCodes = "";
		try {
			for (String newStr : strLists) {
				comCodes = comCodes + "、" + newStr + "";
			}
			if (comCodes.startsWith("、")) {
				comCodes = comCodes.substring(1, comCodes.length());
			}
			return comCodes;
		} catch (Exception e) {
			return "";
		}
	}

	// 转换成字符串並换行
	public static String listTostr2(List<String> strLists) {
		String comCodes = "";
		try {
			for (String newStr : strLists) {
				comCodes = comCodes + newStr + "、<br/>";
			}
			if (comCodes != null) {
				comCodes = comCodes.substring(0, comCodes.lastIndexOf("、"));
			}
			return comCodes;
		} catch (Exception e) {
			return "";
		}
	}

}
