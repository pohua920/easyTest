package com.sinosoft.claim.reins.vo;

import java.io.Serializable;
import java.util.Collection;

/**
 * 再保危险单位信息
 * @author 中科软
 */
public class ReinsDangerUnit implements Serializable {

	private static final long serialVersionUID = 1L;
	/** 保单号 */
	private String policyNo;
	/** 危险单位号 */
	private Integer dangerNo;
	/** 危险单位描述 */
	private String dangerDesc;
	/** 地址名称 */
	private String addressName;
	/** 币别 */
	private String currency;
	/** 保额 */
	private Double amount;
	/** 保费 */
	private Double premium;
	/** 占比（该危险单位总保额/投保单总保额） */
	private Double dangerShare;

	/** 标的列表 */
	private Collection<?> dangerItemList;

	public String getAddressName() {
		return addressName;
	}

	public void setAddressName(String addressName) {
		this.addressName = addressName;
	}

	public Double getAmount() {
		return amount;
	}

	public void setAmount(Double amount) {
		this.amount = amount;
	}

	public String getCurrency() {
		return currency;
	}

	public void setCurrency(String currency) {
		this.currency = currency;
	}

	public String getDangerDesc() {
		return dangerDesc;
	}

	public void setDangerDesc(String dangerDesc) {
		this.dangerDesc = dangerDesc;
	}

	public Collection<?> getDangerItemList() {
		return dangerItemList;
	}

	public void setDangerItemList(Collection<?> dangerItemList) {
		this.dangerItemList = dangerItemList;
	}

	public Integer getDangerNo() {
		return dangerNo;
	}

	public void setDangerNo(Integer dangerNo) {
		this.dangerNo = dangerNo;
	}

	public Double getDangerShare() {
		return dangerShare;
	}

	public void setDangerShare(Double dangerShare) {
		this.dangerShare = dangerShare;
	}

	public String getPolicyNo() {
		return policyNo;
	}

	public void setPolicyNo(String policyNo) {
		this.policyNo = policyNo;
	}

	public Double getPremium() {
		return premium;
	}

	public void setPremium(Double premium) {
		this.premium = premium;
	}

}
