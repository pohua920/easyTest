package com.tlg.aps.vo;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class FirAddressRuleObj extends BaseRuleVo {
	/* mantis：FIR0225，處理人員：BJ085，需求單編號：FIR0225 稽核議題調整-過往資料檢核 start */
	/**
	 * 必填。險別<br>
	 */
	private String riskcode;
	/**
	 * 必填。作業類別<br>
	 */
	private String funcType;
	/**
	 * 必填。郵遞區號<br>
	 */
	private String postcode;
	/**
	 * 必填。標的物地址<br>
	 */
	private String address;
	/**
	 * 必填。建物等級<br>
	 */
	private String addrStructure;
	/**
	 * 必填。總樓層數<br>
	 */
	private String addrSumfloors;
	/**
	 * 必填。外牆<br>
	 */
	private String addrWall;
	/**
	 * 必填。屋頂<br>
	 */
	private String addrRoof;

	public String getRiskcode() {
		return riskcode;
	}

	public void setRiskcode(String riskcode) {
		this.riskcode = riskcode;
	}

	public String getFuncType() {
		return funcType;
	}

	public void setFuncType(String funcType) {
		this.funcType = funcType;
	}

	public String getPostcode() {
		return postcode;
	}

	public void setPostcode(String postcode) {
		this.postcode = postcode;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getAddrStructure() {
		return addrStructure;
	}

	public void setAddrStructure(String addrStructure) {
		this.addrStructure = addrStructure;
	}

	public String getAddrSumfloors() {
		return addrSumfloors;
	}

	public void setAddrSumfloors(String addrSumfloors) {
		this.addrSumfloors = addrSumfloors;
	}

	public String getAddrWall() {
		return addrWall;
	}

	public void setAddrWall(String addrWall) {
		this.addrWall = addrWall;
	}

	public String getAddrRoof() {
		return addrRoof;
	}

	public void setAddrRoof(String addrRoof) {
		this.addrRoof = addrRoof;
	}

}
