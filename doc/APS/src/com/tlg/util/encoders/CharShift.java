package com.tlg.util.encoders;

public class CharShift {
	/** 編碼 */
	public static String encode(String str) {
		String encodeStr = "";
		char[] charArr = str.toCharArray();
		for(int i = 0; i < charArr.length; i++){
			encodeStr = encodeStr + (char)(charArr[i] + 100 - 1 * 2);
		}
		return encodeStr;
	}

	/** 解碼 */
	public static String decode(String str) {
		String decodeStr = "";
		char[] charArr = str.toCharArray();
		for(int i = 0; i < charArr.length; i++){
			decodeStr = decodeStr + (char)(charArr[i] - 100 + 1 * 2);
		}
		return decodeStr;
	}
}
