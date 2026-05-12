package com.sinosoft.claim.common.util;

import java.text.SimpleDateFormat;
import java.util.Calendar;

import com.sinosoft.claim.util.StringConvert;
import com.sinosoft.sysframework.common.datatype.DateTime;
import com.sinosoft.utility.string.Date;

/**
 * 日期计算工具类
 * @Description 数据类型相关处理工具类
 * @author 中科软
 */
public class DateCompute {
	/**
	 * 根据起始和终止日期计算天数差，公共休息日不计,计算工作日
	 * @param iStartDate 起始日期
	 * @param iStartHour 起始小时
	 * @param iEndDate 终止日期
	 * @param iEndHour 终止小时
	 * @param iOption 待扩展
	 * @return 天数差
	 */
	public static int getBusinessDays(DateTime iStartDate, DateTime iEndDate) throws Exception {
		int intDay = 0;
		int intRest = 0; // 记录区间段中休息日的天数
		int intYear = 0;
		int intMonth = 0;
		int intDate = 0;
		int intWeek = 0;

		try {
			DateTime computeDate = iStartDate;
			String strStartDate = iStartDate.getYear() + "-" + iStartDate.getMonth() + "-" + iStartDate.getDay();
			String strEndDate = iEndDate.getYear() + "-" + iEndDate.getMonth() + "-" + iEndDate.getDay();

			if (compareDate(strStartDate, strEndDate) >= 0) // 报案日期和录入日期为同一天,同时排除报案日期比录入日期晚的异常
			{
				return 1; // jsp中用0时到24时所以算1天
			} else {
				intDay = (int) ((iStartDate.getTime() - iEndDate.getTime()) / 86400000l);
				intRest = 0;
				while (compareDate(strStartDate, strEndDate) <= 0) {
					// 累计有多少休息日
					// //格式化成yyyy-MM-dd的形式
					intYear = computeDate.getYear();
					intMonth = computeDate.getMonth();
					intDate = computeDate.getDate();
					java.util.Calendar cal = java.util.Calendar.getInstance();
					cal.set(intYear, intMonth, intDate); // 1月份对应的是值0，星期日对应的是值1
					intMonth = cal.get(Calendar.MONTH);
					intDate = cal.get(Calendar.DATE);
					intWeek = cal.get(Calendar.DAY_OF_WEEK);
					if (intWeek == 1 || intWeek == 7 || ((intMonth == 4 || intMonth == 9) && intDate >= 1 && intDate <= 7) || (intMonth == 0 && intDate == 1)) {
						intRest++;
					}
					// 取下一天
					computeDate = computeDate.addDay(1);
					strStartDate = computeDate.getYear() + "-" + computeDate.getMonth() + "-" + computeDate.getDay();
				}
				intDay -= intRest; // 休息日不计
				if (intDay < 0) {
					intDay = 0;
				}
				return intDay;

			}
		} catch (Exception e) {
			throw e;
		}
	}

	/**
	 * 功能：比较日期是否相等
	 * @param FirstDate String 输入为yyyy-MM-DD或者yyyy/MM/DD
	 * @param SecondDate String
	 * @return int 1：大於，0：相等，-1：小於
	 */
	public static int compareDate(String FirstDate, String SecondDate) throws Exception {
		int intReturn = 0; // 返回值
		java.util.Date firstDate = null;
		java.util.Date secondDate = null;
		try {
			SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
			StringConvert.replace(FirstDate, "/", "-"); // 将“/”的分隔符
			firstDate = format.parse(FirstDate);
			secondDate = format.parse(SecondDate);
			if (firstDate.after(secondDate)) {
				intReturn = 1;
			} else if (firstDate.before(secondDate)) {
				intReturn = -1;
			} else if (firstDate.equals(secondDate)) {
				intReturn = 0;
			}
			return intReturn;
		} catch (Exception e) {
			throw e;
		}
	}

