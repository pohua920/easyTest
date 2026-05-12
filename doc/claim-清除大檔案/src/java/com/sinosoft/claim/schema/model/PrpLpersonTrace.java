package com.sinosoft.claim.schema.model;

// 采用工具 Hibernate Tools 3.2.4.GA (sinosoft version) 生成，请勿手工修改。

import java.util.Collection;
import java.util.Date;//mantis：CLM0181，處理人員：DP0713，需求單編號：新核心-案件備案WS 3.10查詢及記錄留存作業

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.sinosoft.sysframework.common.util.StringUtils;

/**
 * POJO类PrpLpersonTrace人伤跟踪表
 */
@Entity
@Table(name = "PRPLPERSONTRACE")
public class PrpLpersonTrace implements java.io.Serializable {
	private static final long serialVersionUID = 1L;

	/** 属性id */
	private PrpLpersonTraceId id;

	/** 属性立案号码 */
	private String claimNo = "";

	/** 属性保单号 */
	private String policyNo = "";

	/** 属性人员姓名 */
	private String personName = "";

	/** 属性性别 */
	private String personSex = "";

	/** 属性年龄 */
	private Integer personAge = 0;

	/** 属性IdentifyNumber */
	private String identifyNumber = "";

	/** 属性关联人员序号 */
	private Integer relatePersonNo = 0;

	/** 属性行业代码 */
	private String jobCode = "";

	/** 属性行业名称 */
	private String jobName = "";

	/** 属性事故所涉及险种 */
	private String referKind = "";

	/** 属性受伤部位描述 */
	private String partDesc = "";

	/** 属性就诊医院 */
	private String hospital = "";

	/** 属性是否自行就医 */
	private String motionFlag = "";

	/** 属性伤情描述 */
	private String woundRemark = "";

	/** 属性备注 */
	private String remark = "";

	/** 属性状态字段 */
	private String flag = "";

	/** 属性是哪个节点的调用 */
	private String nodeType = "";

	private Collection<PrpLpersonTrace> personTraceList;

	/** 险别 */
	private String prpLpersonTraceReferKind = "";

	/** 属性一级行业代码 */
	private String jobCode1 = "";
	/** 属性一级行业名称 */
	private String jobName1 = "";
	/** 属性二级行业代码 */
	private String jobCode2 = "";
	/** 属性二级行业名称 */
	private String jobName2 = "";
	/** 属性调度处理标志 */
	private String scheduleType = "";
	/** 属性是否选择发送 */
	private String selectSend = "";
	/** 就診醫師*/
	private String doctor = "";
	/** 就诊医院代碼*/
	private String hospitalCode = "";
	
	//mantis：CLM0181，處理人員：DP0713，需求單編號：新核心-案件備案WS 3.10查詢及記錄留存作業 START
	/** 受害人身分證號 **/
	private String idNumber = "";
	/** 出險時乘坐狀況 **/
	private String rideSituation = "";
	/** 出險時乘坐牌照號碼 **/
	private String licenseno = "";
	/** 查詢區塊鏈日期時間**/
	private Date bklineQueryDate;
	/** 受害人身分證號類別**/
	private String idNumberType;
	/** 受害人出生年月日**/
	private Date applicantBirthday;
	//mantis：CLM0181，處理人員：DP0713，需求單編號：新核心-案件備案WS 3.10查詢及記錄留存作業 END

	/**
	 * 类PrpLpersonTrace的默认构造方法
	 */
	public PrpLpersonTrace() {
		id = new PrpLpersonTraceId();
	}

	/**
	 * 属性id的getter方法
	 */
	@EmbeddedId
	@AttributeOverrides({ @AttributeOverride(name = "registNo", column = @Column(name = "REGISTNO")), @AttributeOverride(name = "personNo", column = @Column(name = "PERSONNO")) })
	public PrpLpersonTraceId getId() {
		return this.id;
	}

	/**
	 * 属性id的setter方法
	 */
	public void setId(PrpLpersonTraceId id) {
		this.id = id;
	}

	/**
	 * 属性立案号码的getter方法
	 */

	@Column(name = "CLAIMNO")
	public String getClaimNo() {
		return this.claimNo;
	}

