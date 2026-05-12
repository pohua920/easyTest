package com.sinosoft.claim.schema.model;

// default package
// 采用工具 Hibernate Tools 3.2.4.GA (sinosoft version) 生成，请勿手工修改。

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * POJO类PrpPhead
 */
@Entity
@Table(name = "PRPPHEAD")
public class PrpPhead implements java.io.Serializable {
	private static final long serialVersionUID = 1L;

	/** 属性批单号码 */
	private String endorseNo;

	/** 属性保单号码 */
	private String policyNo;

	/** 属性批单印刷号 */
	private String printNo;

	/** 属性险类代码 */
	private String classCode;

	/** 属性险种代码 */
	private String riskCode;

	/** 属性保单批改次数 */
	private Integer endorseTimes;

	/** 属性出单机构代码 */
	private String makeCom;

	/** 属性赔款计算书号 */
	private String compensateNo;

	/** 属性被保险人代码 */
	private String insuredCode;

	/** 属性被保险人姓名 */
	private String insuredName;

	/** 属性中/英文 */
	private String language;

	/** 属性保单类型 */
	private String policyType;

	/** 属性批改类型 */
	private String endorType;

	/** 属性批改日期 */
	private Date endorDate;

	/** 属性生效日期 */
	private Date validDate;

	/** 属性生效小时 */
	private Integer validHour;

	/** 属性经办人代码 */
	private String handlerCode;

	/** 属性归属业务员代码 */
	private String handler1Code;

	/** 属性复核人代码 */
	private String approverCode;

	/** 属性最终核批人代码 */
	private String underWriteCode;

	/** 属性最终核批人名称 */
	private String underWriteName;

	/** 属性操作员代码 */
	private String operatorCode;

	/** 属性计算机输单日期 */
	private Date inputDate;

	/** 属性计算机输单小时 */
	private Integer inputHour;

	/** 属性业务归属机构代码 */
	private String comCode;

	/** 属性代理人代码 */
	private String agentCode;

	/** 属性批单统计年月 */
	private Date statisticsYM;

	/** 属性核批完成日期 */
	private Date underWriteEndDate;

	/** 属性核批标志 */
	private String underWriteFlag;

	/** 属性标志字段 */
	private String flag;

	/** 属性信息修改人代码 */
	private String updaterCode;

	/** 属性最後一次修改的日期 */
	private Date updateDate;

	/** 属性最後一次修改的小时 */
	private String updateHour;

	/** 属性见费出单标志位 */
	private String jfeeFlag;

	/** 属性预审核时间 */
	private Date precheckDate;

	/** 属性经办人姓名 */
	private String handlerName;

	/** 属性归属业务员姓名 */
	private String handler1Name;

	/** 属性实收确认人代码 */
	private String payrefCode;

	/** 属性实收确认人姓名 */
	private String payrefName;

	/** 属性SUBBUSINESSNATURE */
	private String subBusinessNature;

	/** 属性单证代码 */
	private String visaCode;

	/** 属性交强险及时生效 生效日期 */
	private Date newValidDate;

	/** 属性ENDORREASON */
	private String endorReason;

	/** 属性AGENTFLAG */
	private String agentFlag;

	/** 属性批改申请人 */
	private String endorSeappli;

	/** 属性prpPfees */
	private List<PrpPfee> prpPfees = new ArrayList<PrpPfee>(0);

	/** 属性prpPtexts */
	private List<PrpPtext> prpPtexts = new ArrayList<PrpPtext>(0);

	/** 属性prpPengages */
	private List<PrpPengage> prpPengages = new ArrayList<PrpPengage>(0);

	/** 属性批改保单信息表 */
	private PrpPmain prpPmain;

	/** 属性prpPitemKinds */
	private List<PrpPitemKind> prpPitemKinds = new ArrayList<PrpPitemKind>(0);

	/** 属性prpPitemCars */
	private List<PrpPitemCar> prpPitemCars = new ArrayList<PrpPitemCar>(0);

