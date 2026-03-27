package com.sinosoft.claim.common.util;

/****************************************************************************
 * DESC       ：字符串相关处理工具类
 * AUTHOR     ：中科软
 * CREATEDATE ： 2004-08-23
 * MODIFYLIST ：  Name       Date            Reason/Contents
 ****************************************************************************/

import ins.framework.utils.DataUtils;

import java.text.DecimalFormat;
import java.util.StringTokenizer;

import javax.servlet.http.HttpServletRequest;

import com.sinosoft.sysframework.exceptionlog.UserException;

/**
 * 数据类型相关处理工具类 MODIFYLIST ： id Date Reason/Contents zhangshi 20080504
 * convertString方法增加处理=*操作为 like '##%'
 */
public class StringConvert {
	// 标准的货币格式化标准
	private final static DecimalFormat nf = new DecimalFormat("###,##0.00");

	/**
	 * 日期型条件的拼串函数
	 * @param strName 字段名
	 * @param strValue 匹配值
	 * @param strSign 匹配方式
	 * @return 条件字串
	 */
	public static String convertDate(String strName, String strValue, String strSign) throws UserException {
		String strReturn = "";
		String strDbType = "ORACLE";
		if (strValue == null || strValue.equals("")) {
			return "";
		} else {
			if (strDbType.equals("ORACLE")) {
				strReturn = " and " + strName + strSign + "to_date('" + strValue.trim() + "','yyyy-mm-dd') ";
			} else {
				strReturn = " and " + strName + strSign + "'" + strValue.trim() + "' ";
			}
		}
		return strReturn;
	}

	/**
	 * 字符串型条件的拼串函数
	 * @param strName 字段名
	 * @param strValue 匹配值
	 * @param strSign 匹配方式
	 * @return 条件字串
	 */
	public static String convertString(String strName, String strValue, String strSign) {
		String strReturn = "";
		if (strSign == null) {
			strSign = "=";
		}
		if (strValue == null || strValue.trim().equals("")) {
			return "";
		} else {
			if (strSign.equals("=")) {
				strReturn = " and " + strName + "='" + strValue.trim() + "' ";
			} else if (strSign.equals("=*")) {
				strReturn = " and " + strName + " like '" + strValue.trim() + "%' ";
			} else {
				strValue = replace(strValue, "*", "%");
				strReturn = " and " + strName + " like '%" + strValue.trim() + "%' ";
			}
			return strReturn;
		}
	}

	/**
	 * 字符串 转换成HTML格式
	 * @param strInValue：传入字符串
	 * @return strOutValue:传入字符串
	 */
	public static String toHTMLFormat(String strInValue) {
		String strOutValue = "";
		char c;
		for (int i = 0; i < strInValue.length(); i++) {
			c = strInValue.charAt(i);
			switch (c) {
			case '<':
				strOutValue += "&lt;";
				break;
			case '>':
				strOutValue += "&gt;";
				break;
			case '\n':
				strOutValue += "<br>";
				break;
			case '\r':
				break;
			case ' ':
				strOutValue += "&nbsp;";
				break;
			default:
				strOutValue += c;
				break;
			}
		}
		return strOutValue;
	}

	/**
	 * 字符串替换函数
	 * @param strMain：原串，strFind：查找字符串，strReplaceWith：替换字符串
	 * @return 替换後的字符串，如果原串为空或者为""，则返回""
	 */
	public static String replace(String strMain, String strFind, String strReplaceWith) {
		String strReturn = "";
		int intStartIndex = 0, intEndIndex = 0;

		if (strMain == null || strMain.equals(""))
			return "";

		while ((intEndIndex = strMain.indexOf(strFind, intStartIndex)) > -1) {
			strReturn = strReturn + strMain.substring(intStartIndex, intEndIndex) + strReplaceWith;
			intStartIndex = intEndIndex + strFind.length();
		}
		strReturn = strReturn + strMain.substring(intStartIndex, strMain.length());
		return strReturn;
	}

	/**
	 * 字符串小时分钟格式化函数
	 * @param strTime 小时分钟
	 * @return 替换後的字符串，如果原串为空或者为""，则返回"00"
	 */
	public static String newString(String strTime) {
		String strReturn = "00";
		if (strTime != null) {
			if (strTime.trim().length() == 0) {
				strReturn = "00";
			}
			if (strTime.trim().length() == 1) {
				strReturn = "0" + strTime.trim();
			}
			if (strTime.trim().length() >= 2) {
				strReturn = strTime.trim();
			}
		}
		return strReturn;
	}

