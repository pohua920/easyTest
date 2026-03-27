package cn.com.sinosoft.inf.dict.xmlmsg.getPrpDship;

import cn.com.sinosoft.inf.dict.xmlmsg.common.RequestHeadSchema;
import cn.com.sinosoft.inf.dict.xmlmsg.common.SchemaNode;

public class GetPrpDshipReqPacket implements SchemaNode{
	private static final long serialVersionUID = 1L;
	public void validate() throws Exception {
	}
	private RequestHeadSchema HEAD = new RequestHeadSchema();
	private GetPrpDshipReqBody BODY = new GetPrpDshipReqBody();
	public RequestHeadSchema getHEAD() {
		return HEAD;
	}
	public void setHEAD(RequestHeadSchema HEAD) {
		this.HEAD = HEAD;
	}
	public GetPrpDshipReqBody getBODY() {
		return BODY;
	}
	public void setBODY(GetPrpDshipReqBody BODY) {
		this.BODY = BODY;
	}
	
}
