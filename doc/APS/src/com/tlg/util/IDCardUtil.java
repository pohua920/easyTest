package com.tlg.util;

public class IDCardUtil {
	
	/**
	 * 身份證檢查
	 * @param value 身份證號字串
	 * @return true: 正確的證號<BR/>false: 錯誤的證號
	 */		
	public static boolean twidCheck(String value) {
		boolean accept=false;
		if (value==null) {
			return false;
		}
		if (value.length()!=10) {
			return false;
		}
		value=value.toUpperCase();
		byte valueByte[]=value.getBytes();
		/*
		 * A=10  台北市       J=18 新竹縣         S=26  高雄縣
		 * B=11  台中市       K=19 苗栗縣         T=27  屏東縣
		 * C=12  基隆市       L=20 台中縣         U=28  花蓮縣
		 * D=13  台南市       M=21 南投縣         V=29  台東縣
		 * E=14  高雄市       N=22 彰化縣         W=32  金門縣
		 * F=15  台北縣       O=35 新竹市         X=30  澎湖縣
		 * G=16  宜蘭縣       P=23 雲林縣         Y=31  陽明山
		 * H=17  桃園市       Q=24 嘉義縣         Z=33  連江縣
		 * I=34  嘉義市       R=25 台南縣
		 */
		int localNum[]={ 10, 11, 12, 13, 14, 15, 16, 17, 34, 18, 19, 20, 21, 22, 35, 23, 24, 25,26, 27, 28, 29, 32, 30, 31, 33 };
		// A=65 Z=90
		if (valueByte[0] >= 65 && valueByte[0] <= 90) { 
			int idNum[]=new int[11];
			/*
			 * A123456789   A=10  台北市
			 * idNum[0]=1
			 * idNum[1]=0
			 */
			idNum[0]=localNum[(valueByte[0]-65)] / 10; // ASCII A = 65 ==> localNum[0]/10
			idNum[1]=localNum[(valueByte[0]-65)] % 10; // ASCII A = 65 ==> localNum[0]%10
			for (int i = 1; i <= 9; i++) {
				// 把A123456789 的 123456789分別放入 idNum
				idNum[i + 1] = valueByte[i] - 48; // ASCII 0 = 48
			}
			int count=idNum[0];
			for (int i = 1; i <= 9; i++) {
				count += idNum[i] * (10 - i); 
			}
			if ( ( (count % 10) + idNum[10]) % 10==0) {
			     accept = true;
			}	
		}
		return accept;
	}
	
	/**
	 * 統編檢查
	 * @param value 統編字串
	 * @return true: 正確的統編<BR/>false: 錯誤的統編
	 */	
	public static boolean companyIDCheck(String value) {
		//這是從jquery.validate.js抄過來的
		if (value==null ) {
			return false;
		}
		if (value.length()!=8 ) {
			return false;
		}
		if (!org.apache.commons.lang.StringUtils.isNumeric(value) ) {
			return false;
		}
		int tmp[]={ 1, 2, 1, 2, 1, 2, 4, 1 };
		int sum=0;
		for (int i=0; i<8; i++ ) {
			int s1=Integer.parseInt(value.substring(i, i+1) );
			sum += cal(s1*tmp[i]);
		}		
		if (!validModTen(sum) ) {
			if ("7".equals(value.substring(6, 7) ) ) {
				return (validModTen(sum+1));
			}
		}
		return (validModTen(sum));
	}
	
	/**
	 * 外僑編號檢查
	 * @param value 外僑編號
	 * @return true: 正確的編號<BR/>false: 錯誤的編號
	 */		
	public static boolean foreignerIDCheck(String value) {
		//這是從jquery.validate.js抄過來的
		if (value==null ) {
			return false;
		}
		if (value.length()!=10 ) {
			return false;
		}		
		value=value.toUpperCase();
		String str1="ABCDEFGHIJKLMNOPQRSTUVWXYZ";
		String str2="1011121314151617341819202122352324252627282932303133";		
		if (
				!(
				str1.indexOf(value.substring(0, 1) )>-1 && "ABCDEF".indexOf(value.substring(1, 2) )>-1 && 
				org.apache.commons.lang.StringUtils.isNumeric(value.substring(2, value.length() ) ) ) ) {
			return false;
		}
		char valueChr[]=value.toCharArray();
		int sum=0;
		String t1 = str2.substring(str1.indexOf(value.substring(0,1))* 2,(str1.indexOf(value.substring(0,1))* 2) + 2);
		String t2 = str2.substring(str1.indexOf(value.substring(1,2))* 2,(str1.indexOf(value.substring(1,2))* 2) + 2);		
		sum = Integer.parseInt(t1.toCharArray()[0]+"", 10) * 1 + (Integer.parseInt(t1.toCharArray()[1]+"", 10) * 9) % 10;
		sum += (Integer.parseInt(t2, 10) % 10) * 8;
		
		String t10=value.substring(9);
		String val10;
		for (int i=2; i<=9; i++ ) {
			sum += (Integer.parseInt(valueChr[i]+"") * (9 - i) % 10);
		}
		if(sum % 10 == 0) {
			val10 = "0";
		} else {
			val10 = (10 - (sum % 10) )+"";
		}
		if(val10.equals(t10)) {
			return true;
		} else {
			return false;
		}
	}
	
