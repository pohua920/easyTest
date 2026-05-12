package com.sinosoft.claim.schema.model;

// 采用工具 Hibernate Tools 3.2.4.GA (sinosoft version) 生成，请勿手工修改。

import java.math.BigDecimal;
import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * POJO类PrpLpersonTraceLog人伤跟踪修改轨迹表
 */
@Entity
@Table(name = "PRPLPERSONTRACELOG")
public class PrpLpersonTraceLog implements java.io.Serializable {
	private static final long serialVersionUID = 1L;

	/** 属性id */
	private PrpLpersonTraceLogId id;

	/** 属性赔案号 */
	private String claimNo;

	/** 属性保单号码 */
	private String policyNo;

	/** 属性人员姓名 */
	private String personName;

	/** 属性性别 */
	private String personSex;

	/** 属性年龄 */
	private BigDecimal personAge;

	/** 属性收款人身份证号 */
	private String identifyNumber;

	/** 属性关联人员序号 */
	private BigDecimal relatePersonNo;

	/** 属性雇员工种代码 */
	private String jobCode;

	/** 属性雇员工种名称 */
	private String jobName;

	/** 属性事故所涉及险种 */
	private String referKind;

	/** 属性受伤部位描述 */
	private String partDesc;

	/** 属性就诊医院 */
	private String hospital;

	/** 属性是否自行就医 */
	private String motionFlag;

	/** 属性伤情描述 */
	private String woundRemark;

	/** 属性说明 */
	private String remark;

	/** 属性标志字段 */
	private String flag;

	/**
	 * 类PrpLpersonTraceLog的默认构造方法
	 */
	public PrpLpersonTraceLog() {
	}

	/**
	 * 属性id的getter方法
	 */
	@EmbeddedId
	@AttributeOverrides({ @AttributeOverride(name = "logID", column = @Column(name = "LOGID")), @AttributeOverride(name = "registNo", column = @Column(name = "REGISTNO")), @AttributeOverride(name = "personNo", column = @Column(name = "PERSONNO")) })
	public PrpLpersonTraceLogId getId() {
		return this.id;
	}

	/**
	 * 属性id的setter方法
	 */
	public void setId(PrpLpersonTraceLogId id) {
		this.id = id;
	}

	/**
	 * 属性赔案号的getter方法
	 */

	@Column(name = "CLAIMNO")
	public String getClaimNo() {
		return this.claimNo;
	}

	/**
	 * 属性赔案号的setter方法
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
	public BigDecimal getPersonAge() {
		return this.personAge;
	}

	/**
	 * 属性年龄的setter方法
	 */
	public void setPersonAge(BigDecimal personAge) {
		this.personAge = personAge;
	}

	/**
	 * 属性收款人身份证号的getter方法
	 */

	@Column(name = "IDENTIFYNUMBER")
	public String getIdentifyNumber() {
		return this.identifyNumber;
	}

	/**
	 * 属性收款人身份证号的setter方法
	 */
	public void setIdentifyNumber(String identifyNumber) {
		this.identifyNumber = identifyNumber;
	}

	/**
	 * 属性关联人员序号的getter方法
	 */

	@Column(name = "RELATEPERSONNO")
	public BigDecimal getRelatePersonNo() {
		return this.relatePersonNo;
	}

	/**
	 * 属性关联人员序号的setter方法
	 */
	public void setRelatePersonNo(BigDecimal relatePersonNo) {
		this.relatePersonNo = relatePersonNo;
	}

	/**
	 * 属性雇员工种代码的getter方法
	 */

	@Column(name = "JOBCODE")
	public String getJobCode() {
		return this.jobCode;
	}

	/**
	 * 属性雇员工种代码的setter方法
	 */
	public void setJobCode(String jobCode) {
		this.jobCode = jobCode;
	}

	/**
	 * 属性雇员工种名称的getter方法
	 */

	@Column(name = "JOBNAME")
	public String getJobName() {
		return this.jobName;
	}

	/**
	 * 属性雇员工种名称的setter方法
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
	 * 属性说明的getter方法
	 */

	@Column(name = "REMARK")
	public String getRemark() {
		return this.remark;
	}

	/**
	 * 属性说明的setter方法
	 */
	public void setRemark(String remark) {
		this.remark = remark;
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
