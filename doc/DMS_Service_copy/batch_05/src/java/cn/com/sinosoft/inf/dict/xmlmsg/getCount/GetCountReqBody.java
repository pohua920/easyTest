package cn.com.sinosoft.inf.dict.xmlmsg.getCount;

import cn.com.sinosoft.inf.dict.xmlmsg.common.SchemaNode;

public class GetCountReqBody implements SchemaNode{

	private static final long serialVersionUID = 1L;

	public void validate() throws Exception {
	}

	private String TABLENAME = "";
	private String CONDITION = "";

	public String getTABLENAME() {
		return TABLENAME;
	}

	public void setTABLENAME(String TABLENAME) {
		this.TABLENAME = TABLENAME;
	}

	public String getCONDITION() {
		return CONDITION;
	}

	public void setCONDITION(String CONDITION) {
		this.CONDITION = CONDITION;
	}

}

