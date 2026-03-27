package com.sinosoft.claim.schema.model;

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
import javax.persistence.Transient;

/**
 * POJO类PrpLext备注摘要信息表
 */
@Entity
@Table(name = "PRPLEXT")
public class PrpLext implements java.io.Serializable {
	private static final long serialVersionUID = 1L;

	/** 属性id */
	private PrpLextId id;

	/** 属性第三者亡人数 */
	private Integer personDeathB;

	/** 属性车上人员亡人数 */
	private Integer personDeathD1;

	/** 属性第三者伤人数 */
	private Integer personInjureB;

	/** 属性车上人员伤人数 */
	private Integer personInjureD1;

	/** 属性承运人 */
	private String carrier;

	/** 属性开航日期 */
	private Date sailStartDate;

	/** 属性卸货日期 */
	private Date unloadDate;

	/** 属性申请查勘日期 */
	private Date appliCheckDate;

	/** 属性投保人或代表联系电话、传真 */
	private String appliPhone;

	/** 属性被保险人或代表联系电话、传真 */
	private String insuredPhone;

	/** 属性货损查勘公司 */
	private String cargoLossCheckCom;

	/** 属性货运险损失类型 */
	private String cargoLossType;

	/** 属性共损理算师 */
	private String shareClaimer;

	/** 属性救助人 */
	private String salvor;

	/** 属性是否涉及担保 */
	private String guaranteeFlag;

	/** 属性币别代码 */
	private String currency;

	/** 属性货价 */
	private Double cargoValue = 0D;

	/** 属性救助担保金额 */
	private Double salvaGuarantAmount;

	/** 属性残值数量 */
	private Integer restQuantity = 0;

	/** 属性担保人 */
	private String guarantor;

	/** 属性预留字段1 */
	private String value1;

	/** 属性预留字段2 */
	private String value2;

	/** 属性预留字段3 */
	private String value3;

	/** 属性备注 */
	private String remark;

	/** 属性状态字段 */
	private String flag;
	/** 属性保额 */
	private String sumAmount = "";
	/** 属性免赔 */
	private String limitAmount = "";
	/** 属性承保公司 */
	private String prpCompanyName = "";
	/** 币别中文名称 */
	private String currencyCname = "";

	/**
	 * 类PrpLext的默认构造方法
	 */
	public PrpLext() {
		id = new PrpLextId();
	}

	/**
	 * 属性id的getter方法
	 */
	@EmbeddedId
	@AttributeOverrides({ @AttributeOverride(name = "certiNo", column = @Column(name = "CERTINO")), @AttributeOverride(name = "certiType", column = @Column(name = "CERTITYPE")) })
	public PrpLextId getId() {
		return this.id;
	}

	/**
	 * 属性id的setter方法
	 */
	public void setId(PrpLextId id) {
		this.id = id;
	}

	/**
	 * 属性第三者亡人数的getter方法
	 */

	@Column(name = "PERSONDEATHB")
	public Integer getPersonDeathB() {
		return this.personDeathB;
	}

	/**
	 * 属性第三者亡人数的setter方法
	 */
	public void setPersonDeathB(Integer personDeathB) {
		this.personDeathB = personDeathB;
	}

	/**
	 * 属性车上人员亡人数的getter方法
	 */

	@Column(name = "PERSONDEATHD1")
	public Integer getPersonDeathD1() {
		return this.personDeathD1;
	}

	/**
	 * 属性车上人员亡人数的setter方法
	 */
	public void setPersonDeathD1(Integer personDeathD1) {
		this.personDeathD1 = personDeathD1;
	}

	/**
	 * 属性第三者伤人数的getter方法
	 */

	@Column(name = "PERSONINJUREB")
	public Integer getPersonInjureB() {
		return this.personInjureB;
	}

	/**
	 * 属性第三者伤人数的setter方法
	 */
	public void setPersonInjureB(Integer personInjureB) {
		this.personInjureB = personInjureB;
	}

	/**
	 * 属性车上人员伤人数的getter方法
	 */

	@Column(name = "PERSONINJURED1")
	public Integer getPersonInjureD1() {
		return this.personInjureD1;
	}

	/**
	 * 属性车上人员伤人数的setter方法
	 */
	public void setPersonInjureD1(Integer personInjureD1) {
		this.personInjureD1 = personInjureD1;
	}