	/**
	 * 规范正确的日期格式
	 * @param strTime 时间
	 * @return 替换後的字符串，"00:00:00"
	 */
	public static String toStandardTime(String strTime) {
		String strReturn = "";
		if (strTime != null) {
			java.util.StringTokenizer t = new StringTokenizer(strTime, ":");
			while (t.hasMoreTokens()) {
				String strTemp = t.nextToken();
				if (strTemp.trim().length() == 1) {
					strTemp = "0" + strTemp.trim();
				} else if (strTemp.trim().length() >= 2) {
					strTemp = strTemp.trim().substring(0, 2);
				} else if (strTemp.trim().length() == 0) {
					strTemp = "00";
				}
				strReturn = strReturn + strTemp + ":";
			}
			if (strReturn.length() == 3) {
				strReturn = strReturn + "00:00";
			} else if (strReturn.length() == 6) {
				strReturn = strReturn + "00";
			} else if (strReturn.length() == 9) {
				strReturn = strReturn.substring(0, 8);
			} else if (strReturn.length() == 0) {
				strReturn = "00:00:00";
			}
		} else {
			strReturn = "00:00:00";
		}
		return strReturn;
	}

	/**
	 * 规范String 格式化成###,###.00的钱的方式
	 * @param strMoney 金额
	 * @return 替换後的字符串，"数值3位，小数2位"
	 */
	public static String formatToMoney(String strMoney) {
		if (strMoney == null || strMoney.equals("")) {
			return "0.00";
		}
		try {
			return formatToMoney(Double.parseDouble(strMoney));
		} catch (Exception e) {
			return strMoney;
		}
	}

	/**
	 * 格式化金额
	 * @param d 金额
	 * @return
	 */
	public static String formatToMoney(double d) {
		try {
			return nf.format(d);
		} catch (Exception e) {
			return String.valueOf(d);
		}
	}

	/**
	 * 字符串打包
	 * @param strInValue：传入字符串
	 * @return strOutValue:传出字符串
	 */
	public static String encode(String strInValue) {
		strInValue = DataUtils.nullToEmpty(strInValue);
		String strOutValue = "";
		char c;

		for (int i = 0; i < strInValue.length(); i++) {
			c = strInValue.charAt(i);
			switch (c) {
			case ':': // hardcode 同Common.js中 NAMEVALUEDELIMITER //域名与域值的分隔符
				strOutValue += "："; // hardcode
				break;
			case '|': // hardcode 同Common.js中 FIELDDELIMITER //域之间的分隔符
				strOutValue += "┃";
				break;
			case '\n':
				strOutValue += "\\n";
				break;
			case '\r':
				strOutValue += "\\r";
				break;
			case '\"':
				strOutValue += "\\\"";
				break;
			// modify by wangliguang20080613 begin
			// reason:应该符合innerHTML的显示方式
			case '\'':
				strOutValue += "\\\'";
				break;
			// modify by wangliguang 200800613 end
			case '\b':
				strOutValue += "\\b";
				break;
			case '\t':
				strOutValue += "\\t";
				break;
			case '\f':
				strOutValue += "\\f";
				break;
			case '\\':
				strOutValue += "\\\\";
				break;
			case '<':
				strOutValue += "\\<";
				break;
			case '>':
				strOutValue += "\\>";
				break;
			default:
				strOutValue += c;
				break;
			}
		}
		return strOutValue;
	}

	/**
	 * 将字符串按照指定的分隔字符进行拆分,返回从指定序号的分隔符到前一个分隔符之间的字符串
	 * @param strMain:主字符串，strDelimiters:分隔符，intSerialNo:分隔符序号
	 * @return 指定序号的分隔符到前一个分隔符之间的字符串,如果没有找到则返回""
	 */
	public static String decodeStr(String strMain, String strDelimiters, int intSerialNo) {
		int posStart = 0;
		int posEnd = 0;
		String strReturn = ""; // 作为返回值的字符串

		if (strMain.length() < strDelimiters.length())
			return ""; // 若主字符串比分隔符串还要短的话,则返回空字符串

		posStart = getPos(strMain, strDelimiters, intSerialNo - 1);
		if (posStart == -1) {
			posStart = 0;
		} else {
			posStart = posStart + strDelimiters.length();
		}

		posEnd = getPos(strMain, strDelimiters, intSerialNo);
		if (posEnd == -1) {
			posEnd = 0;
		}
		strReturn = strMain.substring(posStart, posEnd);
		return strReturn;
	}

