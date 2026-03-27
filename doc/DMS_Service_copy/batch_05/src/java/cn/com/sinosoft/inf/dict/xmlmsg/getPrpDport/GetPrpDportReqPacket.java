package cn.com.sinosoft.inf.dict.xmlmsg.getPrpDport;

import cn.com.sinosoft.inf.dict.xmlmsg.common.RequestHeadSchema;
import cn.com.sinosoft.inf.dict.xmlmsg.common.SchemaNode;

public class GetPrpDportReqPacket implements SchemaNode {

	private static final long serialVersionUID = 1L;
	public void validate() throws Exception {
	}
	private RequestHeadSchema HEAD = new RequestHeadSchema();
	private GetPrpDportReqBody BODY = new GetPrpDportReqBody();
	
	public RequestHeadSchema getHEAD() {
		return HEAD;
	}
	public void setHEAD(RequestHeadSchema HEAD) {
		this.HEAD = HEAD;
	}
	public GetPrpDportReqBody getBODY() {
		return BODY;
	}
	public void setBODY(GetPrpDportReqBody BODY) {
		this.BODY = BODY;
	}
	
}
