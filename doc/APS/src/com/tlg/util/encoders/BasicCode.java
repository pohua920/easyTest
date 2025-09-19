package com.tlg.util.encoders;


public class BasicCode {
	
	public static String encode(String key,String value) {
		
		byte[] bKeys = key.getBytes();
		int iKey=0;
		for(int i = 0 ; i < bKeys.length ; i++){
			iKey += bKeys[i];
		}
		byte[] bVal = value.getBytes();
		StringBuffer hexString = new StringBuffer();
		String plainText;
		for (int i = 0; i < bVal.length; i++) {
//			System.out.println(bVal[i] + iKey);
			plainText = Integer.toHexString(bVal[i] + iKey);
			if (plainText.length()>=3) {
				plainText = "0" + plainText;
			}
			else if (plainText.length()>=2) {
				plainText = "00" + plainText;
			}
			else if (plainText.length()>=1) {
				plainText = "000" + plainText;
			}
			hexString.append(plainText);
		}
		return hexString.toString();
	}
	
	public static String decode(String key,String value) {
		byte[] bKeys = key.getBytes();
		int iKey=0;
		for(int i = 0 ; i < bKeys.length ; i++){
			iKey += bKeys[i];
		}
		int length = value.length() / 4 ;
		byte[] bArray = new byte[length];
		for(int i = 0 ; i < length ; i++){
			String word = value.substring(i * 4 , (i + 1) * 4);
			bArray[i] = (byte) (Integer.parseInt(word,16) - iKey);
		}
		return new String(bArray);
	}
	
	 public static byte[] intToByteArray1(int i) {   
		  byte[] result = new byte[4];   
		  result[0] = (byte)((i >> 24) & 0xFF);
		  result[1] = (byte)((i >> 16) & 0xFF);
		  result[2] = (byte)((i >> 8) & 0xFF); 
		  result[3] = (byte)(i & 0xFF);
		  return result;
	}
	
	public static void main(String args[]){
		String s = BasicCode.encode("cartest19", "cartest19");
		System.out.println("s = " + s);
		//String s1 = BasicCode.decode("adPassword", "0448044b044904490448044804480449");
		//System.out.println("s1 = " + s1);
	}
}