	/**
	 * 字符串解包
	 * @param strInValue：传入字符串
	 * @return strOutValue:传入字符串
	 */
	public static String decode(String strInValue) {
		String strOutValue = strInValue;
		strOutValue = replace(strOutValue, "\\n", "\n");
		strOutValue = replace(strOutValue, "\\r", "\r");
		strOutValue = replace(strOutValue, "\\\\", "\\");
		strOutValue = replace(strOutValue, "\\b", "\b");
		strOutValue = replace(strOutValue, "\\t", "\t");
		strOutValue = replace(strOutValue, "\\\"", "\"");
		strOutValue = replace(strOutValue, "\\\'", "\'");
		strOutValue = replace(strOutValue, "\\f", "\f");
		strOutValue = replace(strOutValue, "\\<", "<");
		strOutValue = replace(strOutValue, "\\>", ">");
		return strOutValue;
	}

	/**
	 * 获取子串在主串中出现 n 次的位置
	 * @param strMain:主字符串，strSub:子字符串，intTimes:出现次数
	 * @return 位置值,如果子串在主串中没有出现指定次数,则返回-1
	 */
	public static int getPos(String strMain, String strSub, int intTimes) {
		return getPos(strMain, strSub, 0, intTimes);
	}

	/**
	 * 获取从指定位置开始子串在主串中出现 n 次的位置
	 * @param strMain:主字符串，strSub:子字符串，intStartIndex:起始位置，intTimes:出现次数
	 * @return 位置值,如果从起始位置起子串在主串中没有出现指定次数,则返回-1
	 */
	public static int getPos(String strMain, String strSub, int intStartIndex, int intTimes) {
		int intCounter = 0; // 循环记数
		int intPosition = intStartIndex; // 位置记录
		int intLength = strSub.length(); // 子串长度

		if (intTimes <= 0)
			return -1;

		if (strMain.length() - 1 < intStartIndex)
			return -1;

		if (strSub.equals(""))
			return 0;

		while (intCounter < intTimes) {
			intPosition = strMain.indexOf(strSub, intPosition);
			if (intPosition == -1)
				return -1;

			intCounter++;
			intPosition += intLength;
		}
		return intPosition - intLength;
	}

	/**
	 * 将字符串按照指定的分隔字符进行拆分,返回拆分後的字符串数组
	 * @param strIn:待拆分的字符串，strDelimiters:分隔符
	 * @return 字符串数组
	 */
	public static String[] split(String strIn, String strDelimiters) throws Exception {
		int theIndex = 0;
		String[] arrReturn = null; // 返回值字符串数组
		int intCount = 0; // 数组的大小

		if (strIn == null || strIn.equals("") || strDelimiters == null || strDelimiters.equals("")) {
			return new String[0];
		}

		if (strIn.length() < strDelimiters.length()) {
			return new String[0];
		}

		// 计算字符串有多少个分隔符
		String strTemp = strIn;
		while (strTemp != "" && strTemp != null) {
			theIndex = strTemp.indexOf(strDelimiters);
			if (theIndex == -1) {
				break;
			}
			intCount++;
			strTemp = strTemp.substring(theIndex + strDelimiters.length());
		}
		arrReturn = new String[++intCount];

		// 生成字符串数组
		for (int i = 0; i < intCount - 1; i++) {
			theIndex = strIn.indexOf(strDelimiters);
			arrReturn[i] = strIn.substring(0, theIndex);
			strIn = strIn.substring(theIndex + strDelimiters.length());
		}
		arrReturn[intCount - 1] = strIn;

		return arrReturn;
	}

	/**
	 * 以指定的编码取出request中的参数 主要是为了解决YUI乱码问题
	 * @author 中科软
	 * @param request
	 * @param paramName 参数名称
	 * @param charSet 编码格式
	 * @return
	 * @throws Exception
	 */
	public static String getParam(HttpServletRequest request, String paramName, String charSet) throws Exception {
		String tempCharSet = request.getCharacterEncoding();
		request.setCharacterEncoding(charSet);
		paramName = request.getParameter(paramName);
		request.setCharacterEncoding(tempCharSet);
		if (DataUtils.emptyToNull(paramName) != null) {
			return paramName.trim();
		}
		return "";
	}

}
