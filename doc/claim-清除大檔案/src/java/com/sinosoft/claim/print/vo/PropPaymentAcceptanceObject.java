package com.sinosoft.claim.print.vo;

import java.util.List;

/**
 * 火災保險賠款接受書  數據對象
 * @author 中科軟
 *
 */
public class PropPaymentAcceptanceObject {
	/**被保險人名稱 */
	private String insuredName;
	/**大寫賠款金額*/
	private String payAmount;
	/**出險日期（民國年精确到分鐘）*/
	private String damageDate;
	/** 循环子报表*/
	private List<PropPaymentAcceptanceSubObject> propPaymentAcceptanceSubObjectList;
	
	public String getInsuredName() {
		return insuredName;
	}
	public void setInsuredName(String insuredName) {
		this.insuredName = insuredName;
	}

	public String getPayAmount() {
		return payAmount;
	}
	public void setPayAmount(String payAmount) {
		this.payAmount = payAmount;
	}
	public String getDamageDate() {
		return damageDate;
	}
	public void setDamageDate(String damageDate) {
		this.damageDate = damageDate;
	}
	public List<PropPaymentAcceptanceSubObject> getPropPaymentAcceptanceSubObjectList() {
		return propPaymentAcceptanceSubObjectList;
	}
	public void setPropPaymentAcceptanceSubObjectList(List<PropPaymentAcceptanceSubObject> propPaymentAcceptanceSubObjectList) {
		this.propPaymentAcceptanceSubObjectList = propPaymentAcceptanceSubObjectList;
	}

}
