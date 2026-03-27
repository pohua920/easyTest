package com.tlg.commons.util.api.rest.blockChain.enums;

/**
 * mantis：CLM0168，處理人員：BI086，需求單編號：CLM0168  區塊鏈查詢、新增及更新攤賠案件排程
 * Gets or Sets EnumApplicantsTypes
 */
public enum EnumApplicantsTypes {
	DRIVER("DRIVER"), PASSENGER("PASSENGER"), PEDESTRIAN("PEDESTRIAN"), THIS_CAR_PASSENGER(
			"THIS_CAR_PASSENGER");
	//mantis：CLM0277 ，處理人員： DP0713 ，需求單編號：理算任務串聯區塊鏈API3.10同業確認+API3.5建立理賠單 START
	//'1':'本車上乘客',==>EnumApplicantsTypes.THIS_CAR_PASSENGER
	//'3':'車外人員',==>EnumApplicantsTypes.PEDESTRIAN
	//'4':'對方車上乘客',==>EnumApplicantsTypes.PASSENGER
	//'5':'對方車上駕駛',==>EnumApplicantsTypes.DRIVER
	//'6':'本車上駕駛' ==>NAN(不該選到這項)
	//mantis：CLM0277 ，處理人員： DP0713 ，需求單編號：理算任務串聯區塊鏈API3.10同業確認+API3.5建立理賠單 END
	
	private String value;

	EnumApplicantsTypes(String value) {
		this.value = value;
	}

	public String getValue() {
		return value;
	}

	@Override
	public String toString() {
		return String.valueOf(value);
	}

	public static EnumApplicantsTypes fromValue(String text) {
		for (EnumApplicantsTypes b : EnumApplicantsTypes.values()) {
			if (String.valueOf(b.value).equals(text)) {
				return b;
			}
		}
		return null;
	}

}
