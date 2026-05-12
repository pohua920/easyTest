package com.sinosoft.app.common.util;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.StringTokenizer;

/**
 * 时间工具类
 * @Company: sinosig
 * @author 中科软
 * @Date: 2011-07-28
 */

public class TimeUtil extends org.apache.commons.lang.time.DateUtils {
	/**
	 * 日期的月份增加或减少
	 * @param String 2005-09-09 ; len=5
	 * @return 2006-02-09
	 */
	public static String addMonth(String datestr, int len) {
		StringTokenizer token = new StringTokenizer(datestr, "-");
		int year = Integer.parseInt(token.nextToken());
		int month = Integer.parseInt(token.nextToken());
		int day = Integer.parseInt(token.nextToken());
		Calendar date = Calendar.getInstance();
		month = month + len - 1;
		date.set(year, month, day);
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
		return formatter.format(date.getTime()).toString();
	}

	/**
	 * 获取当前日期 yyyy-MM-dd HH:mm:ss
	 * @return String
	 */
	public static String getToday() {
		Calendar cal = Calendar.getInstance();
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String mDateTime = formatter.format(cal.getTime());
		return mDateTime;
	}

	/**
	 * 获取当前日期 yyyyMMddHHmmssSSS
	 * @return String
	 */
	public static String getCurrentTime() {
		Calendar cal = Calendar.getInstance();
		SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMddHHmmssSSS");
		String mDateTime = formatter.format(cal.getTime());
		return mDateTime;
	}

	/**
	 * 获取当前日期 yyyyMMddHHmmss
	 * @return String
	 */
	public static String getCurrent() {
		Calendar cal = Calendar.getInstance();
		SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMddHHmmss");
		String mDateTime = formatter.format(cal.getTime());
		return mDateTime;
	}

	/**
	 * 获取当前日期 yyyy-MM-dd HH:mm:ss
	 * @return String
	 */
	public static String getTime() {
		Calendar cal = Calendar.getInstance();
		SimpleDateFormat formatter = new SimpleDateFormat("HH:mm:ss");
		String mDateTime = formatter.format(cal.getTime());
		return mDateTime;
	}

	/**
	 * 获取当前日期 yyyy-MM-dd HH:mm:ss
	 * @return String
	 */
	public static String getDate() {
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
		return formatter.format(new Date());
	}

	/**
	 * 获取当前日期 yyyyMMdd
	 * @return String
	 */
	public static String getSimpleDate() {
		SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMdd");
		return formatter.format(new Date());
	}

	/**
	 * 获取当前日期年 yyyy
	 * @return String
	 */
	public static String getCurrentYear() {
		String time = getToday();
		return time.substring(0, time.indexOf("-"));
	}

	/**
	 * 获取当前日期月 MM
	 * @return String
	 */
	public static String getCurrentMonth() {
		String time = getSimpleDate();
		return time.substring(4, 6);
	}

	/**
	 * 获取当前日期的上一年 yyyy
	 * @return String
	 */
	public static String getPreviousYear() {
		Calendar calendar = Calendar.getInstance();
		calendar.add(Calendar.YEAR, -1); // 得到上一年的日期
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy");
		return formatter.format(calendar.getTime());
	}

	/**
	 * 获取当前日期的上一个月 yyyyMMdd
	 * @return String
	 */
	public static String getPreviousMothDate() {
		Calendar calendar = Calendar.getInstance();
		calendar.add(Calendar.MONTH, -1); // 得到上一个月的日期
		SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMdd");
		return formatter.format(calendar.getTime());
	}

	/**
	 * 获取当前日期的上一个日 yyyyMMdd
	 * @return String
	 */
	public static String getPreviousDate() {
		Calendar calendar = Calendar.getInstance();
		calendar.add(Calendar.DATE, -1); // 得到上一个日的日期
		SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMdd");
		return formatter.format(calendar.getTime());
	}

	// 当前日期的第二天
	public static String getNextDayDate() {
		Calendar calendar = Calendar.getInstance();
		calendar.add(Calendar.DATE, +1); // 得到後一天
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
		return formatter.format(calendar.getTime());
	}

	// 当前日期的过了一年
	public static String getNextYearDate() {
		Calendar calendar = Calendar.getInstance();
		calendar.add(Calendar.YEAR, +1); // 得到过了一年的日期
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
		return formatter.format(calendar.getTime());
	}

	/**
	 * 日期转换为大写
	 * @author 中科软
	 * @param String date：日期
	 * @return String
	 * @throws
	 */
	public static String dataToUpper(Date date) {
		Calendar ca = Calendar.getInstance();
		ca.setTime(date);
		int year = ca.get(Calendar.YEAR);
		int month = ca.get(Calendar.MONTH) + 1;
		int day = ca.get(Calendar.DAY_OF_MONTH);
		return numToUpper(year) + "年" + monthToUppder(month) + "月" + dayToUppder(day) + "日";
	}