	/**
	 * 属性立案号码的setter方法
	 */
	public void setClaimNo(String claimNo) {
		this.claimNo = claimNo;
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
	 * 属性人员姓名的getter方法
	 */

	@Column(name = "PERSONNAME")
	public String getPersonName() {
		return this.personName;
	}

	/**
	 * 属性人员姓名的setter方法
	 */
	public void setPersonName(String personName) {
		this.personName = personName;
	}

	/**
	 * 属性性别的getter方法
	 */

	@Column(name = "PERSONSEX")
	public String getPersonSex() {
		return this.personSex;
	}

	/**
	 * 属性性别的setter方法
	 */
	public void setPersonSex(String personSex) {
		this.personSex = personSex;
	}

	/**
	 * 属性年龄的getter方法
	 */

	@Column(name = "PERSONAGE")
	public Integer getPersonAge() {
		return this.personAge;
	}

	/**
	 * 属性年龄的setter方法
	 */
	public void setPersonAge(Integer personAge) {
		this.personAge = personAge;
	}

	/**
	 * 属性IdentifyNumber的getter方法
	 */

	@Column(name = "IDENTIFYNUMBER")
	public String getIdentifyNumber() {
		return this.identifyNumber;
	}

	/**
	 * 属性IdentifyNumber的setter方法
	 */
	public void setIdentifyNumber(String identifyNumber) {
		this.identifyNumber = identifyNumber;
	}

	/**
	 * 属性关联人员序号的getter方法
	 */

	@Column(name = "RELATEPERSONNO")
	public Integer getRelatePersonNo() {
		return this.relatePersonNo;
	}

	/**
	 * 属性关联人员序号的setter方法
	 */
	public void setRelatePersonNo(Integer relatePersonNo) {
		this.relatePersonNo = relatePersonNo;
	}

	/**
	 * 属性行业代码的getter方法
	 */

	@Column(name = "JOBCODE")
	public String getJobCode() {
		return this.jobCode;
	}

	/**
	 * 属性行业代码的setter方法
	 */
	public void setJobCode(String jobCode) {
		this.jobCode = jobCode;
	}

	/**
	 * 属性行业名称的getter方法
	 */

	@Column(name = "JOBNAME")
	public String getJobName() {
		return this.jobName;
	}

	/**
	 * 属性行业名称的setter方法
	 */
	public void setJobName(String jobName) {
		this.jobName = jobName;
	}

	/**
	 * 属性事故所涉及险种的getter方法
	 */

	@Column(name = "REFERKIND")
	public String getReferKind() {
		return this.referKind;
	}

	/**
	 * 属性事故所涉及险种的setter方法
	 */
	public void setReferKind(String referKind) {
		this.referKind = referKind;
	}

	/**
	 * 属性受伤部位描述的getter方法
	 */

	@Column(name = "PARTDESC")
	public String getPartDesc() {
		return this.partDesc;
	}

	/**
	 * 属性受伤部位描述的setter方法
	 */
	public void setPartDesc(String partDesc) {
		this.partDesc = partDesc;
	}

	/**
	 * 属性就诊医院的getter方法
	 */

	@Column(name = "HOSPITAL")
	public String getHospital() {
		return this.hospital;
	}

	/**
	 * 属性就诊医院的setter方法
	 */
	public void setHospital(String hospital) {
		this.hospital = hospital;
	}

	/**
	 * 属性是否自行就医的getter方法
	 */

	@Column(name = "MOTIONFLAG")
	public String getMotionFlag() {
		return this.motionFlag;
	}

	/**
	 * 属性是否自行就医的setter方法
	 */
	public void setMotionFlag(String motionFlag) {
		this.motionFlag = motionFlag;
	}

	/**
	 * 属性伤情描述的getter方法
	 */

	@Column(name = "WOUNDREMARK")
	public String getWoundRemark() {
		return this.woundRemark;
	}

	/**
	 * 属性伤情描述的setter方法
	 */
	public void setWoundRemark(String woundRemark) {
		this.woundRemark = woundRemark;
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
	 * 设置属性节点类型
	 * @param nodeType 待设置的属性节点类型的值
	 */
	public void setNodeType(String nodeType) {
		this.nodeType = StringUtils.rightTrim(nodeType);
	}

	/**
	 * 获取属性节点类型
	 * @return 属性节点类型的值
	 */
	@Transient
	public String getNodeType() {
		return nodeType;
	}

	/**
	 * 获取列表
	 * @return 属性列表
	 */
	@Transient
	public Collection<PrpLpersonTrace> getPersonTraceList() {
		return personTraceList;
	}

	/**
	 * 设置列表
	 * @param driverList 待设置的列表
	 */
	public void setPersonTraceList(Collection<PrpLpersonTrace> personTraceList) {
		this.personTraceList = personTraceList;
	}

	/**
	 * 获取属性险别代码
	 * @return 属性险别代码
	 */
	@Transient
	public String getPrpLpersonTraceReferKind() {
		return this.prpLpersonTraceReferKind;
	}

	/**
	 * 设置属性险别代码
	 * @param prpLpersonTraceReferKind 待设置的属性险别代码
	 */
	public void setPrpLpersonTraceReferKind(String prpLpersonTraceReferKind) {
		this.prpLpersonTraceReferKind = prpLpersonTraceReferKind;
	}

	@Transient
	public String getJobCode1() {
		return jobCode1;
	}

	public void setJobCode1(String jobCode1) {
		this.jobCode1 = jobCode1;
	}

	@Transient
	public String getJobName1() {
		return jobName1;
	}

	public void setJobName1(String jobName1) {
		this.jobName1 = jobName1;
	}

	@Transient
	public String getJobCode2() {
		return jobCode2;
	}

	public void setJobCode2(String jobCode2) {
		this.jobCode2 = jobCode2;
	}

	@Transient
	public String getJobName2() {
		return jobName2;
	}

	public void setJobName2(String jobName2) {
		this.jobName2 = jobName2;
	}

	/**
	 * 设置属性调度处理标志
	 * @param scheduleType 待设置的属性调度处理标志的值
	 */
	public void setScheduleType(String scheduleType) {
		this.scheduleType = StringUtils.rightTrim(scheduleType);
	}

	/**
	 * 获取属性调度处理标志
	 * @return 属性调度处理标志的值
	 */
	@Transient
	public String getScheduleType() {
		return scheduleType;
	}

	/**
	 * 设置属性是否选择发送
	 * @param selectSend 待设置的属性是否选择发送的值
	 */
	public void setSelectSend(String selectSend) {
		this.selectSend = StringUtils.rightTrim(selectSend);
	}

	/**
	 * 获取属性是否选择发送
	 * @return 属性是否选择发送的值
	 */
	@Transient
	public String getSelectSend() {
		return selectSend;
	}
	/**
	 * 就診醫師
	 * @return 就診醫師
	 */
	@Column(name="DOCTOR")
	public String getDoctor() {
		return doctor;
	}

	public void setDoctor(String doctor) {
		this.doctor = doctor;
	}
	/**
	 * 就诊医院代碼
	 * @return 就诊医院代碼
	 */
	@Column(name="HOSPITALCODE")
	public String getHospitalCode() {
		return hospitalCode;
	}

	public void setHospitalCode(String hospitalCode) {
		this.hospitalCode = hospitalCode;
	}

	//mantis：CLM0181，處理人員：DP0713，需求單編號：新核心-案件備案WS 3.10查詢及記錄留存作業 START
	@Column(name="IDNUMBER")
	public String getIdNumber() {
		return idNumber;
	}

	public void setIdNumber(String idNumber) {
		this.idNumber = idNumber;
	}

	@Column(name="RIDESITUATION")
	public String getRideSituation() {
		return rideSituation;
	}

	public void setRideSituation(String rideSituation) {
		this.rideSituation = rideSituation;
	}

	@Column(name="LICENSENO")
	public String getLicenseno() {
		return licenseno;
	}

	public void setLicenseno(String licenseno) {
		this.licenseno = licenseno;
	}

	public Date getBklineQueryDate() {
		return bklineQueryDate;
	}

	public void setBklineQueryDate(Date bklineQueryDate) {
		this.bklineQueryDate = bklineQueryDate;
	}

	public String getIdNumberType() {
		return idNumberType;
	}

	public void setIdNumberType(String idNumberType) {
		this.idNumberType = idNumberType;
	}

	public Date getApplicantBirthday() {
		return applicantBirthday;
	}

	public void setApplicantBirthday(Date applicantBirthday) {
		this.applicantBirthday = applicantBirthday;
	}
	
	//mantis：CLM0181，處理人員：DP0713，需求單編號：新核心-案件備案WS 3.10查詢及記錄留存作業 END
}
