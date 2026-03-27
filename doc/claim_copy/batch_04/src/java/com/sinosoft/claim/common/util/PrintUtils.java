package com.sinosoft.claim.common.util;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.sinosoft.claim.schema.model.PrpLcharge;
import com.sinosoft.sysframework.common.datatype.DateTime;
/***
 * 打印工具类
 * @author 中科软
 */
public class PrintUtils {
	public static final SimpleDateFormat yearToDayformatMG = new SimpleDateFormat("yyyy-MM-dd");
	public static final SimpleDateFormat yearToDayformatHZ = new SimpleDateFormat("yyyy年MM月dd日");
	public static final SimpleDateFormat yearToHourformatMG = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	public static final SimpleDateFormat yearToHourformatHZ = new SimpleDateFormat("yyyy年MM月dd日 HH時mm分ss秒");
	public static final String serialNoList = "serialNoList";
	public static final String chargeList =  "chargeList";

	/***
	 * 获取驾驶员性别名称
	 * @param sexCode 性别代码
	 * @return
	 */
	public static String getDriverSexName(String sexCode) {
		String strSex = "";
		if ("1".equals(sexCode)) {
			strSex = "男";// 驾驶员性别
		} else if ("2".equals(sexCode)) {
			strSex = "女";// 驾驶员性别
		} else if ("3".equals(sexCode)) {
			strSex = "不確定";// 驾驶员性别
		}
		return strSex;
	}

	/***
	 * 根据Date类型获取民国时间(年月日)
	 * @param date  西元年时间
	 * @return
	 */
	public static String getYearToDayMGName(Date date) {
		String strDate = "";
		if (date != null) {
			strDate = CommonUtils.getMGDateStr(date, yearToDayformatHZ);
		}
		return strDate;
	}

	/***
	 * 根据Date类型获取民国时间(年月日 时分秒)
	 * @param date 西元年时间
	 * @return
	 */
	public static String getYearToHourMGName(Date date) {
		String strDate = "";
		if (date != null) {
			strDate = CommonUtils.getMGDateStr(date, yearToHourformatHZ);
		}
		return strDate;
	}

	/***
	 * 根据Date类型获取民国时间(yyy-MM-dd)
	 * @param date 西元年时间
	 * @return
	 */
	public static String getYearToDayMGStr(Date date) {
		String strDate = "";
		if (date != null) {
			strDate = CommonUtils.getMGDateStr(date, yearToDayformatMG);
		}
		return strDate;
	}

	/***
	 * 根据Date类型获取民国时间(yyy-MM-dd HH:mm:ss)
	 * @param date 西元年时间
	 * @return
	 */
	public static String getYearToHourMGStr(Date date) {
		String strDate = "";
		if (date != null) {
			strDate = CommonUtils.getMGDateStr(date, yearToHourformatMG);
		}
		return strDate;
	}
	
	/***
	 * 获取驾驶员是否已婚
	 * @param marriedCode 是否婚配代码
	 * @return
	 */
	public static String getDriverIsMarried(String marriedCode) {
		String married = "";
		if ("0".equals(marriedCode)) {
			married = "未婚";// 驾驶员婚姻
		} else if ("1".equals(marriedCode)) {
			married = "已婚";// 驾驶员婚姻
		}
		return married;
	}
	
	/**
	 * 根据PrpLclaim 的 DamageStartDate 和  DamageStartHour 获取打印时所需的出险时间
	 * 根据PrpLCmain 的 startdate ，startHour，enddate，endhour 获取打印时所需的保险期间
	 * @param damageDay
	 * @param damageHour
	 * @return
	 */
	public static String getDamageDate(Date damageDay, String damageHour){
		String hours = "";
		String minutes = "";
		if(!CommonUtils.isEmpty(damageHour)){
			String[] damageHours = damageHour.split(":");
			hours = damageHours.length > 0 ? damageHours[0] + "時" : "" ;
			minutes = damageHours.length > 1 ? damageHours[1] + "分" : "";
		}
		return getYearToDayMGName(new DateTime(damageDay)) + hours + minutes;
	}
	
