package com.tlg.aps.util;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import java.util.Scanner;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;

import org.apache.commons.codec.binary.Base64;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 *  mantis：MOB0015, 處理人員：DP0706,遠傳行動裝置保險與全虹介接資料交換
 */
@Component
public class AESEcbUtil {
  
	public  SecretKeySpec setKey() {
		SecretKeySpec secretKey = null;
	    try {
//	    	String skValue = envConfig != null ?envConfig.getSkValue() : "";
	    	String skValue = "2wsx#EDCXXXXXXXX";
	    	byte[] bytes = skValue.getBytes();
	    	secretKey = new SecretKeySpec(bytes, "AES");
	    } catch (Exception e) {
	    	e.printStackTrace();
	    }
	    return secretKey;
	}
  
	public  String encrypt(String strToEncrypt) {
		try {
			SecretKeySpec secretKey = setKey();
			Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
			cipher.init(Cipher.ENCRYPT_MODE, secretKey);
			byte[] byteStr = strToEncrypt.getBytes();
			return new String(Base64.encodeBase64(cipher.doFinal(byteStr)));
		} catch (Exception e) {
			System.out.println("Error while encrypting: " + e.toString());
	    }
	    return null;
	  }

	public  String encrypt(String strToEncrypt, String charsetName) {
		try {
			if(charsetName == null || charsetName.trim().length() <= 0) {
				return null;
			}
			SecretKeySpec secretKey = setKey();
			Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
			cipher.init(Cipher.ENCRYPT_MODE, secretKey);
			byte[] byteStr = strToEncrypt.getBytes(charsetName);
			return new String(Base64.encodeBase64(cipher.doFinal(byteStr)));
		} catch (Exception e) {
			System.out.println("Error while encrypting: " + e.toString());
		}
		return null;
	}

	public  String decrypt(String strToDecrypt) {
		try {
			SecretKeySpec secretKey = setKey();
			Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
			cipher.init(Cipher.DECRYPT_MODE, secretKey);
			byte[] byteStr = strToDecrypt.getBytes();
			return new String(cipher.doFinal(Base64.decodeBase64(byteStr)));
		} catch (Exception e) {
			System.out.println("Error while decrypting: " + e.toString());
		}
		return null;
	}
  
	public  String decrypt(String strToDecrypt, String charsetName) {
		try {
			if(charsetName == null || charsetName.trim().length() <= 0) {
				return null;
			}
			SecretKeySpec secretKey = setKey();
			Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
			cipher.init(Cipher.DECRYPT_MODE, secretKey);
			byte[] byteStr = strToDecrypt.getBytes(charsetName);
			return new String(cipher.doFinal(Base64.decodeBase64(byteStr)),charsetName);
		} catch (Exception e) {
			System.out.println("Error while decrypting: " + e.toString());
		}
		return null;
	}
  
	public static void main(String[] args) throws Exception{
		StringBuffer sb = new StringBuffer();
		try {
			File myObj = new File("E:\\Johnny\\23.遠傳手機保險\\測試資料\\20230926\\content_2023092601.txt");
			Scanner myReader = new Scanner(myObj,"UTF-8");
			while (myReader.hasNextLine()) {
				String data = myReader.nextLine();
				sb.append(data);
			}
			myReader.close();
			String content = "";
			if(sb != null && sb.length() > 0) content = sb.toString();

			AESEcbUtil util = new AESEcbUtil();
			System.out.println("Original Text : " + content);
			
			String encryptText = util.encrypt(content);
			System.out.println("encryptText : " + encryptText);

			String decryptedText = util.decrypt(encryptText);
			System.out.println("decryptedText : " + decryptedText);
  		
//  		AESEcbUtil util = new AESEcbUtil();
//  		String content = "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 ";
//  		String decryptedText = util.decrypt(content);
//        System.out.println("decryptedText : " + decryptedText);
  		
//  		AESEcbUtil util = new AESEcbUtil();
//  		String contentTemp = "黃大大";
//		contentTemp = util.encrypt(contentTemp);
//		System.out.println("contentTemp encrypt= "+contentTemp);
//		contentTemp = util.decrypt(contentTemp);
//		System.out.println("contentTemp decrypt= "+contentTemp);
        
        
		} catch (Exception e) {
			System.out.println("An error occurred.");
			e.printStackTrace();
		}
	}
}