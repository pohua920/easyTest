package com.tlg.aps.vo;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class FirClaimQueryVo {

	/**
	 * 險種代號
	 */
	private String riskCode;
	/**
	 * 標的物地址
	 */
	private String address;
	/**
	 * 查詢結果，必填。<br> N = 無理賠記錄 <br>Y = 有理賠記錄
	 */
	private String flag;
	/**
	 * 保單號，非必填。 <br>當FLAG = Y時，本欄位才有值。
	 */
	private String policyNo;

	public String getRiskCode() {
		return riskCode;
	}

	public void setRiskCode(String riskCode) {
		this.riskCode = riskCode;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getFlag() {
		return flag;
	}

	public void setFlag(String flag) {
		this.flag = flag;
	}

	public String getPolicyNo() {
		return policyNo;
	}

	public void setPolicyNo(String policyNo) {
		this.policyNo = policyNo;
	}

}
