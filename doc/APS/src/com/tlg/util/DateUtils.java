package com.tlg.util;

import java.text.DecimalFormat;
import java.text.MessageFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Locale;
import java.util.TimeZone;
import java.util.regex.Pattern;

import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.time.DateFormatUtils;
import org.apache.commons.lang.time.DurationFormatUtils;

import com.tlg.exception.SystemException;

/**
 * Date/Time utilities
 *
 * @author Jessica
 */
public class DateUtils {

	public static String TW_DATE_PATTERN = "yyy.MM.dd";
	public static String ROC_DATE_PATTERN = "yyy/MM/dd";
	private static final String adDateFormat = "yyyyMMdd";
	private static final SimpleDateFormat addf = new SimpleDateFormat(adDateFormat);

	/**
	 * 計算 fromDate 到 toDate 之間的周年數
	 * <p>
	 * from 2001/1/10 to 2002/1/9 = 0<br/>
	 * from 2001/1/10 to 2002/1/10 = 1<br/>
	 * 其計算經準度 根據 {@link Date} 類別的經準度決定
	 * </p>
	 *
	 * @param fromDate
	 * @param toDate
	 * @return
	 */
	public static int calculateAge(Date fromDate, Date toDate) {
		Calendar to = Calendar.getInstance();
		to.setTime(toDate);

		Calendar from = Calendar.getInstance();
		from.setTime(fromDate);

		if (to.before(from)) {
			return 0;
		}

		int age = to.get(Calendar.YEAR) - from.get(Calendar.YEAR);

		int nowYear = to.get(Calendar.YEAR);
		Calendar dueDateOfThisYear = Calendar.getInstance();
		dueDateOfThisYear.setTime(from.getTime());
		dueDateOfThisYear.set(Calendar.YEAR, nowYear);

		if (dueDateOfThisYear.after(to)) {
			return age - 1;
		}
		return age;
	}

	/**
	 * 日期加上年份，例如傳入的Date物件為2009/01/01，加上1年後，回傳2010/01/01
	 * 
	 *
	 * @param fromDate
	 * @param toDate
	 * @return
	 */
	public static Date plusYear(Date date,int year) {
		Calendar c1 = Calendar.getInstance();
		c1.setTime(date);
		c1.add(Calendar.YEAR, year);
		
		return c1.getTime();
	}
	/**
	 * 計算 fromDate 到 toDate 之間的天數
	 *
	 * @param fromDate
	 * @param toDate
	 * @return
	 * @throws ParseException 
	 */
	public static int calculateDays2(Date fromDate, Date toDate) throws SystemException, ParseException{
		
		if(fromDate.compareTo(toDate) == 1){
			return 0;
		}
		if(fromDate.compareTo(toDate) == 0){
			return 0;
		}
		SimpleDateFormat format = new java.text.SimpleDateFormat("yyyy/MM/dd");
		String fromStr = DateUtils.getDateString(fromDate);
		String toStr = DateUtils.getDateString(toDate);
		fromDate = format.parse(fromStr);
		toDate = format.parse(toStr);
		
		long times = toDate.getTime() - fromDate.getTime();
		long days =  times / (1000 * 24 * 60 * 60);
		
		System.out.println(">>> days = " + days);
		
		return (int)days;
	}
	/**
	 * 計算 fromDate 到 toDate 之間的天數
	 *
	 * @param fromDate
	 * @param toDate
	 * @return
	 */
	public static int calculateDays(Date fromDate, Date toDate) throws SystemException{
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy/MM/dd");
		long diff = 0;
		try{
			String fromDateStr = formatter.format(fromDate);
			String toDateStr = formatter.format(toDate);
			if(fromDateStr.equals(toDateStr)){
				return 0;
			}
			fromDate = DateUtils.getDateObj(fromDateStr);
			toDate = DateUtils.getDateObj(toDateStr);

			diff = (toDate.getTime()/1000 - fromDate.getTime()/1000)/(60*60*24);
			
			if(diff < 0){
				diff = diff * -1;
			}
			
		}catch(Exception e){
			return -1;
		}
		return Integer.parseInt(String.valueOf(diff));
	}
	/**
	 * 計算 fromDate 到 toDate 之間的月數
	 * 計算月份有包含日，整不進位
	 * ex : 2009/05/02 ~ 2009/06/03 => 2
	 *      2009/05/02 ~ 2009/06/02 => 1 
	 *      2009/05/02 ~ 2009/06/01 => 1
	 * @param string
	 * @param string2
	 * @return
	 */
	public static int calculateMonths(Date string, Date string2) {
		int m =0;
		if(DateUtils.isCorrectDateRange(string, string2)) {
			int tM = DateUtils.getMonth(string2);
			int fM = DateUtils.getMonth(string);
			
			int tY = DateUtils.getYear(string2);
			int fY = DateUtils.getYear(string);
			
			int tD = DateUtils.getDay(string2);
			int fD = DateUtils.getDay(string);
			System.out.println(tY-fY);
			System.out.println(fM - tM);
			m = (tY-fY)*12 + (tM - fM);
			
			if(fD<tD) {
				m+=1;
			}
		}	
		
		return m ; 
	}
	
	/**
	 * 將年齡依據單位轉換為天數
	 *
	 * @param age
	 * @param ageUnit
	 * @return
	 */
	public static int calculateDaysByAgeUnit(Integer age, AgeUnit ageUnit) {
		if (ageUnit == AgeUnit.DAY) {
			return age;
		} else if (ageUnit == AgeUnit.MONTH) {
			return age * 30;
		} else if (ageUnit == AgeUnit.YEAR) {
			return age * 365;
		}
		return 0;
	}

	/**
	 * 取得年齡的字串描述，如 28歲10個月或28Y 10M
	 * <p>
	 * 計算之基準以傳入的基準日期為主，年月日各數值必須超過基準日才算1，
	 * 如1/19~2/19，算1個月，但1/18~2/19，則不算1個月
	 * 如2007/06/20~2008/06/20，算1年，但2007/05/20~2008/06/20則不算1年
	 * </p>
	 * @param fromDate 出生日期
	 * @param toDate 計算年齡之基準日期
	 * @yearPattern 年份表示之樣版，如Y, Years, 年, 歲
	 * @monthPattern 月份表示之樣版，如M, Months, 月, 個月
	 * @author Jessica
	 * @return 年齡的字串描述
	 */
	public static String getAgeDescription(Date fromDate, Date toDate, String yearPattern, String monthPattern) {
		Calendar from = Calendar.getInstance();
		from.setTime(fromDate);

		Calendar to = Calendar.getInstance();
		to.setTime(toDate);

		int year = 0;
		int month = 0;
		if (to.after(from)) {
			if (to.get(Calendar.DAY_OF_MONTH) < from.get(Calendar.DAY_OF_MONTH)) {
				to.add(Calendar.MONTH, -1);
			}

			if (to.get(Calendar.MONTH) < from.get(Calendar.MONDAY)) {
				month = 12;
				to.add(Calendar.YEAR, -1);
			}
			year = to.get(Calendar.YEAR) - from.get(Calendar.YEAR);
			month += (to.get(Calendar.MONTH) - from.get(Calendar.MONTH));
		}

		return MessageFormat.format("{0}{1}{2}{3}", year, yearPattern, month, monthPattern);
	}

