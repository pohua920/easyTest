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
@Table(name="VEHICLE_CLAIM_API_LOG"
)
public class VehicleClaimApiLog  implements java.io.Serializable {
    private static final long serialVersionUID = 1L;


   
//     LOGID 記錄ID
 /** 属性id */
     private VehicleClaimApiLogId id;
   
//     CLAIMNO 立案號
     private String claimNo;
//     POLICYNO 保單號
     private String policyNo;
//     CARNO 車牌號碼
     private String carNo;
//     COMPENSATENO 賠款計算書號碼
     private String compensateNo;
//     UNDERWRITEFLAG 核賠標誌(0:初始值/1:通過/2:不通過/3:無需核賠/9:待核賠)
     private String underWriteFlag;
//     APIURL API網址
     private String apiUrl;
//     APICODE API代碼(如: API 3.5, API 3.7, API 3.14)
     private String apiCode;
//     HTTPMETHOD HTTP方法(GET/POST)
     private String httpMethod;
//     REQUESTJSON 請求JSON
     private String requestJson;
//     RESPONSEJSON 回應JSON
     private String responseJson;
//     STATUS 狀態(PENDING待執行/PROCESSING執行中/SUCCESS成功/FAILED失敗/CANCELLED已取消/TIMEOUT執行逾時)
     private String status;
//     STATUSCODE HTTP狀態碼
     private String statusCode;
//     RETRYCOUNT 重試次數
     private String retryCount;
//     CREATETIME 建立時間
     private Date createTime;
//     STARTTIME 開始時間
     private Date startTime;
//     UPDATETIME 更新時間
     private Date updateTime;
//     ENDTIME 結束時間
     private Date endTime;

//	IDENTIFYNUMBER
     private String identifyNumber;
//     PERSONNAME
     private String personName;

//   HITTIME 
     private String hitTime;
//   IDNUMBERTYPE 
     private String idNumberType;
//   CASEID 
     private String caseId;
     
     //USERCODE
     private String userCode;
     
     //APPORTIONID
     private String apportionId;
     
	/**
	 * 类PrpTfee的默认构造方法
	 */
    public VehicleClaimApiLog() {
    }

   
    /**
     * 属性id的getter方法
     */      @EmbeddedId

    
    @AttributeOverrides( {
        @AttributeOverride(name="logId", column=@Column(name="LOGID") )
        } )

    public VehicleClaimApiLogId getId() {
        return this.id;
    }
    /**
     * 属性id的setter方法
     */
    public void setId(VehicleClaimApiLogId id) {
        this.id = id;
    }
    /**
     * 
     */
    @Column(name="CLAIMNO")
	public String getClaimNo() {
		return claimNo;
	}

    /**
     * 
     */
	public void setClaimNo(String claimNo) {
		this.claimNo = claimNo;
	}

	/**
     * 
     */
    @Column(name="POLICYNO")
	public String getPolicyNo() {
		return policyNo;
	}

    /**
     * 
     */
	public void setPolicyNo(String policyNo) {
		this.policyNo = policyNo;
	}

	/**
     * 
     */
    @Column(name="CARNO")
	public String getCarNo() {
		return carNo;
	}

    /**
     * 
     */
	public void setCarNo(String carNo) {
		this.carNo = carNo;
	}

	/**
     * 
     */
    @Column(name="COMPENSATENO")
	public String getCompensateNo() {
		return compensateNo;
	}

    /**
     * 
     */
	public void setCompensateNo(String compensateNo) {
		this.compensateNo = compensateNo;
	}

	/**
     * 
     */
    @Column(name="UNDERWRITEFLAG")
	public String getUnderWriteFlag() {
		return underWriteFlag;
	}

    /**
     * 
     */
	public void setUnderWriteFlag(String underWriteFlag) {
		this.underWriteFlag = underWriteFlag;
	}

	/**
     * 
     */
    @Column(name="APIURL")
	public String getApiUrl() {
		return apiUrl;
	}