	/**
	 * 身份種類
	 * @param cardType
	 * 		<br/>1:身份證, 
	 * 		<br/>2:統編, 
	 * 		<br/>5:居留證
	 * 		<hr size='1'/>
	 * @param value 身份證/統編/居留證 編號字串
	 * 		
	 * @return
	 * 		<br/>1.自然人本國
	 * 		<br/>2.自然人外國
	 * 		<br/>3.法人,非法人團體或機關
	 * 		<br/>7.自然人外國國籍居留證不符
	 * 		<br/>8.自然人本國國籍居留證不符
	 * 		<br/>9.法人,非法人團體或機關統一編號不符
	 * 
	 * 		<br/>0.參數代入錯誤
	 */
	public static String getFinsType(String cardType, String value) {
		String retValue="0";
		if (cardType.equals("1") ) {
			// 身份證
			if (twidCheck(value) ) {
				retValue="1"; //自然人本國
			} else {
				retValue="8"; //自然人本國國籍居留證不符
			}
		} else if (cardType.equals("2") ) {
			// 統一編號
			if (companyIDCheck(value) ) {
				retValue="3"; //法人,非法人團體或機關
			} else {
				retValue="9"; //法人,非法人團體或機關統一編號不符
			}
		} else if (cardType.equals("5") ) {
			// 居留證
			if (foreignerIDCheck(value) ) {
				retValue="2"; //自然人外國
			} else {
				retValue="7"; //自然人外國國籍居留證不符
			}
		}
		return retValue;
	}
	
	/**
	 * 性別
	 * @param cardType 
	 * 		<br/>1:身份證, 
	 * 		<br/>2:統編, 
	 * 		<br/>5:居留證
	 * 		<hr size='1'/>
	 * @param value 身份證/統編/居留證 編號字串
	 * 		
	 * @return
	 * 		<br/>1.男
	 * 		<br/>2.女
	 * 		<br/>3.法人
	 * 
	 */	
	public static String getSexType(String cardType, String value) {
		String retValue="1";
		if (cardType.equals("1") ) {
			// 身份證
			if ("1".equals(value.substring(1, 2))) {
				retValue="1"; // 男
			} else {
				retValue="2"; // 女
			}			
			/*
			if (twidCheck(value) ) {
				if ("1".equals(value.substring(1, 2))) {
					retValue="1"; // 男
				} else {
					retValue="2"; // 女
				}
			}
			*/
		} else if (cardType.equals("2") ) {
			// 統一編號
			retValue="3";
		} else if (cardType.equals("5") ) {
			// 居留證
			if ("ACE".indexOf(value.substring(1, 2))>-1 ) {
				retValue="1"; // 男
			} else {
				retValue="2"; // 女
			}			
			/*
			if (foreignerIDCheck(value) ) {
				if ("ACE".indexOf(value.substring(1, 2))>-1 ) {
					retValue="1"; // 男
				} else {
					retValue="2"; // 女
				}
			}
			*/
		}
		return retValue;
	}
	
	//統編檢查用
	private static boolean validModTen(int n) {
		//這是從jquery.validate.js抄過來的
		return (n%10 == 0) ? true : false;
	}
	
	//統編檢查用
	private static int cal(int n) {
		//這是從jquery.validate.js抄過來的
		int sum=0;
		while (n!=0) {
			sum+=(n%10);
			n=(n - n % 10 ) / 10; // 取整數
		}
		return sum;
	}
	
	public static void main(String args[]){
		System.out.println(" id check = " + IDCardUtil.twidCheck("C121293660"));
	}
	
}
