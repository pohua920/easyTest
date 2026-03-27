/*
 * Created on 2004-11-3
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.sinosoft.app.common.util;

import ins.framework.utils.StringUtils;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * @author 中科软 TODO To change the template for this generated type comment go
 *         to Window - Preferences - Java - Code Style - Code Templates
 */
public class MD5 {

	private final static String[] hexDigits = { "0", "1", "2", "3", "4", "5", "6", "7", "8", "9", "a", "b", "c", "d", "e", "f" };

	/**
	 * 转换字节数组为16进制字串
	 * @param b 字节数组
	 * @return 16进制字串
	 */

	private static String byteArrayToHexString(byte[] b) {
		StringBuffer buf = new StringBuffer();
		for (int i = 0; i < b.length; i++) {
			buf.append(byteToHexString(b[i]));
		}
		return buf.toString();
	}

	private static String byteToHexString(byte b) {

		return hexDigits[(b & 0xf0) >> 4] + hexDigits[b & 0x0f];
	}

	public static String MD5Encode(String origin) {
		String resultString = null;
		resultString = new String(origin);
		try {
			MessageDigest md = MessageDigest.getInstance("MD5");
			resultString = byteArrayToHexString(md.digest(resultString.getBytes()));
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}

		return StringUtils.upperCase(resultString);
	}
}