	/**
	 * 属性承运人的getter方法
	 */

	@Column(name = "CARRIER")
	public String getCarrier() {
		return this.carrier;
	}

	/**
	 * 属性承运人的setter方法
	 */
	public void setCarrier(String carrier) {
		this.carrier = carrier;
	}

	/**
	 * 属性开航日期的getter方法
	 */
	@Temporal(TemporalType.DATE)
	@Column(name = "SAILSTARTDATE")
	public Date getSailStartDate() {
		return this.sailStartDate;
	}

	/**
	 * 属性开航日期的setter方法
	 */
	public void setSailStartDate(Date sailStartDate) {
		this.sailStartDate = sailStartDate;
	}

	/**
	 * 属性卸货日期的getter方法
	 */
	@Temporal(TemporalType.DATE)
	@Column(name = "UNLOADDATE")
	public Date getUnloadDate() {
		return this.unloadDate;
	}

	/**
	 * 属性卸货日期的setter方法
	 */
	public void setUnloadDate(Date unloadDate) {
		this.unloadDate = unloadDate;
	}

	/**
	 * 属性申请查勘日期的getter方法
	 */
	@Temporal(TemporalType.DATE)
	@Column(name = "APPLICHECKDATE")
	public Date getAppliCheckDate() {
		return this.appliCheckDate;
	}

	/**
	 * 属性申请查勘日期的setter方法
	 */
	public void setAppliCheckDate(Date appliCheckDate) {
		this.appliCheckDate = appliCheckDate;
	}

	/**
	 * 属性投保人或代表联系电话、传真的getter方法
	 */

	@Column(name = "APPLIPHONE")
	public String getAppliPhone() {
		return this.appliPhone;
	}

	/**
	 * 属性投保人或代表联系电话、传真的setter方法
	 */
	public void setAppliPhone(String appliPhone) {
		this.appliPhone = appliPhone;
	}

	/**
	 * 属性被保险人或代表联系电话、传真的getter方法
	 */

	@Column(name = "INSUREDPHONE")
	public String getInsuredPhone() {
		return this.insuredPhone;
	}

	/**
	 * 属性被保险人或代表联系电话、传真的setter方法
	 */
	public void setInsuredPhone(String insuredPhone) {
		this.insuredPhone = insuredPhone;
	}

	/**
	 * 属性货损查勘公司的getter方法
	 */

	@Column(name = "CARGOLOSSCHECKCOM")
	public String getCargoLossCheckCom() {
		return this.cargoLossCheckCom;
	}

	/**
	 * 属性货损查勘公司的setter方法
	 */
	public void setCargoLossCheckCom(String cargoLossCheckCom) {
		this.cargoLossCheckCom = cargoLossCheckCom;
	}

	/**
	 * 属性货运险损失类型的getter方法
	 */

	@Column(name = "CARGOLOSSTYPE")
	public String getCargoLossType() {
		return this.cargoLossType;
	}

	/**
	 * 属性货运险损失类型的setter方法
	 */
	public void setCargoLossType(String cargoLossType) {
		this.cargoLossType = cargoLossType;
	}

	/**
	 * 属性共损理算师的getter方法
	 */

	@Column(name = "SHARECLAIMER")
	public String getShareClaimer() {
		return this.shareClaimer;
	}

	/**
	 * 属性共损理算师的setter方法
	 */
	public void setShareClaimer(String shareClaimer) {
		this.shareClaimer = shareClaimer;
	}

	/**
	 * 属性救助人的getter方法
	 */

	@Column(name = "SALVOR")
	public String getSalvor() {
		return this.salvor;
	}

	/**
	 * 属性救助人的setter方法
	 */
	public void setSalvor(String salvor) {
		this.salvor = salvor;
	}

	/**
	 * 属性是否涉及担保的getter方法
	 */

	@Column(name = "GUARANTEEFLAG")
	public String getGuaranteeFlag() {
		return this.guaranteeFlag;
	}

	/**
	 * 属性是否涉及担保的setter方法
	 */
	public void setGuaranteeFlag(String guaranteeFlag) {
		this.guaranteeFlag = guaranteeFlag;
	}

	/**
	 * 属性币别代码的getter方法
	 */

	@Column(name = "CURRENCY")
	public String getCurrency() {
		return this.currency;
	}

	/**
	 * 属性币别代码的setter方法
	 */
	public void setCurrency(String currency) {
		this.currency = currency;
	}

