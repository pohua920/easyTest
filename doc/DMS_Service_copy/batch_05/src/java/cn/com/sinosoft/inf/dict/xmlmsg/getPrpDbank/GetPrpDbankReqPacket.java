package cn.com.sinosoft.inf.dict.xmlmsg.getPrpDbank;

import cn.com.sinosoft.inf.dict.xmlmsg.common.RequestHeadSchema;
import cn.com.sinosoft.inf.dict.xmlmsg.common.SchemaNode;

public class GetPrpDbankReqPacket implements SchemaNode {
	private static final long serialVersionUID = 1L;

	public void validate() throws Exception {
	}

	private RequestHeadSchema HEAD = new RequestHeadSchema();
	private GetPrpDbankReqBody BODY = new GetPrpDbankReqBody();

	public RequestHeadSchema getHEAD() {
		return HEAD;
	}

	public void setHEAD(RequestHeadSchema HEAD) {
		this.HEAD = HEAD;
	}

	public GetPrpDbankReqBody getBODY() {
		return BODY;
	}

	public void setBODY(GetPrpDbankReqBody BODY) {
		this.BODY = BODY;
	}

}
