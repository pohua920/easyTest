package com.sinosoft.common.schema.model;

// 采用工具 Hibernate Tools 3.2.4.GA (sinosoft version) 生成，请勿手工修改。

import java.util.Date;

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * mantis： CAR0369，處理人員：BI086，需求單編號：CAR0369:核心車險地址正規化作業
 */
@Entity
@Table(name = "PRPTPLANEDRIVER")
public class PrpTplaneDriver implements java.io.Serializable {
	private static final long serialVersionUID = 1L;

	/** 属性id */
	private PrpTplaneDriverId id;
	
	 /** 属性PRPTMAIN */
    private PrpTmain prpTmain;

	/** 属性險種代碼 */
	private String riskCode;

	/** 属性標的序號 */
	private Long itemNo;

	/** 属性驾驶证号码 */
	private String drivingLicenseNo;

	/** 属性飞行机型 */
	private String drivingPlaneType;

	/** 属性驾驶员姓名 */
	private String driverName;

	/** 属性個人身份證號碼 法人組織機構號碼 */
	private String identifynumber;

	/** 属性性別 */
	private String sex;

	/** 属性年齡 */
	private Long age;

	/** 属性飞行机型时数 */
	private Long drivingHours;

	/** 属性飞行总时数 */
	private Long drivingSumHours;

	/** 属性训练飞行机型 */
	private String trainDrivingType;

	/** 属性训练飞行时数 */
	private Long trainDrivingHours;

	/** 属性飞安记录日期 */
	private Date drivingRecordDate;

	/** 属性飞安记录原因 */
	private String drivingRecordReason;

	/** 属性標誌 */
	private String flag;

	/**
	 * 类prpTplaneDriver的默认构造方法
	 */
	public PrpTplaneDriver() {
	}

	/**
	 * 属性id的getter方法
	 */
	@EmbeddedId
	@AttributeOverrides({
			@AttributeOverride(name = "proposalNo", column = @Column(name = "PROPOSALNO")),
			@AttributeOverride(name = "serialNo", column = @Column(name = "SERIALNO")) })
	public PrpTplaneDriverId getId() {
		return this.id;
	}

	/**
	 * 属性id的setter方法
	 */
	public void setId(PrpTplaneDriverId id) {
		this.id = id;
	}

