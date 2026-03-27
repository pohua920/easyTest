package com.sinosoft.claim.schema.model;

// 采用工具 Hibernate Tools 3.2.4.GA (sinosoft version) 生成，请勿手工修改。

import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * POJO类PrpLclaimAgent代理赔保单信息表
 */
@Entity
@Table(name = "PRPLCLAIMAGENT")
public class PrpLclaimAgent implements java.io.Serializable {
	private static final long serialVersionUID = 1L;

	/** 属性立案号 */
	private String claimNo;

	/** 属性保单号码 */
	private String policyNo;

	/** 属性承保地区编码 */
	private String centreCode;

	/** 属性承保地区名称 */
	private String centreName;

	/** 属性承保公司编码 */
	private String unitCode;

	/** 属性承保公司名称 */
	private String unitName;

	/** 属性实际操作日期 */
	private Date operateDate;

	/** 属性运输方式 */
	private String conveyance;

	/** 属性启运地编码 */
	private String startSiteCode;

	/** 属性起始地 */
	private String startSiteName;

	/** 属性中转地编码 */
	private String viaSiteCode;

	/** 属性中转地 */
	private String viaSiteName;

	/** 属性目的地编码 */
	private String endSiteCode;

	/** 属性目的地 */
	private String endSiteName;

	/** 属性标志 */
	private String flag;

	/**
	 * 类PrpLclaimAgent的默认构造方法
	 */
	public PrpLclaimAgent() {
	}

	/**
	 * 属性立案号的getter方法
	 */
	@Id
	@Column(name = "CLAIMNO")
	public String getClaimNo() {
		return this.claimNo;
	}

	/**
	 * 属性立案号的setter方法
	 */
	public void setClaimNo(String claimNo) {
		this.claimNo = claimNo;
	}

	/**
	 * 属性保单号码的getter方法
	 */

	@Column(name = "POLICYNO")
	public String getPolicyNo() {
		return this.policyNo;
	}

	/**
	 * 属性保单号码的setter方法
	 */
	public void setPolicyNo(String policyNo) {
		this.policyNo = policyNo;
	}

	/**
	 * 属性承保地区编码的getter方法
	 */

	@Column(name = "CENTRECODE")
	public String getCentreCode() {
		return this.centreCode;
	}

	/**
	 * 属性承保地区编码的setter方法
	 */
	public void setCentreCode(String centreCode) {
		this.centreCode = centreCode;
	}

	/**
	 * 属性承保地区名称的getter方法
	 */

	@Column(name = "CENTRENAME")
	public String getCentreName() {
		return this.centreName;
	}

	/**
	 * 属性承保地区名称的setter方法
	 */
	public void setCentreName(String centreName) {
		this.centreName = centreName;
	}

	/**
	 * 属性承保公司编码的getter方法
	 */

	@Column(name = "UNITCODE")
	public String getUnitCode() {
		return this.unitCode;
	}

	/**
	 * 属性承保公司编码的setter方法
	 */
	public void setUnitCode(String unitCode) {
		this.unitCode = unitCode;
	}

	/**
	 * 属性承保公司名称的getter方法
	 */

	@Column(name = "UNITNAME")
	public String getUnitName() {
		return this.unitName;
	}

	/**
	 * 属性承保公司名称的setter方法
	 */
	public void setUnitName(String unitName) {
		this.unitName = unitName;
	}

	/**
	 * 属性实际操作日期的getter方法
	 */
	@Temporal(TemporalType.DATE)
	@Column(name = "OPERATEDATE")
	public Date getOperateDate() {
		return this.operateDate;
	}

	/**
	 * 属性实际操作日期的setter方法
	 */
	public void setOperateDate(Date operateDate) {
		this.operateDate = operateDate;
	}

	/**
	 * 属性运输方式的getter方法
	 */

	@Column(name = "CONVEYANCE")
	public String getConveyance() {
		return this.conveyance;
	}

	/**
	 * 属性运输方式的setter方法
	 */
	public void setConveyance(String conveyance) {
		this.conveyance = conveyance;
	}

	/**
	 * 属性启运地编码的getter方法
	 */

	@Column(name = "STARTSITECODE")
	public String getStartSiteCode() {
		return this.startSiteCode;
	}

	/**
	 * 属性启运地编码的setter方法
	 */
	public void setStartSiteCode(String startSiteCode) {
		this.startSiteCode = startSiteCode;
	}

	/**
	 * 属性起始地的getter方法
	 */

	@Column(name = "STARTSITENAME")
	public String getStartSiteName() {
		return this.startSiteName;
	}

	/**
	 * 属性起始地的setter方法
	 */
	public void setStartSiteName(String startSiteName) {
		this.startSiteName = startSiteName;
	}

	/**
	 * 属性中转地编码的getter方法
	 */

	@Column(name = "VIASITECODE")
	public String getViaSiteCode() {
		return this.viaSiteCode;
	}

	/**
	 * 属性中转地编码的setter方法
	 */
	public void setViaSiteCode(String viaSiteCode) {
		this.viaSiteCode = viaSiteCode;
	}

	/**
	 * 属性中转地的getter方法
	 */

	@Column(name = "VIASITENAME")
	public String getViaSiteName() {
		return this.viaSiteName;
	}

	/**
	 * 属性中转地的setter方法
	 */
	public void setViaSiteName(String viaSiteName) {
		this.viaSiteName = viaSiteName;
	}

	/**
	 * 属性目的地编码的getter方法
	 */

	@Column(name = "ENDSITECODE")
	public String getEndSiteCode() {
		return this.endSiteCode;
	}

	/**
	 * 属性目的地编码的setter方法
	 */
	public void setEndSiteCode(String endSiteCode) {
		this.endSiteCode = endSiteCode;
	}

	/**
	 * 属性目的地的getter方法
	 */

	@Column(name = "ENDSITENAME")
	public String getEndSiteName() {
		return this.endSiteName;
	}

	/**
	 * 属性目的地的setter方法
	 */
	public void setEndSiteName(String endSiteName) {
		this.endSiteName = endSiteName;
	}

	/**
	 * 属性标志的getter方法
	 */

	@Column(name = "FLAG")
	public String getFlag() {
		return this.flag;
	}

	/**
	 * 属性标志的setter方法
	 */
	public void setFlag(String flag) {
		this.flag = flag;
	}

}