    /**
     * 
     */
	public void setApiUrl(String apiUrl) {
		this.apiUrl = apiUrl;
	}

	/**
     * 
     */
    @Column(name="APICODE")
	public String getApiCode() {
		return apiCode;
	}

    /**
     * 
     */
	public void setApiCode(String apiCode) {
		this.apiCode = apiCode;
	}

	/**
     * 
     */
    @Column(name="HTTPMETHOD")
	public String getHttpMethod() {
		return httpMethod;
	}

    /**
     * 
     */
	public void setHttpMethod(String httpMethod) {
		this.httpMethod = httpMethod;
	}

	/**
     * 
     */
    @Column(name="REQUESTJSON")
	public String getRequestJson() {
		return requestJson;
	}

    /**
     * 
     */
	public void setRequestJson(String requestJson) {
		this.requestJson = requestJson;
	}

	/**
     * 
     */
    @Column(name="RESPONSEJSON")
	public String getResponseJson() {
		return responseJson;
	}

    /**
     * 
     */
	public void setResponseJson(String responseJson) {
		this.responseJson = responseJson;
	}

	/**
     * 
     */
    @Column(name="STATUS")
	public String getStatus() {
		return status;
	}

    /**
     * 
     */
	public void setStatus(String status) {
		this.status = status;
	}

	/**
     * 
     */
    @Column(name="STATUSCODE")
	public String getStatusCode() {
		return statusCode;
	}

    /**
     * 
     */
	public void setStatusCode(String statusCode) {
		this.statusCode = statusCode;
	}

	/**
     * 
     */
    @Column(name="RETRYCOUNT")
	public String getRetryCount() {
		return retryCount;
	}

    /**
     * 
     */
	public void setRetryCount(String retryCount) {
		this.retryCount = retryCount;
	}

	/**
     * 
     */
    @Column(name="CREATETIME")
	public Date getCreateTime() {
		return createTime;
	}

    /**
     * 
     */
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	/**
     * 
     */
    @Column(name="STARTTIME")
	public Date getStartTime() {
		return startTime;
	}

    /**
     * 
     */
	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}

	/**
     * 
     */
    @Column(name="UPDATETIME")
	public Date getUpdateTime() {
		return updateTime;
	}

    /**
     * 
     */
	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}

	/**
     * 
     */
    @Column(name="ENDTIME")
	public Date getEndTime() {
		return endTime;
	}

    /**
     * 
     */
	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}

	/**
     * 
     */
    @Column(name="IDENTIFYNUMBER")
	public String getIdentifyNumber() {
		return identifyNumber;
	}

    /**
     * 
     */
	public void setIdentifyNumber(String identifyNumber) {
		this.identifyNumber = identifyNumber;
	}

	/**
     * 
     */
    @Column(name="PERSONNAME")
	public String getPersonName() {
		return personName;
	}

    /**
     * 
     */
	public void setPersonName(String personName) {
		this.personName = personName;
	}

	@Column(name="HITTIME")
	public String getHitTime() {
		return hitTime;
	}


	public void setHitTime(String hitTime) {
		this.hitTime = hitTime;
	}

	@Column(name="IDNUMBERTYPE")
	public String getIdNumberType() {
		return idNumberType;
	}


	public void setIdNumberType(String idNumberType) {
		this.idNumberType = idNumberType;
	}

	@Column(name="CASEID")
	public String getCaseId() {
		return caseId;
	}


	public void setCaseId(String caseId) {
		this.caseId = caseId;
	}


	@Column(name="USERCODE")
	public String getUserCode() {
		return userCode;
	}


	public void setUserCode(String userCode) {
		this.userCode = userCode;
	}


	@Column(name="APPORTIONID")
	public String getApportionId() {
		return apportionId;
	}


	public void setApportionId(String apportionId) {
		this.apportionId = apportionId;
	}

	

}