	/**
	 * 功能：比较日期是否相等
	 * @param FirstDate String 输入为yyyy-MM-DD hh:mm:ss或者yyyy/MM/DD
	 * @param SecondDate String
	 * @param ps 返回的时间点
	 *		ins.framework.common.DateTime.DAY_TO_DAY = 33;//到天
     *      ins.framework.common.DateTime.DAY_TO_HOUR = 34;//到小时
     *      ins.framework.common.DateTime.DAY_TO_MINUTE = 35;//到分钟
	 * @return String X天X小时X分钟X秒
	 * 
	 */
	public static String betweenDate(String firstDateTime, String secondDateTime,int ps) throws Exception {
		// 计算文字描述
		long dayToMiins = 1000 * 60 * 60 * 24;
		long hourToMiins = 60 * 60 * 1000;
		long minuteToMiins = 60 * 1000;
		int secondToMiins = 1000;
		long hourCount = 0; // 小时数
		long minuteCount = 0; // 分钟
		int secondCount = 0;
		long dayCount = 0;
		long remainNum = 0;
		long diffTime = 0;
		DateTime beginTime = null;
		DateTime endTime = null;
		boolean isDate = false;

		// 对於没有时间的案件 设置stopTime = 0 ;
		if (firstDateTime == null || firstDateTime.equals("null") || firstDateTime.equals("") || secondDateTime == null || secondDateTime.equals("null") || secondDateTime.equals("")) {
			return "";
		}
		isDate = isDateTime(firstDateTime);
		// 判断输入日期是否正确
		if (!isDate)
			return "";

		isDate = isDateTime(secondDateTime);
		// 判断输入日期是否正确
		if (!isDate)
			return "";

		if (firstDateTime.length() < 11) {
			beginTime = new DateTime(firstDateTime, DateTime.YEAR_TO_DAY);
		} else {
			beginTime = new DateTime(firstDateTime, DateTime.YEAR_TO_SECOND);
		}

		if (secondDateTime.length() < 11) {
			endTime = new DateTime(secondDateTime, DateTime.YEAR_TO_DAY);
		} else {
			endTime = new DateTime(secondDateTime, DateTime.YEAR_TO_SECOND);
		}

		diffTime = endTime.getTime() - beginTime.getTime();
		// 计算天数之差
		dayCount = (long) diffTime / dayToMiins;
		// 计算剩余的小时和分钟
		remainNum = diffTime % dayToMiins;

		if (remainNum > 0) {
			hourCount = (long) remainNum / hourToMiins; // 计算小时
			remainNum = remainNum % hourToMiins;
			if (remainNum > 0) {
				minuteCount = (long) remainNum / minuteToMiins; // 计算分钟
				remainNum = remainNum % minuteToMiins;
				if (remainNum > 0) {
					secondCount = (int) remainNum / secondToMiins;

				} else {
					secondCount = 0;
				}
			} else {
				minuteCount = 0;
			}
		} else {
			hourCount = 0;
		}

		StringBuffer stopTimeDesc = new StringBuffer();
		stopTimeDesc.append(dayCount + "天");
		if(DateTime.DAY_TO_HOUR == ps || DateTime.DAY_TO_MINUTE == ps){
			stopTimeDesc.append(hourCount + "小時");
			if(DateTime.DAY_TO_MINUTE == ps){
				stopTimeDesc.append(minuteCount + "分鐘");
			}
		}
		return stopTimeDesc.toString();
	}

	/**
	 * 判断日期源串是否是日期
	 * @param sourceDateTime 日期字符串
	 * @throws Exception
	 * @return boolean
	 */
	private static boolean isDateTime(String sourceDateTime) throws Exception {
		boolean isDate = false;
		try {
			if (sourceDateTime.length() < 11) {
				new DateTime(sourceDateTime, DateTime.YEAR_TO_DAY);
				isDate = true;
			} else {
				new DateTime(sourceDateTime, DateTime.YEAR_TO_SECOND);
				isDate = true;
			}
		} catch (Exception e) {
			isDate = false;
		}

		return isDate;
	}

