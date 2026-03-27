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
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * mantis：CLM0277 ，處理人員： DP0713 ，需求單編號：理算任務串聯區塊鏈API3.10同業確認+API3.5建立理賠單
 * POJO类VehicleClaimApiLog
 */
@Entity
@Table(name="CLAIM_COMPULSORY_APPLICANT"
)
public class ClaimCompulsoryApplicant  implements java.io.Serializable {
    private static final long serialVersionUID = 1L;

     private ClaimCompulsoryApplicantId id;

     private String applicantIdNumberType;//身分證號類別
     private String applicantIdNumber;
     private String applicantBirthday;
     private String applicantName;
     private Date dcreate;
     private String icreate;
     private Date dupdate;
     private String iupdate;
     private BigDecimal jobOid;


     public ClaimCompulsoryApplicant() {
     }
     @EmbeddedId
     @AttributeOverrides( {
         @AttributeOverride(name="oId", column=@Column(name="OID") )
         } )
     public ClaimCompulsoryApplicantId getId() {
         return this.id;
     }
     /**
      * 属性id的setter方法
      */
     public void setId(ClaimCompulsoryApplicantId id) {
         this.id = id;
     }
     
     /**
      * get 身分證號類別
      */
     @Column(name="APPLICANT_ID_NUMBER_TYPE")
     public String getApplicantIdNumberType() {
         return applicantIdNumberType;
     }

     /**
      * set 身分證號類別
      */
     public void setApplicantIdNumberType(String applicantIdNumberType) {
         this.applicantIdNumberType = applicantIdNumberType;
     }

     /**
      * get 身分證號
      */
     @Column(name="APPLICANT_ID_NUMBER")
     public String getApplicantIdNumber() {
         return applicantIdNumber;
     }

     /**
      * set 身分證號
      */
     public void setApplicantIdNumber(String applicantIdNumber) {
         this.applicantIdNumber = applicantIdNumber;
     }

     /**
      * get 受害者民國年生日
      */
     @Column(name="APPLICANT_BIRTHDAY")
     public String getApplicantBirthday() {
         return applicantBirthday;
     }

     /**
      * set 受害者民國年生日
      */
     public void setApplicantBirthday(String applicantBirthday) {
         this.applicantBirthday = applicantBirthday;
     }

     /**
      * get 姓名
      */
     @Column(name="APPLICANT_NAME")
     public String getApplicantName() {
         return applicantName;
     }

     /**
      * set 姓名
      */
     public void setApplicantName(String applicantName) {
         this.applicantName = applicantName;
     }

     /**
      * get 新增時間
      */
     @Column(name="DCREATE")
     public Date getDcreate() {
         return dcreate;
     }

     /**
      * set 新增時間
      */
 	 @Temporal(TemporalType.DATE)
     public void setDcreate(Date dcreate) {
         this.dcreate = dcreate;
     }

     /**
      * get 新增人員
      */
     @Column(name="ICREATE")
     public String getIcreate() {
         return icreate;
     }

     /**
      * set 新增人員
      */
     public void setIcreate(String icreate) {
         this.icreate = icreate;
     }

     /**
      * get 修改時間
      */
 	 @Temporal(TemporalType.DATE)
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


