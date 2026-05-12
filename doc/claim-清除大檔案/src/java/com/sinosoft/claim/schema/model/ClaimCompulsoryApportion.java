// default package
// 采用工具 Hibernate Tools 3.2.4.GA (sinosoft version) 生成，请勿手工修改。
package com.sinosoft.claim.schema.model;
import java.math.BigDecimal;
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

/**
 * mantis：CLM0277 ，處理人員： DP0713 ，需求單編號：理算任務串聯區塊鏈API3.10同業確認+API3.5建立理賠單
 * POJO类VehicleClaimApiLog
 */
@Entity
@Table(name="CLAIM_COMPULSORY_APPORTION"
)
public class ClaimCompulsoryApportion  implements java.io.Serializable {
    private static final long serialVersionUID = 1L;

     private ClaimCompulsoryApportionId id;

     private String insuredRole;
     private String healthInsuranceApportion;
     private String responsibilityType;
     private BigDecimal responsibilityRate;
     private String lossReason;
     private String lossCity;
     private BigDecimal amount;
     private String insuranceNumber;
     private String vehiclePayloadCapacityUnit;
     private BigDecimal vehiclePayloadCapacity;
     private String vehicleType;
     private String recoveryItem;
     private String driverName;
     private String healthInsurance;
     private BigDecimal otherResponsibilityRate;
     private BigDecimal applicantResponsibilityRate;
     private String insuranceCarNumber;
     private Date dcreate;
     private String icreate;
     private Date dupdate;
     private String iupdate;
     private BigDecimal jobOid;


     public ClaimCompulsoryApportion() {
     }
     @EmbeddedId
     @AttributeOverrides( {
         @AttributeOverride(name="oId", column=@Column(name="OID") )
         } )
     public ClaimCompulsoryApportionId getId() {
         return this.id;
     }
     /**
      * 属性id的setter方法
      */
     public void setId(ClaimCompulsoryApportionId id) {
         this.id = id;
     }

     /**
      * get 被保險人身份代號
      */
 	@Column(name="INSURED_ROLE")
     public String getInsuredRole() {
		return insuredRole;
	}
	
    /**
     * set 被保險人身份代號
     */
	public void setInsuredRole(String insuredRole) {
		this.insuredRole = insuredRole;
	}

    /**
     * get 健保攤賠註記
     */
	@Column(name="HEALTH_INSURANCE_APPORTION")
	public String getHealthInsuranceApportion() {
		return healthInsuranceApportion;
	}
	
    /**
     * set 健保攤賠註記
     */
	public void setHealthInsuranceApportion(String healthInsuranceApportion) {
		this.healthInsuranceApportion = healthInsuranceApportion;
	}

    /**
     * get 肇事責任類型
     */
	@Column(name="RESPONSIBILITY_TYPE")
	public String getResponsibilityType() {
		return responsibilityType;
	}
	
    /**
     * set 肇事責任類型
     */
	public void setResponsibilityType(String responsibilityType) {
		this.responsibilityType = responsibilityType;
	}

    /**
     * get 肇事責任百分比
     */
	@Column(name="RESPONSIBILITY_RATE")
	public BigDecimal getResponsibilityRate() {
		return responsibilityRate;
	}
	
    /**
     * set 肇事責任百分比
     */
	public void setResponsibilityRate(BigDecimal responsibilityRate) {
		this.responsibilityRate = responsibilityRate;
	}

    /**
     * get 出險原因
     */
	@Column(name="LOSS_REASON")
	public String getLossReason() {
		return lossReason;
	}
	
    /**
     * set 出險原因
     */
	public void setLossReason(String lossReason) {
		this.lossReason = lossReason;
	}

    /**
     * get 肇事縣市health_insurance
     */
	@Column(name="LOSS_CITY")
	public String getLossCity() {
		return lossCity;
	}
	
    /**
     * set 肇事縣市health_insurance
     */
	public void setLossCity(String lossCity) {
		this.lossCity = lossCity;
	}

    /**
     * get 賠付金額
     */
	@Column(name="AMOUNT")
	public BigDecimal getAmount() {
		return amount;
	}
	
