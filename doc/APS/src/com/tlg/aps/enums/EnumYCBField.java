package com.tlg.aps.enums;
 
import org.apache.poi.ss.formula.functions.T;

/**
 * 元大續保產檔-保單副本檔欄位
 * 
 * FIR0690、FIR0693	 CF048住火	住火_APS_元大續保作業Ph2_N+1_產生保單副本&N+1產生出單明細檔 
 * @author CF048
 */
public enum EnumYCBField {	
 
//	BATCH_NO("批次號碼", "batchNo", String.class),						//批次號碼
//	PRPINS_BATCH_NO("承保批次號碼", "prpinsBatchNo", String.class),		//承保-批次號碼
	COLLATERALNUMBER("PO","擔保品編號", "collateralNo", String.class),		//擔保品編號
	POLICYNO("PO", "保單號碼", "policyNo", String.class),					//保單號碼
	UBN("PO","保險公司統編", "ubn", String.class),							//保險公司統編
	STARTDATE("PO","保險起始日", "startDate", java.util.Date.class),		//保險起始日
	ENDDATE("PO","保險到期日", "endDate",  java.util.Date.class),			//保險到期日
	CURRENCY("PO","保險金額幣別", "currency", String.class),				//保險金額幣別
	AMOUNT_F("PO","火險金額", "amountF", String.class),					//火險金額
	AMOUNT_Q("PO","地震險金額", "amountQ", String.class),					//地震險金額
	HOLDERIDS("PO","借戶ID(要保人)", "holderIDs", String.class),			//借戶ID(要保人)
	INSUREDIDS("PO","借戶姓名(要保人)", "insuredIDs", String.class),			//借戶姓名(要保人)
	HOLDERNAMES("PO","所有權人ID(被保險人)", "holderNames", String.class),	//所有權人ID(被保險人)
	INSUREDNAMES("PO","所有權人姓名(被保險人)", "insuredNames", String.class),	//所有權人姓名(被保險人)
	OLDPOLICYNO("PO","原保單號", "oldPolicyNo", String.class),				//原保單號
	LAST_PREMIUM("PO","前一年度保費", "lastPermium", String.class),			//前一年度保費
	LAST_AMOUNT_F("PO","前一年度火險保額", "lastAmountF", String.class),		//前一年度火險保額
	LAST_AMOUNT_Q("PO","前一年度地震險保額", "lastAmountQ", String.class),	//前一年度地震險保額
	SUMPREMIUM("PO","本年度保費", "sumPermium", String.class),				//本年度保費
	
	POLICYNO_CO("CO", "保單號碼", "policyNo", String.class),				//保單號碼
	TELPHONE("CO", "電話號碼", "telphone", String.class),					//電話號碼
	INSUREDID1("CO", "被保險人 ID", "insuredID1", String.class),			//被保險人 ID	
	INSUREDNAME1("CO", "被保險人姓名 1", "insuredName1", String.class),		//被保險人姓名 1
	INSUREDNAME2("CO", "被保險人姓名 2", "insuredName2", String.class),		//被保險人姓名 2
	HOLDERID1("CO", "要保人 ID", "holderID1", String.class),				//要保人 ID
	HOLDERNAME1("CO", "要保人姓名 1", "holderName1", String.class),			//要保人姓名 1
	HOLDERNAME2("CO", "要保人姓名 2", "holderName2", String.class),			//要保人姓名 2
	POSTCODE("CO", "通訊郵遞區號一", "postCode", String.class),				//通訊郵遞區號一
	POSTADDRESS("CO", "通訊地址一", "postAddress", String.class),			//通訊地址一
 
	POSTCODE2("CO", "通訊郵遞區號二 ", "postCode2", String.class),			//通訊郵遞區號二 
	POSTADDRESS2("CO", "通訊地址二", "postAddress2", String.class),			//通訊地址二
	ADDRESSCODE("CO", "標的物郵遞區號一", "addressCode", String.class),			//標的物郵遞區號一
	ADDRESSDETAILINFO("CO", "標的物地址一", "addressDetailInfo", String.class),	//標的物地址一
	ADDRESSCODE2("CO", "標的物郵遞區號二", "addressCode2", String.class),			//標的物郵遞區號二

