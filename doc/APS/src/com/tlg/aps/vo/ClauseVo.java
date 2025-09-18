package com.tlg.aps.vo;

import javax.xml.bind.annotation.XmlRootElement;

/**
 * 
 * 附約明細資料（多筆） 註：計算PB公共意外責任險時可能會傳入，非必填，但若有傳入則所有TAG必填。 RISKCODE 險種代碼，必填。 例如：F01_PB
 * KINDCODE 險別代碼，必填。 例如：Q_PB CLUASECODE 附約代碼，必填。 例如：PL01 AMOUNT_01 保額1，非必填。
 * 例如：1000000 DEDUCTION_01 自負額１，非必填。 例如：2500
 * 
 * 
 * 
 * 
 * @author bi086
 * 
 */
@XmlRootElement
public class ClauseVo {

	private String riskcode;
	private String kindcode;
	private String clausecode;
	private String amount01;
	private String deduction01;

	public String getRiskcode() {
		return riskcode;
	}

	public void setRiskcode(String riskcode) {
		this.riskcode = riskcode;
	}

	public String getKindcode() {
		return kindcode;
	}

	public void setKindcode(String kindcode) {
		this.kindcode = kindcode;
	}

	public String getClausecode() {
		return clausecode;
	}

	public void setClausecode(String clausecode) {
		this.clausecode = clausecode;
	}

	public String getAmount01() {
		return amount01;
	}

	public void setAmount01(String amount01) {
		this.amount01 = amount01;
	}

	public String getDeduction01() {
		return deduction01;
	}

	public void setDeduction01(String deduction01) {
		this.deduction01 = deduction01;
	}

}
