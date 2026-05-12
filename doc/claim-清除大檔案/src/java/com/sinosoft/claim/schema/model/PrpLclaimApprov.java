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
 * POJO类PrpLclaimApprov代赔数据转出确认表
 */
@Entity
@Table(name = "PRPLCLAIMAPPROV")
public class PrpLclaimApprov implements java.io.Serializable {
	private static final long serialVersionUID = 1L;

	/** 属性REGISTNO */
	private String registno;

	/** 属性复核人代码 */
	private String approverCode;

	/** 属性确认日期 */
	private Date approvDate;

	/** 属性是否转出标志 */
	private String transferStatus;

	/** 属性标志 */
	private String flag;

	/**
	 * 类PrpLclaimApprov的默认构造方法
	 */
	public PrpLclaimApprov() {
	}

	/**
	 * 属性REGISTNO的getter方法
	 */
	@Id
	@Column(name = "REGISTNO")
	public String getRegistno() {
		return this.registno;
	}

	/**
	 * 属性REGISTNO的setter方法
	 */
	public void setRegistno(String registno) {
		this.registno = registno;
	}

	/**
	 * 属性复核人代码的getter方法
	 */

	@Column(name = "APPROVERCODE")
	public String getApproverCode() {
		return this.approverCode;
	}

	/**
	 * 属性复核人代码的setter方法
	 */
	public void setApproverCode(String approverCode) {
		this.approverCode = approverCode;
	}

	/**
	 * 属性确认日期的getter方法
	 */
	@Temporal(TemporalType.DATE)
	@Column(name = "APPROVDATE")
	public Date getApprovDate() {
		return this.approvDate;
	}

	/**
	 * 属性确认日期的setter方法
	 */
	public void setApprovDate(Date approvDate) {
		this.approvDate = approvDate;
	}

	/**
	 * 属性是否转出标志的getter方法
	 */

	@Column(name = "TRANSFERSTATUS")
	public String getTransferStatus() {
		return this.transferStatus;
	}

	/**
	 * 属性是否转出标志的setter方法
	 */
	public void setTransferStatus(String transferStatus) {
		this.transferStatus = transferStatus;
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
