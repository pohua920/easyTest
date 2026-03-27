package com.sinosoft.dmsdriver.domain.common;



public class UserMgrRequestHeadSchema implements SchemaNode{
	private static final long serialVersionUID = 1L;
	private String REQUEST_TYPE;//请求类型

	public String getREQUEST_TYPE() {
		return REQUEST_TYPE;
	}

	public void setREQUEST_TYPE(String REQUEST_TYPE) {
		this.REQUEST_TYPE = REQUEST_TYPE;
	}

	public void validate() throws Exception {
	}

}
