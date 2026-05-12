package com.sinosoft.claim.schema.model;

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
 * 受害人就诊医院
 * @author 中科软
 *
 */
@Entity
@Table(name = "PrpLpersonHospital")
public class PrpLpersonHospital implements java.io.Serializable{
	
	/** 序号*/
	private static final long serialVersionUID = 1L;
	/** 属性id */
	private PrpLpersonHospitalId id;
	/** 属性人员序号 */
	private int personNo;
	/** 醫療院所代號 */
	private String hospitalCode;
	/**  醫療院所名稱  */
	private String hospitalName;
	/** 属性入院日期 */
	private Date inHospDate;
	/** 属性出院日期 */
	private Date outHospDate;
	/** 醫師姓名 */
	private String doctor;
	/**診斷科別*/
	private String diagnosisDivision;
	/**診斷名稱*/
	private String diagnosisName;
	/** 属性标志 */
	private String flag;
	/** 属性标志 */
	private String remark;
	
	private PrpLpersonLoss prpLpersonLoss;
	
	public PrpLpersonHospital(){
		id = new PrpLpersonHospitalId();
	}
	
	public PrpLpersonHospital(PrpLpersonHospitalId id, int personNo, String hospitalCode, String hospitalName, Date inHospDate, Date outHospDate) {
		super();
		this.id = id;
		this.personNo = personNo;
		this.hospitalCode = hospitalCode;
		this.hospitalName = hospitalName;
		this.inHospDate = inHospDate;
		this.outHospDate = outHospDate;
	}


	/**
	 * 属性id的getter方法
	 */
	@EmbeddedId
	@AttributeOverrides({ @AttributeOverride(name = "compensateNo", column = @Column(name = "COMPENSATENO")), @AttributeOverride(name = "serialNo", column = @Column(name = "SERIALNO")) })
	public PrpLpersonHospitalId getId() {
		return this.id;
	}

	public void setId(PrpLpersonHospitalId id) {
		this.id = id;
	}
	
	@Column(name = "personNo")
	public int getPersonNo() {
		return personNo;
	}

	public void setPersonNo(int personNo) {
		this.personNo = personNo;
	}

	@Column(name = "hospitalCode")
	public String getHospitalCode() {
		return hospitalCode;
	}

	public void setHospitalCode(String hospitalCode) {
		this.hospitalCode = hospitalCode;
	}

	@Column(name = "hospitalName")
	public String getHospitalName() {
		return hospitalName;
	}

	public void setHospitalName(String hospitalName) {
		this.hospitalName = hospitalName;
	}

	@Temporal(TemporalType.DATE)
	@Column(name = "INHOSPDATE")
	public Date getInHospDate() {
		return inHospDate;
	}

	public void setInHospDate(Date inHospDate) {
		this.inHospDate = inHospDate;
	}

	@Temporal(TemporalType.DATE)
	@Column(name = "outHospDate")
	public Date getOutHospDate() {
		return outHospDate;
	}

	public void setOutHospDate(Date outHospDate) {
		this.outHospDate = outHospDate;
	}
	@Column(name = "flag")
	public String getFlag() {
		return flag;
	}

	public void setFlag(String flag) {
		this.flag = flag;
	}
	@Column(name = "remark")
	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	@Transient
	public PrpLpersonLoss getPrpLpersonLoss() {
		return prpLpersonLoss;
	}

	public void setPrpLpersonLoss(PrpLpersonLoss prpLpersonLoss) {
		this.prpLpersonLoss = prpLpersonLoss;
	}
	@Column(name = "diagnosisDivision")
	public String getDiagnosisDivision() {
		return diagnosisDivision;
	}

	public void setDiagnosisDivision(String diagnosisDivision) {
		this.diagnosisDivision = diagnosisDivision;
	}
	@Column(name = "diagnosisName")
	public String getDiagnosisName() {
		return diagnosisName;
	}

	public void setDiagnosisName(String diagnosisName) {
		this.diagnosisName = diagnosisName;
	}
	@Column(name = "DOCTOR")
	public String getDoctor() {
		return doctor;
	}

	public void setDoctor(String doctor) {
		this.doctor = doctor;
	}
}
