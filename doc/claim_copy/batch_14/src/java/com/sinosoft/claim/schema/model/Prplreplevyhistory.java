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
 * POJO类Prplreplevyhistory追偿信息历史记录表
 */
@Entity
@Table(name = "PRPLREPLEVYHISTORY")
public class Prplreplevyhistory implements java.io.Serializable {
	private static final long serialVersionUID = 1L;

	/** 属性业务号 */
	private String businessNo;

	/** 属性REGISTNO */
	private String registNo;

	/** 属性立案号 */
	private String claimNo;

	/** 属性保单号码 */
	private String policyNo;

	/** 属性险种 */
	private String riskCode;

	/** 属性追偿次数 */
	private int replevytimes;

	/** 属性追偿类型 */
	private String replevytype;

	/** 属性币别 */
	private String currency;

	/** 属性追偿金额 */
	private Double replevysumpaid;

	/** 属性追偿费用 */
	private Double replevyfee;

	/** 属性REPLEVYSUMREALPAID */
	private Double replevysumrealpaid;

	/** 属性签发人 */
	private String operatorCode;

	/** 属性操作员名称 */
	private String operatorname;

	/** 属性REPLEVYTIME */
	private Date replevytime;

	/** 属性REPLEVYENDTIME */
	private Date replevyendtime;

	/** 属性归属机构 */
	private String comCode;

	/** 属性归属机构名称 */
	private String comname;

	/** 属性追偿原因 */
	private String replevyreason;

	/** 属性追偿描述 */
	private String replevytext;

	/** 属性ADDITIONALREPLEVYFLAG */
	private String additionalreplevyflag;

	/** 属性ADDITIONALREPLEVYREASON */
	private String additionalreplevyreason;

	/** 属性EXTENDSTRING1 */
	private String extendString1;

	/** 属性EXTENDSTRING2 */
	private String extendString2;

	/** 属性EXTENDSTRING3 */
	private String extendstring3;

	/**
	 * 类Prplreplevyhistory的默认构造方法
	 */
	public Prplreplevyhistory() {
	}

	/**
	 * 属性业务号的getter方法
	 */
	@Id
	@Column(name = "BUSINESSNO")
	public String getBusinessNo() {
		return this.businessNo;
	}

	/**
	 * 属性业务号的setter方法
	 */
	public void setBusinessNo(String businessNo) {
		this.businessNo = businessNo;
	}

	/**
	 * 属性REGISTNO的getter方法
	 */

	@Column(name = "REGISTNO")
	public String getRegistNo() {
		return this.registNo;
	}

	/**
	 * 属性REGISTNO的setter方法
	 */
	public void setRegistNo(String registNo) {
		this.registNo = registNo;
	}

	/**
	 * 属性立案号的getter方法
	 */

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
	 * 属性险种的getter方法
	 */

	@Column(name = "RISKCODE")
	public String getRiskCode() {
		return this.riskCode;
	}

	/**
	 * 属性险种的setter方法
	 */
	public void setRiskCode(String riskCode) {
		this.riskCode = riskCode;
	}

	/**
	 * 属性追偿次数的getter方法
	 */

	@Column(name = "REPLEVYTIMES")
	public int getReplevytimes() {
		return this.replevytimes;
	}

	/**
	 * 属性追偿次数的setter方法
	 */
	public void setReplevytimes(int replevytimes) {
		this.replevytimes = replevytimes;
	}

	/**
	 * 属性追偿类型的getter方法
	 */

	@Column(name = "REPLEVYTYPE")
	public String getReplevytype() {
		return this.replevytype;
	}

	/**
	 * 属性追偿类型的setter方法
	 */
	public void setReplevytype(String replevytype) {
		this.replevytype = replevytype;
	}

	/**
	 * 属性币别的getter方法
	 */

	@Column(name = "CURRENCY")
	public String getCurrency() {
		return this.currency;
	}

	/**
	 * 属性币别的setter方法
	 */
	public void setCurrency(String currency) {
		this.currency = currency;
	}

	/**
	 * 属性追偿金额的getter方法
	 */

	@Column(name = "REPLEVYSUMPAID")
	public Double getReplevysumpaid() {
		return this.replevysumpaid;
	}

	/**
	 * 属性追偿金额的setter方法
	 */
	public void setReplevysumpaid(Double replevysumpaid) {
		this.replevysumpaid = replevysumpaid;
	}

	/**
	 * 属性追偿费用的getter方法
	 */

	@Column(name = "REPLEVYFEE")
	public Double getReplevyfee() {
		return this.replevyfee;
	}

	/**
	 * 属性追偿费用的setter方法
	 */
	public void setReplevyfee(Double replevyfee) {
		this.replevyfee = replevyfee;
	}

	/**
	 * 属性REPLEVYSUMREALPAID的getter方法
	 */

	@Column(name = "REPLEVYSUMREALPAID")
	public Double getReplevysumrealpaid() {
		return this.replevysumrealpaid;
	}