	/**
	 * 属性货价的getter方法
	 */

	@Column(name = "CARGOVALUE")
	public Double getCargoValue() {
		return this.cargoValue;
	}

	/**
	 * 属性货价的setter方法
	 */
	public void setCargoValue(Double cargoValue) {
		this.cargoValue = cargoValue;
	}

	/**
	 * 属性救助担保金额的getter方法
	 */

	@Column(name = "SALVAGUARANTAMOUNT")
	public Double getSalvaGuarantAmount() {
		return this.salvaGuarantAmount;
	}

	/**
	 * 属性救助担保金额的setter方法
	 */
	public void setSalvaGuarantAmount(Double salvaGuarantAmount) {
		this.salvaGuarantAmount = salvaGuarantAmount;
	}

	/**
	 * 属性残值数量的getter方法
	 */

	@Column(name = "RESTQUANTITY")
	public Integer getRestQuantity() {
		return this.restQuantity;
	}

	/**
	 * 属性残值数量的setter方法
	 */
	public void setRestQuantity(Integer restQuantity) {
		this.restQuantity = restQuantity;
	}

	/**
	 * 属性担保人的getter方法
	 */

	@Column(name = "GUARANTOR")
	public String getGuarantor() {
		return this.guarantor;
	}

	/**
	 * 属性担保人的setter方法
	 */
	public void setGuarantor(String guarantor) {
		this.guarantor = guarantor;
	}

	/**
	 * 属性预留字段1的getter方法
	 */

	@Column(name = "VALUE1")
	public String getValue1() {
		return this.value1;
	}

	/**
	 * 属性预留字段1的setter方法
	 */
	public void setValue1(String value1) {
		this.value1 = value1;
	}

	/**
	 * 属性预留字段2的getter方法
	 */

	@Column(name = "VALUE2")
	public String getValue2() {
		return this.value2;
	}

	/**
	 * 属性预留字段2的setter方法
	 */
	public void setValue2(String value2) {
		this.value2 = value2;
	}

	/**
	 * 属性预留字段3的getter方法
	 */

	@Column(name = "VALUE3")
	public String getValue3() {
		return this.value3;
	}

	/**
	 * 属性预留字段3的setter方法
	 */
	public void setValue3(String value3) {
		this.value3 = value3;
	}

	/**
	 * 属性备注的getter方法
	 */

	@Column(name = "REMARK")
	public String getRemark() {
		return this.remark;
	}

	/**
	 * 属性备注的setter方法
	 */
	public void setRemark(String remark) {
		this.remark = remark;
	}

	/**
	 * 属性状态字段的getter方法
	 */

	@Column(name = "FLAG")
	public String getFlag() {
		return this.flag;
	}

	/**
	 * 属性状态字段的setter方法
	 */
	public void setFlag(String flag) {
		this.flag = flag;
	}

	/**
	 * 获取属性保额
	 * @return 属性保额
	 */
	@Transient
	public String getSumAmount() {
		return sumAmount;
	}

	/**
	 * 设置属性保额
	 * @param sumAmount 待设置的属性保额
	 */
	public void setSumAmount(String sumAmount) {
		this.sumAmount = sumAmount;
	}

	/**
	 * 获取属性免赔
	 * @return 属性免赔
	 */
	@Transient
	public String getLimitAmount() {
		return limitAmount;
	}

	/**
	 * 设置属性免赔
	 * @param sumAmount 待设置的属性免赔
	 */
	public void setLimitAmount(String limitAmount) {
		this.limitAmount = limitAmount;
	}

	/**
	 * 获取属性承保公司
	 * @return 属性承保公司
	 */
	@Transient
	public String getPrpCompanyName() {
		return prpCompanyName;
	}

	/**
	 * 设置属性承保公司
	 * @param sumAmount 待设置的属性承保公司
	 */
	public void setPrpCompanyName(String prpCompanyName) {
		this.prpCompanyName = prpCompanyName;
	}

	/**
	 * 获取属性币别中文名称
	 * @return 属性币别中文名称
	 */
	@Transient
	public String getCurrencyCname() {
		return currencyCname;
	}

	/**
	 * 设置属性币别中文名称
	 * @param 待设置的币别中文名称
	 */
	public void setCurrencyCname(String currencyCname) {
		this.currencyCname = currencyCname;
	}

}
