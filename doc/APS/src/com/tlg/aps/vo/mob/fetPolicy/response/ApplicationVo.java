package com.tlg.aps.vo.mob.fetPolicy.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.google.gson.annotations.SerializedName;

/** mantis：MOB0001，處理人員：CC009，需求單編號：MOB0001 遠傳手機保險投保&批改作業 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ApplicationVo {
	
	@SerializedName("APPLICATION_ID")
    private String applicationId;

	@SerializedName("APPLICATION_STATUS")
    private String applicationStatus;

	@SerializedName("CREATE_DTM")
    private String createDtm;

	@SerializedName("MODI_DTM")
    private String modiDtm;
	
	@SerializedName("Applicant")
	private ApplicantVo applicant;
	
	@SerializedName("Insured")
	private InsuredVo insured;
	
	@SerializedName("SALES_ID")
    private String salesId;

	@SerializedName("Pay_Method")
    private String payMethod;

	@SerializedName("RC")
    private String rc;
	
	@SerializedName("Ext_Cols")
	private ExtColsVo extCols;
	
	//20240515：遠傳新增BILLING_FREQUENCY欄位
	@SerializedName("BILLING_FREQUENCY")
    private String billingFrequency;

	public String getApplicationId() {
		return applicationId;
	}

	public void setApplicationId(String applicationId) {
		this.applicationId = applicationId;
	}

	public String getApplicationStatus() {
		return applicationStatus;
	}

	public void setApplicationStatus(String applicationStatus) {
		this.applicationStatus = applicationStatus;
	}

	public String getCreateDtm() {
		return createDtm;
	}

	public void setCreateDtm(String createDtm) {
		this.createDtm = createDtm;
	}

	public String getModiDtm() {
		return modiDtm;
	}

	public void setModiDtm(String modiDtm) {
		this.modiDtm = modiDtm;
	}

	public ApplicantVo getApplicant() {
		return applicant;
	}

	public void setApplicant(ApplicantVo applicant) {
		this.applicant = applicant;
	}

	public InsuredVo getInsured() {
		return insured;
	}

	public void setInsured(InsuredVo insured) {
		this.insured = insured;
	}

	public String getSalesId() {
		return salesId;
	}

	public void setSalesId(String salesId) {
		this.salesId = salesId;
	}

	public String getPayMethod() {
		return payMethod;
	}

	public void setPayMethod(String payMethod) {
		this.payMethod = payMethod;
	}

	public String getRc() {
		return rc;
	}

	public void setRc(String rc) {
		this.rc = rc;
	}

	public ExtColsVo getExtCols() {
		return extCols;
	}

	public void setExtCols(ExtColsVo extCols) {
		this.extCols = extCols;
	}

	public String getBillingFrequency() {
		return billingFrequency;
	}

	public void setBillingFrequency(String billingFrequency) {
		this.billingFrequency = billingFrequency;
	}

}
