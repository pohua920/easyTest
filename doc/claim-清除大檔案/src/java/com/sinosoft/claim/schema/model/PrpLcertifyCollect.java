package com.sinosoft.claim.schema.model;

// 采用工具 Hibernate Tools 3.2.4.GA (sinosoft version) 生成，请勿手工修改。

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

import com.sinosoft.sysframework.common.datatype.DateTime;

/**
 * POJO类PrpLcertifyCollect 单证收集表
 */
@Entity
@Table(name = "PRPLCERTIFYCOLLECT")
public class PrpLcertifyCollect implements java.io.Serializable {
	private static final long serialVersionUID = 1L;

	/** 属性id */
	private PrpLcertifyCollectId id;

	/** 属性标的名称 */
	private String lossItemName;

	/** 属性单证份数 */
	private BigDecimal picCount;

	/** 属性起保日期 */
	private Date startDate;

	/** 属性起保小时 */
	private String startHour;

	/** 属性终保日期 */
	private Date endDate;

	/** 属性终保小时 */
	private String endHour;

	/** 属性操作员代码 */
	private String operatorCode;
	private String operatorName;

	/** 属性收集标志 */
	private String collectFlag;

	/** 属性主车收集标志 */
	private String cltInsureCarFlag;

	/** 属性三者车收集标志 */
	private String cltThirdCarFlag;

	/** 属性伤收集标志 */
	private String cltPersonFlag;

	/** 属性物损收集标志 */
	private String cltPropFlag;

	/** 属性盗抢收集标志 */
	private String cltCarLossFlag;

	/** 属性全损收集标志 */
	private String cltAllLossFlag;

	/** 属性存放事故类型 */
	private String caseFlag;

	/** 属性反馈/回访内容 */
	private String content;

	/** 属性标志字段 */
	private String flag;

	/** 属性保单号码 */
	private String policyNo;

	/** 属性险种代码 */
	private String riskCode;

	/** 属性年 */
	private String uploadYear;

	/** 属性强制保险收集标志 */
	private String compelFlag;

	/** 单证的生成状态，数据库中不存在字段 */
	private String status;
	/** 存放案件信息 */
	private String noSubmitMsg;
	/** 集合 **/
	List<PrpLcertifyCollect> certifyCollectList;
	/** 编辑类型 */
	private String editType = "";
	/** 属性此案件的操作时间 */
	private DateTime operateDate = new DateTime();
	/** 属性出险次数 */
	private int perilCount = 0;

	/**
	 * 属性operateDate的方法
	 */
	@Transient
	public DateTime getOperateDate() {
		return operateDate;
	}

	@Transient
	public int getPerilCount() {
		return perilCount;
	}

	/**
	 * 属性perilCount的方法
	 */
	public void setPerilCount(int perilCount) {
		this.perilCount = perilCount;
	}

	@Transient
	public void setOperateDate(DateTime operateDate) {
		this.operateDate = operateDate;
	}

	/**
	 * 属性editType的方法
	 */
	@Transient
	public String getEditType() {
		return editType;
	}

	public void setEditType(String editType) {
		this.editType = editType;
	}

	/**
	 * 属性Collection的方法
	 */
	@Transient
	public List<PrpLcertifyCollect> getCertifyCollectList() {
		return certifyCollectList;
	}

	public void setCertifyCollectList(List<PrpLcertifyCollect> certifyCollectList) {
		this.certifyCollectList = certifyCollectList;
	}

	/**
	 * 类PrpLcertifyCollect的默认构造方法
	 */
	public PrpLcertifyCollect() {
		id = new PrpLcertifyCollectId();
	}

	/**
	 * 属性id的getter方法
	 */
	@EmbeddedId
	@AttributeOverrides({ @AttributeOverride(name = "businessNo", column = @Column(name = "BUSINESSNO")), @AttributeOverride(name = "lossItemCode", column = @Column(name = "LOSSITEMCODE")) })
	public PrpLcertifyCollectId getId() {
		return this.id;
	}

	/**
	 * 属性id的setter方法
	 */
	public void setId(PrpLcertifyCollectId id) {
		this.id = id;
	}

	/**
	 * 属性标的名称的getter方法
	 */

	@Column(name = "LOSSITEMNAME")
	public String getLossItemName() {
		return this.lossItemName;
	}

	/**
	 * 属性标的名称的setter方法
	 */
	public void setLossItemName(String lossItemName) {
		this.lossItemName = lossItemName;
	}

	/**
	 * 属性单证份数的getter方法
	 */

	@Column(name = "PICCOUNT")
	public BigDecimal getPicCount() {
		return this.picCount;
	}

	/**
	 * 属性单证份数的setter方法
	 */
	public void setPicCount(BigDecimal picCount) {
		this.picCount = picCount;
	}

	/**
	 * 属性起保日期的getter方法
	 */
	@Temporal(TemporalType.DATE)
	@Column(name = "STARTDATE")
	public Date getStartDate() {
		return this.startDate;
	}

	/**
	 * 属性起保日期的setter方法
	 */
	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	/**
	 * 属性起保小时的getter方法
	 */

	@Column(name = "STARTHOUR")
	public String getStartHour() {
		return this.startHour;
	}

