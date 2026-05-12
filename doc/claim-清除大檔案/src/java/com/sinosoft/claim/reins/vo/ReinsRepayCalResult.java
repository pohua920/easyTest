package com.sinosoft.claim.reins.vo;

import java.io.Serializable;
/**
 * 分摊预配结果
 * @author 中科软
 *
 */
public class ReinsRepayCalResult implements Serializable {

	private static final long serialVersionUID = 1L;
	/**
	 * 危险单位序号
	 */
	private String policyNo;
	/**
	 * 危险单位序号
	 */
	private Integer dangerNo;
	/**
	 * 分保方式名称（法定、自留、合同、临分）
	 */
	private String reinsModeName;
	/**
	 * 合约编码
	 */
	private String treatyNo;
	/**
	 * 合约名称
	 */
	private String treatyName;
	/**
	 * 摊回份额
	 */
	private Double shareRate;
	/**
	 * 摊赔币种
	 */
	private String currency;
	/**
	 * 分摊金额（含费用、预赔）
	 */
	private Double sumPaid;

	public String getCurrency() {
		return currency;
	}

	public void setCurrency(String currency) {
		this.currency = currency;
	}

	public Integer getDangerNo() {
		return dangerNo;
	}

	public void setDangerNo(Integer dangerNo) {
		this.dangerNo = dangerNo;
	}

	public String getPolicyNo() {
		return policyNo;
	}

	public void setPolicyNo(String policyNo) {
		this.policyNo = policyNo;
	}

	public String getReinsModeName() {
		return reinsModeName;
	}

	public void setReinsModeName(String reinsModeName) {
		this.reinsModeName = reinsModeName;
	}

	public Double getShareRate() {
		return shareRate;
	}

	public void setShareRate(Double shareRate) {
		this.shareRate = shareRate;
	}

	public Double getSumPaid() {
		return sumPaid;
	}

	public void setSumPaid(Double sumPaid) {
		this.sumPaid = sumPaid;
	}

	public String getTreatyName() {
		return treatyName;
	}

	public void setTreatyName(String treatyName) {
		this.treatyName = treatyName;
	}

	public String getTreatyNo() {
		return treatyNo;
	}

	public void setTreatyNo(String treatyNo) {
		this.treatyNo = treatyNo;
	}

}