	/**
	 * 根据起始和终止日期计算小时差，公共休息日不计,计算工作日
	 * @param iStartDate 起始日期
	 * @param iStartHour 起始小时
	 * @param iEndDate 终止日期
	 * @param iEndHour 终止小时
	 * @param iOption 待扩展
	 * @return 小时差
	 */
	public int getBusinessDaysHour(DateTime iStartDate, int hour1, DateTime iEndDate, int hour2) throws Exception {
		int intYear = 0;
		int intMonth = 0;
		int intDate = 0;
		int intWeek = 0;
		int resulthour = 0;

		try {
			DateTime computeDate = iStartDate;
			String strStartDate = iStartDate.getYear() + "-" + iStartDate.getMonth() + "-" + iStartDate.getDay();
			String strEndDate = iEndDate.getYear() + "-" + iEndDate.getMonth() + "-" + iEndDate.getDay();
			if (compareDate(strStartDate, strEndDate) >= 0) // 流入日期和操作日期为同一天,同时排除流入日期比操作日期晚的异常
			{
				resulthour = hour2 - hour1;
				return resulthour;
			}

			while (compareDate(strStartDate, strEndDate) <= 0) {
				// 累计有多少休息日
				// //格式化成yyyy-MM-dd的形式
				intYear = computeDate.getYear();
				intMonth = computeDate.getMonth() - 1;
				intDate = computeDate.getDate();
				java.util.Calendar cal = java.util.Calendar.getInstance();
				cal.set(intYear, intMonth, intDate); // 1月份对应的是值0，星期日对应的是值1
				intMonth = cal.get(cal.MONTH);
				intDate = cal.get(cal.DATE);
				intWeek = cal.get(cal.DAY_OF_WEEK);

				// 取下一天
				DateTime computeDateTemp = new DateTime(computeDate.toString(), DateTime.YEAR_TO_DAY);
				computeDate = computeDate.addDay(1);
				strStartDate = computeDate.getYear() + "-" + computeDate.getMonth() + "-" + computeDate.getDay();

				if (intWeek == 1 || intWeek == 7 // 剔除星期六，日
						|| ((intMonth == 4 || intMonth == 9) && intDate >= 1 && intDate <= 7) // 剔除五一，十一
						|| (intMonth == 0 && intDate >= 1 && intDate <= 3) // 剔除元旦
						|| (intMonth == 0 && intDate >= 29 && intDate <= 31) || (intMonth == 1 && intDate >= 1 && intDate <= 4)) // 剔除06年春节休息日
				{
					continue;
				}

				if (computeDateTemp.getTime() == iStartDate.getTime()) {
					resulthour = 24 - hour1;
					continue;
				}
				if (computeDateTemp.getTime() == iEndDate.getTime()) {
					resulthour = resulthour + hour2;
					continue;
				}

				resulthour = resulthour + 24;
			}

			return resulthour;

		} catch (Exception e) {
			throw e;
		}
	}

	/**
	 * 根据起始和终止日期计算小时差，算公共休息日
	 * @param iStartDate 起始日期
	 * @param iStartHour 起始小时
	 * @param iEndDate 终止日期
	 * @param iEndHour 终止小时
	 * @param iOption 待扩展
	 * @return 小时差
	 */
	public int getDaysHour(DateTime iStartDate, int hour1, DateTime iEndDate, int hour2) throws Exception {
		int resulthour = 0;

		try {
			String strStartDate = iStartDate.getYear() + "-" + iStartDate.getMonth() + "-" + iStartDate.getDay();
			String strEndDate = iEndDate.getYear() + "-" + iEndDate.getMonth() + "-" + iEndDate.getDay();
			if (compareDate(strStartDate, strEndDate) > 0) // 报案日期和录入日期为同一天,同时排除报案日期比录入日期晚的异常
			{
				return resulthour;
			}

			if (compareDate(strStartDate, strEndDate) == 0) // 报案日期和录入日期为同一天,同时排除报案日期比录入日期晚的异常
			{
				resulthour = hour2 - hour1;
				return resulthour;
			}

			resulthour = (int) ((iEndDate.getTime() - iStartDate.getTime()) / (60 * 60 * 1000)) + hour2 - hour1;

			return resulthour;

		} catch (Exception e) {
			throw e;
		}
	}