	ADDRESSDETAILINFO2("CO", "標的物地址二", "addressDetailInfo2", String.class),	//標的物地址二
	MORTGAGEEBANK1("CO", "抵押權人一", "mortgageeBank1", String.class),			//抵押權人一
	MORTGAGEEBANK2("CO", "抵押權人二", "mortgageeBank2", String.class),			//抵押權人二
	SUMAMOUNT_CO("CO", "總火險保額", "sumAmount", String.class),				//總火險保額
	SUMPREMIUM_CO("CO", "總火險保費", "sumPermium", String.class),				//總火險保費
	  
	BUILDSTRUCTURE("CO", "建築等級構造", "buildStructure", String.class),		//建築等級構造
	BUILDAREA("CO", "使用面積", "buildArea", String.class),				//使用面積 
	BUILDYEARS("CO", "建造年份", "buildYears", String.class),					//建造年份
	STARTDATE_CO("CO", "保險期間－起", "startDate", java.util.Date.class),		//保險起始日 

	ENDDATE_CO("CO", "保險期間－迄", "endDate",  java.util.Date.class),			//保險期間－迄
	AMOUNT_F_CO("CO", "火險保額", "amountF", String.class),					//火險保額
	PREMIUM_F("CO", "火險保費", "premiumF", String.class),						//火險保費
	AMOUNT_Q_CO("CO", "地震保額", "amountQ", String.class),					//地震保額
	PREMIUM_Q("CO", "地震保費", "premiumQ", String.class),						//地震保費

	RATE("CO", "費率", "rate", String.class),									//費率
	POSSESSNATURECODE("CO", "使用性質", "possessNatureCode", String.class),	//使用性質
	STRUCTURELEVEL("CO", "建築等級", "structureLevel", String.class),			//建築等級
	SIGNDATE("CO", "簽單日期", "signDate", java.util.Date.class),				//簽單日期
	PAYMENTAREA("CO", "校核", "paymentArea", String.class),					//校核
	 
	PAYMENTUNIT("CO", "印花單位", "paymentUnit", String.class),				//印花單位
	PAYMENTRESP("CO", "總繳人", "paymentResp", String.class),					//總繳人
	HANDLEUSER("CO", "經辦人員", "handleUser", String.class),					//經辦人員
	HANDLER1CODE("CO", "服務人員", "handle1Code", String.class),				//服務人員
	VIRTUALCODE("CO", "虛擬碼 ", "virtualCode", String.class),					//虛擬碼 
	
	REMARK1("CO", "備註 1", "remark1", String.class),							//備註 1
	REMARK2("CO", "備註 2", "remark2", String.class),							//備註 2
	REMARK3("CO", "備註 3", "remark3", String.class),							//備註 3
	REMARK4("CO", "備註 4", "remark4", String.class),							//備註 4
	REMARK5("CO", "備註 5", "remark5", String.class);							//備註 5
	    
    private final String fileType;		//檔案類型: CO、PO
    private final String title;			//欄位中文名稱
    private final String name;			//對應DB欄位
	private final Class<?> fieldType;
 	
	/**
	 * constructor
	 * @param fileType
	 * @param title
	 * @param name
	 * @param targetType
	 */
	private <T> EnumYCBField(String fileType, String title, String name, Class<T> fieldType ) {
    	this.fileType =fileType ;
    	this.title =title ;
    	this.name = name; 
    	this.fieldType = fieldType;   
 	}

    /**
     * getFileType
     * @return String fileType
     */
	public String getFileType() {
		return fileType;
	}

	/**
	 * getTitle 
	 * @return String title 
	 */
	public String getTitle() {
		return title;
	}

	/**
	 * getName
	 * @return String name 
	 */
	public String getName() {
		return name;
	}
	
	/**
	 * getFieldType
	 * @return String fieldType
	 */
	public Class<?> getFieldType() {
		return fieldType;
	}	 
 
}
