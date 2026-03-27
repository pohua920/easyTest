package com.sinosoft.claim.regist.vo;

import ins.framework.utils.StringUtils;

import java.io.Serializable;

import com.sinosoft.claim.schema.model.PrpLregist;

/**
 * 这是一个用於传输报案信息和立案信息的DTO对象
 * @author 中科软
 * @version 1.0 2013-03-01
 */
public class RegistClaimInfoDto extends PrpLregist implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -6601601201247001818L;
	/** 赔付金额 */
	private double sumPaidShow = 0D;
	/** 属性赔案号码 */
	private String claimNo = "";
	/** 属性保险损失金额(同保单币别) */
	private double sumClaim = 0d;

	public RegistClaimInfoDto() {
	}

	/**
	 * 构造函数
	 * @param prpLregist
	 */
	public RegistClaimInfoDto(PrpLregist prpLregist) {
		this.setSerialNo(prpLregist.getSerialNo());
		this.setRegistNo(prpLregist.getRegistNo());
		this.setDamageStartDate(prpLregist.getDamageStartDate());
		this.setLinkerName(prpLregist.getLinkerName());
		this.setOperatorCode(prpLregist.getOperatorCode());
		this.setOperatorName(prpLregist.getOperatorName());
		this.setDamageAddress(prpLregist.getDamageAddress());
		this.setBrandName(prpLregist.getBrandName());
		this.setRegistNo(prpLregist.getRegistNo());
		this.setPhoneNumber(prpLregist.getPhoneNumber());
		this.setDamageName(prpLregist.getDamageName());
		this.setDamageName(prpLregist.getDamageName());
		this.setDamageAreaName(prpLregist.getDamageAddress());
	}

	// 原因：添加出险原因
	private String compName;

	/**
	 * 设置属性出险原因
	 * @param compName 出险原因
	 */
	public void setCompName(String compName) {
		this.compName = compName;
	}

	/**
	 * 获得属性出险原因
	 * @return 出险原因
	 */
	public String getCompName() {
		return this.compName;
	}

	/**
	 * 设置属性赔付金额
	 * @param sumPaidShow 赔付金额
	 */
	public void setSumPaidShow(double sumPaidShow) {
		this.sumPaidShow = sumPaidShow;
	}

	/**
	 * 获得属性赔付金额
	 * @return 赔付金额
	 */
	public double getSumPaidShow() {
		return this.sumPaidShow;
	}

	/**
	 * 设置属性赔案号码
	 * @param claimNo 待设置的属性赔案号码的值
	 */
	public void setClaimNo(String claimNo) {
		this.claimNo = StringUtils.rightTrim(claimNo);
	}

	/**
	 * 获取属性赔案号码
	 * @return 属性赔案号码的值
	 */
	public String getClaimNo() {
		return claimNo;
	}

	/**
	 * 设置属性保险损失金额(同保单币别)
	 * @param sumClaim 待设置的属性保险损失金额(同保单币别)的值
	 */
	public void setSumClaim(double sumClaim) {
		this.sumClaim = sumClaim;
	}

	/**
	 * 获取属性保险损失金额(同保单币别)
	 * @return 属性保险损失金额(同保单币别)的值
	 */
	public double getSumClaim() {
		return sumClaim;
	}
}