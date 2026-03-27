package cn.com.sinosoft.inf.PMS.reqDomains.sendPrpDframeIdReqPacket;

import cn.com.sinosoft.inf.dict.xmlmsg.common.RequestHeadSchema;
import cn.com.sinosoft.inf.dict.xmlmsg.common.SchemaNode;


public class SendPrpDframeIdReqPacket implements SchemaNode{

	private static final long serialVersionUID = 1L;

	public void validate() throws Exception {
		// TODO Auto-generated method stub
	}
	private RequestHeadSchema HEAD = new RequestHeadSchema();
	private SendPrpDframeIdReqBody BODY = new SendPrpDframeIdReqBody();

	public RequestHeadSchema getHEAD() {
		return HEAD;
	}
	public void setHEAD(RequestHeadSchema head) {
		HEAD = head;
	}
	public SendPrpDframeIdReqBody getBODY() {
		return BODY;
	}
	public void setBODY(SendPrpDframeIdReqBody body) {
		BODY = body;
	}

}
