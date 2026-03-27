package cn.com.sinosoft.inf.dict.xmlmsg.getUserMsg;

import cn.com.sinosoft.inf.dict.xmlmsg.common.SchemaNode;

public class GetUserMsgReqBody implements SchemaNode{

	private static final long serialVersionUID = 1L;

	public void validate() throws Exception {
	}
	
	private String USERCODE = "";

	public String getUSERCODE() {
		return USERCODE;
	}

	public void setUSERCODE(String USERCODE) {
		this.USERCODE = USERCODE;
	}
	
}