	/**
	 * 属性REPLEVYSUMREALPAID的setter方法
	 */
	public void setReplevysumrealpaid(Double replevysumrealpaid) {
		this.replevysumrealpaid = replevysumrealpaid;
	}

	/**
	 * 属性签发人的getter方法
	 */

	@Column(name = "OPERATORCODE")
	public String getOperatorCode() {
		return this.operatorCode;
	}

	/**
	 * 属性签发人的setter方法
	 */
	public void setOperatorCode(String operatorCode) {
		this.operatorCode = operatorCode;
	}

	/**
	 * 属性操作员名称的getter方法
	 */

	@Column(name = "OPERATORNAME")
	public String getOperatorname() {
		return this.operatorname;
	}

	/**
	 * 属性操作员名称的setter方法
	 */
	public void setOperatorname(String operatorname) {
		this.operatorname = operatorname;
	}

	/**
	 * 属性REPLEVYTIME的getter方法
	 */
	@Temporal(TemporalType.DATE)
	@Column(name = "REPLEVYTIME")
	public Date getReplevytime() {
		return this.replevytime;
	}

	/**
	 * 属性REPLEVYTIME的setter方法
	 */
	public void setReplevytime(Date replevytime) {
		this.replevytime = replevytime;
	}

	/**
	 * 属性REPLEVYENDTIME的getter方法
	 */
	@Temporal(TemporalType.DATE)
	@Column(name = "REPLEVYENDTIME")
	public Date getReplevyendtime() {
		return this.replevyendtime;
	}

	/**
	 * 属性REPLEVYENDTIME的setter方法
	 */
	public void setReplevyendtime(Date replevyendtime) {
		this.replevyendtime = replevyendtime;
	}

	/**
	 * 属性归属机构的getter方法
	 */

	@Column(name = "COMCODE")
	public String getComCode() {
		return this.comCode;
	}

	/**
	 * 属性归属机构的setter方法
	 */
	public void setComCode(String comCode) {
		this.comCode = comCode;
	}

	/**
	 * 属性归属机构名称的getter方法
	 */

	@Column(name = "COMNAME")
	public String getComname() {
		return this.comname;
	}

	/**
	 * 属性归属机构名称的setter方法
	 */
	public void setComname(String comname) {
		this.comname = comname;
	}

	/**
	 * 属性追偿原因的getter方法
	 */

	@Column(name = "REPLEVYREASON")
	public String getReplevyreason() {
		return this.replevyreason;
	}

	/**
	 * 属性追偿原因的setter方法
	 */
	public void setReplevyreason(String replevyreason) {
		this.replevyreason = replevyreason;
	}

	/**
	 * 属性追偿描述的getter方法
	 */

	@Column(name = "REPLEVYTEXT")
	public String getReplevytext() {
		return this.replevytext;
	}

	/**
	 * 属性追偿描述的setter方法
	 */
	public void setReplevytext(String replevytext) {
		this.replevytext = replevytext;
	}

	/**
	 * 属性ADDITIONALREPLEVYFLAG的getter方法
	 */

	@Column(name = "ADDITIONALREPLEVYFLAG")
	public String getAdditionalreplevyflag() {
		return this.additionalreplevyflag;
	}

	/**
	 * 属性ADDITIONALREPLEVYFLAG的setter方法
	 */
	public void setAdditionalreplevyflag(String additionalreplevyflag) {
		this.additionalreplevyflag = additionalreplevyflag;
	}

	/**
	 * 属性ADDITIONALREPLEVYREASON的getter方法
	 */

	@Column(name = "ADDITIONALREPLEVYREASON")
	public String getAdditionalreplevyreason() {
		return this.additionalreplevyreason;
	}

	/**
	 * 属性ADDITIONALREPLEVYREASON的setter方法
	 */
	public void setAdditionalreplevyreason(String additionalreplevyreason) {
		this.additionalreplevyreason = additionalreplevyreason;
	}

	/**
	 * 属性EXTENDSTRING1的getter方法
	 */

	@Column(name = "EXTENDSTRING1")
	public String getExtendString1() {
		return this.extendString1;
	}

	/**
	 * 属性EXTENDSTRING1的setter方法
	 */
	public void setExtendString1(String extendString1) {
		this.extendString1 = extendString1;
	}

	/**
	 * 属性EXTENDSTRING2的getter方法
	 */

	@Column(name = "EXTENDSTRING2")
	public String getExtendString2() {
		return this.extendString2;
	}

	/**
	 * 属性EXTENDSTRING2的setter方法
	 */
	public void setExtendString2(String extendString2) {
		this.extendString2 = extendString2;
	}

	/**
	 * 属性EXTENDSTRING3的getter方法
	 */

	@Column(name = "EXTENDSTRING3")
	public String getExtendstring3() {
		return this.extendstring3;
	}

	/**
	 * 属性EXTENDSTRING3的setter方法
	 */
	public void setExtendstring3(String extendstring3) {
		this.extendstring3 = extendstring3;
	}

}
