package com.sinosoft.claim.print.vo;

/**
 * 貨物運輸險代位追償權利書數據對象
 * @author Sinosoft
 */

public class CargoSubrogationObject {
	/** 保单号码 */
	private String policyNo;
	/** 立案号 */
	private String claimNo;
	/** 被保險人名稱 */
	private String insuredName;
	/** 货物名称 */
	private String goodsName;
	
	public String getPolicyNo() {
		return policyNo;
	}
	public void setPolicyNo(String policyNo) {
		this.policyNo = policyNo;
	}
	public String getClaimNo() {
		return claimNo;
	}
	public void setClaimNo(String claimNo) {
		this.claimNo = claimNo;
	}
	public String getInsuredName() {
		return insuredName;
	}
	public void setInsuredName(String insuredName) {
		this.insuredName = insuredName;
	}
	public String getGoodsName() {
		return goodsName;
	}
	public void setGoodsName(String goodsName) {
		this.goodsName = goodsName;
	}
}
