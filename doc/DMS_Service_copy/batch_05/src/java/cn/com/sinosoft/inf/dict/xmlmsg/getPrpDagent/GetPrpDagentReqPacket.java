package cn.com.sinosoft.inf.dict.xmlmsg.getPrpDagent;

import cn.com.sinosoft.inf.dict.xmlmsg.common.RequestHeadSchema;
import cn.com.sinosoft.inf.dict.xmlmsg.common.SchemaNode;

public class GetPrpDagentReqPacket implements SchemaNode {
	private static final long serialVersionUID = 1L;

	public void validate() throws Exception {
	}

	private RequestHeadSchema HEAD = new RequestHeadSchema();
	private GetPrpDagentReqBody BODY = new GetPrpDagentReqBody();

	public RequestHeadSchema getHEAD() {
		return HEAD;
	}

	public void setHEAD(RequestHeadSchema HEAD) {
		this.HEAD = HEAD;
	}

	public GetPrpDagentReqBody getBODY() {
		return BODY;
	}

	public void setBODY(GetPrpDagentReqBody BODY) {
		this.BODY = BODY;
	}

}
