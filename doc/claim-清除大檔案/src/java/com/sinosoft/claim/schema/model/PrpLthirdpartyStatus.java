package com.sinosoft.claim.schema.model;

// 采用工具 Hibernate Tools 3.2.4.GA (sinosoft version) 生成，请勿手工修改。

import java.math.BigDecimal;
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
 * POJO类PrpLthirdpartyStatus与第三方企业信息交互信息状态表
 */
@Entity
@Table(name = "PRPLTHIRDPARTYSTATUS")
public class PrpLthirdpartyStatus implements java.io.Serializable {
	private static final long serialVersionUID = 1L;

	/** 属性id */
	private PrpLthirdpartyStatusId id;

	/** 属性应用类型 */
	private String businesstype;

	/** 属性交互类型 1:发送 2：接收 */
	private String alternatetype;

	/** 属性交互状态标志 */
	private String alternateflag;

	/** 属性发生错误时的错误代码 */
	private String errorno;

	/** 属性交互时间 */
	private Date alternatedate;

	/** 属性交互小时 */
	private BigDecimal alternatehour;

	/** 属性错误描述 */
	private String errormessage;

	/** 属性标志字段 */
	private String flag;

	/**
	 * 类PrpLthirdpartyStatus的默认构造方法
	 */
	public PrpLthirdpartyStatus() {
	}

	/**
	 * 属性id的getter方法
	 */
	@EmbeddedId
	@AttributeOverrides({ @AttributeOverride(name = "businessNo", column = @Column(name = "BUSINESSNO")), @AttributeOverride(name = "serialNo", column = @Column(name = "SERIALNO")) })
	public PrpLthirdpartyStatusId getId() {
		return this.id;
	}

	/**
	 * 属性id的setter方法
	 */
	public void setId(PrpLthirdpartyStatusId id) {
		this.id = id;
	}

	/**
	 * 属性应用类型的getter方法
	 */

	@Column(name = "BUSINESSTYPE")
	public String getBusinesstype() {
		return this.businesstype;
	}

	/**
	 * 属性应用类型的setter方法
	 */
	public void setBusinesstype(String businesstype) {
		this.businesstype = businesstype;
	}

	/**
	 * 属性交互类型 1:发送 2：接收的getter方法
	 */

	@Column(name = "ALTERNATETYPE")
	public String getAlternatetype() {
		return this.alternatetype;
	}

	/**
	 * 属性交互类型 1:发送 2：接收的setter方法
	 */
	public void setAlternatetype(String alternatetype) {
		this.alternatetype = alternatetype;
	}

	/**
	 * 属性交互状态标志的getter方法
	 */

	@Column(name = "ALTERNATEFLAG")
	public String getAlternateflag() {
		return this.alternateflag;
	}

	/**
	 * 属性交互状态标志的setter方法
	 */
	public void setAlternateflag(String alternateflag) {
		this.alternateflag = alternateflag;
	}

	/**
	 * 属性发生错误时的错误代码的getter方法
	 */

	@Column(name = "ERRORNO")
	public String getErrorno() {
		return this.errorno;
	}

	/**
	 * 属性发生错误时的错误代码的setter方法
	 */
	public void setErrorno(String errorno) {
		this.errorno = errorno;
	}

	/**
	 * 属性交互时间的getter方法
	 */
	@Temporal(TemporalType.DATE)
	@Column(name = "ALTERNATEDATE")
	public Date getAlternatedate() {
		return this.alternatedate;
	}

	/**
	 * 属性交互时间的setter方法
	 */
	public void setAlternatedate(Date alternatedate) {
		this.alternatedate = alternatedate;
	}

	/**
	 * 属性交互小时的getter方法
	 */

	@Column(name = "ALTERNATEHOUR")
	public BigDecimal getAlternatehour() {
		return this.alternatehour;
	}

	/**
	 * 属性交互小时的setter方法
	 */
	public void setAlternatehour(BigDecimal alternatehour) {
		this.alternatehour = alternatehour;
	}

	/**
	 * 属性错误描述的getter方法
	 */

	@Column(name = "ERRORMESSAGE")
	public String getErrormessage() {
		return this.errormessage;
	}

	/**
	 * 属性错误描述的setter方法
	 */
	public void setErrormessage(String errormessage) {
		this.errormessage = errormessage;
	}

	/**
	 * 属性标志字段的getter方法
	 */

	@Column(name = "FLAG")
	public String getFlag() {
		return this.flag;
	}

	/**
	 * 属性标志字段的setter方法
	 */
	public void setFlag(String flag) {
		this.flag = flag;
	}

}
