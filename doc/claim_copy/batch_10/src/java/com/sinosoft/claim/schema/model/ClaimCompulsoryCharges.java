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
@Table(name="CLAIM_COMPULSORY_CHARGES"
)
public class ClaimCompulsoryCharges  implements java.io.Serializable {
    private static final long serialVersionUID = 1L;

     private ClaimCompulsoryChargesId id;
     
     private String mspId;
     private String insuranceCarNumber;
     private String insuranceNumber;
     private BigDecimal amount;
     private BigDecimal responsibilityRate;
     private String driverName;
     private String vehicleType;
     private String vehiclePayloadCapacity;
     private String vehiclePayloadCapacityUnit;
     
     private Date dcreate;
     private String icreate;
     private Date dupdate;
     private String iupdate;
     private BigDecimal jobOid;

     public ClaimCompulsoryCharges() {
     }
     @EmbeddedId
     @AttributeOverrides( {
         @AttributeOverride(name="oId", column=@Column(name="OID") )
         } )
     public ClaimCompulsoryChargesId getId() {
         return this.id;
     }
     /**
      * 属性id的setter方法
      */
     public void setId(ClaimCompulsoryChargesId id) {
         this.id = id;
     }

     /**
      * get攤出公司代號
      */
     @Column(name="MSP_ID")
	public String getMspId() {
		return mspId;
	}

     /**
      * set攤出公司代號
      */
	public void setMspId(String mspId) {
		this.mspId = mspId;
	}

    /**
     * get攤出保險車號
     */
    @Column(name="INSURANCE_CAR_NUMBER")
	public String getInsuranceCarNumber() {
		return insuranceCarNumber;
	}

    /**
     * set攤出保險車號
     */
	public void setInsuranceCarNumber(String insuranceCarNumber) {
		this.insuranceCarNumber = insuranceCarNumber;
	}

    /**
     * get攤出保險案號
     */
    @Column(name="INSURANCE_NUMBER")
	public String getInsuranceNumber() {
		return insuranceNumber;
	}

    /**
     * set攤出保險案號
     */
	public void setInsuranceNumber(String insuranceNumber) {
		this.insuranceNumber = insuranceNumber;
	}

    /**
     * get攤出金額
     */
    @Column(name="AMOUNT")
	public BigDecimal getAmount() {
		return amount;
	}

    /**
     * set攤出金額
     */
	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}

    /**
     * get攤出肇責比例
     */
    @Column(name="RESPONSIBILITY_RATE")
	public BigDecimal getResponsibilityRate() {
		return responsibilityRate;
	}

    /**
     * set攤出肇責比例
     */
	public void setResponsibilityRate(BigDecimal responsibilityRate) {
		this.responsibilityRate = responsibilityRate;
	}

    /**
     * get駕駛姓名
     */
    @Column(name="DRIVER_NAME")
	public String getDriverName() {
		return driverName;
	}

    /**
     * set駕駛姓名
     */
	public void setDriverName(String driverName) {
		this.driverName = driverName;
	}

    /**
     * get對車駕駛車輛類型
     */
    @Column(name="VEHICLE_TYPE")
	public String getVehicleType() {
		return vehicleType;
	}

    /**
     * set對車駕駛車輛類型
     */
	public void setVehicleType(String vehicleType) {
		this.vehicleType = vehicleType;
	}

    /**
     * get對車乘載限制
     */
    @Column(name="VEHICLE_PAYLOAD_CAPACITY")
	public String getVehiclePayloadCapacity() {
		return vehiclePayloadCapacity;
	}

    /**
     * set對車乘載限制
     */
	public void setVehiclePayloadCapacity(String vehiclePayloadCapacity) {
		this.vehiclePayloadCapacity = vehiclePayloadCapacity;
	}

    /**
     * get對車乘載限制單位
     */
    @Column(name="VEHICLE_PAYLOAD_CAPACITY_UNIT")
	public String getVehiclePayloadCapacityUnit() {
		return vehiclePayloadCapacityUnit;
	}

    /**
     * set對車乘載限制單位
     */
	public void setVehiclePayloadCapacityUnit(String vehiclePayloadCapacityUnit) {
		this.vehiclePayloadCapacityUnit = vehiclePayloadCapacityUnit;
	}

    /**
     * get建檔時間
     */
	@Column(name="DCREATE")
     public Date getDcreate() {
         return dcreate;
     }

    /**
     * set建檔時間
     */
     public void setDcreate(Date dcreate) {
         this.dcreate = dcreate;
     }

     /**
      * get建檔人員
      */
     @Column(name="ICREATE")
     public String getIcreate() {
         return icreate;
     }

     /**
      * set建檔人員
      */
     public void setIcreate(String icreate) {
         this.icreate = icreate;
     }

     /**
      * get修改時間
      */
     @Column(name="DUPDATE")
     public Date getDupdate() {
         return dupdate;
     }

     /**
      * set修改時間
      */
     public void setDupdate(Date dupdate) {
         this.dupdate = dupdate;
     }

     /**
      * get修改人員
      */
     @Column(name="IUPDATE")
     public String getIupdate() {
         return iupdate;
     }
     
     /**
      * set修改人員
      */
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


