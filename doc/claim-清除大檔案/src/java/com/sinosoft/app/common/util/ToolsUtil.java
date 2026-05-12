package com.sinosoft.app.common.util;

import java.io.Serializable;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

/**
 * 工具类
 * @Company: sinosig
 * @author 中科软
 */

public class ToolsUtil {

	/**
	 * 判断对象数组是否为空或null
	 */
	public static boolean isEmpty(final Object[] objs) {

		return (objs == null) || (objs.length <= 0);
	}

	/***
	 * Description: 判断list集合是否为空
	 * @param list
	 * @return true表示为空
	 */
	@SuppressWarnings("unchecked")
	public static boolean isEmpty(final List list) {
		if (list == null || list.isEmpty()) {
			return true;
		}
		return false;
	}

	/**
	 * 判断Collection对象是否为空或没有值
	 */
	@SuppressWarnings("unchecked")
	public static boolean isEmpty(final Collection obj) {

		return (obj == null) || (obj.size() <= 0);
	}

	/**
	 * 判断ｓｅｔ对象是否为空或没有值
	 */
	@SuppressWarnings("unchecked")
	public static boolean isEmpty(final Set obj) {

		return (obj == null) || (obj.size() <= 0);
	}

	/**
	 * 判断持久化对象是否为空
	 */
	public static boolean isEmpty(final Serializable obj) {

		return obj == null;
	}

	/**
	 * 判断Map对象是否为空
	 */
	@SuppressWarnings("unchecked")
	public static boolean isEmpty(final Map obj) {

		return (obj == null) || (obj.size() <= 0);
	}

	/***
	 * Description: 判断map中指定key的value是否存在
	 * @param map
	 * @return true表示不存在
	 */
	@SuppressWarnings("unchecked")
	public static boolean isNotExist(Map map, String key) {
		if (map == null || map.isEmpty())
			return true;
		if (map.get(key) == null || "".equals(map.get(key)))
			return true;
		return false;
	}

	/**
	 * OutputStream转化为InputStream方法
	 * @return InputStream
	 */
	public static ByteArrayInputStream OutToIn(ByteArrayOutputStream ops) throws IOException {
		byte[] ba = new byte[1024 * 4];
		try {
			ba = ops.toByteArray();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return new ByteArrayInputStream(ba);
	}

	/**
	 * 用於Html展现的字符串如果含有特殊字符，调用该方法转义，可支持对於空格符的转义.
	 * @param strText
	 * @param bConvertSpace : true --对空格字符转义成"&nbsp;"; false -- 不对空格字符转义。
	 * @return
	 */
	public static String EscapeString(String strText, boolean bConvertSpace) {
		try {
			StringBuffer strBuffer = new StringBuffer("");
			int n = 0;
			char ch = 0;
			int nCode = 0;

			if (strText == null || strText.length() == 0)
				return strText;

			for (n = 0; n < strText.length(); n++) {
				ch = strText.charAt(n);
				nCode = ch;

				if (nCode == 32) {
					if (bConvertSpace)
						strBuffer.append("&nbsp;");
					else
						strBuffer.append(ch);
				} else if (nCode == 160) {
					if (bConvertSpace)
						strBuffer.append("&nbsp;");
					else
						strBuffer.append(ch);
				} else if (nCode < 32 || nCode == '\'')
					strBuffer.append("&#" + nCode + ";");
				else if (nCode == '&')
					strBuffer.append("&amp;");
				else if (nCode == '<')
					strBuffer.append("&lt;");
				else if (nCode == '>')
					strBuffer.append("&gt;");
				else if (nCode == '"')
					strBuffer.append("&quot;");
				else if (nCode == ',')
					strBuffer.append("&#44;");
				else
					strBuffer.append(ch);
			}

			return strBuffer.toString();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 用於Html展现的字符串如果含有特殊字符，调用该方法转义
	 * @param strText
	 * @return
	 */
	public static String EscapeString(String strText) {
		try {
			StringBuffer strBuffer = new StringBuffer("");
			int n = 0;
			char ch = 0;
			int nCode = 0;
			if (strText == null || strText.length() == 0)
				return strText;

			for (n = 0; n < strText.length(); n++) {
				ch = strText.charAt(n);
				nCode = ch;
				if (nCode == '"')
					strBuffer.append("&quot;");
				else if (nCode == ',')
					strBuffer.append("&#44;");
				else if (nCode == '&')
					strBuffer.append("&amp;");
				else if (nCode == '<')
					strBuffer.append("&lt;");
				else if (nCode == '>')
					strBuffer.append("&gt;");
				else
					strBuffer.append(ch);
			}
			return strBuffer.toString();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 如果用於js操作中的字符串中含有特殊字符(\,"和\n)，调用该方法对特殊字符转义
	 * @param strText
	 * @return
	 */
	public static String EscapeQuotForJS(String strText) {
		try {
			if (strText != null) {
				strText = strText.replaceAll("\\", "\\\\");
				strText = strText.replaceAll("\"", "\\\"");
				strText = strText.replaceAll("\n", "\\n");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return strText;
	}

	/**
	 * 如果用於js操作中的字符串中含有特殊字符(\,",\n和空格)，调用该方法对特殊字符转义(包含对於空格的转义)
	 * @param strText
	 * @return
	 */
	public static String EscapeQuotForStaticJS(String strText) {
		try {
			if (strText != null) {
				strText = strText.replaceAll("\\", "\\\\");
				strText = strText.replaceAll("\"", "\\\"");
				strText = strText.replaceAll("\n", "\\n");
				strText = strText.replaceAll(" ", "&nbsp;");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return strText;
	}
}
