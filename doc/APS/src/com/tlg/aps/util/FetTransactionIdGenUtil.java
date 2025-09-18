package com.tlg.aps.util;

import java.security.NoSuchAlgorithmException;
import java.text.SimpleDateFormat;

import java.util.Date;
import java.util.Random;

public class FetTransactionIdGenUtil {

	/**
	 * @param data
	 * @return
	 * @throws NoSuchAlgorithmException
	 */
	public static String getTransactionId(String prefix, int randomNo) throws Exception {
		String randomStr = getRandom(randomNo);
		Date now = new Date();
		String date = new SimpleDateFormat("yyyyMMddHHmmssSSS").format(now);
		String transactionId = prefix + date + randomStr;
		return transactionId;
	}

	/**
	 * .pw 內容 密碼檔案 【保單唯一編號】 = 【密碼值】
	 * 
	 * @param appliIdentifynumber
	 *            身份證字號
	 * @return
	 * @throws Exception
	 */
	public static String getRandom(int arryBsize) throws Exception {
		String returnValue = "";
		// 建立樣本(對照表) array
		String[] RegSNContent = {
				"0","1","2","3","4","5","6","7","8","9",
		        "a","b","c","d","e","f","g","h","i","j",
		        "k","l","m","n","o","p","q","r","s","t",
		        "u","v","w","x","y","z"};
 
		Random r = new Random();
		for (int i=0; i<arryBsize; i++) {
			returnValue += RegSNContent[r.nextInt(RegSNContent.length)];
		}
		
		return returnValue;
	}

}
