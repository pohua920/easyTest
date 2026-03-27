package com.sinosoft.claim.reins.vo;

import java.io.Serializable;
/**
 * 重大赔案信息
 * @author 中科软
 *
 */
public class ReinsLargeCase implements Serializable {


	private static final long serialVersionUID = 1L;
	/**
	 * 保单号码
	 */
	private String policyNo;
	/**
	 * 危险单位号
	 */
	private Integer dangerNo;
	/**
	 * 合约名称
	 */
	private String treatyName;
	/**
	 * 是否重大赔案
	 */
	private Boolean largeLoss;
	/**
	 * 是否现金赔款
	 */
	private Boolean cashLoss;

	public Boolean getCashLoss() {
		return cashLoss;
	}

	public void setCashLoss(Boolean cashLoss) {
		this.cashLoss = cashLoss;
	}

	public Integer getDangerNo() {
		return dangerNo;
	}

	public void setDangerNo(Integer dangerNo) {
		this.dangerNo = dangerNo;
	}

	public Boolean getLargeLoss() {
		return largeLoss;
	}

	public void setLargeLoss(Boolean largeLoss) {
		this.largeLoss = largeLoss;
	}

	public String getPolicyNo() {
		return policyNo;
	}

	public void setPolicyNo(String policyNo) {
		this.policyNo = policyNo;
	}

	public String getTreatyName() {
		return treatyName;
	}

	public void setTreatyName(String treatyName) {
		this.treatyName = treatyName;
	}

}