	public static int getAge(Date fromDate) {
		Calendar from = Calendar.getInstance();
		from.setTime(fromDate);

		Date today = new Date();
		Calendar to = Calendar.getInstance();
		to.setTime(today);

		int year = 0;
		int month = 0;
		if (to.after(from)) {
			if (to.get(Calendar.DAY_OF_MONTH) < from.get(Calendar.DAY_OF_MONTH)) {
				to.add(Calendar.MONTH, -1);
			}

			if (to.get(Calendar.MONTH) < from.get(Calendar.MONDAY)) {
				month = 12;
				to.add(Calendar.YEAR, -1);
			}
			year = to.get(Calendar.YEAR) - from.get(Calendar.YEAR);
			month += (to.get(Calendar.MONTH) - from.get(Calendar.MONTH));
		}
		
		
		return year;

	}
	/**
	 * 取得年齡-用起日-迄日計算
	 * @param fromDate
	 * @param toDate
	 * @return
	 */
	public static int getAge(String iinskind,Date fromDate, Date toDate) {
		Calendar from = Calendar.getInstance();
		from.setTime(fromDate);
		from.set(Calendar.HOUR, 0);
		from.set(Calendar.MINUTE, 0);
		from.set(Calendar.SECOND, 0);
		from.set(Calendar.MILLISECOND, 0);
		Calendar to = Calendar.getInstance();
		to.setTime(toDate);
		to.set(Calendar.HOUR, 0);
		to.set(Calendar.MINUTE, 0);
		to.set(Calendar.SECOND, 0);
		to.set(Calendar.MILLISECOND, 0);
		int year = 0;
		if (to.after(from)) {
			year = to.get(Calendar.YEAR) - from.get(Calendar.YEAR);
			if("C2".equals(iinskind)){
/*
 * 
				If 保險起日(月) < 生日(月)
			    	年齡 = 年齡 - 1 
				Else if保險起日(月) = 生日(月)
			  		If 保險起日(日) < 生日(日)
			    		年齡 = 年齡 -1
			  		End if 
				End if
 * 
 * 				
 */				
				if (to.get(Calendar.MONTH) < from.get(Calendar.MONDAY)) {
					year--;
					return year;
				}
				if (to.get(Calendar.MONTH) == from.get(Calendar.MONDAY)) {
					if (to.get(Calendar.DATE) < from.get(Calendar.DATE)) {
						year--;
						return year;
					}
				}
			}
			if("C1".equals(iinskind)){
				if (to.get(Calendar.MONTH) > from.get(Calendar.MONDAY)) {
					year++;
					return year;
				}
				if (to.get(Calendar.MONTH) == from.get(Calendar.MONDAY)) {
					if (to.get(Calendar.DATE) > from.get(Calendar.DATE)) {
						year++;
						return year;
					}
				}
			}
		}
//		int year = 0;
//		int month = 0;
//		if (to.after(from)) {
//			if (to.get(Calendar.DAY_OF_MONTH) < from.get(Calendar.DAY_OF_MONTH)) {
//				to.add(Calendar.MONTH, -1);
//			}
//
//			if (to.get(Calendar.MONTH) < from.get(Calendar.MONDAY)) {
//				month = 12;
//				to.add(Calendar.YEAR, -1);
//			}
//			year = to.get(Calendar.YEAR) - from.get(Calendar.YEAR);
//			month += (to.get(Calendar.MONTH) - from.get(Calendar.MONTH));
//		}		
		return year;
	}
	public static int getAge(String fromDate) {
		if(!"".equals(fromDate)) {
			return getAge(DateUtils.getDateObj(fromDate));	
		}
		return 0 ;
	}
	
	/**
	 * 得到當前周的起始日期,默認為SUNDAY,如當前為西元2008-5-13星期三,則將會得到起始日期為2008-5-11
	 * @author Jessica
	 * @return 當前周的起始日期
	 */
	public static Date getFirstDayOfCurrentWeek(){
		Date date = new Date();
		GregorianCalendar beginDate = new GregorianCalendar();
		beginDate.setTime(date);

		int dayOfWeek = beginDate.get(Calendar.DAY_OF_WEEK);
		beginDate.add(GregorianCalendar.DATE, 1 - dayOfWeek);

		return beginDate.getTime();
	}


	/**
	 * 得到當前周的最后一天,默認為SATURDAY,如當前為西元2008-5-13星期三,則將會得到最后天数為2008-5-17
	 * @author Jessica
	 * @return 當前周的最后天数
	 */
	public static Date getLastDayOfCurrentWeek(){
		Date date = new Date();
		GregorianCalendar endDate = new GregorianCalendar();
		endDate.setTime(date);

		int dayOfWeek = endDate.get(Calendar.DAY_OF_WEEK);
		endDate.add(GregorianCalendar.DATE, 7-dayOfWeek);

		return endDate.getTime();
	}


	/**
	 * 得到當前前一個月的第一天,如當前為西元2008-5-13星期三,則將會得到2008-5-1
	 * @author Jessica
	 * @return 前一個月的起始天数
	 */
	@SuppressWarnings("static-access")
	public static Date getFirstDayOfPreviousMonth(){
		Calendar c = Calendar.getInstance();
		c.set(Calendar.MONTH, c.get(Calendar.MONTH) - 1);
		Calendar calFirst = Calendar.getInstance();
		calFirst.set(Calendar.MONTH, calFirst.get(Calendar.MONTH) - 1);

		// now 是今天在本月的第幾天
		int now = c.get(c.DAY_OF_MONTH);
		calFirst.add(calFirst.DATE, 1 - now);

		return calFirst.getTime();
	}


	/**
	 * 得到當前前一個月的最后一天,如當前為西元2008-5-13星期三,則將會得到2008-5-31
	 * @author Jessica
	 * @return 前一個月的结束天数
	 */
	@SuppressWarnings("static-access")
	public static Date getLastDayOfPreviousMonth(){
		Calendar c = Calendar.getInstance();
		c.set(Calendar.MONTH, c.get(Calendar.MONTH) - 1);
		Calendar calLast = Calendar.getInstance();
		calLast.set(Calendar.MONTH, calLast.get(Calendar.MONTH) - 1);

		// now 是今天在本月的第幾天
		int now = c.get(c.DAY_OF_MONTH);
		// max是本月一共有多少天
		int max = c.getActualMaximum(c.DAY_OF_MONTH);

		calLast.add(calLast.DATE, max - now);

		return calLast.getTime();
	}




