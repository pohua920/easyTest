package cn.com.sinosoft.inf.dict.xmlmsg.getPrpDagent;

import cn.com.sinosoft.inf.dict.xmlmsg.common.SchemaNode;

public class GetPrpDagentReqBody implements SchemaNode {
	private static final long serialVersionUID = 1L;

	public void validate() throws Exception {
	}

	private String AGENTCODE = "";

	public String getAGENTCODE() {
		return AGENTCODE;
	}

	public void setAGENTCODE(String agentcode) {
		AGENTCODE = agentcode;
	}


}
