package cn.com.sinosoft.inf.dict.xmlmsg.getPrpDagent;

import cn.com.sinosoft.inf.dict.xmlmsg.common.ResponseHeadSchema;
import cn.com.sinosoft.inf.dict.xmlmsg.common.SchemaNode;

public class GetPrpDagentResPacket implements SchemaNode {

	private static final long serialVersionUID = 1L;

	public void validate() throws Exception {
	}

	private ResponseHeadSchema HEAD = new ResponseHeadSchema();
	private GetPrpDagentResBody BODY = new GetPrpDagentResBody();

	public ResponseHeadSchema getHEAD() {
		return HEAD;
	}

	public void setHEAD(ResponseHeadSchema HEAD) {
		this.HEAD = HEAD;
	}

	public GetPrpDagentResBody getBODY() {
		return BODY;
	}

	public void setBODY(GetPrpDagentResBody BODY) {
		this.BODY = BODY;
	}

}
