package com.tlg.aps.vo.mob.fetPolicy.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.google.gson.annotations.SerializedName;

/** mantis：MOB0001，處理人員：CC009，需求單編號：MOB0001 遠傳手機保險投保&批改作業 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public class CustomerVo {
	
	@SerializedName("SEQ")
	private String seq;

	@SerializedName("RPT_DATE")
    private String rptDate;

	@SerializedName("CONTRACT_ID")
    private String contractId;

	@SerializedName("SUBSCRIBER_ID")
    private String subscriberId;
	
	@SerializedName("Application")
	private ApplicationVo application;

	@SerializedName("BILLING_CYCLE")
    private String billingCycle;
	
	@SerializedName("Product")
	private ProductVo product;

	@SerializedName("STORE_ID")
    private String storeId;

	@SerializedName("STORE_NAME")
    private String storeName;

	@SerializedName("EMPLOYEE_ID")
    private String employeeId;

	@SerializedName("FEATURE_NAME")
    private String featureName;

	@SerializedName("SERVICE_EFFECTIVE_DATE")
    private String serviceEffectiveDate;

	@SerializedName("SERVICE_TERMINATION_DATE")
    private String serviceTerminationDate;

	@SerializedName("SERVICE_TERMINATION_REASON")
    private String serviceTerminationReason;

	@SerializedName("TRANSACTION_TYPE")
    private String transactionType;

	@SerializedName("TRANSACTION_REASON")
    private String transactionReason;

	@SerializedName("CYCLE_CODE")
    private String cycleCode;

	@SerializedName("CHANNEL_TYPE")
    private String channelType;

	@SerializedName("OFFER_INSTANCE_ID")
    private String offerInstanceId;

	@SerializedName("MSISDN")
    private String msisdn;

	@SerializedName("RPT_BATCH_NO")
    private String rptBatchNo;

	@SerializedName("FORM_TYPE")
    private String formType;

	@SerializedName("IS_FET_SUPPLY")
    private String isFetSupply;

	@SerializedName("IN_PREPAID_PERIOD")
    private String inPrepaidPeriod;

	public String getSeq() {
		return seq;
	}

	public void setSeq(String seq) {
		this.seq = seq;
	}

	public String getRptDate() {
		return rptDate;
	}

	public void setRptDate(String rptDate) {
		this.rptDate = rptDate;
	}

	public String getContractId() {
		return contractId;
	}

	public void setContractId(String contractId) {
		this.contractId = contractId;
	}

	public String getSubscriberId() {
		return subscriberId;
	}

	public void setSubscriberId(String subscriberId) {
		this.subscriberId = subscriberId;
	}

	public ApplicationVo getApplication() {
		return application;
	}

	public void setApplication(ApplicationVo application) {
		this.application = application;
	}

	public String getBillingCycle() {
		return billingCycle;
	}

	public void setBillingCycle(String billingCycle) {
		this.billingCycle = billingCycle;
	}

	public ProductVo getProduct() {
		return product;
	}

	public void setProduct(ProductVo product) {
		this.product = product;
	}

	public String getStoreId() {
		return storeId;
	}

	public void setStoreId(String storeId) {
		this.storeId = storeId;
	}

	public String getStoreName() {
		return storeName;
	}

	public void setStoreName(String storeName) {
		this.storeName = storeName;
	}

	public String getEmployeeId() {
		return employeeId;
	}

	public void setEmployeeId(String employeeId) {
		this.employeeId = employeeId;
	}

	public String getFeatureName() {
		return featureName;
	}

	public void setFeatureName(String featureName) {
		this.featureName = featureName;
	}

	public String getServiceEffectiveDate() {
		return serviceEffectiveDate;
	}

	public void setServiceEffectiveDate(String serviceEffectiveDate) {
		this.serviceEffectiveDate = serviceEffectiveDate;
	}

	public String getServiceTerminationDate() {
		return serviceTerminationDate;
	}

	public void setServiceTerminationDate(String serviceTerminationDate) {
		this.serviceTerminationDate = serviceTerminationDate;
	}

	public String getServiceTerminationReason() {
		return serviceTerminationReason;
	}

	public void setServiceTerminationReason(String serviceTerminationReason) {
		this.serviceTerminationReason = serviceTerminationReason;
	}

	public String getTransactionType() {
		return transactionType;
	}

	public void setTransactionType(String transactionType) {
		this.transactionType = transactionType;
	}

	public String getTransactionReason() {
		return transactionReason;
	}

	public void setTransactionReason(String transactionReason) {
		this.transactionReason = transactionReason;
	}

	public String getCycleCode() {
		return cycleCode;
	}

	public void setCycleCode(String cycleCode) {
		this.cycleCode = cycleCode;
	}

	public String getChannelType() {
		return channelType;
	}

	public void setChannelType(String channelType) {
		this.channelType = channelType;
	}

	public String getOfferInstanceId() {
		return offerInstanceId;
	}

	public void setOfferInstanceId(String offerInstanceId) {
		this.offerInstanceId = offerInstanceId;
	}

	public String getMsisdn() {
		return msisdn;
	}

	public void setMsisdn(String msisdn) {
		this.msisdn = msisdn;
	}

	public String getRptBatchNo() {
		return rptBatchNo;
	}

	public void setRptBatchNo(String rptBatchNo) {
		this.rptBatchNo = rptBatchNo;
	}

	public String getFormType() {
		return formType;
	}

	public void setFormType(String formType) {
		this.formType = formType;
	}

	public String getIsFetSupply() {
		return isFetSupply;
	}

	public void setIsFetSupply(String isFetSupply) {
		this.isFetSupply = isFetSupply;
	}

	public String getInPrepaidPeriod() {
		return inPrepaidPeriod;
	}

	public void setInPrepaidPeriod(String inPrepaidPeriod) {
		this.inPrepaidPeriod = inPrepaidPeriod;
	}
}

