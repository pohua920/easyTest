package com.tlg.tag;

import com.tlg.util.encoders.BasicCode;

public class CustomJstlFunctions {
	
	/**
	 * 編碼
	 * 
	 * @param key 
	 * @param value
	 * @return 
	 */
	public static String encode(String key, String value){
		return BasicCode.encode(key, value);
	}
	
	/**
	 * 解碼
	 * 
	 * @param key
	 * @param value
	 * @return
	 */
	public static String decode(String key, String value){
		return BasicCode.decode(key, value);
	}
}
