package cn.com.sinosoft.inf.PMS.reqDomains.sendPrpDclassIdReqPacket;

import cn.com.sinosoft.inf.dict.xmlmsg.common.RequestHeadSchema;
import cn.com.sinosoft.inf.dict.xmlmsg.common.SchemaNode;


public class SendPrpDclassIdReqPacket implements SchemaNode{

	private static final long serialVersionUID = 1L;

	public void validate() throws Exception {
		// TODO Auto-generated method stub
	}
	private RequestHeadSchema HEAD = new RequestHeadSchema();
	private SendPrpDclassIdReqBody BODY = new SendPrpDclassIdReqBody();

	public RequestHeadSchema getHEAD() {
		return HEAD;
	}
	public void setHEAD(RequestHeadSchema head) {
		HEAD = head;
	}
	public SendPrpDclassIdReqBody getBODY() {
		return BODY;
	}
	public void setBODY(SendPrpDclassIdReqBody body) {
		BODY = body;
	}

}
