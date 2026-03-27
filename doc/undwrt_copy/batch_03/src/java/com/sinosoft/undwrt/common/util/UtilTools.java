package com.sinosoft.undwrt.common.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 通用工具类
 * 
 * @author sinosoft
 * 
 * @created 2014-6-13
 */
public class UtilTools {

	/**
	 * Checks if is numeric.
	 * 
	 * @param str
	 *            the str
	 * @return true, if is numeric
	 */
	public static boolean isNumeric(String str) {
		if ("".equals(str) || null == str) {
			return false;
		}
		Pattern pattern = Pattern.compile("-?[0-9]+.?[0-9]+");
		Matcher isNum = pattern.matcher(str);
		if (!isNum.matches()) {
			return false;
		}
		return true;
	}
	
	/*
	mantis： OTH0139，處理人員：Sam，需求單編號：OTH0139--- start
	保單內容批改規則異動
	*/
	public static String getNonNullString(String pString) {
        return pString != null ? pString : "";
    }
	/* mantis： OTH0139，處理人員：Sam，需求單編號：OTH0139 --- end */
}
