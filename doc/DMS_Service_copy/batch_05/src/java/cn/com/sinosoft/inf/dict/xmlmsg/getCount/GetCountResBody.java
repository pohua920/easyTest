package cn.com.sinosoft.inf.dict.xmlmsg.getCount;

import cn.com.sinosoft.inf.dict.xmlmsg.common.SchemaNode;

public class GetCountResBody implements SchemaNode{

	private static final long serialVersionUID = 1L;

	public void validate() throws Exception {
	}

	private String COUNT = "";

	public String getCOUNT() {
		return COUNT;
	}

	public void setCOUNT(String COUNT) {
		this.COUNT = COUNT;
	}
}