	/**
	 * 属性起保小时的setter方法
	 */
	public void setStartHour(String startHour) {
		this.startHour = startHour;
	}

	/**
	 * 属性终保日期的getter方法
	 */
	@Temporal(TemporalType.DATE)
	@Column(name = "ENDDATE")
	public Date getEndDate() {
		return this.endDate;
	}

	/**
	 * 属性终保日期的setter方法
	 */
	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	/**
	 * 属性终保小时的getter方法
	 */

	@Column(name = "ENDHOUR")
	public String getEndHour() {
		return this.endHour;
	}

	/**
	 * 属性终保小时的setter方法
	 */
	public void setEndHour(String endHour) {
		this.endHour = endHour;
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
	 * 属性操作员代码的setter方法
	 */
	public void setOperatorName(String operatorName) {
		if (operatorName == null) {
			this.operatorName = "";
		} else {
			this.operatorName = operatorName;
		}
	}

	@Transient
	public String getOperatorName() {
		return this.operatorName;
	}

	/**
	 * 属性收集标志的getter方法
	 */

	@Column(name = "COLLECTFLAG")
	public String getCollectFlag() {
		return this.collectFlag;
	}

	/**
	 * 属性收集标志的setter方法
	 */
	public void setCollectFlag(String collectFlag) {
		this.collectFlag = collectFlag;
	}

	/**
	 * 属性主车收集标志的getter方法
	 */

	@Column(name = "CLTINSURECARFLAG")
	public String getCltInsureCarFlag() {
		return this.cltInsureCarFlag;
	}

	/**
	 * 属性主车收集标志的setter方法
	 */
	public void setCltInsureCarFlag(String cltInsureCarFlag) {
		this.cltInsureCarFlag = cltInsureCarFlag;
	}

	/**
	 * 属性三者车收集标志的getter方法
	 */

	@Column(name = "CLTTHIRDCARFLAG")
	public String getCltThirdCarFlag() {
		return this.cltThirdCarFlag;
	}

	/**
	 * 属性三者车收集标志的setter方法
	 */
	public void setCltThirdCarFlag(String cltThirdCarFlag) {
		this.cltThirdCarFlag = cltThirdCarFlag;
	}

	/**
	 * 属性伤收集标志的getter方法
	 */

	@Column(name = "CLTPERSONFLAG")
	public String getCltPersonFlag() {
		return this.cltPersonFlag;
	}

	/**
	 * 属性伤收集标志的setter方法
	 */
	public void setCltPersonFlag(String cltPersonFlag) {
		this.cltPersonFlag = cltPersonFlag;
	}

	/**
	 * 属性物损收集标志的getter方法
	 */

	@Column(name = "CLTPROPFLAG")
	public String getCltPropFlag() {
		return this.cltPropFlag;
	}

	/**
	 * 属性物损收集标志的setter方法
	 */
	public void setCltPropFlag(String cltPropFlag) {
		this.cltPropFlag = cltPropFlag;
	}

	/**
	 * 属性盗抢收集标志的getter方法
	 */

	@Column(name = "CLTCARLOSSFLAG")
	public String getCltCarLossFlag() {
		return this.cltCarLossFlag;
	}

	/**
	 * 属性盗抢收集标志的setter方法
	 */
	public void setCltCarLossFlag(String cltCarLossFlag) {
		this.cltCarLossFlag = cltCarLossFlag;
	}

	/**
	 * 属性全损收集标志的getter方法
	 */

	@Column(name = "CLTALLLOSSFLAG")
	public String getCltAllLossFlag() {
		return this.cltAllLossFlag;
	}

	/**
	 * 属性全损收集标志的setter方法
	 */
	public void setCltAllLossFlag(String cltAllLossFlag) {
		this.cltAllLossFlag = cltAllLossFlag;
	}

	/**
	 * 属性存放事故类型的getter方法
	 */

	@Column(name = "CASEFLAG")
	public String getCaseFlag() {
		return this.caseFlag;
	}

	/**
	 * 属性存放事故类型的setter方法
	 */
	public void setCaseFlag(String caseFlag) {
		this.caseFlag = caseFlag;
	}

	/**
	 * 属性反馈/回访内容的getter方法
	 */

	@Column(name = "CONTENT")
	public String getContent() {
		return this.content;
	}

	/**
	 * 属性反馈/回访内容的setter方法
	 */
	public void setContent(String content) {
		this.content = content;
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
	 * 属性年的getter方法
	 */

	@Column(name = "UPLOADYEAR")
	public String getUploadYear() {
		return this.uploadYear;
	}

	/**
	 * 属性年的setter方法
	 */
	public void setUploadYear(String uploadYear) {
		this.uploadYear = uploadYear;
	}

	/**
	 * 属性强制保险收集标志的getter方法
	 */

	@Column(name = "COMPELFLAG")
	public String getCompelFlag() {
		return this.compelFlag;
	}

	/**
	 * 属性强制保险收集标志的setter方法
	 */
	public void setCompelFlag(String compelFlag) {
		this.compelFlag = compelFlag;
	}

	@Transient
	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	@Transient
	public String getNoSubmitMsg() {
		return noSubmitMsg;
	}

	public void setNoSubmitMsg(String noSubmitMsg) {
		this.noSubmitMsg = noSubmitMsg;
	}

}
