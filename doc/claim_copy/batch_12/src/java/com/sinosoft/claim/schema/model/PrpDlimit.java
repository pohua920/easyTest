package com.sinosoft.claim.schema.model;

// 采用工具 Hibernate Tools 3.2.4.GA (sinosoft version) 生成，请勿手工修改。

import java.math.BigDecimal;
import java.util.List;

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Transient;

/**
 * POJO类PrpDlimit 限额免赔代码表
 */
@Entity
@Table(name = "PRPDLIMIT")
public class PrpDlimit implements java.io.Serializable {
	private static final long serialVersionUID = 1L;

	/** 属性id */
	private PrpDlimitId id;

	/** 属性限额/免赔中文名称 */
	private String limitCName;

	/** 属性限额/免赔英文名称 */
	private String limitEName;

	/** 属性限额/免赔优先级别[0,9] */
	private String limitPriority;

	/** 属性险别 */
	private String kindCode;

	/** 属性标的代码 */
	private String itemCode;

	/** 属性投保方式/责任分类 */
	private String modeCode;

	/** 属性LIMITFEE */
	private BigDecimal limitFee;

	/** 属性限额之间关系 */
	private String limitRelation;

	/** 属性限额之间关系说明 */
	private String limitRelationDesc;

	/** 属性新的限额类别代码 */
	private String newLimitCode;

	/** 属性有效状态 */
	private String validStatus;

	/** 属性LIMITFLAG */
	private String limitFlag;

	/** 属性标志 */
	private String flag;

	/** 属性交费计划序号 */
	private String serialNo;

	/** 属性CALCULATEFLAG */
	private String calculateFlag;

	/** 属性LIMITNO */
	private String limitNo;
	/** 列表 */
	List<PrpDlimit> prpDlimitList = null;

	/**
	 * 类PrpDlimit的默认构造方法
	 */
	public PrpDlimit() {
		this.id = new PrpDlimitId();
	}

	/**
	 * 属性id的getter方法
	 */
	@EmbeddedId
	@AttributeOverrides({ @AttributeOverride(name = "riskCode", column = @Column(name = "RISKCODE")), @AttributeOverride(name = "limitCode", column = @Column(name = "LIMITCODE")) })
	public PrpDlimitId getId() {
		return this.id;
	}

	/**
	 * 属性id的setter方法
	 */
	public void setId(PrpDlimitId id) {
		this.id = id;
	}

	/**
	 * 属性限额/免赔中文名称的getter方法
	 */

	@Column(name = "LIMITCNAME")
	public String getLimitCName() {
		return this.limitCName;
	}

	/**
	 * 属性限额/免赔中文名称的setter方法
	 */
	public void setLimitCName(String limitCName) {
		this.limitCName = limitCName;
	}

	/**
	 * 属性限额/免赔英文名称的getter方法
	 */

	@Column(name = "LIMITENAME")
	public String getLimitEName() {
		return this.limitEName;
	}

	/**
	 * 属性限额/免赔英文名称的setter方法
	 */
	public void setLimitEName(String limitEName) {
		this.limitEName = limitEName;
	}

	/**
	 * 属性限额/免赔优先级别[0,9]的getter方法
	 */

	@Column(name = "LIMITPRIORITY")
	public String getLimitPriority() {
		return this.limitPriority;
	}

	/**
	 * 属性限额/免赔优先级别[0,9]的setter方法
	 */
	public void setLimitPriority(String limitPriority) {
		this.limitPriority = limitPriority;
	}

	/**
	 * 属性险别的getter方法
	 */

	@Column(name = "KINDCODE")
	public String getKindCode() {
		return this.kindCode;
	}

	/**
	 * 属性险别的setter方法
	 */
	public void setKindCode(String kindCode) {
		this.kindCode = kindCode;
	}

	/**
	 * 属性标的代码的getter方法
	 */

	@Column(name = "ITEMCODE")
	public String getItemCode() {
		return this.itemCode;
	}

