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
@Table(name="CLAIM_COMPULSORY_STATE_PRICES"
)
public class ClaimCompulsoryStatePrices  implements java.io.Serializable {
    private static final long serialVersionUID = 1L;

     private ClaimCompulsoryStatePricesId id;

     private BigDecimal oidClaimCompulsoryApportion;//CLAIM_COMPULSORY_APPORTION的OID
     private String type;
     private String amount;
     private String code;
     
     private Date dcreate;
     private String icreate;
     private Date dupdate;
     private String iupdate;
     private BigDecimal jobOid;


     public ClaimCompulsoryStatePrices() {
     }
     @EmbeddedId
     @AttributeOverrides( {
         @AttributeOverride(name="oId", column=@Column(name="OID") )
         } )
     public ClaimCompulsoryStatePricesId getId() {
         return this.id;
     }
     /**
      * 属性id的setter方法
      */
     public void setId(ClaimCompulsoryStatePricesId id) {
         this.id = id;
     }

 	
 	/**
      * get CLAIM_COMPULSORY_APPORTION的OID
      */
 	@Column(name="OID_CLAIM_COMPULSORY_APPORTION")
    public BigDecimal getOidClaimCompulsoryApportion() {
		return oidClaimCompulsoryApportion;
	}
 	
	/**
     * set CLAIM_COMPULSORY_APPORTION的OID
     */
	public void setOidClaimCompulsoryApportion(
			BigDecimal oidClaimCompulsoryApportion) {
		this.oidClaimCompulsoryApportion = oidClaimCompulsoryApportion;
	}
	
	/**
     * get 給附代號
     */
	@Column(name="TYPE")
	public String getType() {
		return type;
	}
	/**
	 * set 給附代號
	 */
	public void setType(String type) {
		this.type = type;
	}
	
	/**
     * get 金額
     */
	@Column(name="AMOUNT")
	public String getAmount() {
		return amount;
	}
	/**
	 * set 金額
	 */
	public void setAmount(String amount) {
		this.amount = amount;
	}
	
	/**
     * get 給附代號
     */
	@Column(name="CODE")
	public String getCode() {
		return code;
	}
	/**
	 * set 給附代號
	 */
	public void setCode(String code) {
		this.code = code;
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


