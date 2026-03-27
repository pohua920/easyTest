package cn.com.sinosoft.intf.image.model.vo;



public class META_DATA {
	
	//业务类型
	private String APP_CODE;
	
	//叶节点ID
	private String NODE_ID;
	
	//业务受理号
	private String BUSI_NUM;
	
	//险类
	private String INS_TYPE;
	
	//客户号
	private String CUST_ID;
	
	//保单号
	private String POLC_NO;
	
    //规则标志 
	private String CLASSIFY_LIMIT;
	
	//投保客户号
	private String customerID;
	
	//个人证件号
	private String identifyNumber;	

	//个人客户姓名
	private String customerCName;	
	
    //组织机构代码
	private String orgNumber; 
	
	//客户名称
	private String orgcname;
	
	public String getCLASSIFY_LIMIT() {
		return CLASSIFY_LIMIT;
	}

	public void setCLASSIFY_LIMIT(String classify_limit) {
		CLASSIFY_LIMIT = classify_limit;
	}

	public String getAPP_CODE() {
		return APP_CODE;
	}

	public void setAPP_CODE(String app_code) {
		APP_CODE = app_code;
	}
	
	public String getINS_TYPE(){
		return INS_TYPE;
	}
	
	public void setINS_TYPE(String ins_type){
		INS_TYPE = ins_type;
	}

	public String getNODE_ID() {
		return NODE_ID;
	}

	public void setNODE_ID(String node_id) {
		NODE_ID = node_id;
	}

	public String getBUSI_NUM() {
		return BUSI_NUM;
	}

	public void setBUSI_NUM(String busi_num) {
		BUSI_NUM = busi_num;
	}

	public String getCUST_ID() {
		return CUST_ID;
	}

	public void setCUST_ID(String cust_id) {
		CUST_ID = cust_id;
	}

	public String getPOLC_NO() {
		return POLC_NO;
	}

	public void setPOLC_NO(String polc_no) {
		POLC_NO = polc_no;
	}

	public String getCustomerCName() {
		return customerCName;
	}

	public void setCustomerCName(String customerCName) {
		this.customerCName = customerCName;
	}

	public String getCustomerID() {
		return customerID;
	}

	public void setCustomerID(String customerID) {
		this.customerID = customerID;
	}

	public String getIdentifyNumber() {
		return identifyNumber;
	}

	public void setIdentifyNumber(String identifyNumber) {
		this.identifyNumber = identifyNumber;
	}

	public String getOrgcname() {
		return orgcname;
	}

	public void setOrgcname(String orgcname) {
		this.orgcname = orgcname;
	}

	public String getOrgNumber() {
		return orgNumber;
	}

	public void setOrgNumber(String orgNumber) {
		this.orgNumber = orgNumber;
	}
}