	/**
	 * 属性标的代码的setter方法
	 */
	public void setItemCode(String itemCode) {
		this.itemCode = itemCode;
	}

	/**
	 * 属性投保方式/责任分类的getter方法
	 */

	@Column(name = "MODECODE")
	public String getModeCode() {
		return this.modeCode;
	}

	/**
	 * 属性投保方式/责任分类的setter方法
	 */
	public void setModeCode(String modeCode) {
		this.modeCode = modeCode;
	}

	/**
	 * 属性LIMITFEE的getter方法
	 */

	@Column(name = "LIMITFEE")
	public BigDecimal getLimitFee() {
		return this.limitFee;
	}

	/**
	 * 属性LIMITFEE的setter方法
	 */
	public void setLimitFee(BigDecimal limitFee) {
		this.limitFee = limitFee;
	}

	/**
	 * 属性限额之间关系的getter方法
	 */

	@Column(name = "LIMITRELATION")
	public String getLimitRelation() {
		return this.limitRelation;
	}

	/**
	 * 属性限额之间关系的setter方法
	 */
	public void setLimitRelation(String limitRelation) {
		this.limitRelation = limitRelation;
	}

	/**
	 * 属性限额之间关系说明的getter方法
	 */

	@Column(name = "LIMITRELATIONDESC")
	public String getLimitRelationDesc() {
		return this.limitRelationDesc;
	}

	/**
	 * 属性限额之间关系说明的setter方法
	 */
	public void setLimitRelationDesc(String limitRelationDesc) {
		this.limitRelationDesc = limitRelationDesc;
	}

	/**
	 * 属性新的限额类别代码的getter方法
	 */

	@Column(name = "NEWLIMITCODE")
	public String getNewLimitCode() {
		return this.newLimitCode;
	}

	/**
	 * 属性新的限额类别代码的setter方法
	 */
	public void setNewLimitCode(String newLimitCode) {
		this.newLimitCode = newLimitCode;
	}

	/**
	 * 属性有效状态的getter方法
	 */

	@Column(name = "VALIDSTATUS")
	public String getValidStatus() {
		return this.validStatus;
	}

	/**
	 * 属性有效状态的setter方法
	 */
	public void setValidStatus(String validStatus) {
		this.validStatus = validStatus;
	}

	/**
	 * 属性LIMITFLAG的getter方法
	 */

	@Column(name = "LIMITFLAG")
	public String getLimitFlag() {
		return this.limitFlag;
	}

	/**
	 * 属性LIMITFLAG的setter方法
	 */
	public void setLimitFlag(String limitFlag) {
		this.limitFlag = limitFlag;
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

	/**
	 * 属性交费计划序号的getter方法
	 */

	@Column(name = "SERIALNO")
	public String getSerialNo() {
		return this.serialNo;
	}

	/**
	 * 属性交费计划序号的setter方法
	 */
	public void setSerialNo(String serialNo) {
		this.serialNo = serialNo;
	}

	/**
	 * 属性CALCULATEFLAG的getter方法
	 */

	@Column(name = "CALCULATEFLAG")
	public String getCalculateFlag() {
		return this.calculateFlag;
	}

	/**
	 * 属性CALCULATEFLAG的setter方法
	 */
	public void setCalculateFlag(String calculateFlag) {
		this.calculateFlag = calculateFlag;
	}

	/**
	 * 属性LIMITNO的getter方法
	 */

	@Column(name = "LIMITNO")
	public String getLimitNo() {
		return this.limitNo;
	}

	/**
	 * 属性LIMITNO的setter方法
	 */
	public void setLimitNo(String limitNo) {
		this.limitNo = limitNo;
	}

	public void setPrpDlimitList(List<PrpDlimit> prpDlimitList) {
		this.prpDlimitList = prpDlimitList;
	}

	@Transient
	public List<PrpDlimit> getPrpDlimitList() {
		return prpDlimitList;
	}

}
