package com.sinosoft.claim.print.vo;

/**
 * 貨物運輸險索賠函數據對象
 * @author Sinosoft
 */

public class CargoClaimApplicationObject {
	/** 保單號碼 */
	private String policyNo;
	/** 貨物名稱 */
	private String goodsName;
	
	public String getPolicyNo() {
		return policyNo;
	}
	public void setPolicyNo(String policyNo) {
		this.policyNo = policyNo;
	}
	public String getGoodsName() {
		return goodsName;
	}
	public void setGoodsName(String goodsName) {
		this.goodsName = goodsName;
	}

}