    /**
     * set 賠付金額
     */
	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}

    /**
     * get 保險證號
     */
	@Column(name="INSURANCE_NUMBER")
	public String getInsuranceNumber() {
		return insuranceNumber;
	}
	
    /**
     * set 保險證號
     */
	public void setInsuranceNumber(String insuranceNumber) {
		this.insuranceNumber = insuranceNumber;
	}

    /**
     * get 車輛乘載限制(單位名稱)
     */
	@Column(name="VEHICLE_PAYLOAD_CAPACITY_UNIT")
	public String getVehiclePayloadCapacityUnit() {
		return vehiclePayloadCapacityUnit;
	}
	
    /**
     * set 車輛乘載限制(單位名稱)
     */
	public void setVehiclePayloadCapacityUnit(String vehiclePayloadCapacityUnit) {
		this.vehiclePayloadCapacityUnit = vehiclePayloadCapacityUnit;
	}

    /**
     * get 車輛乘載限制
     */
	@Column(name="VEHICLE_PAYLOAD_CAPACITY")
	public BigDecimal getVehiclePayloadCapacity() {
		return vehiclePayloadCapacity;
	}
	
    /**
     * set 車輛乘載限制
     */
	public void setVehiclePayloadCapacity(BigDecimal vehiclePayloadCapacity) {
		this.vehiclePayloadCapacity = vehiclePayloadCapacity;
	}

    /**
     * get 車子種類
     */
	@Column(name="VEHICLE_TYPE")
	public String getVehicleType() {
		return vehicleType;
	}
	
    /**
     * set 車子種類
     */
	public void setVehicleType(String vehicleType) {
		this.vehicleType = vehicleType;
	}

    /**
     * get 追償事項
     */
	@Column(name="RECOVERY_ITEM")
	public String getRecoveryItem() {
		return recoveryItem;
	}
	
    /**
     * set 追償事項
     */
	public void setRecoveryItem(String recoveryItem) {
		this.recoveryItem = recoveryItem;
	}

    /**
     * get 駕駛姓名
     */
	@Column(name="DRIVER_NAME")
	public String getDriverName() {
		return driverName;
	}
	
    /**
     * set 駕駛姓名
     */
	public void setDriverName(String driverName) {
		this.driverName = driverName;
	}

    /**
     * get 是否有健保
     */
	@Column(name="HEALTH_INSURANCE")
	public String getHealthInsurance() {
		return healthInsurance;
	}
	
    /**
     * set 是否有健保
     */
	public void setHealthInsurance(String healthInsurance) {
		this.healthInsurance = healthInsurance;
	}

    /**
     * get 其他肇事責任百分比(行人/腳踏車/乘客)
     */
	@Column(name="OTHER_RESPONSIBILITY_RATE")
	public BigDecimal getOtherResponsibilityRate() {
		return otherResponsibilityRate;
	}	
	
    /**
     * set 其他肇事責任百分比(行人/腳踏車/乘客)
     */
	public void setOtherResponsibilityRate(BigDecimal otherResponsibilityRate) {
		this.otherResponsibilityRate = otherResponsibilityRate;
	}

    /**
     * get 肇事責任百分比(受害人)
     */
	@Column(name="APPLICANT_RESPONSIBILITY_RATE")
	public BigDecimal getApplicantResponsibilityRate() {
		return applicantResponsibilityRate;
	}
	
    /**
     * set 肇事責任百分比(受害人)
     */
	public void setApplicantResponsibilityRate(
			BigDecimal applicantResponsibilityRate) {
		this.applicantResponsibilityRate = applicantResponsibilityRate;
	}

    /**
     * get 賠付車號
     */
	@Column(name="INSURANCE_CAR_NUMBER")
	public String getInsuranceCarNumber() {
		return insuranceCarNumber;
	}
	
    /**
     * set 賠付車號
     */
	public void setInsuranceCarNumber(String insuranceCarNumber) {
		this.insuranceCarNumber = insuranceCarNumber;
	}
	
    /**
     * get 建檔時間
     */
	@Column(name="DCREATE")
     public Date getDcreate() {
         return dcreate;
     }

    /**
     * set 建檔時間
     */
     public void setDcreate(Date dcreate) {
         this.dcreate = dcreate;
     }

     /**
      * get 建檔人員
      */
     @Column(name="ICREATE")
     public String getIcreate() {
         return icreate;
     }

     /**
      * set 建檔人員
      */
     public void setIcreate(String icreate) {
         this.icreate = icreate;
     }

     /**
      * get 修改時間
      */
     @Column(name="DUPDATE")
     public Date getDupdate() {
         return dupdate;
     }
     
     /**
      * set 修改時間
      */
     public void setDupdate(Date dupdate) {
         this.dupdate = dupdate;
     }

     /**
      * get 修改人員
      */
     @Column(name="IUPDATE")
     public String getIupdate() {
         return iupdate;
     }

     /**
      * set 修改人員
      **/
     public void setIupdate(String iupdate) {
         this.iupdate = iupdate;
     }

     @Column(name="JOB_OID")
     public BigDecimal getJobOid() {
         return jobOid;
     }

     public void setJobOid(BigDecimal jobOid) {
         this.jobOid = jobOid;
     }
     
}


