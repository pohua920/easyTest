package cn.com.sinosoft.intf.image.model.vo;


public class BASE_DATA {
	
	//操作来源
	private String APP_TYPE;
	
	//机构号码
	private String ORG_NUM;
	
	//操作员ID
	private String OP_ID;
	
	//操作员姓名
	private String OP_USER;
	
	//操作权限
	private String OP_RIGHT;

	public String getAPP_TYPE() {
		return APP_TYPE;
	}

	public void setAPP_TYPE(String app_type) {
		APP_TYPE = app_type;
	}

	public String getORG_NUM() {
		return ORG_NUM;
	}

	public void setORG_NUM(String org_num) {
		ORG_NUM = org_num;
	}

	public String getOP_ID() {
		return OP_ID;
	}

	public void setOP_ID(String op_id) {
		OP_ID = op_id;
	}

	public String getOP_USER() {
		return OP_USER;
	}

	public void setOP_USER(String op_user) {
		OP_USER = op_user;
	}

	public String getOP_RIGHT() {
		return OP_RIGHT;
	}

	public void setOP_RIGHT(String op_right) {
		OP_RIGHT = op_right;
	}
	
}
