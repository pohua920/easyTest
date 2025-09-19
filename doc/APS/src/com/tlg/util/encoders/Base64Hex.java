package com.tlg.util.encoders;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.security.NoSuchAlgorithmException;

import org.apache.commons.codec.DecoderException;
import org.apache.commons.codec.binary.Base64;

public class Base64Hex {

	public static String encode(String str) {
		// 編碼
		Base64 base64 = new Base64();
		// 將字串編碼為BASE64
		byte[] b1 = new String(base64.encode(str.getBytes())).getBytes();
		// 將編碼為BASE64的字串轉為16進位
		String encodeStr = Base64Hex.bytesToHexString(b1);

		return encodeStr;
	}

	public static String decode(String str) {
		// 解碼
		Base64 base64 = new Base64();
		// 16進位轉成byte[]
		byte[] byteAry = Base64Hex.hexStringToBytes(str);
		// BASE64解碼
		byte[] strByte = base64.decode(new String(byteAry));
		String decodeStr = new String(strByte);

		return decodeStr;
	}

	/*
	 * Convert byte[] to hex string
	 * 將byte轉換成int，然後利用Integer.toHexString(int)來轉換成16進制字符串。
	 * 
	 * @param src byte[] data
	 * 
	 * @return hex string
	 */
	public static String bytesToHexString(byte[] src) {
		StringBuilder stringBuilder = new StringBuilder("");
		if (src == null || src.length <= 0) {
			return null;
		}
		for (int i = 0; i < src.length; i++) {
			int v = src[i] & 0xFF;
			String hv = Integer.toHexString(v);
			if (hv.length() < 2) {
				stringBuilder.append(0);
			}
			stringBuilder.append(hv);
		}
		return stringBuilder.toString();
	}

	/**
	 * Convert hex string to byte[]
	 * 
	 * @param hexString
	 *            the hex string
	 * @return byte[]
	 */
	public static byte[] hexStringToBytes(String hexString) {
		if (hexString == null || hexString.equals("")) {
			return null;
		}
		hexString = hexString.toUpperCase();
		int length = hexString.length() / 2;
		char[] hexChars = hexString.toCharArray();
		byte[] d = new byte[length];
		for (int i = 0; i < length; i++) {
			int pos = i * 2;
			d[i] = (byte) (charToByte(hexChars[pos]) << 4 | charToByte(hexChars[pos + 1]));
		}
		return d;
	}

	/**
	 * Convert char to byte
	 * 
	 * @param c
	 *            char
	 * @return byte
	 */
	private static byte charToByte(char c) {
		return (byte) "0123456789ABCDEF".indexOf(c);
	}

	public static void main(String[] args) throws NoSuchAlgorithmException,
			FileNotFoundException, IOException, DecoderException {

		String str = "test1,test2,中文測試1,A123456789,中文測試2";
		String encodeStr = Base64Hex.encode(str);
		System.out.println("encodeStr = " + encodeStr);
		System.out.println("decodeStr = " + Base64Hex.decode(encodeStr));

	}

}