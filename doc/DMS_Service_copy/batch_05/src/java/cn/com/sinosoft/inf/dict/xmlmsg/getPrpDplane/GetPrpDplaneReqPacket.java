package cn.com.sinosoft.inf.dict.xmlmsg.getPrpDplane;

import cn.com.sinosoft.inf.dict.xmlmsg.common.RequestHeadSchema;
import cn.com.sinosoft.inf.dict.xmlmsg.common.SchemaNode;

public class GetPrpDplaneReqPacket implements SchemaNode {

	private static final long serialVersionUID = 1L;
	public void validate() throws Exception {
	}
	private RequestHeadSchema HEAD = new RequestHeadSchema();
	private GetPrpDplaneReqBody BODY = new GetPrpDplaneReqBody();
	public RequestHeadSchema getHEAD() {
		return HEAD;
	}
	public void setHEAD(RequestHeadSchema HEAD) {
		this.HEAD = HEAD;
	}
	public GetPrpDplaneReqBody getBODY() {
		return BODY;
	}
	public void setBODY(GetPrpDplaneReqBody BODY) {
		this.BODY = BODY;
	}
	
}
