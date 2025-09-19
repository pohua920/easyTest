package com.tlg.util.encoders;



import java.security.Security;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

import org.apache.commons.codec.binary.Base64;
import org.bouncycastle.jce.provider.BouncyCastleProvider;

 
public class AESpkcs7paddingUtil {
	
	/**
	 * 密鑰算法
	 */
	private static final String KEY_ALGORITHM = "AES";
	
	/**
	 * 加密/解密算法 / 工作模式 / 填充方式
	 * Java 6支持PKCS5Padding填充方式
	 * Bouncy Castle支持PKCS7Padding填充方式
	 */
	private static final String CIPHER_ALGORITHM = "AES/CBC/PKCS7Padding";
	
	/**
	 * 偏移量，只有CBC模式才需要
	 */
	private final static String ivParameter = "0000000000000000";
	
	/**
	 * AES要求密鑰長度爲128位或192位或256位，java默認限制AES密鑰長度最多128位
	 */
	public static String sKey="" ;
	
	/**
	 * 編碼格式
	 */
	public static final String ENCODING = "utf-8";
	
	
	static {
		//如果是PKCS7Padding填充方式，則必須加上下面這行
		Security.addProvider(new BouncyCastleProvider());
	}
	
	/**
	 * AES加密
	 * @param source	源字符串
	 * @param key	密鑰
	 * @return	加密後的密文
	 * @throws Exception
	 */
	public static String encrypt(String source, String key, String ivString) throws Exception {
		try {
	        return Base64.encodeBase64String(encryptToByte(source, key, ivString));
		}catch (Exception e) {
			e.printStackTrace();
		}
       return null;
	}
	
	/**
	 * AES加密
	 * @param source	源字符串
	 * @param key	密鑰
	 * @return	加密後的密文
	 * @throws Exception
	 */
	public static byte[] encryptToByte(String source, String key, String ivString) throws Exception {
		byte[] sourceBytes = null;
		byte[] keyBytes = null;
		Cipher cipher = null;
		IvParameterSpec iv = null;
		try {
			
			sourceBytes = source.getBytes(ENCODING);
			keyBytes = key.getBytes(ENCODING);
	        cipher = Cipher.getInstance(CIPHER_ALGORITHM, "BC");  
	        iv = new IvParameterSpec(ivString.getBytes(ENCODING));
	        cipher.init(Cipher.ENCRYPT_MODE, new SecretKeySpec(keyBytes, KEY_ALGORITHM),iv);  
	        return cipher.doFinal(sourceBytes);
		}catch (Exception e) {
			e.printStackTrace();
		}finally {
			sourceBytes = null;
			keyBytes = null;
			cipher = null;
			iv = null;
		}
       return null;
	}
	
	/**
	 * AES解密
	 * @param encryptStr	加密後的密文
	 * @param key	密鑰
	 * @return	源字符串
	 * @throws Exception
	 */
	public static String decrypt(String encryptStr, String key, String ivString) throws Exception {
		
		try {
			byte[] sourceBytes = Base64.decodeBase64(encryptStr);
	        return decryptByte(sourceBytes, key, ivString);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	/**
	 * AES解密
	 * @param encryptStr	加密後的密文
	 * @param key	密鑰
	 * @return	源字符串
	 * @throws Exception
	 */
	public static String decryptByte(byte encryptByte[], String key, String ivString) throws Exception {
		byte[] keyBytes = null;
		Cipher cipher = null;
		IvParameterSpec iv = null; 
		try {
			keyBytes = key.getBytes(ENCODING);
	        cipher = Cipher.getInstance(CIPHER_ALGORITHM, "BC");  
	        iv = new IvParameterSpec(ivString.getBytes(ENCODING));
	        cipher.init(Cipher.DECRYPT_MODE, new SecretKeySpec(keyBytes, KEY_ALGORITHM),iv);  
	        return new String(cipher.doFinal(encryptByte), ENCODING);
		} catch (Exception e) {
			e.printStackTrace();
		}finally {
			keyBytes = null;
			cipher = null;
			iv = null; 
		}
		return null;
	}
	
	
	
	public static void main(String[] args) throws Exception {
		
		// 加密
		String iv = "IBC Web Services";
		String key = "Prokey IT Consulting Services Co";
		String content = "保經科技";
		long lStart = System.currentTimeMillis();
		String enString = AESpkcs7paddingUtil.encrypt(content, key, iv);
		System.out.println("加密後的字串是：" + enString);
		long lUseTime = System.currentTimeMillis() - lStart;
		System.out.println("加密耗時：" + lUseTime + "毫秒");
		
		// 解密
		lStart = System.currentTimeMillis();
		String DeString = AESpkcs7paddingUtil.decrypt(enString, key, iv);
		System.out.println("解密後的字串是：" + DeString);
		lUseTime = System.currentTimeMillis() - lStart;
		System.out.println("解密耗時：" + lUseTime + "毫秒");
		
		Date date = new Date(); 
		DateFormat dateFormat = new SimpleDateFormat("yyyyMMddHHmmssSSS");
		System.out.println(dateFormat.format(date));
		
		               
		String iv1 = "xrYLG/7pmrb4IHS6";
		String key1 = "U2FsdGVkX19wb+zKKyWZ8AZJ8rZ3f/Xf";
		String content1 = "測試字串";
		long lStart1 = System.currentTimeMillis();
		String enString1 = AESpkcs7paddingUtil.encrypt(content1, key1, iv1);
		System.out.println("加密後的字串是：" + enString1);
		long lUseTime1 = System.currentTimeMillis() - lStart;
		System.out.println("加密耗時：" + lUseTime1 + "毫秒");
		
		// 解密
		lStart1 = System.currentTimeMillis();
		String DeString1 = AESpkcs7paddingUtil.decrypt(enString1, key1, iv1);
		System.out.println("解密後的字串是：" + DeString1);
		lUseTime1 = System.currentTimeMillis() - lStart1;
		System.out.println("解密耗時：" + lUseTime1 + "毫秒");
	}
	
}