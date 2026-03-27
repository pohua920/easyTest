package com.sinosoft.claim.schema.model;

// 采用工具 Hibernate Tools 3.2.4.GA (sinosoft version) 生成，请勿手工修改。

import java.util.Collection;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

import com.sinosoft.claim.dto.custom.TurnPageDto;
import com.sinosoft.sysframework.common.util.StringUtils;

/**
 * POJO类PrpLquickCase简易赔案主表
 */
@Entity
@Table(name = "PRPLQUICKCASE")
public class PrpLquickCase implements java.io.Serializable {
	private static final long serialVersionUID = 1L;

	/** 属性报案号 */
	private String registNo;

	/** 属性保单号 */
	private String policyNo;

	/** 属性操作员代码 */
	private String operatorCode;

	/** 属性业务归属机构代码 */
	private String comCode;

	/** 属性险种代码 */
	private String riskCode;

	/** 属性节点种类 */
	private String nodeType;

	/** 属性处理时间 */
	private Date inputTime;

	/** 属性转入时间 */
	private Date startTime;

	/** 属性结束时间 */
	private Date endTime;

	/** 属性简易赔案状态 */
	private String quickCaseStatus;

	/** 属性保单有效标志 */
	private String validStatus;

	/** 属性备注 */
	private String note;

	/** 属性状态字段 */
	private String flag;
	/** 一页数据 */
	private TurnPageDto turnPageDto = null;
	/** 编辑类型 */
	private String editType = "";
	/** 简易赔案集合 **/
	Collection<PrpLquickCase> prpLquickCaseList;
	/** 属性被保险人名称 */
	private String insuredName = "";

	/** 属性车牌号码 */
	private String licenseNo = "";
	// 关联保单号
	private Collection<?> relatepolicyNo = null;

	/**
	 * 类PrpLquickCase的默认构造方法
	 */
	public PrpLquickCase() {
	}

	/**
	 * 属性报案号的getter方法
	 */
	@Id
	@Column(name = "REGISTNO")
	public String getRegistNo() {
		return this.registNo;
	}

	/**
	 * 属性报案号的setter方法
	 */
	public void setRegistNo(String registNo) {
		this.registNo = registNo;
	}

	/**
	 * 属性保单号的getter方法
	 */

	@Column(name = "POLICYNO")
	public String getPolicyNo() {
		return this.policyNo;
	}

	/**
	 * 属性保单号的setter方法
	 */
	public void setPolicyNo(String policyNo) {
		this.policyNo = policyNo;
	}

	/**
	 * 属性操作员代码的getter方法
	 */

	@Column(name = "OPERATORCODE")
	public String getOperatorCode() {
		return this.operatorCode;
	}

	/**
	 * 属性操作员代码的setter方法
	 */
	public void setOperatorCode(String operatorCode) {
		this.operatorCode = operatorCode;
	}

	/**
	 * 属性业务归属机构代码的getter方法
	 */

	@Column(name = "COMCODE")
	public String getComCode() {
		return this.comCode;
	}

	/**
	 * 属性业务归属机构代码的setter方法
	 */
	public void setComCode(String comCode) {
		this.comCode = comCode;
	}

	/**
	 * 属性险种代码的getter方法
	 */

	@Column(name = "RISKCODE")
	public String getRiskCode() {
		return this.riskCode;
	}

	/**
	 * 属性险种代码的setter方法
	 */
	public void setRiskCode(String riskCode) {
		this.riskCode = riskCode;
	}

	/**
	 * 属性节点种类的getter方法
	 */

	@Column(name = "NODETYPE")
	public String getNodeType() {
		return this.nodeType;
	}

	/**
	 * 属性节点种类的setter方法
	 */
	public void setNodeType(String nodeType) {
		this.nodeType = nodeType;
	}

	/**
	 * 属性处理时间的getter方法
	 */
	@Temporal(TemporalType.DATE)
	@Column(name = "INPUTTIME")
	public Date getInputTime() {
		return this.inputTime;
	}

