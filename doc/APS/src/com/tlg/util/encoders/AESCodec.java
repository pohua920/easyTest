package com.tlg.util.encoders;

import java.io.UnsupportedEncodingException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.Key;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.KeyGenerator;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

import org.apache.commons.codec.binary.Base64;

public class AESCodec {

	/**
	 * 預設的Initialization Vector，為16 Bits的0
	 */
	private static final IvParameterSpec DEFAULT_IV = new IvParameterSpec(new byte[]{0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0});

	/**
	 * 密鑰算法
	 */
	private static final String KEY_ALGORITHM = "AES";

	private static final String DEFAULT_CIPHER_ALGORITHM_A = "AES/ECB/PKCS5Padding";
	
	private static final String DEFAULT_CIPHER_ALGORITHM_B = "AES/CBC/PKCS5Padding";

	/**
	 * 初始化密鑰
	 * 
	 * @return byte[] 密鑰
	 * @throws Exception
	 */
	public static byte[] initSecretKey() {
		// 返回生成指定算法的秘密密鑰的 KeyGenerator 對象
		KeyGenerator kg = null;
		try {
			kg = KeyGenerator.getInstance(KEY_ALGORITHM);
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
			return new byte[0];
		}
		// 初始化此密鑰生成器，使其具有確定的密鑰大小
		// AES 要求密鑰長度為 128
		kg.init(128);
		// 生成一個密鑰
		SecretKey secretKey = kg.generateKey();
		return secretKey.getEncoded();
	}

	/**
	 * 轉換密鑰
	 * 
	 * @param key
	 *            二進制密鑰
	 * @return 密鑰
	 */
	private static Key toKey(byte[] key) {
		// 生成密鑰
		return new SecretKeySpec(key, KEY_ALGORITHM);
	}

	/**
	 * 加密
	 * 
	 * @param data
	 *            待加密數據
	 * @param key
	 *            密鑰
	 * @return byte[] 加密數據
	 * @throws Exception
	 */
	public static byte[] encrypt(byte[] data, Key key) throws Exception {
		return encrypt(data, key, DEFAULT_CIPHER_ALGORITHM_A);
	}

	/**
	 * 加密
	 * 
	 * @param data
	 *            待加密數據
	 * @param key
	 *            二進制密鑰
	 * @return byte[] 加密數據
	 * @throws Exception
	 */
	public static byte[] encrypt(byte[] data, byte[] key) throws Exception {
		return encrypt(data, key, DEFAULT_CIPHER_ALGORITHM_A);
	}

	/**
	 * 加密
	 * 
	 * @param data
	 *            待加密數據
	 * @param key
	 *            二進制密鑰
	 * @param cipherAlgorithm
	 *            加密算法/工作模式/填充方式
	 * @return byte[] 加密數據
	 * @throws Exception
	 */
	public static byte[] encrypt(byte[] data, byte[] key, String cipherAlgorithm)
			throws Exception {
		// 還原密鑰
		Key k = toKey(key);
		return encrypt(data, k, cipherAlgorithm);
	}

	/**
	 * 加密
	 * 
	 * @param data
	 *            待加密數據
	 * @param key
	 *            密鑰
	 * @param cipherAlgorithm
	 *            加密算法/工作模式/填充方式
	 * @return byte[] 加密數據
	 * @throws Exception
	 */
	public static byte[] encrypt(byte[] data, Key key, String cipherAlgorithm)
			throws Exception {
		// 實例化
		Cipher cipher = Cipher.getInstance(cipherAlgorithm);
		// 使用密鑰初始化，設置為加密模式
		cipher.init(Cipher.ENCRYPT_MODE, key);
		// 執行操作
		return cipher.doFinal(data);
	}

	/**
	 * 解密
	 * 
	 * @param data
	 *            待解密數據
	 * @param key
	 *            二進制密鑰
	 * @return byte[] 解密數據
	 * @throws Exception
	 */
	public static byte[] decrypt(byte[] data, byte[] key) throws Exception {
		return decrypt(data, key, DEFAULT_CIPHER_ALGORITHM_A);
	}

	/**
	 * 解密
	 * 
	 * @param data
	 *            待解密數據
	 * @param key
	 *            密鑰
	 * @return byte[] 解密數據
	 * @throws Exception
	 */
	public static byte[] decrypt(byte[] data, Key key) throws Exception {
		return decrypt(data, key, DEFAULT_CIPHER_ALGORITHM_A);
	}

	/**
	 * 解密
	 * 
	 * @param data
	 *            待解密數據
	 * @param key
	 *            二進制密鑰
	 * @param cipherAlgorithm
	 *            加密算法/工作模式/填充方式
	 * @return byte[] 解密數據
	 * @throws Exception
	 */
	public static byte[] decrypt(byte[] data, byte[] key, String cipherAlgorithm)
			throws Exception {
		// 還原密鑰
		Key k = toKey(key);
		return decrypt(data, k, cipherAlgorithm);
	}