	/**
	 * 根据起始和终止日期计算月份差
	 * @param iStartDate 起始日期
	 * @param iStartHour 起始小时
	 * @param iEndDate 终止日期
	 * @param iEndHour 终止小时
	 * @return 短期费率
	 */
	public static int getDayMinus(Date iStartDate, int iStartHour, Date iEndDate, int iEndHour) throws Exception {
		int intDay = 0;
		java.util.Date startDate = new java.util.Date(iStartDate.get(Date.YEAR), iStartDate.get(Date.MONTH) - 1, iStartDate.get(Date.DATE), iStartHour, 0, 0);
		java.util.Date endDate = new java.util.Date(iEndDate.get(Date.YEAR), iEndDate.get(Date.MONTH) - 1, iEndDate.get(Date.DATE), iEndHour, 0, 0);
		intDay = (int) ((endDate.getTime() - startDate.getTime()) / 86400000l);
		return intDay;
	}

	/**
	 * 判断两个日期的时间差天数
	 * @param iStartDate 开始日期
	 * @param iEndDate 结束日期
	 * @return 
	 * @throws Exception
	 */
	public int getHoliDays(DateTime iStartDate, DateTime iEndDate) throws Exception {
		int intRest = 0; // 记录区间段中休息日的天数
		int intYear = 0;
		int intMonth = 0;
		int intDate = 0;
		int intWeek = 0;

		try {
			DateTime computeDate = iStartDate;
			String strStartDate = iStartDate.getYear() + "-" + iStartDate.getMonth() + "-" + iStartDate.getDay();
			String strEndDate = iEndDate.getYear() + "-" + iEndDate.getMonth() + "-" + iEndDate.getDay();

			intRest = 0;
			while (compareDate(strStartDate, strEndDate) <= 0) {
				// 累计有多少休息日
				// //格式化成yyyy-MM-dd的形式

				intYear = computeDate.getYear();
				intMonth = computeDate.getMonth() - 1;
				intDate = computeDate.getDate();
				java.util.Calendar cal = java.util.Calendar.getInstance();
				cal.set(intYear, intMonth, intDate); // 1月份对应的是值0，星期日对应的是值1
				intMonth = cal.get(cal.MONTH);
				intDate = cal.get(cal.DATE);
				intWeek = cal.get(cal.DAY_OF_WEEK);

				if (intWeek == 1 || intWeek == 7 || ((intMonth == 4 || intMonth == 9) && intDate >= 1 && intDate <= 7) || (intMonth == 0 && intDate == 1)) {
					intRest++;
				}
				// 取下一天
				computeDate = computeDate.addDay(1);
				strStartDate = computeDate.getYear() + "-" + computeDate.getMonth() + "-" + computeDate.getDay();
			}
			if (intRest < 0) {
				intRest = 0;
			}
			return intRest;

		} catch (Exception e) {
			throw e;
		}

	}

	/**
	 * 数字转换成汉字
	 * @param number 数字1234
	 * @return
	 * @throws Exception
	 */
	public static String numberToChinese(String number) throws Exception {
		String temp = "";
		String[] num = { "零", "一", "二", "三", "四", "五", "六", "七", "八", "九", "十" };
		if (number == null || "".equals(number)) {
			return "";
		}

		if (number.length() == 1) {
			temp = num[Integer.parseInt(number.substring(0))];
		} else {
			if ("0".equals(number.substring(0, 1))) {
				temp = num[Integer.parseInt(number.substring(1))];
			} else if ("1".equals(number.substring(0, 1))) {
				if ("0".equals(number.substring(1))) {
					temp = num[10];
				} else {
					temp = num[10] + num[Integer.parseInt(number.substring(1))];
				}
			} else {
				if ("0".equals(number.substring(1))) {
					temp = num[Integer.parseInt(number.substring(0, 1))] + num[10];
				} else {
					temp = num[Integer.parseInt(number.substring(0, 1))] + num[10] + num[Integer.parseInt(number.substring(1))];
				}
			}
		}

		return temp;
	}

	public static void main(String[] args) throws Exception {
		System.out.println(DateCompute.numberToChinese("21"));
	}
}
