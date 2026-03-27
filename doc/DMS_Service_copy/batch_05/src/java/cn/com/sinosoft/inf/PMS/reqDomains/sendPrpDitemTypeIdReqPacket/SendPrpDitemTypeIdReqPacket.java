package cn.com.sinosoft.inf.PMS.reqDomains.sendPrpDitemTypeIdReqPacket;

import cn.com.sinosoft.inf.dict.xmlmsg.common.RequestHeadSchema;
import cn.com.sinosoft.inf.dict.xmlmsg.common.SchemaNode;


public class SendPrpDitemTypeIdReqPacket implements SchemaNode{

	private static final long serialVersionUID = 1L;

	public void validate() throws Exception {
		// TODO Auto-generated method stub
	}
	private RequestHeadSchema HEAD = new RequestHeadSchema();
	private SendPrpDitemTypeIdReqBody BODY = new SendPrpDitemTypeIdReqBody();

	public RequestHeadSchema getHEAD() {
		return HEAD;
	}
	public void setHEAD(RequestHeadSchema head) {
		HEAD = head;
	}
	public SendPrpDitemTypeIdReqBody getBODY() {
		return BODY;
	}
	public void setBODY(SendPrpDitemTypeIdReqBody body) {
		BODY = body;
	}

}
