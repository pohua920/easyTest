package cn.com.sinosoft.inf.dict.xmlmsg.getprpdcompany;

import cn.com.sinosoft.inf.dict.xmlmsg.common.SchemaNode;

public class GetPrpDcompanyReqBody implements SchemaNode {

	private static final long serialVersionUID = 1L;
	public void validate() throws Exception {
	}
	private String COMCODE="";
	public String getCOMCODE() {
		return COMCODE;
	}
	public void setCOMCODE(String COMCODE) {
		this.COMCODE = COMCODE;
	}
	
}
