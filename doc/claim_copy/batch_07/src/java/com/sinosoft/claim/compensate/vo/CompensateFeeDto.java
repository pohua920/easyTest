package com.sinosoft.claim.compensate.vo;

import java.io.Serializable;

/**
 * @Description 自定义已决未决金额数据传输对象
 * @author 中科软
 */
public class CompensateFeeDto implements Serializable {

	private static final long serialVersionUID = 1L;

	/** 报案号 */
	private String registNo = "";

	/** 属性保险损失金额 */
	private double sumClaim = 0d;

	/** 属性已决金额 */
	private double sumPaid = 0d;

	/** 属性未决金额 */
	private double sumNoPaid = 0d;

	/**
	 * @return Returns the registNo.
	 */
	public String getRegistNo() {
		return registNo;
	}

	/**
	 * @param registNo The registNo to set.
	 */
	public void setRegistNo(String registNo) {
		this.registNo = registNo;
	}

	/**
	 * @return Returns the sumClaim.
	 */
	public double getSumClaim() {
		return sumClaim;
	}

	/**
	 * @param sumClaim The sumClaim to set.
	 */
	public void setSumClaim(double sumClaim) {
		this.sumClaim = sumClaim;
	}

	/**
	 * @return Returns the sumNoPaid.
	 */
	public double getSumNoPaid() {
		return sumNoPaid;
	}

	/**
	 * @param sumNoPaid The sumNoPaid to set.
	 */
	public void setSumNoPaid(double sumNoPaid) {
		this.sumNoPaid = sumNoPaid;
	}

	/**
	 * @return Returns the sumPaid.
	 */
	public double getSumPaid() {
		return sumPaid;
	}

	/**
	 * @param sumPaid The sumPaid to set.
	 */
	public void setSumPaid(double sumPaid) {
		this.sumPaid = sumPaid;
	}
}
