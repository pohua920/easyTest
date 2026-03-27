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
@Table(name="CLAIM_COMPULSORY_CASE"
)
public class ClaimCompulsoryCase  implements java.io.Serializable {
    private static final long serialVersionUID = 1L;

     private ClaimCompulsoryCaseId id;
     
     private String applicantRole;
     private String policeName;
     private String policeUnit;
     private String hitRoad;
     private String inCarNumber;
     private String hitTime;
     private String applicantType;
     private String caseNumber;
     
     private Date dcreate;
     private String icreate;
     private Date dupdate;
     private String iupdate;
     private BigDecimal jobOid;

     public ClaimCompulsoryCase() {
     }
     @EmbeddedId
     @AttributeOverrides( {
         @AttributeOverride(name="oId", column=@Column(name="OID") )
         } )
     public ClaimCompulsoryCaseId getId() {
         return this.id;
     }
     /**
      * 属性id的setter方法
      */
     public void setId(ClaimCompulsoryCaseId id) {
         this.id = id;
     }

     /**
      * get 受害者身份
      */
     @Column(name="APPLICANT_ROLE")
	public String getApplicantRole() {
		return applicantRole;
	}
 	
	 /**
	  * set 受害者身份
	  */
	public void setApplicantRole(String applicantRole) {
		this.applicantRole = applicantRole;
	}

    /**
     * get 憲警名稱
     */
    @Column(name="POLICE_NAME")
	public String getPoliceName() {
		return policeName;
	} 	
	 /**
	  * set 憲警名稱
	  */
	public void setPoliceName(String policeName) {
		this.policeName = policeName;
	}

    /**
     * get 憲警單位
     */
    @Column(name="POLICE_UNIT")
	public String getPoliceUnit() {
		return policeUnit;
	} 	
	 /**
	  * set 憲警單位
	  */
	public void setPoliceUnit(String policeUnit) {
		this.policeUnit = policeUnit;
	}

    /**
     * get 事故路段
     */
    @Column(name="HIT_ROAD")
	public String getHitRoad() {
		return hitRoad;
	} 	
	 /**
	  * set 事故路段
	  */
	public void setHitRoad(String hitRoad) {
		this.hitRoad = hitRoad;
	}

    /**
     * get 乘坐車號
     */
    @Column(name="IN_CAR_NUMBER")
	public String getInCarNumber() {
		return inCarNumber;
	} 	
	 /**
	  * set 乘坐車號
	  */
	public void setInCarNumber(String inCarNumber) {
		this.inCarNumber = inCarNumber;
	}

    /**
     * get 事故時間
     */
    @Column(name="HIT_TIME")
	public String getHitTime() {
		return hitTime;
	} 	
	 /**
	  * set 事故時間
	  */
	public void setHitTime(String hitTime) {
		this.hitTime = hitTime;
	}

    /**
     * get 乘坐身份
     */
    @Column(name="APPLICANT_TYPE")
	public String getApplicantType() {
		return applicantType;
	} 	
	 /**
	  * set 乘坐身份
	  */
	public void setApplicantType(String applicantType) {
		this.applicantType = applicantType;
	}

    /**
     * get 強制險案號
     */
    @Column(name="CASE_NUMBER")
	public String getCaseNumber() {
		return caseNumber;
	} 	
	 /**
	  * set 強制險案號
	  */
	public void setCaseNumber(String caseNumber) {
		this.caseNumber = caseNumber;
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


