package com.sinosoft.sysframework.common.util;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class StringUtils {
	public static String newString(String value, int length) {
		StringBuffer buffer = new StringBuffer();
		if (value == null) {
			return null;
		}
		for (int i = 0; i < length; ++i) {
			buffer.append(value);
		}
		return buffer.toString();
	}

	public static String newString(char ch, int length) {
		return newString(String.valueOf(ch), length);
	}

	public static String copyString(String str, int copyTimes) {
		StringBuffer buffer = new StringBuffer();
		if (str == null) {
			return null;
		}
		for (int i = 0; i < copyTimes; ++i) {
			buffer.append(str);
		}
		return buffer.toString();
	}

	public static int getBytesLength(String str) {
		if (str == null) {
			return -1;
		}
		return str.getBytes().length;
	}

	public static int indexOf(String str, String subStr, int startIndex, int occurrenceTimes) {
		int foundCount = 0;
		int index = startIndex;
		int substrLength = subStr.length();
		if (occurrenceTimes <= 0)
			return -1;
		if (str.length() - 1 < startIndex)
			return -1;
		if (subStr.equals(""))
			return 0;
		while (foundCount < occurrenceTimes) {
			index = str.indexOf(subStr, index);
			if (index == -1)
				return -1;
			++foundCount;
			index += substrLength;
		}
		return (index - substrLength);
	}

	public static int indexOf(String str, String subStr, int occurrenceTimes) {
		return indexOf(str, subStr, 0, occurrenceTimes);
	}

	public static int indexOf(String str, String subStr, int fromIndex, boolean caseSensitive) {
		if (!(caseSensitive)) {
			return str.toLowerCase().indexOf(subStr.toLowerCase(), fromIndex);
		}
		return str.indexOf(subStr, fromIndex);
	}

	public static String replace(String str, String searchStr, String replaceStr, boolean caseSensitive) {
		String result = "";
		int i = 0;
		int j = 0;
		if (str == null) {
			return null;
		}
		if (str.equals("")) {
			return "";
		}
		if ((searchStr == null) || (searchStr.equals(""))) {
			return str;
		}
		if (replaceStr == null) {
			replaceStr = "";
		}
		while ((j = indexOf(str, searchStr, i, caseSensitive)) > -1) {
			result = result + str.substring(i, j) + replaceStr;
			i = j + searchStr.length();
		}
		result = result + str.substring(i, str.length());
		return result;
	}

	public static String replace(String str, String searchStr, String replaceStr) {
		return replace(str, searchStr, replaceStr, true);
	}

	public static String replace(String str, char searchChar, String replaceStr) {
		return replace(str, searchChar + "", replaceStr, true);
	}

	public static String replace(String str, int beginIndex, String replaceStr) {
		String result = null;
		if (str == null) {
			return null;
		}
		if (replaceStr == null) {
			replaceStr = "";
		}
		result = str.substring(0, beginIndex) + replaceStr + str.substring(beginIndex + replaceStr.length());

		return result;
	}
	
	public static String[] split(String originalString, int splitByteLength) {
		List<String> vector = new ArrayList<String>();
		if (originalString == null) {
			return new String[0];
		} else if (originalString.equals("")) {
			return new String[0];
		} else if (originalString.trim().equals("")) {
			return (new String[] { "" });
		} else if (splitByteLength <= 1) {
			return (new String[] { originalString });
		}
		StringBuilder sb = new StringBuilder();
		int index = 0;// 当前索引
		int length = originalString.length();// 字符长
		int b = 0;
		do {
			char c = originalString.charAt(index);
			byte[] s = String.valueOf(c).getBytes();
			int l = s.length;// c 字节长
			b += l;
			if (b <= splitByteLength) {
				sb.append(c);
			} else {// 如果超出，则存储，再初始化一批
				vector.add(sb.toString());
				sb = new StringBuilder().append(c);
				b = l;
			}
			if(index == length - 1){
				if(sb.length() > 0){
					vector.add(sb.toString());
				}
				break;
			}
			index++;
		} while (true);
		String[] temp = new String[vector.size()];
		vector.toArray(temp);
		return temp;
	}
//
//  因存在切割亂碼問題被斃掉 例如splits("0時1時2時3時", 9)
//	public static String[] split(String originalString, int splitByteLength) {
//		ArrayList vector = new ArrayList();
//		String strText = "";
//		byte[] arrByte = null;
//		int intStartIndex = 0;
//		int intEndIndex = 0;
//		int index = 0;
//		int count = 0;
//		String[] arrReturn = null;
//
//		if (originalString == null) {
//			return new String[0];
//		}
//		if (originalString.equals("")) {
//			return new String[0];
//		}
//		if (originalString.trim().equals("")) {
//			return new String[] { "" };
//		}
//		if (splitByteLength <= 1) {
//			return new String[] { originalString };
//		}
//
//		arrByte = originalString.getBytes();
//		intEndIndex = 0;
//		while (true) {
//			intStartIndex = intEndIndex;
//			intEndIndex = intStartIndex + splitByteLength;
//
//			if (intStartIndex >= arrByte.length) {
//				break;
//			}
//			if (intEndIndex > arrByte.length) {
//				intEndIndex = arrByte.length;
//				strText = new String(arrByte, intStartIndex, intEndIndex - intStartIndex);
//
//				vector.add(strText);
//				break;
//			}
//
//			count = 0;
//			for (index = intStartIndex; index < intEndIndex; ++index) {
//				if (arrByte[index] < 0) {
//					++count;
//				}
//			}
//			if (count % 2 != 0) {
//				--intEndIndex;
//			}
//			strText = new String(arrByte, intStartIndex, intEndIndex - intStartIndex);
//
//			vector.add(strText);
//		}
//
//		arrReturn = new String[vector.size()];
//		for (index = 0; index < vector.size(); ++index) {
//			arrReturn[index] = ((String) vector.get(index));
//		}
//		return arrReturn;
//	}

	public static String[] split(String originalString, String delimiterString) {
		int index = 0;
		String[] returnArray = null;
		int length = 0;

		if ((originalString == null) || (delimiterString == null) || (originalString.equals(""))) {
			return new String[0];
		}

		if ((originalString.equals("")) || (delimiterString.equals("")) || (originalString.length() < delimiterString.length())) {
			return new String[] { originalString };
		}

		String strTemp = originalString;
		while ((strTemp != null) && (!(strTemp.equals("")))) {
			index = strTemp.indexOf(delimiterString);
			if (index == -1) {
				break;
			}
			++length;
			strTemp = strTemp.substring(index + delimiterString.length());
		}
		returnArray = new String[++length];

		for (int i = 0; i < length - 1; ++i) {
			index = originalString.indexOf(delimiterString);
			returnArray[i] = originalString.substring(0, index);
			originalString = originalString.substring(index + delimiterString.length());
		}

		returnArray[(length - 1)] = originalString;
		return returnArray;
	}

	public static String rightTrim(String str) {
		if (str == null) {
			return "";
		}
		int length = str.length();
		for (int i = length - 1; i >= 0; --i) {
			if (str.charAt(i) != ' ') {
				break;
			}
			--length;
		}
		return str.substring(0, length);
	}

	public static String leftTrim(String str) {
		if (str == null) {
			return "";
		}
		int start = 0;
		int i = 0;
		for (int n = str.length(); i < n; ++i) {
			if (str.charAt(i) != ' ') {
				break;
			}
			++start;
		}
		return str.substring(start);
	}

	public static String absoluteTrim(String str) {
		String result = replace(str, " ", "");
		return result;
	}

	public static String lowerCase(String str, int beginIndex, int endIndex) {
		StringBuffer buffer = new StringBuffer();
		buffer.append(str.substring(0, beginIndex));
		buffer.append(str.substring(beginIndex, endIndex).toLowerCase());
		buffer.append(str.substring(endIndex));
		return buffer.toString();
	}

	public static String upperCase(String str, int beginIndex, int endIndex) {
		StringBuffer buffer = new StringBuffer();
		buffer.append(str.substring(0, beginIndex));
		buffer.append(str.substring(beginIndex, endIndex).toUpperCase());
		buffer.append(str.substring(endIndex));
		return buffer.toString();
	}

	public static String lowerCaseFirstChar(String iString) {
		String newString = iString.substring(0, 1).toLowerCase() + iString.substring(1);

		return newString;
	}

	public static String upperCaseFirstChar(String iString) {
		String newString = iString.substring(0, 1).toUpperCase() + iString.substring(1);

		return newString;
	}

	public static int timesOf(String str, String subStr) {
		int foundCount = 0;
		if (subStr.equals("")) {
			return 0;
		}
		int fromIndex = str.indexOf(subStr);
		while (fromIndex != -1) {
			++foundCount;
			fromIndex = str.indexOf(subStr, fromIndex + subStr.length());
		}
		return foundCount;
	}

	public static int timesOf(String str, char ch) {
		int foundCount = 0;
		int fromIndex = str.indexOf(ch);
		while (fromIndex != -1) {
			++foundCount;
			fromIndex = str.indexOf(ch, fromIndex + 1);
		}
		return foundCount;
	}

	public static Map toMap(String str, String splitString) {
		Map map = Collections.synchronizedMap(new HashMap());
		String[] values = split(str, splitString);
		for (int i = 0; i < values.length; ++i) {
			String tempValue = values[i];
			int pos = tempValue.indexOf("=");
			String key = "";
			String value = "";
			if (pos > -1) {
				key = tempValue.substring(0, pos);
				value = tempValue.substring(pos + splitString.length());
			} else {
				key = tempValue;
			}
			map.put(key, value);
		}
		return map;
	}
}