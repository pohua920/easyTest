package com.tlg.filter;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;

import org.apache.commons.lang3.StringUtils;

import com.tlg.util.XssFilterUtil;

public class XSSRequestWrapper extends HttpServletRequestWrapper {

	public XSSRequestWrapper(HttpServletRequest request) {
		super(request);
	}

	// 對陣列引數進行特殊字元過濾
	@Override
	public String[] getParameterValues(String name) {

		String[] values = super.getParameterValues(name);
		if (values == null) {
			return null;
		}
		int count = values.length;
		String[] encodedValues = new String[count];
		for (int i = 0; i < count; i++) {
			encodedValues[i] = clearXss(values[i]);
		}
		return encodedValues;
	}

	// 對引數中特殊字元進行過濾
	@Override
	public String getParameter(String name) {
		String value = super.getParameter(name);
		if (value == null) {
			return null;
		}
		return clearXss(value);
	}

	// 覆蓋getParameterMap方法，將引數名和引數值都做xss & sql過濾 【一般post表單請求，或者前臺接收為實體需要這樣處理】
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public Map getParameterMap() {

		Map<String, Object> paramMap = new HashMap<String, Object>();
		Map<String, String[]> requestMap = super.getParameterMap();
		Iterator<Entry<String, String[]>> it = requestMap.entrySet().iterator();
		while (it.hasNext()) {
			Entry<String, String[]> entry = it.next();
			if (entry.getValue().length == 1) {
				paramMap.put(xssEncode(entry.getKey()),
						xssEncode(entry.getValue()[0]));
			} else {
				String[] values = entry.getValue();
				String value = "";
				for (int i = 0; i < values.length; i++) {
					value = values[i] + ",";
				}
				value = value.substring(0, value.length() - 1);
				paramMap.put(xssEncode(entry.getKey()),
						xssEncode(entry.getValue()[0]));
			}
		}
		return paramMap;
	}

	// 獲取attribute, 特殊字元過濾
	@Override
	public Object getAttribute(String name) {
		Object value = super.getAttribute(name);
		if (value != null && value instanceof String) {
			clearXss((String) value);
		}
		return value;
	}

	// 對請求頭部進行特殊字元過濾
	@Override
	public String getHeader(String name) {

		String value = super.getHeader(name);
		if (value == null) {
			return null;
		}
		return clearXss(value);
	}

	// 特殊字元處理（轉義或刪除）
	private String clearXss(String value) {

		if (StringUtils.isEmpty(value)) {
			return value;
		}

		// 字元編碼不一致，需要轉換。我們系統是UTF-8編碼，這裡不需要
		/*
		 * try { value = new String(value.getBytes("ISO8859-1"), "UTF-8"); }
		 * catch (UnsupportedEncodingException e) { e.printStackTrace(); return
		 * null; }
		 */

		return XssFilterUtil.stripXss(value);
	}

	// 將容易引起xss漏洞的半形字元直接替換成全形字元 在保證不刪除資料的情況下儲存
	private static String xssEncode(String value) {
		if (value == null || value.isEmpty()) {
			return value;
		}
		value = value.replaceAll("eval\\((.*)\\)", "");
		value = value.replaceAll("<", "&lt;");
		value = value.replaceAll(">", "&gt;");
		value = value.replaceAll("'", "&apos;");
		value = value.replaceAll("[\\\"\\\'][\\s]*javascript:(.*)[\\\"\\\']",
				"\"\"");
		value = value.replaceAll("(?i)<script.*?>.*?<script.*?>", "");
		value = value.replaceAll("(?i)<script.*?>.*?</script.*?>", "");
		value = value.replaceAll("(?i)<.*?javascript:.*?>.*?</.*?>", "");
		value = value.replaceAll("(?i)<.*?\\s+on.*?>.*?</.*?>", "");
		// value = value.replaceAll("[<>{}\\[\\];\\&]","");
		return value;
	}
}