	/**
	 * 得到當前月的第一天,如當前為西元2008-5-13星期三,則將會得到2008-5-1
	 * @author Jessica
	 * @return 當前月的起始天数
	 */
	@SuppressWarnings("static-access")
	public static Date getFirstDayOfCurrentMonth(){
		Calendar c = Calendar.getInstance();
		Calendar calFirst = Calendar.getInstance();

		// now 是今天在本月的第幾天
		int now = c.get(c.DAY_OF_MONTH);
		calFirst.add(calFirst.DATE, 1 - now);

		return calFirst.getTime();
	}


	/**
	 * 得到當前月的最后一天,如當前為西元2008-5-13星期三,則將會得到2008-5-31
	 * @author Jessica
	 * @return 當前月的结束天数
	 */
	@SuppressWarnings("static-access")
	public static Date getLastDayOfCurrentMonth(){
		Calendar c = Calendar.getInstance();
		Calendar calLast = Calendar.getInstance();

		// now 是今天在本月的第幾天
		int now = c.get(c.DAY_OF_MONTH);
		// max是本月一共有多少天
		int max = c.getActualMaximum(c.DAY_OF_MONTH);

		calLast.add(calLast.DATE, max - now);

		return calLast.getTime();
	}


	/**
	 *  取得年齡的纖細字串描述，如 28歲10個月23天2小時或28Y10M23D2H
	 * @param fromDate 出生日期
	 * @param toDate 計算年齡之基準日期
	 * @yearPattern 年份表示之樣版，如Y, Years, 年, 歲
	 * @monthPattern 月份表示之樣版，如M, Months, 月, 個月
	 * @dayPattern 天表示之樣板，如D，Days,天，個天
	 * @hourPattern 時表示之樣板，如H，Hours,時
	 * @author Jessica
	 * @return 年齡的字串描述
	 */
	public static String getAgeDetailDescription(Date fromDate, Date toDate, String yearPattern, String monthPattern,
												 String dayPattern,String hourPattern) {

		long from = fromDate.getTime();
		long to = toDate.getTime();

		String expectedResults = DurationFormatUtils.formatPeriod(from, to,"y-M-d-H",false,TimeZone.getDefault());
		String[] tempArrayDate = Pattern.compile("-+").split(expectedResults);

		int year = Integer.parseInt(tempArrayDate[0]);
		int month = Integer.parseInt(tempArrayDate[1]);
		int day = Integer.parseInt(tempArrayDate[2]);
		int hour = Integer.parseInt(tempArrayDate[3]);

		return  MessageFormat.format("{0}{1}{2}{3}{4}{5}{6}{7}", year,yearPattern,month,monthPattern, day, dayPattern, hour,hourPattern);
	}



	/**
	 * 根據指定的 參數產生 {@link Date} 實體
	 *
	 * @param year
	 *            年份
	 * @param month
	 *            月份, 1-12
	 * @param day
	 *            日期, 1-31
	 * @return 指定的時間 {@link Date} 實體，若指定的時間不存在，會依照日期數往後遞增到對應的日期
	 */
	public static Date getDate(int year, int month, int day) {
		return getDate(year, month, day, 0, 0, 0);
	}

   /**
     * 產生 {@link Date} 實體
     * @return 今天的最後一刻 {@link Date} 實體
     */
    @SuppressWarnings("static-access")
	public static Date getLastTimeOfToday() {
        Calendar c = Calendar.getInstance();

        c.set(c.HOUR_OF_DAY, 23);
        c.set(c.MINUTE, 59);
        c.set(c.SECOND, 59);
        c.clear(Calendar.MILLISECOND);
        return c.getTime();
    }


	/**
	 * 根據指定的 參數產生 {@link Date} 實體
	 *
	 * @param year
	 *            年份
	 * @param month
	 *            月份, 1-12
	 * @param day
	 *            日期, 1-31
	 * @param hour
	 *            小時，0-23
	 * @param minute
	 *            分鐘 0-59
	 * @param second
	 *            秒 0-59
	 * @return 指定的時間 {@link Date} 實體，若指定的時間不存在，會依照日期數往後遞增到對應的日期
	 */
	public static Date getDate(int year, int month, int day, int hour, int minute, int second) {
		Calendar c = Calendar.getInstance();
		c.set(year, month - 1, day, hour, minute, second);
		c.clear(Calendar.MILLISECOND);
		return c.getTime();
	}


	/**
	 * 取得僅含有"年月日"的日期物件
	 * @param date
	 * @author EmilyChen
	 */
	public static Date getDate(Date date) {
		Calendar calendarDateTime = Calendar.getInstance();
		calendarDateTime.setTime(date);
		Calendar calendarDate = new GregorianCalendar(calendarDateTime.get(Calendar.YEAR), calendarDateTime.get(Calendar.MONTH), calendarDateTime.get(Calendar.DATE));
		return calendarDate.getTime();
	}



