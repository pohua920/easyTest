package cn.com.sinosoft.inf.PMS.reqDomains.sendPrpDitemIdReqPacket;

import cn.com.sinosoft.inf.dict.xmlmsg.common.RequestHeadSchema;
import cn.com.sinosoft.inf.dict.xmlmsg.common.SchemaNode;


public class SendPrpDitemIdReqPacket implements SchemaNode{

	private static final long serialVersionUID = 1L;

	public void validate() throws Exception {
		// TODO Auto-generated method stub
	}
	private RequestHeadSchema HEAD = new RequestHeadSchema();
	private SendPrpDitemIdReqBody BODY = new SendPrpDitemIdReqBody();

	public RequestHeadSchema getHEAD() {
		return HEAD;
	}
	public void setHEAD(RequestHeadSchema head) {
		HEAD = head;
	}
	public SendPrpDitemIdReqBody getBODY() {
		return BODY;
	}
	public void setBODY(SendPrpDitemIdReqBody body) {
		BODY = body;
	}

}
