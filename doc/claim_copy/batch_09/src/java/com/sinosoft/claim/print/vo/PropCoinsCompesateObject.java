package com.sinosoft.claim.print.vo;

import java.util.List;

/**
 * 聯共保計算書 數據對象
 * @author 中科軟
 *
 */
public class PropCoinsCompesateObject {
	/** 共保類型*/
	private String coinsFlag;
	/** 份額合計*/
	private String sumCoinsRate;
	/**應支付賠款金額合計*/
	private String sumCoinsClaimPaid;
	/**應支付費用金額合計*/
	private String sumCoinsChargePaid;
	/**賠款費用合計*/
	private String sumAllPaid;
	/**機構名稱*/
	private String companyName;
	/**列印時間*/
	private String printTime;
	/** 聯共保計算書 數據子對象*/
	private List<PropCoinsCompesateSubObject> propCoinsCompesateSubObjectList;
	public String getCoinsFlag() {
		return coinsFlag;
	}
	public void setCoinsFlag(String coinsFlag) {
		this.coinsFlag = coinsFlag;
	}
	public String getSumCoinsRate() {
		return sumCoinsRate;
	}
	public void setSumCoinsRate(String sumCoinsRate) {
		this.sumCoinsRate = sumCoinsRate;
	}
	public String getSumCoinsClaimPaid() {
		return sumCoinsClaimPaid;
	}
	public void setSumCoinsClaimPaid(String sumCoinsClaimPaid) {
		this.sumCoinsClaimPaid = sumCoinsClaimPaid;
	}
	public String getSumCoinsChargePaid() {
		return sumCoinsChargePaid;
	}
	public void setSumCoinsChargePaid(String sumCoinsChargePaid) {
		this.sumCoinsChargePaid = sumCoinsChargePaid;
	}
	public String getSumAllPaid() {
		return sumAllPaid;
	}
	public void setSumAllPaid(String sumAllPaid) {
		this.sumAllPaid = sumAllPaid;
	}
	public String getCompanyName() {
		return companyName;
	}
	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}
	public String getPrintTime() {
		return printTime;
	}
	public void setPrintTime(String printTime) {
		this.printTime = printTime;
	}
	public List<PropCoinsCompesateSubObject> getPropCoinsCompesateSubObjectList() {
		return propCoinsCompesateSubObjectList;
	}
	public void setPropCoinsCompesateSubObjectList(List<PropCoinsCompesateSubObject> propCoinsCompesateSubObjectList) {
		this.propCoinsCompesateSubObjectList = propCoinsCompesateSubObjectList;
	}
	
}
