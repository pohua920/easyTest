package com.tlg.aps.vo;

import java.util.Date;/** mantis：MOB0025，處理人員：CE035，需求單編號：MOB0025 新增要保書紙本進件流程，修正APS查詢畫面要保人ID抓取資料欄位值 */

/** mantis：，處理人員：BJ016，需求單編號：險部客服行動裝置險保單查詢作業 */
public class Aps050ResultVo {
	private String transactionId;
	private String batchDate;
	private String contractId;
	private String name;
	private String customerId;
	private String msisdn;
	private String featureName;
	private String startDate;
	private String endDate;
	private String chubbReturnStatus;
	private String chubbReturnMsg;
	private String dataStatus;
	private String policyNo;
	private String endorseNo;
	private String proposalFileCheck;
	private String storeId;
	private String transactionType;
	private String formType;
	private Date createdTime;/** mantis：MOB0025，處理人員：CE035，需求單編號：MOB0025 新增要保書紙本進件流程，修正APS查詢畫面要保人ID抓取資料欄位值 */
	/** mantis：MOB0031，處理人員：CE035，需求單編號：MOB0031 行動裝置險estore網投案開發 出單流程 START*/
    private String dataSrc;
    private String txId;
    private String ctbcRcvNo;
    private String projectCode;
    
    public String getDataSrc() {
		return dataSrc;
	}
	public void setDataSrc(String dataSrc) {
		this.dataSrc = dataSrc;
	}
	public String getTxId() {
		return txId;
	}
	public void setTxId(String txId) {
		this.txId = txId;
	}
	public String getCtbcRcvNo() {
		return ctbcRcvNo;
	}
	public void setCtbcRcvNo(String ctbcRcvNo) {
		this.ctbcRcvNo = ctbcRcvNo;
	}
	public String getProjectCode() {
		return projectCode;
	}
	public void setProjectCode(String projectCode) {
		this.projectCode = projectCode;
	}
	/** mantis：MOB0031，處理人員：CE035，需求單編號：MOB0031 行動裝置險estore網投案開發 出單流程 END*/
	public String getBatchDate() {
		return batchDate;
	}
	public void setBatchDate(String batchDate) {
		this.batchDate = batchDate;
	}
	public String getContractId() {
		return contractId;
	}
	public void setContractId(String contractId) {
		this.contractId = contractId;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getMsisdn() {
		return msisdn;
	}
	public void setMsisdn(String msisdn) {
		this.msisdn = msisdn;
	}
	public String getFeatureName() {
		return featureName;
	}
	public void setFeatureName(String featureName) {
		this.featureName = featureName;
	}
	public String getStartDate() {
		return startDate;
	}
	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}
	public String getEndDate() {
		return endDate;
	}
	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}
	public String getDataStatus() {
		return dataStatus;
	}
	public void setDataStatus(String dataStatus) {
		this.dataStatus = dataStatus;
	}
	public String getPolicyNo() {
		return policyNo;
	}
	public void setPolicyNo(String policyNo) {
		this.policyNo = policyNo;
	}
	public String getEndorseNo() {
		return endorseNo;
	}
	public void setEndorseNo(String endorseNo) {
		this.endorseNo = endorseNo;
	}
	public String getTransactionId() {
		return transactionId;
	}
	public void setTransactionId(String transactionId) {
		this.transactionId = transactionId;
	}
	public String getChubbReturnStatus() {
		return chubbReturnStatus;
	}
	public void setChubbReturnStatus(String chubbReturnStatus) {
		this.chubbReturnStatus = chubbReturnStatus;
	}
	public String getChubbReturnMsg() {
		return chubbReturnMsg;
	}
	public void setChubbReturnMsg(String chubbReturnMsg) {
		this.chubbReturnMsg = chubbReturnMsg;
	}
	public String getProposalFileCheck() {
		return proposalFileCheck;
	}
	public void setProposalFileCheck(String proposalFileCheck) {
		this.proposalFileCheck = proposalFileCheck;
	}
	public String getCustomerId() {
		return customerId;
	}
	public void setCustomerId(String customerId) {
		this.customerId = customerId;
	}
	public String getStoreId() {
		return storeId;
	}
	public void setStoreId(String storeId) {
		this.storeId = storeId;
	}
	public String getTransactionType() {
		return transactionType;
	}
	public void setTransactionType(String transactionType) {
		this.transactionType = transactionType;
	}
	public String getFormType() {
		return formType;
	}
	public void setFormType(String formType) {
		this.formType = formType;
	}
	/** mantis：MOB0025，處理人員：CE035，需求單編號：MOB0025 新增要保書紙本進件流程，修正APS查詢畫面要保人ID抓取資料欄位值 START */
	public Date getCreatedTime() {
		return createdTime;
	}
	public void setCreatedTime(Date createdTime) {
		this.createdTime = createdTime;
	}
	/** mantis：MOB0025，處理人員：CE035，需求單編號：MOB0025 新增要保書紙本進件流程，修正APS查詢畫面要保人ID抓取資料欄位值 END */
}
