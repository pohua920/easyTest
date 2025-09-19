/*
 * Created on 2005/3/12
 * 
 * TODO To change the template for this generated file go to Window -
 * Preferences - Java - Code Style - Code Templates
 */
package com.tlg.util;

import java.io.Serializable;
import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

/**
 * @author Nancy.Su
 * 
 *         TODO To change the template for this generated type comment go to
 *         Window - Preferences - Java - Code Style - Code Templates
 */
public class Commen implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;



	/**
	 * 調整字串，若輸入null則傳回""
	 * 
	 * @param str
	 * @return String
	 */
	public String adjustStrig(String str) throws Exception {
		try {
			if (str == null || str.equals("null")) {
				str = "";
			}
		} catch (Exception e) {
			System.out.println("Commen:adjustStrig:error");
			throw e;
		}
		return str.trim();
	}

	/**
	 * 將西元年2004/01/02轉為民國年93/01/02
	 * 
	 * @param transDate
	 * @return String
	 */
	public String transChinese(String transDate) throws Exception {
		String[] d = transDate.split("/");
		int year = Integer.parseInt(d[0]);
		try {
			year = year - 1911;
			transDate = year + "/" + d[1] + "/" + d[2];
		} catch (Exception e) {
			System.out.println("Commen:transChinese:error");
			throw e;
		}
		return transDate;
	}



	/**
	 * 取得系統民國年月日
	 * 
	 * @return String
	 */
	public String getChineseDate() throws Exception {
		try {
			DecimalFormat df = new DecimalFormat("00");
			Calendar c = Calendar.getInstance();
			int nYear = c.get(Calendar.YEAR) - 1911;
			int nMonth = c.get(Calendar.MONTH);
			int nDay = c.get(Calendar.DAY_OF_MONTH);
			return nYear + "/" + df.format(nMonth + 1) + "/" + df.format(nDay);
		} catch (Exception e) {
			System.out.println("Commen:getChineseDate:error");
			throw e;
		}
	}

	/**
	 * 取得系統民國年月日(yyy/mm/dd)
	 * 
	 * @return String
	 */
	public String getChineseDate2() throws Exception {
		try {
			DecimalFormat df2 = new DecimalFormat("00");
			DecimalFormat df3 = new DecimalFormat("000");
			Calendar c = Calendar.getInstance();
			int nYear = c.get(Calendar.YEAR) - 1911;
			int nMonth = c.get(Calendar.MONTH);
			int nDay = c.get(Calendar.DAY_OF_MONTH);
			return df3.format(nYear) + "/" + df2.format(nMonth + 1) + "/"
					+ df2.format(nDay);
		} catch (Exception e) {
			System.out.println("Commen:getChineseDate2:error");
			throw e;
		}
	}

	/**
	 * 將輸入的date轉為民國年
	 * 
	 * @param date
	 * @return String
	 */
	public String getChineseDate(java.util.Date date) throws Exception {
		String res = "";
		try {
			if (date != null) {
				DateFormat dateformat = DateFormat.getDateInstance(
						DateFormat.FULL, Locale.TAIWAN);
				dateformat = new SimpleDateFormat("/MM/dd");
				Calendar cal = Calendar.getInstance();
				cal.setTime(date);
				int nYear = cal.get(Calendar.YEAR) - 1911;
				res = nYear + dateformat.format(date);
			}
		} catch (Exception e) {
			System.out.println("Commen:getChineseDate:error");
			throw e;
		}
		return res;
	}

	/**
	 * 將輸入的date轉為民國年時分秒
	 * 
	 * @param date
	 * @return String
	 */
	public String getChineseDateTime(java.util.Date date) throws Exception {
		String res = "";
		try {
			if (date != null) {
				DateFormat dateformat = DateFormat.getDateInstance(
						DateFormat.FULL, Locale.TAIWAN);
				dateformat = new SimpleDateFormat("/MM/dd HH:mm:ss");
				Calendar cal = Calendar.getInstance();
				cal.setTime(date);
				int nYear = cal.get(Calendar.YEAR) - 1911;
				res = nYear + dateformat.format(date);
			}
		} catch (Exception e) {
			System.out.println("Commen:getChineseDateTime:error");
			throw e;
		}
		return res;
	}

	/**
	 * 取得系統西元年月日
	 * 
	 * @return String
	 */
	public String getDate() throws Exception {
		try {
			DecimalFormat df = new DecimalFormat("00");
			Calendar c = Calendar.getInstance();
			int nYear = c.get(Calendar.YEAR);
			int nMonth = c.get(Calendar.MONTH);
			int nDay = c.get(Calendar.DAY_OF_MONTH);
			return nYear + "/" + df.format(nMonth + 1) + "/" + df.format(nDay);
		} catch (Exception e) {
			System.out.println("Commen:getDate:error");
			throw e;
		}
	}

	/**
	 * 取得系統西元年月日
	 * 
	 * @return String
	 */
	public String getDate(String pattern) throws Exception {
		DecimalFormat df = new DecimalFormat("00");
		Calendar c = Calendar.getInstance();
		int nYear = c.get(Calendar.YEAR);
		int nMonth = c.get(Calendar.MONTH);
		int nDay = c.get(Calendar.DAY_OF_MONTH);
		try {
			if (pattern.equals("MM/DD")) {
				return df.format(nMonth + 1) + "/" + df.format(nDay);
			} else if (pattern.equals("-")) {
				return nYear + "-" + df.format(nMonth + 1) + "-"
						+ df.format(nDay);
			} else {
				return nYear + "/" + df.format(nMonth + 1) + "/"
						+ df.format(nDay);
			}
		} catch (Exception e) {
			System.out.println("Commen:getDate:error");
			throw e;
		}
	}

	/**
	 * 取得系統時間 時：分：秒
	 * 
	 * @return String
	 */
	public String getTime() throws Exception {
		try {
			DecimalFormat df = new DecimalFormat("00");
			Calendar c = Calendar.getInstance();
			int nHour = c.get(Calendar.HOUR_OF_DAY);
			int nMin = c.get(Calendar.MINUTE);
			int nSec = c.get(Calendar.SECOND);
			return df.format(nHour) + ":" + df.format(nMin) + ":"
					+ df.format(nSec);
		} catch (Exception e) {
			System.out.println("Commen:getTime:error");
			throw e;
		}
	}

	/**
	 * 將字串src裡有originalString轉換成newString
	 * 
	 * @param src
	 * @param originalString
	 * @param newString
	 * @return String
	 */
	public String replaceAll(String src, String originalString, String newString)
			throws Exception {
		try {
			if (!src.trim().equals("")) {
				StringBuffer sb = new StringBuffer();
				int index = 0;

				do {
					index = src.indexOf(originalString);
					if (index == -1) {
						sb.append(src);
					} else {
						sb.append(src.substring(0, index));
						sb.append(newString);
						src = src.substring(index + originalString.length());
					}
				} while (index != -1);
				return sb.toString();
			}
		} catch (Exception e) {
			System.out.println("Commen:replaceAll:error");
			throw e;
		}
		return "";

	}

	/**
	 * 比較輸入dateA日在dateB之前或之後
	 * 
	 * @param dateA
	 * @param dateB
	 * @return String
	 */
	public String compareDate(String dateA, String dateB) throws Exception {
		String desc = "";
		String[] date1 = dateA.split("/");
		String[] date2 = dateB.split("/");
		Calendar a = Calendar.getInstance();
		Calendar b = Calendar.getInstance();
		try {
			if (date1.length == 3 && date2.length == 3) {
				a.set(Integer.parseInt(date1[0]),
						Integer.parseInt(date1[1]) - 1, Integer
								.parseInt(date1[2]));
				b.set(Integer.parseInt(date2[0]),
						Integer.parseInt(date2[1]) - 1, Integer
								.parseInt(date2[2]));
				if (a.before(b))
					desc = "before";
				if (a.after(b))
					desc = "after";
				if (!a.before(b) && !a.after(b))
					desc = "equal";
			}
		} catch (Exception e) {
			System.out.println("Commen:compareDate:error");
			throw e;
		}
		return desc;
	}

	/**
	 * 四捨五入
	 * 
	 * @param val
	 * @param scale
	 * @return String
	 * @throws Exception
	 */
	public double round(double v, int scale) throws Exception {
		try {
			if (scale < 0) {
				throw new IllegalArgumentException(
						"The scale must be a positive integer or zero");
			}
			BigDecimal b = new BigDecimal(Double.toString(v));
			BigDecimal one = new BigDecimal("1");
			return b.divide(one, scale, BigDecimal.ROUND_HALF_UP).doubleValue();

		} catch (Exception e) {
			System.out.println("Commen:round:error");
			throw e;
		}
	}


	/**
	 * 將傳入的Date轉為String
	 * 
	 * @param date
	 * @return String
	 */
	public String getDateString(java.util.Date date) {
		String res = "";
		if (date != null) {
			DateFormat dateformat = DateFormat.getDateInstance(DateFormat.FULL,
					Locale.TAIWAN);
			dateformat = new SimpleDateFormat("/MM/dd");
			Calendar cal = Calendar.getInstance();
			cal.setTime(date);
			int nYear = cal.get(Calendar.YEAR);
			res = nYear + dateformat.format(date);
		}
		return res;
	}

	public String checkSingleQuote2(String strInput) {
		return strInput;
	}