	/**
	 * 解密
	 * 
	 * @param data
	 *            待解密數據
	 * @param key
	 *            密鑰
	 * @param cipherAlgorithm
	 *            加密算法/工作模式/填充方式
	 * @return byte[] 解密數據
	 * @throws Exception
	 */
	public static byte[] decrypt(byte[] data, Key key, String cipherAlgorithm)
			throws Exception {
		// 實例化
		Cipher cipher = Cipher.getInstance(cipherAlgorithm);
		// 使用密鑰初始化，設置為解密模式
		cipher.init(Cipher.DECRYPT_MODE, key);
		// 執行操作
		return cipher.doFinal(data);
	}

	private static String showByteArray(byte[] data) {
		if (null == data) {
			return null;
		}
		StringBuilder sb = new StringBuilder("{");
		for (byte b : data) {
			sb.append(b).append(",");
		}
		sb.deleteCharAt(sb.length() - 1);
		sb.append("}");
		return sb.toString();
	}

	public static String bytesToHexString(byte[] src){  
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
	 * 加密資料
	 *
	 * @param keyStr 傳入任意長度的AES密鑰
	 * @param bit 傳入AES密鑰長度，數值可以是128、256 (Bits) (使用128 Bits或是256 Bits的AES密鑰(計算任意長度密鑰的MD5或是SHA256)，用MD5計算IV值
	 * @param ivStr 傳入任意長度的IV字串 (AES CBC模式使用的Initialization Vector)
	 * @param data 要加密的字串
	 * @throws NoSuchPaddingException 
	 * @throws NoSuchAlgorithmException 
	 * @throws InvalidAlgorithmParameterException 
	 * @throws InvalidKeyException 
	 * @throws BadPaddingException 
	 * @throws IllegalBlockSizeException 
	 * @throws UnsupportedEncodingException 
	 */
	public static String encrypt(String keyStr, int bit, String ivStr, String data) throws NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeyException, InvalidAlgorithmParameterException, IllegalBlockSizeException, BadPaddingException, UnsupportedEncodingException {
		byte[] encryptData = encrypt(keyStr, bit, ivStr, data.getBytes("UTF-8"));
		return Base64.encodeBase64String(encryptData);
	}
	/**
	 * 加密資料
	 *
	 * @param keyStr 傳入任意長度的AES密鑰
	 * @param bit 傳入AES密鑰長度，數值可以是128、256 (Bits) (使用128 Bits或是256 Bits的AES密鑰(計算任意長度密鑰的MD5或是SHA256)，用MD5計算IV值
	 * @param ivStr 傳入任意長度的IV字串 (AES CBC模式使用的Initialization Vector)
	 * @param data 要加密的字串
	 * @throws NoSuchPaddingException 
	 * @throws NoSuchAlgorithmException 
	 * @throws InvalidAlgorithmParameterException 
	 * @throws InvalidKeyException 
	 * @throws BadPaddingException 
	 * @throws IllegalBlockSizeException 
	 */
	public static byte[] encrypt(String keyStr, int bit, String ivStr, byte[] data) throws NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeyException, InvalidAlgorithmParameterException, IllegalBlockSizeException, BadPaddingException {
		Key key;
		IvParameterSpec iv;
		if (bit == 256) {
			key = new SecretKeySpec(getHash("SHA-256", keyStr), KEY_ALGORITHM);
		} else {
			key = new SecretKeySpec(getHash("MD5", keyStr), KEY_ALGORITHM);
		}
		if (!"".equals(ivStr) && ivStr != null) {
			iv = new IvParameterSpec(getHash("MD5", ivStr));
		} else {
			iv = DEFAULT_IV;
		}
		Cipher cipher = Cipher.getInstance(DEFAULT_CIPHER_ALGORITHM_B);
		cipher.init(Cipher.ENCRYPT_MODE, key, iv);
		final byte[] encryptData = cipher.doFinal(data);
		return encryptData;
	}
	
	/**
	 * 
	 * 解密文字
	 * 
	 * @param keyStr 傳入任意長度的AES密鑰
	 * @param bit 傳入AES密鑰長度，數值可以是128、256 (Bits) (使用128 Bits或是256 Bits的AES密鑰(計算任意長度密鑰的MD5或是SHA256)，用MD5計算IV值
	 * @param ivStr 傳入任意長度的IV字串 (AES CBC模式使用的Initialization Vector)
	 * @param data 要解密的字串
	 * @return 傳回解密後的文字
	 * @throws NoSuchAlgorithmException
	 * @throws NoSuchPaddingException
	 * @throws InvalidKeyException
	 * @throws InvalidAlgorithmParameterException
	 * @throws IllegalBlockSizeException
	 * @throws BadPaddingException
	 */
	public static String decrypt(String keyStr, int bit, String ivStr, String data) throws NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeyException, InvalidAlgorithmParameterException, IllegalBlockSizeException, BadPaddingException, UnsupportedEncodingException {
		byte[] decryptData = decrypt(keyStr, bit, ivStr, Base64.decodeBase64(data));
		return new String(decryptData,"UTF-8");
	}
	
	/**
	 * 
	 * 解密文字
	 * 
	 * @param keyStr 傳入任意長度的AES密鑰
	 * @param bit 傳入AES密鑰長度，數值可以是128、256 (Bits) (使用128 Bits或是256 Bits的AES密鑰(計算任意長度密鑰的MD5或是SHA256)，用MD5計算IV值
	 * @param ivStr 傳入任意長度的IV字串 (AES CBC模式使用的Initialization Vector)
	 * @param data 要解密的字串byte[]
	 * @return 傳回解密後的文字byte[]
	 * @throws NoSuchAlgorithmException
	 * @throws NoSuchPaddingException
	 * @throws InvalidKeyException
	 * @throws InvalidAlgorithmParameterException
	 * @throws IllegalBlockSizeException
	 * @throws BadPaddingException
	 */
	public static byte[] decrypt(String keyStr, int bit, String ivStr, byte[] data) throws NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeyException, InvalidAlgorithmParameterException, IllegalBlockSizeException, BadPaddingException {
		
		Key key;
		IvParameterSpec iv;
		if (bit == 256) {
			key = new SecretKeySpec(getHash("SHA-256", keyStr), KEY_ALGORITHM);
		} else {
			key = new SecretKeySpec(getHash("MD5", keyStr), KEY_ALGORITHM);
		}
		if (!"".equals(ivStr) && ivStr != null) {
			iv = new IvParameterSpec(getHash("MD5", ivStr));
		} else {
			iv = DEFAULT_IV;
		}
		Cipher cipher = Cipher.getInstance(DEFAULT_CIPHER_ALGORITHM_B);
		cipher.init(Cipher.DECRYPT_MODE, key, iv);
		byte[] decryptData = cipher.doFinal(data);
		
		return decryptData;
	}
	
	/**
	 * 取得字串的雜湊值
	 *
	 * @param algorithm 傳入雜驟演算法
	 * @param text 傳入要雜湊的字串
	 * @return 傳回雜湊後資料內容
	 */
	private static byte[] getHash(final String algorithm, final String text) {
		try {
			return getHash(algorithm, text.getBytes("UTF-8"));
		} catch (final Exception ex) {
			throw new RuntimeException(ex.getMessage());
		}
	}
	/**
	 * 取得資料的雜湊值
	 *
	 * @param algorithm 傳入雜驟演算法
	 * @param data 傳入要雜湊的資料
	 * @return 傳回雜湊後資料內容
	 */
	private static byte[] getHash(final String algorithm, final byte[] data) {
		try {
			final MessageDigest digest = MessageDigest.getInstance(algorithm);
			digest.update(data);
			return digest.digest();
		} catch (final Exception ex) {
			throw new RuntimeException(ex.getMessage());
		}
	}
	
	
	/** 
	 * Convert hex string to byte[] 
	 * @param hexString the hex string 
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
	 * @param c char 
	 * @return byte 
	 */  
	 private static byte charToByte(char c) {  
	    return (byte) "0123456789ABCDEF".indexOf(c);  
	 }
	 
	 public static void main(String[] args) throws Exception {
		byte[] key = initSecretKey();
		System.out.println("key：" + showByteArray(key));

		Key k = toKey(key);
		System.out.println("AES ECB ***************************************************************");
		String data = "AES數據";
		System.out.println("加密前數據: string:" + data);
		System.out.println("加密前數據: byte[]:" + showByteArray(data.getBytes()));
		System.out.println();
		byte[] encryptData = encrypt(data.getBytes(), k);
		System.out.println("加密後數據: byte[]:" + showByteArray(encryptData));
		System.out.println("加密後數據: hexStr:" + bytesToHexString(encryptData));
		String base64Str = Base64.encodeBase64String(encryptData);
		System.out.println("Base64 : " + base64Str);

		System.out.println();
		byte[] decryptData = decrypt(encryptData, k);
		System.out.println("解密後數據: byte[]:" + showByteArray(decryptData));
		System.out.println("解密後數據: string:" + new String(decryptData));

		/**********************************************************************/
		System.out.println("AES CBC ******************************************************************");
		int bit = 192;
		String ivStr = "5ftgvh9ijklk";
		String keyStr = "hjuk9fui0iphjniy76frt5y";
		System.out.println("加密前數據: string:" + data);
		String encryptStr = encrypt(keyStr, bit, ivStr, data);
		System.out.println("加密後數據: base64:" + encryptStr);
		System.out.println("解密後數據: string:" + decrypt(keyStr, bit, ivStr, encryptStr));
	}
}