	/**
	 * 类PrpPhead的默认构造方法
	 */
	public PrpPhead() {
	}

	/**
	 * 属性批单号码的getter方法
	 */
	@Id
	@Column(name = "ENDORSENO")
	public String getEndorseNo() {
		return this.endorseNo;
	}

	/**
	 * 属性批单号码的setter方法
	 */
	public void setEndorseNo(String endorseNo) {
		this.endorseNo = endorseNo;
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
	 * 属性批单印刷号的getter方法
	 */

	@Column(name = "PRINTNO")
	public String getPrintNo() {
		return this.printNo;
	}

	/**
	 * 属性批单印刷号的setter方法
	 */
	public void setPrintNo(String printNo) {
		this.printNo = printNo;
	}

	/**
	 * 属性险类代码的getter方法
	 */

	@Column(name = "CLASSCODE")
	public String getClassCode() {
		return this.classCode;
	}

	/**
	 * 属性险类代码的setter方法
	 */
	public void setClassCode(String classCode) {
		this.classCode = classCode;
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
	 * 属性保单批改次数的getter方法
	 */

	@Column(name = "ENDORSETIMES")
	public Integer getEndorseTimes() {
		return this.endorseTimes;
	}

	/**
	 * 属性保单批改次数的setter方法
	 */
	public void setEndorseTimes(Integer endorseTimes) {
		this.endorseTimes = endorseTimes;
	}

	/**
	 * 属性出单机构代码的getter方法
	 */

	@Column(name = "MAKECOM")
	public String getMakeCom() {
		return this.makeCom;
	}

	/**
	 * 属性出单机构代码的setter方法
	 */
	public void setMakeCom(String makeCom) {
		this.makeCom = makeCom;
	}

	/**
	 * 属性赔款计算书号的getter方法
	 */

	@Column(name = "COMPENSATENO")
	public String getCompensateNo() {
		return this.compensateNo;
	}

	/**
	 * 属性赔款计算书号的setter方法
	 */
	public void setCompensateNo(String compensateNo) {
		this.compensateNo = compensateNo;
	}

	/**
	 * 属性被保险人代码的getter方法
	 */

	@Column(name = "INSUREDCODE")
	public String getInsuredCode() {
		return this.insuredCode;
	}

	/**
	 * 属性被保险人代码的setter方法
	 */
	public void setInsuredCode(String insuredCode) {
		this.insuredCode = insuredCode;
	}

	/**
	 * 属性被保险人姓名的getter方法
	 */

	@Column(name = "INSUREDNAME")
	public String getInsuredName() {
		return this.insuredName;
	}

	/**
	 * 属性被保险人姓名的setter方法
	 */
	public void setInsuredName(String insuredName) {
		this.insuredName = insuredName;
	}

	/**
	 * 属性中/英文的getter方法
	 */

	@Column(name = "LANGUAGE")
	public String getLanguage() {
		return this.language;
	}

	/**
	 * 属性中/英文的setter方法
	 */
	public void setLanguage(String language) {
		this.language = language;
	}

	/**
	 * 属性保单类型的getter方法
	 */

	@Column(name = "POLICYTYPE")
	public String getPolicyType() {
		return this.policyType;
	}

	/**
	 * 属性保单类型的setter方法
	 */
	public void setPolicyType(String policyType) {
		this.policyType = policyType;
	}

	/**
	 * 属性批改类型的getter方法
	 */

	@Column(name = "ENDORTYPE")
	public String getEndorType() {
		return this.endorType;
	}

	/**
	 * 属性批改类型的setter方法
	 */
	public void setEndorType(String endorType) {
		this.endorType = endorType;
	}

	/**
	 * 属性批改日期的getter方法
	 */
	@Temporal(TemporalType.DATE)
	@Column(name = "ENDORDATE")
	public Date getEndorDate() {
		return this.endorDate;
	}

	/**
	 * 属性批改日期的setter方法
	 */
	public void setEndorDate(Date endorDate) {
		this.endorDate = endorDate;
	}

	/**
	 * 属性生效日期的getter方法
	 */
	@Temporal(TemporalType.DATE)
	@Column(name = "VALIDDATE")
	public Date getValidDate() {
		return this.validDate;
	}

	/**
	 * 属性生效日期的setter方法
	 */
	public void setValidDate(Date validDate) {
		this.validDate = validDate;
	}

	/**
	 * 属性生效小时的getter方法
	 */

	@Column(name = "VALIDHOUR")
	public Integer getValidHour() {
		return this.validHour;
	}

	/**
	 * 属性生效小时的setter方法
	 */
	public void setValidHour(Integer validHour) {
		this.validHour = validHour;
	}

	/**
	 * 属性经办人代码的getter方法
	 */

	@Column(name = "HANDLERCODE")
	public String getHandlerCode() {
		return this.handlerCode;
	}

	/**
	 * 属性经办人代码的setter方法
	 */
	public void setHandlerCode(String handlerCode) {
		this.handlerCode = handlerCode;
	}

	/**
	 * 属性归属业务员代码的getter方法
	 */

	@Column(name = "HANDLER1CODE")
	public String getHandler1Code() {
		return this.handler1Code;
	}

	/**
	 * 属性归属业务员代码的setter方法
	 */
	public void setHandler1Code(String handler1Code) {
		this.handler1Code = handler1Code;
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
	 * 属性最终核批人代码的getter方法
	 */

	@Column(name = "UNDERWRITECODE")
	public String getUnderWriteCode() {
		return this.underWriteCode;
	}

	/**
	 * 属性最终核批人代码的setter方法
	 */
	public void setUnderWriteCode(String underWriteCode) {
		this.underWriteCode = underWriteCode;
	}

	/**
	 * 属性最终核批人名称的getter方法
	 */

	@Column(name = "UNDERWRITENAME")
	public String getUnderWriteName() {
		return this.underWriteName;
	}

	/**
	 * 属性最终核批人名称的setter方法
	 */
	public void setUnderWriteName(String underWriteName) {
		this.underWriteName = underWriteName;
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
	 * 属性计算机输单日期的getter方法
	 */
	@Temporal(TemporalType.DATE)
	@Column(name = "INPUTDATE")
	public Date getInputDate() {
		return this.inputDate;
	}

	/**
	 * 属性计算机输单日期的setter方法
	 */
	public void setInputDate(Date inputDate) {
		this.inputDate = inputDate;
	}

	/**
	 * 属性计算机输单小时的getter方法
	 */

	@Column(name = "INPUTHOUR")
	public Integer getInputHour() {
		return this.inputHour;
	}

	/**
	 * 属性计算机输单小时的setter方法
	 */
	public void setInputHour(Integer inputHour) {
		this.inputHour = inputHour;
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
	 * 属性代理人代码的getter方法
	 */

	@Column(name = "AGENTCODE")
	public String getAgentCode() {
		return this.agentCode;
	}

	/**
	 * 属性代理人代码的setter方法
	 */
	public void setAgentCode(String agentCode) {
		this.agentCode = agentCode;
	}

	/**
	 * 属性批单统计年月的getter方法
	 */
	@Temporal(TemporalType.DATE)
	@Column(name = "STATISTICSYM")
	public Date getStatisticsYM() {
		return this.statisticsYM;
	}

	/**
	 * 属性批单统计年月的setter方法
	 */
	public void setStatisticsYM(Date statisticsYM) {
		this.statisticsYM = statisticsYM;
	}

	/**
	 * 属性核批完成日期的getter方法
	 */
	@Temporal(TemporalType.DATE)
	@Column(name = "UNDERWRITEENDDATE")
	public Date getUnderWriteEndDate() {
		return this.underWriteEndDate;
	}

	/**
	 * 属性核批完成日期的setter方法
	 */
	public void setUnderWriteEndDate(Date underWriteEndDate) {
		this.underWriteEndDate = underWriteEndDate;
	}

	/**
	 * 属性核批标志的getter方法
	 */

	@Column(name = "UNDERWRITEFLAG")
	public String getUnderWriteFlag() {
		return this.underWriteFlag;
	}

	/**
	 * 属性核批标志的setter方法
	 */
	public void setUnderWriteFlag(String underWriteFlag) {
		this.underWriteFlag = underWriteFlag;
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
	 * 属性信息修改人代码的getter方法
	 */

	@Column(name = "UPDATERCODE")
	public String getUpdaterCode() {
		return this.updaterCode;
	}

	/**
	 * 属性信息修改人代码的setter方法
	 */
	public void setUpdaterCode(String updaterCode) {
		this.updaterCode = updaterCode;
	}

	/**
	 * 属性最後一次修改的日期的getter方法
	 */
	@Temporal(TemporalType.DATE)
	@Column(name = "UPDATEDATE")
	public Date getUpdateDate() {
		return this.updateDate;
	}

	/**
	 * 属性最後一次修改的日期的setter方法
	 */
	public void setUpdateDate(Date updateDate) {
		this.updateDate = updateDate;
	}

	/**
	 * 属性最後一次修改的小时的getter方法
	 */

	@Column(name = "UPDATEHOUR")
	public String getUpdateHour() {
		return this.updateHour;
	}

	/**
	 * 属性最後一次修改的小时的setter方法
	 */
	public void setUpdateHour(String updateHour) {
		this.updateHour = updateHour;
	}

	/**
	 * 属性见费出单标志位的getter方法
	 */

	@Column(name = "JFEEFLAG")
	public String getJfeeFlag() {
		return this.jfeeFlag;
	}

	/**
	 * 属性见费出单标志位的setter方法
	 */
	public void setJfeeFlag(String jfeeFlag) {
		this.jfeeFlag = jfeeFlag;
	}

	/**
	 * 属性预审核时间的getter方法
	 */
	@Temporal(TemporalType.DATE)
	@Column(name = "PRECHECKDATE")
	public Date getPrecheckDate() {
		return this.precheckDate;
	}

	/**
	 * 属性预审核时间的setter方法
	 */
	public void setPrecheckDate(Date precheckDate) {
		this.precheckDate = precheckDate;
	}

	/**
	 * 属性经办人姓名的getter方法
	 */

	@Column(name = "HANDLERNAME")
	public String getHandlerName() {
		return this.handlerName;
	}

	/**
	 * 属性经办人姓名的setter方法
	 */
	public void setHandlerName(String handlerName) {
		this.handlerName = handlerName;
	}

	/**
	 * 属性归属业务员姓名的getter方法
	 */

	@Column(name = "HANDLER1NAME")
	public String getHandler1Name() {
		return this.handler1Name;
	}

	/**
	 * 属性归属业务员姓名的setter方法
	 */
	public void setHandler1Name(String handler1Name) {
		this.handler1Name = handler1Name;
	}

	/**
	 * 属性实收确认人代码的getter方法
	 */

	@Column(name = "PAYREFCODE")
	public String getPayrefCode() {
		return this.payrefCode;
	}

	/**
	 * 属性实收确认人代码的setter方法
	 */
	public void setPayrefCode(String payrefCode) {
		this.payrefCode = payrefCode;
	}

	/**
	 * 属性实收确认人姓名的getter方法
	 */

	@Column(name = "PAYREFNAME")
	public String getPayrefName() {
		return this.payrefName;
	}

	/**
	 * 属性实收确认人姓名的setter方法
	 */
	public void setPayrefName(String payrefName) {
		this.payrefName = payrefName;
	}

	/**
	 * 属性SUBBUSINESSNATURE的getter方法
	 */

	@Column(name = "SUBBUSINESSNATURE")
	public String getSubBusinessNature() {
		return this.subBusinessNature;
	}

	/**
	 * 属性SUBBUSINESSNATURE的setter方法
	 */
	public void setSubBusinessNature(String subBusinessNature) {
		this.subBusinessNature = subBusinessNature;
	}

	/**
	 * 属性单证代码的getter方法
	 */

	@Column(name = "VISACODE")
	public String getVisaCode() {
		return this.visaCode;
	}

	/**
	 * 属性单证代码的setter方法
	 */
	public void setVisaCode(String visaCode) {
		this.visaCode = visaCode;
	}

	/**
	 * 属性交强险及时生效 生效日期的getter方法
	 */
	@Temporal(TemporalType.DATE)
	@Column(name = "NEWVALIDDATE")
	public Date getNewValidDate() {
		return this.newValidDate;
	}

	/**
	 * 属性交强险及时生效 生效日期的setter方法
	 */
	public void setNewValidDate(Date newValidDate) {
		this.newValidDate = newValidDate;
	}

	/**
	 * 属性ENDORREASON的getter方法
	 */

	@Column(name = "ENDORREASON")
	public String getEndorReason() {
		return this.endorReason;
	}

	/**
	 * 属性ENDORREASON的setter方法
	 */
	public void setEndorReason(String endorReason) {
		this.endorReason = endorReason;
	}

	/**
	 * 属性AGENTFLAG的getter方法
	 */

	@Column(name = "AGENTFLAG")
	public String getAgentFlag() {
		return this.agentFlag;
	}

	/**
	 * 属性AGENTFLAG的setter方法
	 */
	public void setAgentFlag(String agentFlag) {
		this.agentFlag = agentFlag;
	}

	/**
	 * 属性批改申请人的getter方法
	 */

	@Column(name = "ENDORSEAPPLI")
	public String getEndorSeappli() {
		return this.endorSeappli;
	}

	/**
	 * 属性批改申请人的setter方法
	 */
	public void setEndorSeappli(String endorSeappli) {
		this.endorSeappli = endorSeappli;
	}

	/**
	 * 属性prpPfees的getter方法
	 */
	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "prpPhead")
	public List<PrpPfee> getPrpPfees() {
		return this.prpPfees;
	}

	/**
	 * 属性prpPfees的setter方法
	 */
	public void setPrpPfees(List<PrpPfee> prpPfees) {
		this.prpPfees = prpPfees;
	}

	/**
	 * 属性prpPtexts的getter方法
	 */
	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "prpPhead")
	public List<PrpPtext> getPrpPtexts() {
		return this.prpPtexts;
	}

	/**
	 * 属性prpPtexts的setter方法
	 */
	public void setPrpPtexts(List<PrpPtext> prpPtexts) {
		this.prpPtexts = prpPtexts;
	}

	/**
	 * 属性prpPengages的getter方法
	 */
	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "prpPhead")
	public List<PrpPengage> getPrpPengages() {
		return this.prpPengages;
	}

	/**
	 * 属性prpPengages的setter方法
	 */
	public void setPrpPengages(List<PrpPengage> prpPengages) {
		this.prpPengages = prpPengages;
	}

	/**
	 * 属性批改保单信息表的getter方法
	 */
	@OneToOne(fetch = FetchType.LAZY, mappedBy = "prpPhead")
	public PrpPmain getPrpPmain() {
		return this.prpPmain;
	}

	/**
	 * 属性批改保单信息表的setter方法
	 */
	public void setPrpPmain(PrpPmain prpPmain) {
		this.prpPmain = prpPmain;
	}

	/**
	 * 属性prpPitemKinds的getter方法
	 */
	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "prpPhead")
	public List<PrpPitemKind> getPrpPitemKinds() {
		return this.prpPitemKinds;
	}

	/**
	 * 属性prpPitemKinds的setter方法
	 */
	public void setPrpPitemKinds(List<PrpPitemKind> prpPitemKinds) {
		this.prpPitemKinds = prpPitemKinds;
	}

	/**
	 * 属性prpPitemCars的getter方法
	 */
	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "prpPhead")
	public List<PrpPitemCar> getPrpPitemCars() {
		return this.prpPitemCars;
	}

	/**
	 * 属性prpPitemCars的setter方法
	 */
	public void setPrpPitemCars(List<PrpPitemCar> prpPitemCars) {
		this.prpPitemCars = prpPitemCars;
	}

}