	/**
	 * 属性处理时间的setter方法
	 */
	public void setInputTime(Date inputTime) {
		this.inputTime = inputTime;
	}

	/**
	 * 属性转入时间的getter方法
	 */
	@Temporal(TemporalType.DATE)
	@Column(name = "STARTTIME")
	public Date getStartTime() {
		return this.startTime;
	}

	/**
	 * 属性转入时间的setter方法
	 */
	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}

	/**
	 * 属性结束时间的getter方法
	 */
	@Temporal(TemporalType.DATE)
	@Column(name = "ENDTIME")
	public Date getEndTime() {
		return this.endTime;
	}

	/**
	 * 属性结束时间的setter方法
	 */
	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}

	/**
	 * 属性简易赔案状态的getter方法
	 */

	@Column(name = "QUICKCASESTATUS")
	public String getQuickCaseStatus() {
		return this.quickCaseStatus;
	}

	/**
	 * 属性简易赔案状态的setter方法
	 */
	public void setQuickCaseStatus(String quickCaseStatus) {
		this.quickCaseStatus = quickCaseStatus;
	}

	/**
	 * 属性保单有效标志的getter方法
	 */

	@Column(name = "VALIDSTATUS")
	public String getValidStatus() {
		return this.validStatus;
	}

	/**
	 * 属性保单有效标志的setter方法
	 */
	public void setValidStatus(String validStatus) {
		this.validStatus = validStatus;
	}

	/**
	 * 属性备注的getter方法
	 */

	@Column(name = "NOTE")
	public String getNote() {
		return this.note;
	}

	/**
	 * 属性备注的setter方法
	 */
	public void setNote(String note) {
		this.note = note;
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
	 * 设置属性被保险人名称
	 * @param insuredName 待设置的属性被保险人名称的值
	 */
	public void setInsuredName(String insuredName) {
		this.insuredName = StringUtils.rightTrim(insuredName);
	}

	/**
	 * 获取属性被保险人名称
	 * @return 属性被保险人名称的值
	 */
	@Transient
	public String getInsuredName() {
		return insuredName;
	}

	/**
	 * 设置属性车牌号码
	 * @param licenseNo 待设置的属性车牌号码的值
	 */
	public void setLicenseNo(String licenseNo) {
		this.licenseNo = StringUtils.rightTrim(licenseNo);
	}

	/**
	 * 获取属性车牌号码
	 * @return 属性车牌号码的值
	 */
	@Transient
	public String getLicenseNo() {
		return licenseNo;
	}

	/**
	 * 设置一页数据
	 * @param turnPageDto 一页数据
	 */
	public void setTurnPageDto(TurnPageDto turnPageDto) {
		this.turnPageDto = turnPageDto;
	}

	/**
	 * 获取一页数据
	 * @return 属性一页数据
	 */
	@Transient
	public TurnPageDto getTurnPageDto() {
		return turnPageDto;
	}

	@Transient
	public String getEditType() {
		return editType;
	}

	public void setEditType(String editType) {
		this.editType = editType;
	}

	/**
	 * 得到wfLogList信息集合
	 * @return wfLogList信息集合信息
	 */
	@Transient
	public Collection<PrpLquickCase> getPrpLquickCaseList() {
		return prpLquickCaseList;
	}

	/**
	 * 设置wfLogList信息集合
	 * @param wfLogList信息集合
	 */
	public void setPrpLquickCaseList(Collection<PrpLquickCase> prpLquickCaseList) {
		this.prpLquickCaseList = prpLquickCaseList;
	}

	@Transient
	public Collection<?> getRelatepolicyNo() {
		return relatepolicyNo;
	}

	public void setRelatepolicyNo(Collection<?> relatepolicyNo) {
		this.relatepolicyNo = relatepolicyNo;
	}
	/**
	 * 默认构造方法,构造一个默认的PrpLquickCaseDto对象
	 */

}
