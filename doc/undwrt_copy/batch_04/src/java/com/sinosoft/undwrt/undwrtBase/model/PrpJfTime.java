package com.sinosoft.undwrt.undwrtBase.model;

// default package
// 采用工具 Hibernate Tools 3.2.4.GA (sinosoft version) 生成，请勿手工修改。

import java.util.Date;
import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * POJO类PrpJfTime.
 */
@Entity(name = "PRPJFTIME_UNDWRT")
@Table(name = "PRPJFTIME")
public class PrpJfTime implements java.io.Serializable {
	
	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/** 属性id. */
	private PrpJfTimeId id;

	/** 属性属性保单号码. */
	private String policyNo;

	/** 属性收付类型. */
	private String payRefType;

	/** 属性PREPAYREFTIME. */
	private Date prePayRefTime;

	/** 属性支付日期. */
	private Date payRefTime;

	/** 属性核保日期. */
	private Date undwrtTime;

	/** 属性打印时间. */
	private Date printTime;

	/** 属性打印操作人代码. */
	private String printCode;

	/** 属性打印操作人名称. */
	private String printName;

	/** 属性标识. */
	private String falg;

	/** 属性说明. */
	private String remark;

	/**
	 * 类PrpJfTime的默认构造方法.
	 */
	public PrpJfTime() {
	}

	/**
	 * 属性id的getter方法.
	 * 
	 * @return the 属性id
	 */
	@EmbeddedId
	@AttributeOverrides({
			@AttributeOverride(name = "businessNo", column = @Column(name = "BUSINESSNO")),
			@AttributeOverride(name = "certiType", column = @Column(name = "CERTITYPE")) })
	public PrpJfTimeId getId() {
		return this.id;
	}

	/**
	 * 属性id的setter方法.
	 * 
	 * @param id
	 *            the new 属性id
	 */
	public void setId(PrpJfTimeId id) {
		this.id = id;
	}

	/**
	 * 属性属性保单号码的getter方法.
	 * 
	 * @return the 属性属性保单号码
	 */

	@Column(name = "POLICYNO")
	public String getPolicyNo() {
		return this.policyNo;
	}

	/**
	 * 属性属性保单号码的setter方法.
	 * 
	 * @param policyNo
	 *            the new 属性属性保单号码
	 */
	public void setPolicyNo(String policyNo) {
		this.policyNo = policyNo;
	}

	/**
	 * 属性收付类型的getter方法.
	 * 
	 * @return the 属性收付类型
	 */

	@Column(name = "PAYREFTYPE")
	public String getPayRefType() {
		return this.payRefType;
	}

	/**
	 * 属性收付类型的setter方法.
	 * 
	 * @param payRefType
	 *            the new 属性收付类型
	 */
	public void setPayRefType(String payRefType) {
		this.payRefType = payRefType;
	}

	/**
	 * 属性PREPAYREFTIME的getter方法.
	 * 
	 * @return the 属性PREPAYREFTIME
	 */
	@Temporal(TemporalType.DATE)
	@Column(name = "PREPAYREFTIME")
	public Date getPrePayRefTime() {
		return this.prePayRefTime;
	}

	/**
	 * 属性PREPAYREFTIME的setter方法.
	 * 
	 * @param prePayRefTime
	 *            the new 属性PREPAYREFTIME
	 */
	public void setPrePayRefTime(Date prePayRefTime) {
		this.prePayRefTime = prePayRefTime;
	}

	/**
	 * 属性支付日期的getter方法.
	 * 
	 * @return the 属性支付日期
	 */
	@Temporal(TemporalType.DATE)
	@Column(name = "PAYREFTIME")
	public Date getPayRefTime() {
		return this.payRefTime;
	}

	/**
	 * 属性支付日期的setter方法.
	 * 
	 * @param payRefTime
	 *            the new 属性支付日期
	 */
	public void setPayRefTime(Date payRefTime) {
		this.payRefTime = payRefTime;
	}

	/**
	 * 属性核保日期的getter方法.
	 * 
	 * @return the 属性核保日期
	 */
	@Temporal(TemporalType.DATE)
	@Column(name = "UNDWRTTIME")
	public Date getUndwrtTime() {
		return this.undwrtTime;
	}

	/**
	 * 属性核保日期的setter方法.
	 * 
	 * @param undwrtTime
	 *            the new 属性核保日期
	 */
	public void setUndwrtTime(Date undwrtTime) {
		this.undwrtTime = undwrtTime;
	}

	/**
	 * 属性打印时间的getter方法.
	 * 
	 * @return the 属性打印时间
	 */
	@Temporal(TemporalType.DATE)
	@Column(name = "PRINTTIME")
	public Date getPrintTime() {
		return this.printTime;
	}

	/**
	 * 属性打印时间的setter方法.
	 * 
	 * @param printTime
	 *            the new 属性打印时间
	 */
	public void setPrintTime(Date printTime) {
		this.printTime = printTime;
	}

	/**
	 * 属性打印操作人代码的getter方法.
	 * 
	 * @return the 属性打印操作人代码
	 */

	@Column(name = "PRINTCODE")
	public String getPrintCode() {
		return this.printCode;
	}

	/**
	 * 属性打印操作人代码的setter方法.
	 * 
	 * @param printCode
	 *            the new 属性打印操作人代码
	 */
	public void setPrintCode(String printCode) {
		this.printCode = printCode;
	}

	/**
	 * 属性打印操作人名称的getter方法.
	 * 
	 * @return the 属性打印操作人名称
	 */

	@Column(name = "PRINTNAME")
	public String getPrintName() {
		return this.printName;
	}

	/**
	 * 属性打印操作人名称的setter方法.
	 * 
	 * @param printName
	 *            the new 属性打印操作人名称
	 */
	public void setPrintName(String printName) {
		this.printName = printName;
	}

	/**
	 * 属性标识的getter方法.
	 * 
	 * @return the 属性标识
	 */

	@Column(name = "FALG")
	public String getFalg() {
		return this.falg;
	}

	/**
	 * 属性标识的setter方法.
	 * 
	 * @param falg
	 *            the new 属性标识
	 */
	public void setFalg(String falg) {
		this.falg = falg;
	}

	/**
	 * 属性说明的getter方法.
	 * 
	 * @return the 属性说明
	 */

	@Column(name = "REMARK")
	public String getRemark() {
		return this.remark;
	}

	/**
	 * 属性说明的setter方法.
	 * 
	 * @param remark
	 *            the new 属性说明
	 */
	public void setRemark(String remark) {
		this.remark = remark;
	}

}