	/**
	 * 取得 指定日期的日歷年
	 *
	 * @param date
	 * @return
	 */
	public static int getYear(Date date) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		return calendar.get(Calendar.YEAR);
	}



	/**
	 * 取得 指定日期的月份
	 * @param date
	 * @return
	 */
	public static int getMonth(Date date) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		return calendar.get(Calendar.MONTH);
	}



	/**
	 * 取得 指定日期的天
	 * @param date
	 * @return
	 */
	public static int getDay(Date date) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		return calendar.get(Calendar.DAY_OF_MONTH);
	}



	/**
	 * add year to specified date instance.
	 *
	 * @param date
	 *            date to add year
	 * @param year
	 *            how many year to add, could be negative integer
	 * @return the new date instance with year added
	 */
	public static Date addYear(Date date, int year) {
		Calendar c = Calendar.getInstance();
		c.setTime(date);
		c.add(Calendar.YEAR, year);
		return c.getTime();
	}



	/**
	 * @author Jessica
	 */
	public static Date addDay(Date date, int day)
	{
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.add(Calendar.DATE, day);

		return calendar.getTime();
	}

	/**
	 * @author Jessica
	 */
	public static Date addMonth(Date date, int month)
	{
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.add(Calendar.MONTH, month);

		return calendar.getTime();
	}

	/**
	 * @param date
	 * @param hour
	 * @return
	 * @author Jessica
	 */
	public static Date addHour(Date date, int hour) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.add(Calendar.HOUR, hour);

		return calendar.getTime();
	}

	/**
	 * @param date
	 * @param minute
	 * @return
	 * @author Jessica
	 */
	public static Date addMinute(Date date, int minute) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.add(Calendar.MINUTE, minute);

		return calendar.getTime();
	}

	/**
	 * 檢查是否為正確日期範圍: startDate < endDate
	 *
	 * @param startDate
	 * @param endDate
	 * @return
	 * @author EmilyChen
	 */
	public static boolean isCorrectDateRange(Date startDate, Date endDate)
	{
		checkDate(startDate, endDate);
		if(startDate.getTime() < endDate.getTime())
		{
			return true;
		}
		return false;
	}


	/**
	 * 檢查是否為正確日期範圍(其中包括相等): startDate > endDate
	 * @param fromDate
	 * @param toDate
	 * @return
	 * @author Jessica
	 */
	public static boolean isCorrectDateRangeIncludeEqual(Date fromDate,Date toDate){
		checkDate(fromDate,toDate);
		if(fromDate.getTime() > toDate.getTime()){
			return false;
		}
		return true;
	}



	/**
	 * 檢查checkDate是否在startDate與endDate區間內: startDate <= checkDate <= endDate
	 * @param startDate
	 * @param endDate
	 * @param checkDate
	 * @return
	 * @author Jessica
	 */
	public static boolean isBetweenTwoDate(Date startDate, Date endDate, Date checkDate)
	{
		checkDate(startDate, endDate, checkDate);
		checkCorrectDateRange(startDate, endDate);

		if(checkDate.getTime()>=startDate.getTime() && checkDate.getTime()<=endDate.getTime())
		{
			return true;
		}

		return false;
	}

	/**
	 * 檢查給定的日期，是否在另外兩個日期區間中
	 * <p>
	 * 僅比較日期部分，不比較時間部分
	 * </p>
	 * <p>
	 * 例如：<br/>
	 * <code>
	 * isBeetween('2008/1/1', '2008/1/31', '2008/1/1'') = true<br/>
	 * isBeetween('2008/1/1', '2008/1/31', '2007/12/31'') = false<br/>
	 * isBeetween('2008/1/1', '2008/1/31', '2008/1/31'') = true<br/>
	 * isBeetween('2008/1/1', '2008/1/31', '2008/2/1'') = false<br/>
	 * isBeetween('2008/1/1', '2008/1/31', null) = false<br/>
	 * </code>
	 * </p>
	 *
	 * @param startDate
	 * @param endDate
	 * @param checkDate
	 * @return
	 */
	public static boolean isBetweenDays(Date startDate, Date endDate, Date checkDate) {
		if (checkDate == null || startDate == null || endDate == null) {
			return false;
		}
	/*	LocalDate s = new LocalDate(startDate);
		LocalDate e = new LocalDate(endDate);
		LocalDate c = new LocalDate(checkDate);*/
		
		
		
		if (startDate.after(checkDate) || endDate.before(checkDate)) {
			return false;
		}
		return true;
	}

	/**
	 * date1>date2 => return true. 以"日"為計算基準
	 * @param date1
	 * @param date2
	 * @return
	 * @author Jessica
	 */
	public static boolean isMore(Date date1, Date date2)
	{
		if(getLessDays(date1, date2)>0)
		{
			return true;
		}

		return false;
	}



	/**
	 * date1<date2 => return true. 以"日"為計算基準
	 * @param date1
	 * @param date2
	 * @return
	 * @author Jessica
	 */
	public static boolean isLess(Date date1, Date date2)
	{
		if(getLessDays(date1, date2)<0)
		{
			return true;
		}
		return false;
	}



	/**
	 * 取得日期相差天數，只計算日期差，不包含時間差
	 *
	 * @param date1
	 * @param date2
	 * @return date1 - date2 的日期差，可能為正數或負數
	 * @author Jessica
	 */
	public static Long getLessDays(Date date1, Date date2)
	{
		checkDate(date1, date2);
		long timeSpan = date1.getTime() - date2.getTime();
		if (Math.abs(timeSpan) < (1000 * 60 * 60 * 24)) {
			return 0L;
		}

		long days = timeSpan / 1000 / 60 / 60 / 24;
		return days;
	}

	/**
	 * 檢查是否為正確日期範圍: startDate < endDate
	 * @param startDate
	 * @param endDate
	 * @author Jessica
	 */
	private static void checkCorrectDateRange(Date startDate, Date endDate)
	{
		try
		{
			if(!isCorrectDateRange(startDate, endDate))
			{
				throw new IllegalArgumentException("startDate必須小於endDate");
			}
		}catch(IllegalArgumentException e)
		{
			e.printStackTrace();
		}
	}


	/**
	 * throw {@link IllegalArgumentException} if any date argument is null
	 *
	 * @author Jessica
	 * @param date
	 */
	private static void checkDate(Date... date)
	{
		for(Date checkDate : date)
		{
			if(checkDate==null)
			{
				throw new IllegalArgumentException("date參數不得為null");
			}
		}
	}


	/**
	 * java.util.Date format "yyyyMMdd"
	 * @param date
	 * @return String
	 * @author Jessica
	 */
	public static String formatDate(Date date)
	{
		return date==null ? null : DateFormatUtils.format(date, "yyyyMMdd");
	}

	/**
	 * 根據指定的日期自串格式，將日期格式化成日期字串
	 * <p>
	 * 這個方法即使給定 的date為null，即回傳null，不會拋出例外
	 * </p>
	 *
	 * @param date
	 *            要格式化的日期，若為null回傳null
	 * @param pattern
	 *            日期自串格式
	 * @return
	 */
	public static String format(Date date, String pattern) {
		if (date == null) {
			return null;
		}
		return DateFormatUtils.format(date, pattern);
	}

	/**
	 * 取得 後天日期(午夜零時的時間)
	 *
	 * @return
	 * @author Jessica
	 */
	public static Date getAfterTomorrow() {
		return addDay(getToday(), 2);
	}



	/**
	 * 取得 明天日期(午夜零時的時間)
	 *
	 * @return
	 * @author Jessica
	 */
	public static Date getTomorrow() {
		return addDay(getToday(), 1);
	}



	/**
	 * 取得明年年份
	 *
	 * @return
	 * @author Jessica
	 */
	public static int getNextYear() {
		Calendar dateTime = Calendar.getInstance();
		return dateTime.get(Calendar.YEAR) + 1;
	}



	/**
	 * 取得今日日期(午夜零時的時間)
	 *
	 * @return
	 * @author Jessica
	 */
	public static Date getToday()
	{
		Calendar dateTime = Calendar.getInstance();
		Calendar date = new GregorianCalendar(dateTime.get(Calendar.YEAR), dateTime.get(Calendar.MONTH), dateTime.get(Calendar.DATE));
		return date.getTime();
	}



	/**
	 * 取得昨天的日期(午夜零時的時間)
	 *
	 * @return
	 * @author Jessica
	 */
	public static Date getYesterday() {
		return addDay(getToday(), -1);
	}



	/**
	 * 取得前天的日期(午夜零時的時間)
	 *
	 * @return
	 * @author Jessica
	 */
	public static Date getBeforeYesterday() {
		return addDay(getToday(), -2);
	}

	public static Date convertRocDateTime(String rocDate) {
		Date d = null;
		if(!"".equals(rocDate) && rocDate!= null) {
			rocDate = rocDate.trim();
			String[] time = rocDate.split("/");		
			d = getDate(Integer.parseInt(time[0])+1911, Integer.parseInt(time[1]), Integer.parseInt(time[2]));
		}
		return d;
	}
	/**
	 * 將西元日期轉換成民國日期(含小時,分鐘)
	 * 例如：97.5.5 12:33
	 * @return StringBuffer
	 * @author Jessica
	 */
	public static StringBuffer convertDateTimeOne(Date date){
		Calendar calendar = Calendar.getInstance();
		if(date != null){
			calendar.setTime(date);
			String year = String.valueOf(calendar.get(Calendar.YEAR)-1911);
			String month = String.valueOf(calendar.get(Calendar.MONTH)+1);
			String day = String.valueOf(calendar.get(Calendar.DAY_OF_MONTH));
			String hours = String.valueOf(calendar.get(Calendar.HOUR_OF_DAY));
			String minutes = String.valueOf(calendar.get(Calendar.MINUTE));

			StringBuffer stringBuffer = new StringBuffer();
			stringBuffer.append(year);
			stringBuffer.append(".");
			stringBuffer.append(month);
			stringBuffer.append(".");
			stringBuffer.append(day);
			stringBuffer.append(" ");
			stringBuffer.append(hours);
			stringBuffer.append(":");
			stringBuffer.append(minutes);
			return stringBuffer;
		}else{
			return null;
		}

	}



	/**
	 * 將西元日期轉換成民國日期
	 * 例如：097/5/5
	 * @return StringBuffer
	 * @author Jessica
	 */
	public static StringBuffer convertDateTimeTwo(Date date, String split){
	
		Calendar calendar = Calendar.getInstance();
		if(date != null){
			calendar.setTime(date);
			String year = String.valueOf(calendar.get(Calendar.YEAR)-1911);
			if(year.length() == 2) {
				year = "0" + year;
			}
			String month = String.valueOf(calendar.get(Calendar.MONTH)+1);
			if(month.length() == 1) {
				month = "0" + month;
			}
			String day = String.valueOf(calendar.get(Calendar.DAY_OF_MONTH));
			if(day.length() == 1) {
				day = "0" + day;
			}
			StringBuffer stringBuffer = new StringBuffer();
			stringBuffer.append(year);
			stringBuffer.append(split);
			stringBuffer.append(month);
			stringBuffer.append(split);
			stringBuffer.append(day);
			//stringBuffer.append(" ");
			return stringBuffer;
		}else{
			return null;
		}

	}

	/**
	 * 將西元日期轉換成民國日期String(含小時,分鐘)
	 * 例如：97/5/5 12:33
	 * @return String
	 * @author Jessica
	 */
	public static String getMgDateTimeString(Date date){
		Calendar calendar = Calendar.getInstance();
		if(date != null){
			calendar.setTime(date);
			String year = String.valueOf(calendar.get(Calendar.YEAR)-1911);
			String month = String.valueOf(calendar.get(Calendar.MONTH)+1);
			String day = String.valueOf(calendar.get(Calendar.DAY_OF_MONTH));
			String hours = String.valueOf(calendar.get(Calendar.HOUR_OF_DAY));
			String minutes = String.valueOf(calendar.get(Calendar.MINUTE));

			StringBuffer stringBuffer = new StringBuffer();
			stringBuffer.append(year);
			stringBuffer.append("/");
			stringBuffer.append(month);
			stringBuffer.append("/");
			stringBuffer.append(day);
			stringBuffer.append(" ");
			stringBuffer.append(hours);
			stringBuffer.append(":");
			stringBuffer.append(minutes);
			return stringBuffer.toString();
		}else{
			return "";
		}

	}

	/**
	 * 將西元字串轉換成Date物件
	 * 例如：201112311230 → Date 
	 * @return String
	 */
	public static Date getWestDateObj(String dateStr){
		
		if(!"".equals(dateStr)){
			if(dateStr.length() < 12){
				int p = 12 - dateStr.length();
				for(int i = 0 ; i < p ; i ++ ){
					dateStr = dateStr + "0";
				}
			}
			Date date = getDate(Integer.parseInt(dateStr.substring(0,4)), 
					Integer.parseInt(dateStr.substring(4,6)), 
					Integer.parseInt(dateStr.substring(6,8)), 
					Integer.parseInt(dateStr.substring(8,10)), 
					Integer.parseInt(dateStr.substring(10,12)), 
					0);
			
			return date;
		}else{
			return null;
		}
	}
	
	/**
	 * 將西元日期轉換成字串
	 * 例如：2008/12/08
	 * @return String
	 * @author kevin.zhang
	 */
	public static String getDateString(Date date){
		Calendar calendar = Calendar.getInstance();
		if(date != null){
			calendar.setTime(date);
			String year = String.valueOf(calendar.get(Calendar.YEAR));
			String month = StringUtils.leftPad(String.valueOf(calendar.get(Calendar.MONTH)+1), 2, "0");
			String day = StringUtils.leftPad(String.valueOf(calendar.get(Calendar.DAY_OF_MONTH)), 2, "0");
			StringBuffer stringBuffer = new StringBuffer();
			stringBuffer.append(year);
			stringBuffer.append("/");
			stringBuffer.append(month);
			stringBuffer.append("/");
			stringBuffer.append(day);
			return stringBuffer.toString();
		}else{
			return "";
		}
	}

	/**
	 * 輸入日期樣式，取得台灣使用的民國年字串。
	 * <p>
	 * 日期樣式的使用方式和 (@link java.text.DateFormat} 相同。
	 * </p>
	 * <p>
	 * 如果樣式為日期在前面時，轉換後的字串長度若比樣式長度短，則自動在字串補0，直到長度相等為止。
	 * </p>
	 *
	 * @author Roger
	 * @param date 日期
	 * @param pattern 日期樣式。樣式設定方式請參考(@link java.text.DateFormat}
	 * @return
	 */
	public static String getTaiwanDateString(Date date, String pattern) {
		if (date == null) {
			return null;
		}
		
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		String dateString = ""; 
		if(pattern.equals(ROC_DATE_PATTERN)){
			dateString = calendar.get(Calendar.YEAR) - 1911 + "/" +  CommonFunc.fillZeroA(String.valueOf(calendar.get(Calendar.MONTH) + 1),2) + "/" + CommonFunc.fillZeroA(String.valueOf(calendar.get(Calendar.DATE)),2);
		}
		if(pattern.equals(TW_DATE_PATTERN)){
			dateString = calendar.get(Calendar.YEAR) - 1911 + "." +  CommonFunc.fillZeroA(String.valueOf(calendar.get(Calendar.MONTH) + 1),2) + "." + CommonFunc.fillZeroA(String.valueOf(calendar.get(Calendar.DATE)),2);
		}		
		if (dateString.length() < pattern.length() && pattern.startsWith("y")) {
			return CommonFunc.fillZeroA(dateString, pattern.length());
		}
		return dateString;
	}
	
	/**
	 * 透過預設的民國年日期樣式取得民國年日期
	 * @param date
	 * @return
	 */
	public static String getTaiwanDateString(Date date) {
		return getTaiwanDateString(date, ROC_DATE_PATTERN);
	}
	
	/**
	 * 透過預設的民國年日期樣式取得民國年日期 yyymmdd
	 * @param date
	 * @return
	 */
	public static String getRocYYYMMDD(Date date) {
		return getTaiwanDateString(date, TW_DATE_PATTERN).replace(".", ""); 
	}
	
	/**
	 * 根據指定的日期，取得該日期的年月日。
	 * 例如：getDateDay(new Date(),"/",1)，結果為 2010/06/25。
	 * 例如：getDateDay(new Date(),"/",2)，結果為 099/06/25。
	 * 
	 * @param date(指定的日期)
	 * @param pattern(年月日的分隔符號)
	 * @param type(type=1指西元年，type=2指民國年)
	 * @return 該日期的年月日
	 */
	public static String getDateDay(Date date, String pattern, int type) {

		String str = "";
		if (date != null) {
			DecimalFormat df2 = new DecimalFormat("00");
			DecimalFormat df3 = new DecimalFormat("000");
			Calendar calendar = Calendar.getInstance();
			calendar.setTime(date);
			int year = 0;
			if (type == 1) {
				year = calendar.get(Calendar.YEAR);
			} else {
				year = calendar.get(Calendar.YEAR) - 1911;
			}
			int month = calendar.get(Calendar.MONTH) + 1;
			int day = calendar.get(Calendar.DAY_OF_MONTH);
			StringBuffer sb = new StringBuffer();
			sb.append(df3.format(year));
			sb.append(pattern);
			sb.append(df2.format(month));
			sb.append(pattern);
			sb.append(df2.format(day));
			str = sb.toString();
		}
		return str;
	}

	/**
	 * 根據指定的日期，取得該日期的時分秒。
	 * 例如：getDateTime(new Date(),":")，結果為10:31:01。
	 * 
	 * @param date(指定的日期)
	 * @param pattern(時分秒的分隔符號)
	 * @return 該日期的時分秒
	 */
	public static String getDateTime(Date date, String pattern) {
		DecimalFormat df2 = new DecimalFormat("00");
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		int hour = calendar.get(Calendar.HOUR_OF_DAY);
		int minute = calendar.get(Calendar.MINUTE);
		int second = calendar.get(Calendar.SECOND);
		StringBuffer sb = new StringBuffer();
		sb.append(df2.format(hour));
		sb.append(pattern);
		sb.append(df2.format(minute));
		sb.append(pattern);
		sb.append(df2.format(second));
		return sb.toString();
	}
	
	/**
	 * 根據指定的日期，取得該日期的年月日時分秒。
	 * 例如：getDateArray(new Date(),1)，結果為{"2010","06","25","10","31","01"}。
	 * 例如：getDateArray(new Date(),2)，結果為{"099","06","25","10","31","01"}。
	 * 
	 * @param date(指定的日期)
	 * @param type(type=1指西元年，type=2指民國年)
	 * @return 該日期的年月日時分秒
	 */
	public static String[] getDateArray(Date date, int type) {
		DecimalFormat df2 = new DecimalFormat("00");
		DecimalFormat df3 = new DecimalFormat("000");
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		int year = 0;
		if (type == 1) {
			year = calendar.get(Calendar.YEAR);
		} else {
			year = calendar.get(Calendar.YEAR) - 1911;
		}
		int month = calendar.get(Calendar.MONTH) + 1;
		int day = calendar.get(Calendar.DAY_OF_MONTH);
		int hour = calendar.get(Calendar.HOUR_OF_DAY);
		int minute = calendar.get(Calendar.MINUTE);
		int second = calendar.get(Calendar.SECOND);
		String[] dateArray = new String[6];
		dateArray[0] = df3.format(year);
		dateArray[1] = df2.format(month);
		dateArray[2] = df2.format(day);
		dateArray[3] = df2.format(hour);
		dateArray[4] = df2.format(minute);
		dateArray[5] = df2.format(second);	
		return dateArray;
	}
	
	/**
	 * 根據指定的日期，取得該日期的 凌晨零時起，至該日期的23時59分59秒點999豪秒
	 * <p>
	 * 例如：
	 * 給定日期 = 2008/10/10 17:23:45.234<br/>
	 * 結果為： DateRange[from=2008/10/10 0:0:0.000 to=2008/10/10 23:59:59.999]
	 * </p>
	 *
	 * @param date
	 * @return
	 */
	public static DateRange getRangeInDay(Date date) {
		return getRangeInDay(date, date);
	}



	/**
	 * 根據指定的start日期，取得start日期的 凌晨零時起，至end日期的23時59分59秒點999豪秒
	 * <p>
	 * 例如：
	 * 給定日期 = 2008/10/10 17:23:45.234<br/>
	 * 結果為： DateRange[from=2008/10/10 0:0:0.000 to=2008/10/10 23:59:59.999]
	 * </p>
	 *
	 * @param startDate
	 * @param endDate
	 * @return
	 */
	public static DateRange getRangeInDay(Date startDate, Date endDate) {
		Date start = getDate(startDate);
		Date end = new Date(getDate(addDay(endDate, 1)).getTime() - 1);
		return new DateRange(start, end);
	}



	/**
	 * 若 date1 與date2 在同一日，或在 date2 之前，則回傳true
	 *
	 * @param d1
	 * @param d2
	 * @return
	 */
	public static boolean isBeforeOrSameDay(Date d1, Date d2) {
//		LocalDate dt1 = new LocalDate(d1);
//		LocalDate dt2 = new LocalDate(d2);
		if (d1.before(d2) || d1.equals(d2)) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * 取得目前的時間
	 *
	 * @author morel
	 * @return
	 */
	public static Date getNow() {
		return new Date();
	}


	/**
	 * 得到當前日的淩晨日期如: 2008/10/26 00:00:00
	 * @param beginDate
	 * @author 朱華偉 2008/10/27
	 * @return
	 */
	public static Date getDawnDate(Date beginDate){
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(beginDate);
		calendar.set(Calendar.HOUR_OF_DAY, 0);
		calendar.set(Calendar.MINUTE, 0);
		calendar.set(Calendar.SECOND, 0);

		return calendar.getTime();
	}


	/**
	 * 得到當前日的午夜日期如: 2008/10/26 23:59:59
	 * @param endDate
	 * @author 朱華偉 2008/10/27
	 * @return
	 */
	public static Date getDarkDate(Date endDate){
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(endDate);
		calendar.set(Calendar.HOUR_OF_DAY, 23);
		calendar.set(Calendar.MINUTE, 59);
		calendar.set(Calendar.SECOND, 59);

		return calendar.getTime();
	}

	/**
	 * 取得僅含有"年月日 HH MM"的日期物件
	 * @param date
	 * @author Kevin
	 */
	public static Date getDateMinute(Date date) {
		Calendar calendarDateTime = Calendar.getInstance();
		calendarDateTime.setTime(date);
		Calendar calendarDate
				= new GregorianCalendar(calendarDateTime.get(Calendar.YEAR),
										calendarDateTime.get(Calendar.MONTH),
										calendarDateTime.get(Calendar.DATE),
										calendarDateTime.get(Calendar.HOUR_OF_DAY),
										calendarDateTime.get(Calendar.MINUTE));
		return calendarDate.getTime();
	}

	/**
	 * 取得僅含有"年月日 HH MM"的下一分鐘日期物件
	 * @param date
	 * @author Kevin
	 */
	public static Date getDateNextMinute(Date date) {
		Calendar calendarDateTime = Calendar.getInstance();
		calendarDateTime.setTime(date);
		Calendar calendarDate
				= new GregorianCalendar(calendarDateTime.get(Calendar.YEAR),
										calendarDateTime.get(Calendar.MONTH),
										calendarDateTime.get(Calendar.DATE),
										calendarDateTime.get(Calendar.HOUR_OF_DAY),
										calendarDateTime.get(Calendar.MINUTE) + 1);
		return calendarDate.getTime();
	}
	/**
	 * 取得僅含有"年月日"的日期物件
	 * @param date
	 * @author Jessica
	 */
	public static Date getShortDate(Date date) {
		Calendar calendarDateTime = Calendar.getInstance();
		calendarDateTime.setTime(date);
		Calendar calendarDate
				= new GregorianCalendar(calendarDateTime.get(Calendar.YEAR),
										calendarDateTime.get(Calendar.MONTH),
										calendarDateTime.get(Calendar.DATE));
		return calendarDate.getTime();
	}
	/**
	 * 檢查民國年輸入格式是否正確
	 * @param transDate(yyy/mm/dd) ex:099/01/01
	 * @return
	 * @throws Exception 
	 */
	public static boolean checkTransDate(String transDate) throws Exception {
		boolean result = false;
		if(!StringUtil.isSpace(transDate)) {
			String[] d = transDate.split("/");
			if(d.length == 3) {
				if(d[0].length() == 3 && d[1].length() == 2 && d[2].length() == 2) {
					int month = Integer.parseInt(d[1]);
					int day = Integer.parseInt(d[2]);
					if(month <=12 && day <=31) {
						result = true;
					}
				}
			}
		}
		
		
		return result;
	}
	/**
	 * 將民國年093/01/02轉為西元年2004/01/02
	 * 
	 * @param transDate
	 * @return String
	 * @throws Exception 
	 */
	public static String transDate(String transDate,String splitStr) throws Exception{
		if(StringUtil.isSpace(transDate)){
			return transDate;
		}
		String[] d = transDate.split(splitStr);
		if(d.length > 0) {
			int year = Integer.parseInt(d[0]);
			try {
				year = year + 1911;
				transDate = year + "/" + d[1] + "/" + d[2];
			} catch (Exception e) {
				System.out.println("DateUtil:transDate:error");
			}
		}
		return transDate;
	}
	
	/**
	 * 將西元年2004/01/02轉為民國年93/01/02
	 * 
	 * @param transDate
	 * @return String
	 */
	public static String transChristianEra(String transDate,String splitStr) throws Exception {
		
		if(StringUtil.isSpace(transDate)){
			return transDate;
		}
		
		String[] d = transDate.split(splitStr);
		int year = Integer.parseInt(d[0]);
		try {
			year = year - 1911;
			transDate = year + "/" + d[1] + "/" + d[2];
		} catch (Exception e) {
			System.out.println("Commen:transChristianEra:error");
			throw e;
		}
		if(transDate.length() == 8) {
			transDate = "0"+ transDate;
		}
		return transDate;
	}
	/**
	 * 
	 * 將傳入的字串轉為Date
	 * 傳入字串格式2009/01/01
	 * @param strDate
	 * @return Date
	 */
	public static Date getDateObj(String strDate) {
		Calendar ca = Calendar.getInstance();
		Date d = null;
		if (strDate.indexOf("/") != -1) {
			String[] arrayDate = strDate.split("/");
			if (arrayDate.length == 3) {
				int year = Integer.parseInt(arrayDate[0]);
				int month = Integer.parseInt(arrayDate[1]) - 1;
				int date = Integer.parseInt(arrayDate[2]);
				ca.set(year, month, date,0,0,0);
				d = ca.getTime();
			}
		}
		return d;
	}
	public static Date getDefaultDate() {
		return new Date();
	}
	
//	public static void dateSubtractionV2(String date1 ,String date2) throws Exception{
//		String d1[] = DateUtils.transDate(date1, "/").split("/");
//		String d2[] = DateUtils.transDate(date2, "/").split("/");
//		DateTime dateTime1 = new DateTime(Integer.parseInt(d1[0]), Integer.parseInt(d1[1]), Integer.parseInt(d1[2]), 0, 0, 0, 0);
//		DateTime dateTime2 = new DateTime(Integer.parseInt(d2[0]), Integer.parseInt(d2[1]), Integer.parseInt(d2[2]), 0, 0, 0, 0);
//		
//		
//		
//		Period period1 = new Period(dateTime1, dateTime2);
//
////		PeriodFormatter formatter = new PeriodFormatterBuilder()
////		    .appendSeconds().appendSuffix(" seconds ago\n")
////		    .appendMinutes().appendSuffix(" minutes ago\n")
////		    .appendHours().appendSuffix(" hours ago\n")
////		    .appendDays().appendSuffix(" days ago\n")
////		    .appendMonths().appendSuffix(" months ago\n")
////		    .appendYears().appendSuffix(" years ago\n")
////		    .printZeroNever().toFormatter();
////
////		String elapsed = formatter.print(period1);
////		System.out.println("2.相差 " + period1.getYears() + " 年, " + period1.getMonths() + " 月," + period1.getDays() + " 日");
//		
//		System.out.println(">> period1.getDays() " + period1.getDays());
//		
//		Interval interval = new Interval(dateTime1, dateTime2);
//		
//		long l = (interval.getEndMillis() - interval.getStartMillis()) / (1000*60*60*24);
//		System.out.println(">>> l = " + l );
//		
//		Period period2 = interval.toPeriod(PeriodType.yearMonthDay());
//		System.out.println("3.相差 " + period2.getYears() + " 年, " + period2.getMonths() + " 月," + period2.getDays() + " 日");
//		
////		Duration dur = new Duration(dateTime1, dateTime2);
////		DateTime calc = dateTime1.plus(dur);
//
////		Period period3 = dur.toPeriod(PeriodType.);
////		System.out.println("4.相差 " + period3.toString() +  period3.getYears() + " 年, " + period3.getMonths() + " 月," + period3.getDays() + " 日");
//		
////		System.out.println(elapsed);
//	}
	
	public static Integer[] dateSubtraction(String date1 ,String date2) throws Exception{
		
		Date bd = StringUtil.stringToDate(DateUtils.transDate(date1, "/"));
		Date ed = StringUtil.stringToDate(DateUtils.transDate(date2, "/"));
		
		Calendar objCalendarDate1 = Calendar.getInstance();
		objCalendarDate1.setTime(bd);
//		objCalendarDate1.set(Calendar.HOUR, 0);
//		objCalendarDate1.set(Calendar.MINUTE, 0);
//		objCalendarDate1.set(Calendar.SECOND, 0);
//		objCalendarDate1.set(Calendar.MILLISECOND, 0);
		Calendar clone = (Calendar) objCalendarDate1.clone();
		
		Calendar objCalendarDate2 = Calendar.getInstance();
		objCalendarDate2.setTime(ed);
//		objCalendarDate2.set(Calendar.HOUR, 0);
//		objCalendarDate2.set(Calendar.MINUTE, 0);
//		objCalendarDate2.set(Calendar.SECOND, 0);
//		objCalendarDate2.set(Calendar.MILLISECOND, 0);
		
		
		Integer[] elapsed = new Integer[6];
		elapsed[0] = elapsed(clone, objCalendarDate2, Calendar.YEAR);
		
        clone.add(Calendar.YEAR, elapsed[0]);
        elapsed[1] = elapsed(clone, objCalendarDate2, Calendar.MONTH);
        
        clone.add(Calendar.MONTH, elapsed[1]);
        elapsed[2] = elapsed(clone, objCalendarDate2, Calendar.DATE);
        
//        clone.add(Calendar.DATE, elapsed[2]);
//        elapsed[3] = (int) (objCalendarDate2.getTimeInMillis() - clone.getTimeInMillis()) / 3600000;
//        
//        clone.add(Calendar.HOUR, elapsed[3]);
//        elapsed[4] = (int) (objCalendarDate2.getTimeInMillis() - clone.getTimeInMillis()) / 60000;
//        
//        clone.add(Calendar.MINUTE, elapsed[4]);
//        elapsed[5] = (int) (objCalendarDate2.getTimeInMillis() - clone.getTimeInMillis()) / 1000;
        
        return elapsed;
	}
	private static int elapsed(Calendar before, Calendar after, int field) {
		Calendar clone = (Calendar) before.clone(); // Otherwise changes are been reflected.
		int elapsed = -1;
		while (!clone.after(after)) {
			clone.add(field, 1);
			elapsed++;
		}
		return elapsed;
	}
	
	/**
	 * 判斷日期是否在起用日和截止日之前，否則視為停用
	 * @param startUsedDate
	 * @param prohibitUsedDate
	 * @param date
	 * @return 停用則回傳true
	 */
	public static boolean isValidDate(Date startUsedDate,Date prohibitUsedDate,Date date){
		boolean boo = false;
		
		if(date == null)
			return boo;
		
		// 輸入日期
		Calendar dkeyinC = Calendar.getInstance();
		dkeyinC.setTime(date);
		// 啟用日期
		if (startUsedDate != null) {
			Calendar startUsedDateC = Calendar.getInstance();
			startUsedDateC.setTime(startUsedDate);
			if (startUsedDateC.before(dkeyinC) || startUsedDateC.equals(dkeyinC)) {// 未達停用日					
			} else {// 停用
				boo = true;
			}
		}
		// 截止日期
		if (prohibitUsedDate != null) {
			Calendar prohibitUsedDateC = Calendar.getInstance();
			prohibitUsedDateC.setTime(prohibitUsedDate);
			if (prohibitUsedDateC.after(dkeyinC) || prohibitUsedDateC.equals(dkeyinC)) {
			} else {// 停用
				boo = true;
			}
		}
		return boo;
	}
	
    public static XMLGregorianCalendar convertToXMLGregorianCalendar(Date date) {
    	if(date == null){
    		return null;
    	}
        GregorianCalendar cal = new GregorianCalendar();
        cal.setTime(date);
        XMLGregorianCalendar gc = null;
        try {
            gc = DatatypeFactory.newInstance().newXMLGregorianCalendar(cal);
        } catch (Exception e) {

             e.printStackTrace();
        }
        return gc;
    }
    
    public static Date convertToDate(XMLGregorianCalendar cal) throws Exception{
    	if(cal == null){
    		return null;
    	}
        GregorianCalendar ca = cal.toGregorianCalendar();
        return ca.getTime();
    }
	
    public static String convertTWDateFormate (String AD,String beforeFormat,String afterFormat){//轉年月格式
        if (AD == null) return "";
        SimpleDateFormat df4 = new SimpleDateFormat(beforeFormat);
        SimpleDateFormat df2 = new SimpleDateFormat(afterFormat);
        Calendar cal = Calendar.getInstance();
        try {
            cal.setTime(df4.parse(AD));
            if (cal.get(Calendar.YEAR) > 1492)   cal.add(Calendar.YEAR, -1911); 
            else cal.add(Calendar.YEAR, +1911); 
            return df2.format(cal.getTime());
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
    
    public static String convertDateFormate (String AD,String beforeFormat,String afterFormat){//轉年月格式
        if (AD == null) return "";
        SimpleDateFormat df4 = new SimpleDateFormat(beforeFormat);
        SimpleDateFormat df2 = new SimpleDateFormat(afterFormat);
        Calendar cal = Calendar.getInstance();
        try {
            cal.setTime(df4.parse(AD));
            return df2.format(cal.getTime());
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
    
	public static void main(String args[]) throws Exception{
		
		String date1 = "101/02/28";
		String date2 = "101/03/01";
		Date bd = StringUtil.stringToDate(DateUtils.transDate(date1,"/"));
		Date ed = StringUtil.stringToDate(DateUtils.transDate(date2,"/"));
		int i = DateUtils.calculateDays2(bd, ed);
		System.out.println(">>> i = " + i);

		System.out.println(getDateDay(new Date(), "", 1));
	}
	
    /**
     * 輸入datetime格式資料,取得日期字串,格式為yyyyMMdd
     */
    public static String getADDateString(Date dt) {
    	return addf.format(dt);
    }
    
    /**
     * 取得目前系統日期的西元年月日,格式為yyyyMMdd
     * @return
     */
    public static String getADSysDateString() {
        return addf.format(new Date());
    }
    
	/**
	 * 取得系統日期時間
	 * yyyyMMddHHmmssSSS
	 */
	public static String getADSysDateTimeString() {

		Date currentTime = new Date();
		SimpleDateFormat timeFormatter = new SimpleDateFormat("yyyyMMddHHmmssSSS", Locale.TAIWAN);
		String dateTime = timeFormatter.format(currentTime);
		return dateTime;
	} 

}