	/**
	 * 数字转换为大写
	 * @author 中科软
	 * @param String num：数字
	 * @return String
	 * @throws
	 */
	public static String numToUpper(int num) {
		String u[] = { "〇", "一", "二", "三", "四", "五", "六", "七", "八", "九" };
		char[] str = String.valueOf(num).toCharArray();
		String rstr = "";
		for (int i = 0; i < str.length; i++) {
			rstr = rstr + u[Integer.parseInt(str[i] + "")];
		}
		return rstr;
	}

	/**
	 * 月份转换为大写
	 * @author 中科软
	 * @param String month：月份
	 * @return String
	 * @throws
	 */
	public static String monthToUppder(int month) {
		if (month < 10) {
			return numToUpper(month);
		} else if (month == 10) {
			return "十";
		} else {
			return "十" + numToUpper(month - 10);
		}
	}

	/**
	 * 日转换为大写
	 * @author 中科软
	 * @param String day：年月日中的日
	 * @return String
	 * @throws
	 */
	public static String dayToUppder(int day) {
		if (day < 20) {
			return monthToUppder(day);
		} else {
			char[] str = String.valueOf(day).toCharArray();
			if (str[1] == '0') {
				return numToUpper(Integer.parseInt(str[0] + "")) + "十";
			} else {
				return numToUpper(Integer.parseInt(str[0] + "")) + "十" + numToUpper(Integer.parseInt(str[1] + ""));
			}
		}
	}

	/**
	 * 字符串转换为日期
	 * @author 中科软
	 * @param String strDate：日期的字符串形式
	 * @param String format：转换格式
	 * @return String
	 * @throws
	 */
	public static Date strToDate(String strDate, String format) {
		SimpleDateFormat dateFormat = new SimpleDateFormat(format);
		Date date = null;
		try {
			date = dateFormat.parse(strDate);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return date;
	}

	/**
	 * 日期转换为字符串
	 * @author 中科软
	 * @param Date date：需要转换的日期
	 * @param String format：转换格式
	 * @return String
	 * @throws
	 */
	public static String dateToStr(Date date, String format) {
		SimpleDateFormat dateFormat = new SimpleDateFormat(format);
		return dateFormat.format(date);
	}

	/**
	 * @param beforeNum 前几年
	 * @param afterNum 後几年
	 * @return
	 */
	public static List<String> getYearList(int beforeNum, int afterNum) throws Exception {
		List<String> yearLists = new ArrayList<String>();
		for (int i = beforeNum - 1; i > 0; i--) {// 循环得到前几年（不包括）
			Calendar calendar = Calendar.getInstance();
			calendar.add(Calendar.YEAR, -i);
			SimpleDateFormat formatter = new SimpleDateFormat("yyyy");
			yearLists.add(formatter.format(calendar.getTime()));
		}
		for (int i = 0; i <= afterNum + 1; i++) {// 循环得到後几年（包括）
			Calendar calendar = Calendar.getInstance();
			calendar.add(Calendar.YEAR, +i);
			SimpleDateFormat formatter = new SimpleDateFormat("yyyy");
			yearLists.add(formatter.format(calendar.getTime()));
		}
		return yearLists;
	}

	/**
	 * 获取某日期的上一个周 yyyy-MM-dd
	 * @return String add by zoulijuan 0423
	 */
	public static String getPreWeekDate(Date date) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.add(Calendar.DATE, -7); // 得到上一个日的日期
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
		return formatter.format(calendar.getTime());
	}

