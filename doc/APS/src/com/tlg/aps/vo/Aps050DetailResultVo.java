package com.tlg.aps.vo;

/** mantis：，處理人員：BJ016，需求單編號：險部客服行動裝置險保單查詢作業 */
public class Aps050DetailResultVo {
	private String transactionId;
	private String prodno;
	private String prodname;
	private String imei;
	private String type;
	private String brand;
	private String model;
	private String rrp;
	private String msisdn;
	private String purchaseDate;
	private String isFetSupply;
	private String storeId;
	private String storeName;
	private String endorseContent;
	private String proposalFileName;
	private String isClaim;
	/** mantis：MOB0025，處理人員：CE035，需求單編號：MOB0025 新增要保書紙本進件流程，修改APS查詢畫面 START */
	/** 合約編號 */
	private String contractId;
	/** 申請書編號 */
	private String applicationId;
	/** 保單號碼 */
	private String policyNo;
	/** 被保險人姓名 */
	private String insuredName;
	/** 門市屬性 */
	private String channelTypeName;
	private String channelType;
	/** 招攬人員登錄證字號 */
	private String salesId;
	/** 操作人員代碼/門市編號 */
	private String employeeId;
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
	public String getContractId() {
		return contractId;
	}
	public void setContractId(String contractId) {
		this.contractId = contractId;
	}
	public String getApplicationId() {
		return applicationId;
	}
	public void setApplicationId(String applicationId) {
		this.applicationId = applicationId;
	}
	public String getPolicyNo() {
		return policyNo;
	}
	public void setPolicyNo(String policyNo) {
		this.policyNo = policyNo;
	}
	public String getInsuredName() {
		return insuredName;
	}
	public void setInsuredName(String insuredName) {
		this.insuredName = insuredName;
	}
	public String getChannelTypeName() {
		return channelTypeName;
	}
	public void setChannelTypeName(String channelTypeName) {
		this.channelTypeName = channelTypeName;
	}
	public String getChannelType() {
		return channelType;
	}
	public void setChannelType(String channelType) {
		this.channelType = channelType;
	}
	public String getSalesId() {
		return salesId;
	}
	public void setSalesId(String salesId) {
		this.salesId = salesId;
	}
	public String getEmployeeId() {
		return employeeId;
	}
	public void setEmployeeId(String employeeId) {
		this.employeeId = employeeId;
	}
	/** mantis：MOB0025，處理人員：CE035，需求單編號：MOB0025 新增要保書紙本進件流程，修改APS查詢畫面 END */
	public String getTransactionId() {
		return transactionId;
	}
	public void setTransactionId(String transactionId) {
		this.transactionId = transactionId;
	}
	public String getProdno() {
		return prodno;
	}
	public void setProdno(String prodno) {
		this.prodno = prodno;
	}
	public String getProdname() {
		return prodname;
	}
	public void setProdname(String prodname) {
		this.prodname = prodname;
	}
	public String getImei() {
		return imei;
	}
	public void setImei(String imei) {
		this.imei = imei;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getBrand() {
		return brand;
	}
	public void setBrand(String brand) {
		this.brand = brand;
	}
	public String getModel() {
		return model;
	}
	public void setModel(String model) {
		this.model = model;
	}
	public String getRrp() {
		return rrp;
	}
	public void setRrp(String rrp) {
		this.rrp = rrp;
	}
	public String getMsisdn() {
		return msisdn;
	}
	public void setMsisdn(String msisdn) {
		this.msisdn = msisdn;
	}
	public String getPurchaseDate() {
		return purchaseDate;
	}
	public void setPurchaseDate(String purchaseDate) {
		this.purchaseDate = purchaseDate;
	}
	public String getIsFetSupply() {
		return isFetSupply;
	}
	public void setIsFetSupply(String isFetSupply) {
		this.isFetSupply = isFetSupply;
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
	public String getEndorseContent() {
		return endorseContent;
	}
	public void setEndorseContent(String endorseContent) {
		this.endorseContent = endorseContent;
	}
	public String getProposalFileName() {
		return proposalFileName;
	}
	public void setProposalFileName(String proposalFileName) {
		this.proposalFileName = proposalFileName;
	}
	public String getIsClaim() {
		return isClaim;
	}
	public void setIsClaim(String isClaim) {
		this.isClaim = isClaim;
	}
	
}