    /**
     * 属性PRPTMAIN的getter方法
     */ 
    @ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="PROPOSALNO", nullable=false, insertable=false, updatable=false)

    public PrpTmain getPrpTmain() {
        return this.prpTmain;
    }
    /**
     * 属性PRPTMAIN的setter方法
     */
    public void setPrpTmain(PrpTmain prpTmain) {
        this.prpTmain = prpTmain;
    }
	
    /**
	 * 属性險種代碼的getter方法
	 */

	@Column(name = "RISKCODE")
	public String getRiskCode() {
		return this.riskCode;
	}

	/**
	 * 属性險種代碼的setter方法
	 */
	public void setRiskCode(String riskCode) {
		this.riskCode = riskCode;
	}

	/**
	 * 属性標的序號的getter方法
	 */

	@Column(name = "ITEMNO")
	public Long getItemNo() {
		return this.itemNo;
	}

	/**
	 * 属性標的序號的setter方法
	 */
	public void setItemNo(Long itemNo) {
		this.itemNo = itemNo;
	}

	/**
	 * 属性驾驶证号码的getter方法
	 */

	@Column(name = "DRIVINGLICENSENO")
	public String getDrivingLicenseNo() {
		return this.drivingLicenseNo;
	}

	/**
	 * 属性驾驶证号码的setter方法
	 */
	public void setDrivingLicenseNo(String drivingLicenseNo) {
		this.drivingLicenseNo = drivingLicenseNo;
	}

	/**
	 * 属性飞行机型的getter方法
	 */

	@Column(name = "DRIVINGPLANETYPE")
	public String getDrivingPlaneType() {
		return this.drivingPlaneType;
	}

	/**
	 * 属性飞行机型的setter方法
	 */
	public void setDrivingPlaneType(String drivingPlaneType) {
		this.drivingPlaneType = drivingPlaneType;
	}

	/**
	 * 属性驾驶员姓名的getter方法
	 */

	@Column(name = "DRIVERNAME")
	public String getDriverName() {
		return this.driverName;
	}

	/**
	 * 属性驾驶员姓名的setter方法
	 */
	public void setDriverName(String driverName) {
		this.driverName = driverName;
	}

	/**
	 * 属性個人身份證號碼 法人組織機構號碼的getter方法
	 */

	@Column(name = "IDENTIFYNUMBER")
	public String getIdentifynumber() {
		return this.identifynumber;
	}

	/**
	 * 属性個人身份證號碼 法人組織機構號碼的setter方法
	 */
	public void setIdentifynumber(String identifynumber) {
		this.identifynumber = identifynumber;
	}

	/**
	 * 属性性別的getter方法
	 */

	@Column(name = "SEX")
	public String getSex() {
		return this.sex;
	}

	/**
	 * 属性性別的setter方法
	 */
	public void setSex(String sex) {
		this.sex = sex;
	}

	/**
	 * 属性年齡的getter方法
	 */

	@Column(name = "AGE")
	public Long getAge() {
		return this.age;
	}

	/**
	 * 属性年齡的setter方法
	 */
	public void setAge(Long age) {
		this.age = age;
	}

	/**
	 * 属性飞行机型时数的getter方法
	 */

	@Column(name = "DRIVINGHOURS")
	public Long getDrivingHours() {
		return this.drivingHours;
	}

	/**
	 * 属性飞行机型时数的setter方法
	 */
	public void setDrivingHours(Long drivingHours) {
		this.drivingHours = drivingHours;
	}

	/**
	 * 属性飞行总时数的getter方法
	 */

	@Column(name = "DRIVINGSUMHOURS")
	public Long getDrivingSumHours() {
		return this.drivingSumHours;
	}

	/**
	 * 属性飞行总时数的setter方法
	 */
	public void setDrivingSumHours(Long drivingSumHours) {
		this.drivingSumHours = drivingSumHours;
	}

	/**
	 * 属性训练飞行机型的getter方法
	 */

	@Column(name = "TRAINDRIVINGTYPE")
	public String getTrainDrivingType() {
		return this.trainDrivingType;
	}

	/**
	 * 属性训练飞行机型的setter方法
	 */
	public void setTrainDrivingType(String trainDrivingType) {
		this.trainDrivingType = trainDrivingType;
	}

	/**
	 * 属性训练飞行时数的getter方法
	 */

	@Column(name = "TRAINDRIVINGHOURS")
	public Long getTrainDrivingHours() {
		return this.trainDrivingHours;
	}

	/**
	 * 属性训练飞行时数的setter方法
	 */
	public void setTrainDrivingHours(Long trainDrivingHours) {
		this.trainDrivingHours = trainDrivingHours;
	}

	/**
	 * 属性飞安记录日期的getter方法
	 */
	@Temporal(TemporalType.DATE)
	@Column(name = "DRIVINGRECORDDATE")
	public Date getDrivingRecordDate() {
		return this.drivingRecordDate;
	}

	/**
	 * 属性飞安记录日期的setter方法
	 */
	public void setDrivingRecordDate(Date drivingRecordDate) {
		this.drivingRecordDate = drivingRecordDate;
	}

	/**
	 * 属性飞安记录原因的getter方法
	 */

	@Column(name = "DRIVINGRECORDREASON")
	public String getDrivingRecordReason() {
		return this.drivingRecordReason;
	}

	/**
	 * 属性飞安记录原因的setter方法
	 */
	public void setDrivingRecordReason(String drivingRecordReason) {
		this.drivingRecordReason = drivingRecordReason;
	}

	/**
	 * 属性標誌的getter方法
	 */

	@Column(name = "FLAG")
	public String getFlag() {
		return this.flag;
	}

	/**
	 * 属性標誌的setter方法
	 */
	public void setFlag(String flag) {
		this.flag = flag;
	}

}
