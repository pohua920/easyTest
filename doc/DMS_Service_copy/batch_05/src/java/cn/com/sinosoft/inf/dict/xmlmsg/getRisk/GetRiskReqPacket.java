package cn.com.sinosoft.inf.dict.xmlmsg.getRisk;

import cn.com.sinosoft.inf.dict.xmlmsg.common.RequestHeadPacket;
import cn.com.sinosoft.inf.dict.xmlmsg.common.SchemaNode;


public class GetRiskReqPacket implements SchemaNode{

	private static final long serialVersionUID = 1L;
	private RequestHeadPacket HEAD = new RequestHeadPacket();
	private GetRiskReqBody BODY = new GetRiskReqBody();
	public void validate() throws Exception {
	}
	public RequestHeadPacket getHEAD() {
		return HEAD;
	}
	public void setHEAD(RequestHeadPacket head) {
		HEAD = head;
	}
	public GetRiskReqBody getBODY() {
		return BODY;
	}
	public void setBODY(GetRiskReqBody body) {
		BODY = body;
	}

}
