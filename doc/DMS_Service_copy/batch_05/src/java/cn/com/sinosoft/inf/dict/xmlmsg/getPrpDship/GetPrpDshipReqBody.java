package cn.com.sinosoft.inf.dict.xmlmsg.getPrpDship;

import cn.com.sinosoft.inf.dict.xmlmsg.common.SchemaNode;

public class GetPrpDshipReqBody implements SchemaNode{
	
	private static final long serialVersionUID = 1L;
	public void validate() throws Exception {
	}
	private String SHIPCODE = "";
	public String getSHIPCODE() {
		return SHIPCODE;
	}
	public void setSHIPCODE(String SHIPCODE) {
		this.SHIPCODE = SHIPCODE;
	}

}