	/**
	 * 获取某日期的下一个周 yyyy-MM-dd
	 * @return String add by linsiming 20120518
	 */
	public static String getNextWeekDate(Date date) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.add(Calendar.DATE, 7); // 得到上一个日的日期
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
		return formatter.format(calendar.getTime());
	}

	/**
	 * 获取某日期的下一个周一 yyyy-MM-dd
	 * @return String add by linsiming 20120518
	 */
	public static String getNextWeekMonday() {
		String currentDate = getWeekMonday();
		return getNextWeekDate(strToDate(currentDate, "yyyy-MM-dd"));
	}

	/**
	 * 获取某日期的下一个周五 yyyy-MM-dd
	 * @return String add by linsiming 20120518
	 */
	public static String getNextWeekFriday() {
		String currentDate = getWeekFriday();
		return getNextWeekDate(strToDate(currentDate, "yyyy-MM-dd"));
	}

	/**
	 * 获取本周五获取周日时间 yyyy-MM-dd
	 * @return String add by linsiming 20120518
	 */
	public static String getCurWeekMonday(Date date) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.add(Calendar.DATE, -5);
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
		return formatter.format(calendar.getTime());
	}

	/**
	 * 获取上周周六(yyyy-MM-dd)
	 * @return add by zoulijuan 0509
	 */
	@SuppressWarnings("static-access")
	public static String getWeekSaturday() {
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
		Calendar calendar = Calendar.getInstance(Locale.CHINA);
		calendar.setTime(new Date());
		calendar.add(Calendar.DATE, -7); // 得到上一个日的日期
		calendar.set(calendar.DAY_OF_WEEK, calendar.SATURDAY);
		String saturday = simpleDateFormat.format(calendar.getTime());
		return saturday;
	}

	/**
	 * 获取本周周六(yyyy-MM-dd)
	 * @return add by zoulijuan 0509
	 */
	@SuppressWarnings("static-access")
	public static String getNextWeekSaturday() {
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
		Calendar calendar = Calendar.getInstance(Locale.CHINA);
		calendar.setTime(new Date());
		calendar.add(Calendar.DATE, 7); // 得到上一个日的日期
		calendar.set(calendar.DAY_OF_WEEK, calendar.SATURDAY);
		String saturday = simpleDateFormat.format(calendar.getTime());
		return saturday;
	}

	/**
	 * 获取本周周一(yyyy-MM-dd)
	 * @return add by zoulijuan 0509
	 */
	@SuppressWarnings("static-access")
	public static String getWeekMonday() {
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
		Calendar calendar = Calendar.getInstance(Locale.CHINA);
		calendar.setTime(new Date());
		calendar.set(calendar.DAY_OF_WEEK, calendar.MONDAY);
		String monday = simpleDateFormat.format(calendar.getTime());
		return monday;
	}

	/**
	 * 获取本周周五(yyyy-MM-dd)
	 * @return add by zoulijuan 0509
	 */
	@SuppressWarnings("static-access")
	public static String getWeekFriday() {
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
		Calendar calendar = Calendar.getInstance(Locale.CHINA);
		calendar.setTime(new Date());
		calendar.set(calendar.DAY_OF_WEEK, calendar.FRIDAY);
		String friday = simpleDateFormat.format(calendar.getTime());
		return friday;
	}

	/**
	 * 获取本周周四十二点(yyyy-MM-dd HH:mm:ss)
	 * @return add by zoulijuan 0509
	 */
	@SuppressWarnings("static-access")
	public static String getWeekThursday() {
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
		Calendar calendar = Calendar.getInstance(Locale.CHINA);
		calendar.setTime(new Date());
		calendar.set(calendar.DAY_OF_WEEK, calendar.THURSDAY);
		String thursday = simpleDateFormat.format(calendar.getTime()) + " 12:00:00";
		return thursday;
	}

	/**
	 * 获取本周周五十二点(yyyy-MM-dd HH:mm:ss)
	 * @return add by zoulijuan 0509
	 */
	@SuppressWarnings("static-access")
	public static String getWeekFridays() {
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
		Calendar calendar = Calendar.getInstance(Locale.CHINA);
		calendar.setTime(new Date());
		calendar.set(calendar.DAY_OF_WEEK, calendar.FRIDAY);
		String friday = simpleDateFormat.format(calendar.getTime()) + " 12:00:00";
		return friday;
	}

	/**
	 * 比较当前与周四12点
	 * @return
	 */
	public static boolean compareCurTime() {
		Date weekCur = strToDate(getToday(), "yyyy-MM-dd HH:mm:ss");
		Date weekThurs = strToDate(getWeekThursday(), "yyyy-MM-dd HH:mm:ss");
		return weekCur.before(weekThurs);
	}

	/**
	 * 比较是否是本周工作或者是下周工作
	 * @param endDate 本周周五时间
	 * @param compareDate 需要判断该周周五时间
	 * @return
	 */
	public static boolean compareEndDate(String compareDate) {
		Date weekCur = strToDate(getWeekFriday(), "yyyy-MM-dd");
		Date weekCompare = strToDate(compareDate, "yyyy-MM-dd");
		return weekCompare.after(weekCur);
	}

	/**
	 * 比较是否是本周工作或者是上周周工作
	 * @param endDate 本周周五时间
	 * @param compareDate 需要判断该周周五时间
	 * @return
	 */
	public static boolean beforeEndDate(String compareDate) {
		// 或者周四上午十二点
		Date lastDate = TimeUtil.addHours(strToDate(compareDate, "yyyy-MM-dd"), -12);
		Date nowDate = new Date();
		return lastDate.before(nowDate);
	}

	/**
	 * 比较当前与周五12点
	 * @return
	 */
	public static boolean compareCurTimeForBranch() {
		Date weekCur = strToDate(getToday(), "yyyy-MM-dd HH:mm:ss");
		Date weekThurs = strToDate(getWeekFridays(), "yyyy-MM-dd HH:mm:ss");
		return weekCur.before(weekThurs);
	}

	/**
	 * 获得某天是周几 如果type 为1 则返回输入日期对应的周日期 格式为 星期X 如果type 为2 则返回输入日期对应的周日期 格式为 周X
	 */
	public static String getWeekString(int type, Date date) {
		String[] weekStr = { "星期天", "星期一", "星期二", "星期三", "星期四", "星期五", "星期六" };
		String[] weekStr2 = { "周日", "周一", "周二", "周三", "周四", "周五", "周六" };
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		if (type == 1) {
			return weekStr[cal.get(Calendar.DAY_OF_WEEK) - 1];
		}
		if (type == 2) {
			return weekStr2[cal.get(Calendar.DAY_OF_WEEK) - 1];
		}
		return "";

	}
}
