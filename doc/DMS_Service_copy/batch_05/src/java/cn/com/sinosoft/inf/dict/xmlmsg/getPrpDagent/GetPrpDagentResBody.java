package cn.com.sinosoft.inf.dict.xmlmsg.getPrpDagent;

import cn.com.sinosoft.inf.dict.xmlmsg.common.SchemaNode;

public class GetPrpDagentResBody implements SchemaNode {

	private static final long serialVersionUID = 1L;

	public void validate() throws Exception {
	}

	private PrpDagentInfo PRPDAGENTINFO = new PrpDagentInfo();

	public PrpDagentInfo getPRPDAGENTINFO() {
		return PRPDAGENTINFO;
	}

	public void setPRPDAGENTINFO(PrpDagentInfo PRPDAGENTINFO) {
		this.PRPDAGENTINFO = PRPDAGENTINFO;
	}

}