//	/**
//	 * 檢查傳入的字串是否含有單引號,如果有,就用兩個單引號取
//	 * 
//	 * @param strInput
//	 * @return
//	 */
//	public String checkSingleQuote(String strInput) {
//		String strOutput = "";
//		int checkSingleQuote = -1;
//		// if(strInput == null || "".equals(strInput.trim()) ||
//		// "null".equals(strInput.toLowerCase()))
//		if (strInput == null || "".equals(strInput.trim()))
//			return "";
//
//		do {
//			checkSingleQuote = strInput.indexOf("'");
//			if (checkSingleQuote != -1) {
//				strOutput += strInput.substring(0, strInput.indexOf("'"))
//						+ "''";
//				strInput = strInput.substring(strInput.indexOf("'") + 1);
//			} else {
//				strOutput += strInput;
//			}
//		} while (checkSingleQuote != -1);
//		return strOutput;
//	}

	/**
	 * 把文字轉成unicode
	 * 
	 * @param strDataString
	 * @return
	 */
	public String unicodeReturn(String strDataString) {
		StringBuffer result = new StringBuffer();
		char charData[] = strDataString.toCharArray();
		int data = 0;
		for (int i = 0; i < charData.length; i++) {
			data = charData[i];
			result.append("&#" + data + ";");
		}
		return result.toString();
	}

	// 取得民國或西元格式星期資訊
	// date 096/10/10 or 2008/10/10
	public String getWeekDay(String date) {
		String[] weekStr = { "", "日", "一", "二", "三", "四", "五", "六" };
		String[] newDate = date.split("/");
		Calendar c = Calendar.getInstance();
		int year = Integer.parseInt(newDate[0]);
		if (year < 1000)
			year += 1911;
		c.set(year, Integer.parseInt(newDate[1]) - 1, Integer
				.parseInt(newDate[2]));
		int week = c.get(Calendar.DAY_OF_WEEK);
		return weekStr[week];
	}

	public int getWeekDayInt(String date) {
		String[] newDate = date.split("/");
		Calendar c = Calendar.getInstance();
		int year = Integer.parseInt(newDate[0]);
		if (year < 1000)
			year += 1911;
		c.set(year, Integer.parseInt(newDate[1]) - 1, Integer
				.parseInt(newDate[2]));
		int week = c.get(Calendar.DAY_OF_WEEK);
		if (week == 1)
			week = 7;
		else
			week--;
		return week;
	}

	public String getChineseIntegerStr(String num) {
		StringBuffer sb = new StringBuffer();
		String[] numStr = { "", "一", "二", "三", "四", "五", "六", "七", "八", "九" };
		String[] unitStr = { "千", "百", "十", "兆", "千", "百", "十", "億", "千", "百",
				"十", "萬", "千", "百", "十", "" };
		boolean billonBool = false;
		boolean tenThoudBool = false;
		boolean preZeroBool = false;
		int charIndex = 0;

		int cutIndex = -1;
		for (int i = 0; i < num.length(); i++) {
			String temp = num.substring(i, i + 1);
			if (temp.equals("0")) {
				cutIndex = i;
			} else {
				break;
			}
		}

		if (cutIndex != -1) {
			num = num.substring(cutIndex + 1);
		}

		for (int i = num.length() - 1; i >= 0; i--, charIndex++) {
			int numInt = Integer
					.parseInt(String.valueOf(num.charAt(charIndex)));

			if (i <= 12 && i >= 8 && numInt != 0)
				billonBool = true;

			if (i <= 7 && i >= 4 && numInt != 0)
				tenThoudBool = true;

			if (i == 8 && billonBool) {
				if (preZeroBool)
					sb.setLength(sb.length() - 1);
				sb.append(numStr[numInt] + unitStr[16 - 1 - i]);
				preZeroBool = false;
			} else if (i == 4 && tenThoudBool) {
				if (preZeroBool)
					sb.setLength(sb.length() - 1);
				sb.append(numStr[numInt] + unitStr[16 - 1 - i]);
				preZeroBool = false;
			} else if (numInt == 0 && !preZeroBool) {
				sb.append("零");
				preZeroBool = true;
			} else if (numInt == 0 && preZeroBool) {
				sb.append("");
				preZeroBool = true;
			} else {
				if (num.length() == 2 && i == 1 && num.charAt(0) == '1') {
					sb.append(unitStr[16 - 1 - i]);
				} else {
					sb.append(numStr[numInt] + unitStr[16 - 1 - i]);
				}
				preZeroBool = false;
			}
		}

		if (preZeroBool) {
			sb.setLength(sb.length() - 1);
		}

		return sb.toString();
	}

	/**
	 * 日期相減 傳入日期格式2008/08/01
	 * 
	 * @param date1
	 * @param date2
	 * @return
	 * @throws Exception
	 */
	public long twoDateSub(String date1, String date2) throws Exception {
		long daySub = 0;
		try {
			if ("".equals(this.adjustStrig(date1))) {
				return 0;
			}
			if ("".equals(this.adjustStrig(date2))) {
				return 0;
			}
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
			Date d1 = sdf.parse(date1);
			Date d2 = sdf.parse(date2);
			Calendar calendar = Calendar.getInstance();
			calendar.setTime(d1);
			long d1Time = calendar.getTimeInMillis();
			calendar.setTime(d2);
			long d2Time = calendar.getTimeInMillis();
			daySub = (d2Time - d1Time) / (1000 * 60 * 60 * 24);
		} catch (Exception e) {
			System.out.println("Commen:getTheEndDay:error");
			throw e;
		}
		return daySub;
	}

	/**
	 * 提供精碓的加法運算。
	 * 
	 * @param v1
	 *            被加數
	 * @param v2
	 *            加數
	 * @return 兩個參數的和
	 */
	public double add(double v1, double v2) {

		BigDecimal b1 = new BigDecimal(Double.toString(v1));
		BigDecimal b2 = new BigDecimal(Double.toString(v2));
		return b1.add(b2).doubleValue();

	}

	/**
	 * 提供精碓的減法運算。
	 * 
	 * @param v1
	 *            被減數
	 * @param v2
	 *            減數
	 * @return 兩個參數的差
	 */
	public double sub(double v1, double v2) {

		BigDecimal b1 = new BigDecimal(Double.toString(v1));
		BigDecimal b2 = new BigDecimal(Double.toString(v2));
		return b1.subtract(b2).doubleValue();

	}

	/**
	 * 提供精碓的乘法運算。
	 * 
	 * @param v1
	 *            被乘數
	 * @param v2
	 *            乘數
	 * @return 兩個參數的乘積
	 */
	public double mul(double v1, double v2) {

		BigDecimal b1 = new BigDecimal(Double.toString(v1));
		BigDecimal b2 = new BigDecimal(Double.toString(v2));
		return b1.multiply(b2).doubleValue();

	}

	/**
	 * 提供（相對）精碓的除法運算。當發生除不盡的情?時，由scale參數指 定精度，以後的數字四捨五入。
	 * 
	 * @param v1
	 *            被除數
	 * @param v2
	 *            除數
	 * @param scale
	 *            表示需要精確到小數點以後幾位。
	 * @return 兩個參數的的商
	 */
	public double div(double v1, double v2, int scale) {

		if (scale < 0) {
			throw new IllegalArgumentException(
					"The scale must be a positive integer or zero");
		}

		BigDecimal b1 = new BigDecimal(Double.toString(v1));
		BigDecimal b2 = new BigDecimal(Double.toString(v2));
		return b1.divide(b2, scale, BigDecimal.ROUND_HALF_UP).doubleValue();

	}

	/**
	 * @Input 民國年月如後例中，例： 097/02
	 * @OutPut 當月份的最後一天，例： 097/02/29
	 * @param date
	 * @return
	 */
	public String getTheEndDay(String date) throws Exception {
		String newDate = "";
		try {
			if (date != null && !"".equals(date) && date.length() == 6
					&& date.split("/").length == 2) {
				int year = Integer.valueOf(date.split("/")[0]).intValue();
				int month = Integer.valueOf(date.split("/")[1]).intValue();
				if (month == 1 || month == 3 || month == 5 || month == 7
						|| month == 8 || month == 10 || month == 12) {
					return date + "/31";
				} else if (month == 4 || month == 6 || month == 9
						|| month == 11) {
					return date + "/30";
				} else if (month == 2) {
					if (year % 4 == 0) {
						return date + "/29";
					} else {
						return date + "/28";
					}
				}
			}
		} catch (Exception e) {
			System.out.println("Commen:getTheEndDay:error");
			throw e;
		}
		return newDate;
	}

	/**
	 * @Input 民國年月,加減量 例： 097/02
	 * @OutPut 新日期 例： 098/03
	 * @param date
	 * @return
	 */
	public String getMonthAdd(String date, int add) throws Exception {
		String newDate = "";
		try {
			int year = Integer.parseInt(date.split("/")[0]);
			int month = Integer.parseInt(date.split("/")[1]);

			month = month + add;

			while (month > 12) {
				month = month - 12;
				year = year + 1;
			}

			String yearStr = String.valueOf(year);
			String monthStr = String.valueOf(month);

			while (yearStr.length() < 3) {
				yearStr = "0" + yearStr;
			}
			while (monthStr.length() < 2) {
				monthStr = "0" + monthStr;
			}

			newDate = yearStr + "/" + monthStr;

		} catch (Exception e) {
			System.out.println("Commen:getMonthAdd:error");
			throw e;
		}
		return newDate;
	}

}