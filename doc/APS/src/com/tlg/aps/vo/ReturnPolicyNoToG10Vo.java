package com.tlg.aps.vo;

/** mantis：MOB0031，處理人員：CE035，需求單編號：MOB0031 行動裝置險estore網投案開發 出單流程*/
public class ReturnPolicyNoToG10Vo {
	
	private String transactionId;
	/** 遠傳交易編號 */
	private String txId;
	/** 中信產險保險受理編號 */
	private String ctbcRcvNo;
	/** 專案代號 */
	private String projectCode;
	/** 資料來源 */
    private String dataSrc;
    /** 保單號碼 */
	private String policyNo;
	/** 批單號碼 */
	private String endorseNo;
	/** 批改類型 */
	private String endorseType;
	/** 批文 */
	private String endorseContent;
	/** 資料處理狀態代碼 1:成功 ; 2 資料檢核失敗 ; 3 黑名單拒保 */
	private String returnStatus;
	/** 資料處理狀態說明 */
	private String returnMsg;
	private String chubbReturnStatus;
	private String chubbReturnMsg;
	
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
	public String getDataSrc() {
		return dataSrc;
	}
	public void setDataSrc(String dataSrc) {
		this.dataSrc = dataSrc;
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
	public String getEndorseType() {
		return endorseType;
	}
	public void setEndorseType(String endorseType) {
		this.endorseType = endorseType;
	}
	public String getEndorseContent() {
		return endorseContent;
	}
	public void setEndorseContent(String endorseContent) {
		this.endorseContent = endorseContent;
	}
	public String getReturnStatus() {
		return returnStatus;
	}
	public void setReturnStatus(String returnStatus) {
		this.returnStatus = returnStatus;
	}
	public String getReturnMsg() {
		return returnMsg;
	}
	public void setReturnMsg(String returnMsg) {
		this.returnMsg = returnMsg;
	}
	
}
