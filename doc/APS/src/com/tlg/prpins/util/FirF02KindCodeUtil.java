package com.tlg.prpins.util;

public class FirF02KindCodeUtil {
	
	public enum kindCode {FR2, FR3, RFA, RFB, RFC, RFD, RFE, RF01, RF02, RF03, RF04, RF05, RF06, RF07, RF08, RF09, RF10;};
	
//	public static String getCode(kindCode type) {
//		String code = "";
//		switch (type) {
//		case FR2:
//			code = "FR2";
//			break;
//		case FR3:
//			code = "FR3";
//			break;
//		case RFA:
//			code = "RFA";
//			break;
//		case RFB:
//			code = "RFB";
//			break;
//		case RFC:
//			code = "RFC";
//			break;
//		case RFD:
//			code = "RFD";
//			break;
//		case RFE:
//			code = "RFE";
//			break;
//		case RF01:
//			code = "RF01";
//			break;
//		case RF02:
//			code = "RF02";
//			break;
//		case RF03:
//			code = "RF03";
//			break;
//		case RF04:
//			code = "RF04";
//			break;
//		case RF05:
//			code = "RF05";
//			break;
//		case RF06:
//			code = "RF06";
//			break;
//		case RF07:
//			code = "RF07";
//			break;
//		case RF08:
//			code = "RF08";
//			break;
//		case RF09:
//			code = "RF09";
//			break;
//		case RF10:
//			code = "RF10";
//			break;
//		}
//		return code;
//	}

	public static String getName(String kindCode) {
		if("FR2".equals(kindCode))
			return "基本地震";
		if("FR3".equals(kindCode))
			return "住宅火災";
		if("RFA".equals(kindCode))
			return "家庭財務損失保險-基本地震";
		if("RFB".equals(kindCode))
			return "家庭財務損失保險-住宅火災";
		if("RFD".equals(kindCode))
			return "家庭財物被竊損失保險";
		if("RFE".equals(kindCode))
			return "家庭日常生活責任保險";
		if("RF01".equals(kindCode))
			return "擴大家庭災害費用補償";
		if("RF02".equals(kindCode))
			return "家事代勞費用保險";
		if("RF03".equals(kindCode))
			return "地震災害修復費用";
		if("RF04".equals(kindCode))
			return "特定事故房屋跌價損失補償";
		if("RF05".equals(kindCode))
			return "特定事故房屋租金補償保險(房東)";		
		if("RF06".equals(kindCode))
			return "住宅鑰匙門鎖費用補償保險附加條款";
		if("RF08".equals(kindCode))
			return "住宅輕損地震損失附加條款";
		if("RF09".equals(kindCode))
			return "住宅火險附加家庭成員傷害保險";
		if("RF10".equals(kindCode))
			return "住家綠能升級附加條款";
		return "";
	}
	
	public static  void main (String args[]){
		System.out.println(FirF02KindCodeUtil.kindCode.FR2);
		System.out.println(FirF02KindCodeUtil.getName(FirF02KindCodeUtil.kindCode.RF09.toString()));
		
		
	}
}