	/**
	 * 数字金额大写转换，思想先写个完整的然后将如零拾替换成零
	 * 要用到正则表达式
	 * @param n
	 * @return
	 */
	public static String digitUppercase(double n){
		String fraction[] = {"角", "分"};
	    String digit[] = { "零", "壹", "貳", "叁", "肆", "伍", "陸", "柒", "捌", "玖" };
	    String unit[][] = {{"元", "萬", "億"},{"", "拾", "佰", "仟"}};

	    String head = n < 0? "負": "";
	    n = Math.abs(n);
	    
	    String s = "";
	    for (int i = 0; i < fraction.length; i++) {
	        s += (digit[(int)(Math.floor(n * 10 * Math.pow(10, i)) % 10)] + fraction[i]).replaceAll("(零.)+", "");
	    }
	    if(s.length()<1){
		    s = "整";	
	    }
	    int integerPart = (int)Math.floor(n);

	    for (int i = 0; i < unit[0].length && integerPart > 0; i++) {
	        String p ="";
	        for (int j = 0; j < unit[1].length && n > 0; j++) {
	            p = digit[integerPart%10]+unit[1][j] + p;
	            integerPart = integerPart/10;
	        }
	        s = p.replaceAll("(零.)*零$", "").replaceAll("^$", "零") + unit[0][i] + s;
	    }
	    return head + s.replaceAll("(零.)*零元", "元").replaceFirst("(零.)+", "").replaceAll("(零.)+", "零").replaceAll("^整$", "零元整");
	}
	/**
	 * 錢幣轉換
	 * @param iFee
	 * @param iCurrency
	 * @return
	 */
	public static String toChinese(double iFee, String iCurrency) {
		String strChineseMoney = "";
		String strNumber = "              ";
		String strFee = "";
		String strThat = "";
		int intLength = 0;
		int i = 0;
		int j = 0;
		if ((iCurrency == null) || (iCurrency.length() == 0))
			iCurrency = "CNY";
		if (iFee < 0.0D) {
			throw new IllegalArgumentException("金額不能為負");
		}
		if (iFee == 0.0D)
			return strChineseMoney;
		strFee = new DecimalFormat("0").format(iFee * 100.0D);
		intLength = strFee.length();
		if (intLength > 14) {
			throw new IllegalArgumentException("金額超出範圍");
		}
		strNumber = strNumber.substring(0, 14 - intLength) + strFee;
		for (i = 14 - intLength; i < 14; ++i) {
			j = new Integer(strNumber.substring(i, i + 1)).intValue();
			if (j > 0) {
				strChineseMoney = strChineseMoney.trim() + strThat.trim() + getUpperChineseDigit(j).trim() + getUpperChineseUnit(i, iCurrency);
				strThat = "";
			} else if (strChineseMoney.length() != 0) {
				if (i == 11){
					strChineseMoney = strChineseMoney + getUpperChineseUnit(11, iCurrency);
				}else if ((i == 7) && (!(strNumber.substring(4, 8).equals("0000")))){
					strChineseMoney = strChineseMoney + "萬";
				}else if ((i == 3) && (!(strNumber.substring(0, 4).equals("0000")))) {
					strChineseMoney = strChineseMoney + "億";
				}
				if ((i < 11) || (i == 12)) {
					strThat = getUpperChineseDigit(0);
				}
			}
		}
		if (strChineseMoney.endsWith("拾")) {
			strChineseMoney = strChineseMoney + "分";
		}
		if (strChineseMoney.endsWith("圓")) {
			strChineseMoney = strChineseMoney + "整";
		}
		if (strChineseMoney.endsWith("角")) {
			strChineseMoney = strChineseMoney + "整";
		}
		return strChineseMoney;
	}

	public static String getUpperChineseDigit(int iDigit) {
		String strUpperChineseDigit = "";
		String strUpperChineseChar = "零壹貳叁肆伍陸柒捌玖";
		if (iDigit > 9)
			throw new IllegalArgumentException("金額超出範圍");
		if (iDigit < 0)
			throw new IllegalArgumentException("金額不能為負");
		strUpperChineseDigit = strUpperChineseChar.substring(iDigit, iDigit + 1);
		return strUpperChineseDigit;
	}

	private static String getUpperChineseUnit(int iPoint, String iCurrency) {
		String strUpperChineseUnit = "";
		String strUpperChineseUnitChar = "";
		if (iPoint > 13)
			throw new IllegalArgumentException("金額超出範圍");
		if (iPoint < 0)
			throw new IllegalArgumentException("金額不能為負");
		iCurrency = iCurrency.trim();
		if (iCurrency.equals("CNY")) {
			strUpperChineseUnitChar = "仟佰拾億仟佰拾萬仟佰拾圓角分";
		} else if (iCurrency.equals("HKD")) {
			strUpperChineseUnitChar = "仟佰拾億仟佰拾萬仟佰拾圓角分";
		} else if (iCurrency.equals("JPY")) {
			strUpperChineseUnitChar = "仟佰拾億仟佰拾萬仟佰拾圓角分";
		} else if (iCurrency.equals("GBP")) {
			strUpperChineseUnitChar = "仟佰拾億仟佰拾萬仟佰拾鎊先令便士";
		} else {
			strUpperChineseUnitChar = "仟佰拾億仟佰拾萬仟佰拾圓拾分";
		}
		strUpperChineseUnit = strUpperChineseUnitChar.substring(iPoint, iPoint + 1);
		return strUpperChineseUnit;
	}
	/**
	 * 格式化数值#
	 * @param amount
	 * @return
	 */
	public static String getDoubleToStr(Double amount){
		NumberFormat format = new DecimalFormat("#");
		return getDoubleToStr(amount,format);
	}
	/**
	 * 格式化数值 #.##
	 * @param amount
	 * @param format 
	 * @return
	 */
	public static String getDoubleToStr(Double amount,NumberFormat format){
		if(amount!=null&&format!=null){
			return format.format(amount);
		}
		return "0";
	}
	
	/***
	 * 获取赔付公证机构对应的费用信息
	 * @param prpLchargeList 费用列表
	 * @return
	 */
	public static Map<String,List<?>> getExternalAgencySerialno(List<PrpLcharge> prpLchargeList) {
		Map<String,List<?>> chargeMap = new HashMap<String,List<?>>();
		List<Integer> serialNoList = new ArrayList<Integer>();
		List<PrpLcharge> chargeList = new ArrayList<PrpLcharge>();
		if (!CommonUtils.isEmpty(prpLchargeList)) {
			for (PrpLcharge prpLcharge : prpLchargeList) {
				if ("5".equals(prpLcharge.getChargeCode())) {
					serialNoList.add(prpLcharge.getId().getSerialNo());
					chargeList.add(prpLcharge);
				}
			}
		}
		chargeMap.put(PrintUtils.serialNoList, serialNoList);
		chargeMap.put(PrintUtils.chargeList, chargeList);
		return chargeMap;
	}
}
