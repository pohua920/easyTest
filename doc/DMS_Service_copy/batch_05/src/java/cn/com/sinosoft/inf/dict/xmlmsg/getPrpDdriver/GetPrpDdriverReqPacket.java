package cn.com.sinosoft.inf.dict.xmlmsg.getPrpDdriver;

import cn.com.sinosoft.inf.dict.xmlmsg.common.RequestHeadSchema;
import cn.com.sinosoft.inf.dict.xmlmsg.common.SchemaNode;

public class GetPrpDdriverReqPacket implements SchemaNode {

	private static final long serialVersionUID = 1L;
	public void validate() throws Exception {
	}
	private RequestHeadSchema HEAD = new RequestHeadSchema();
	private GetPrpDdriverReqBody BODY = new GetPrpDdriverReqBody();
	
	public RequestHeadSchema getHEAD() {
		return HEAD;
	}
	public void setHEAD(RequestHeadSchema HEAD) {
		this.HEAD = HEAD;
	}
	public GetPrpDdriverReqBody getBODY() {
		return BODY;
	}
	public void setBODY(GetPrpDdriverReqBody BODY) {
		this.BODY = BODY;
	}
	
	
}
