package cn.com.sinosoft.inf.dict.xmlmsg.getRiskEngage;

import cn.com.sinosoft.inf.dict.xmlmsg.common.RequestHeadSchema;
import cn.com.sinosoft.inf.dict.xmlmsg.common.SchemaNode;


public class GetRiskEngageReqPacket implements SchemaNode{

	private static final long serialVersionUID = 1L;
	private RequestHeadSchema HEAD = new RequestHeadSchema();
	private GetRiskEngageReqBody BODY = new GetRiskEngageReqBody();
	public void validate() throws Exception {
	}
	public RequestHeadSchema getHEAD() {
		return HEAD;
	}
	public void setHEAD(RequestHeadSchema head) {
		HEAD = head;
	}
	public GetRiskEngageReqBody getBODY() {
		return BODY;
	}
	public void setBODY(GetRiskEngageReqBody body) {
		BODY = body;
	}

}
