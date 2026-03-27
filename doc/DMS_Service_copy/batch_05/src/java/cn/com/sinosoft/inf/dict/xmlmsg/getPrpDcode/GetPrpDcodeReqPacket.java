package cn.com.sinosoft.inf.dict.xmlmsg.getPrpDcode;

import cn.com.sinosoft.inf.dict.xmlmsg.common.RequestHeadSchema;
import cn.com.sinosoft.inf.dict.xmlmsg.common.SchemaNode;

public class GetPrpDcodeReqPacket implements SchemaNode{

	private static final long serialVersionUID = 1L;

	public void validate() throws Exception {
	}

	private RequestHeadSchema HEAD = new RequestHeadSchema();
	private GetPrpDcodeReqBody BODY = new GetPrpDcodeReqBody();

	public RequestHeadSchema getHEAD() {
		return HEAD;
	}

	public void setHEAD(RequestHeadSchema HEAD) {
		this.HEAD = HEAD;
	}

	public GetPrpDcodeReqBody getBODY() {
		return BODY;
	}

	public void setBODY(GetPrpDcodeReqBody BODY) {
		this.BODY = BODY;
	}

}
