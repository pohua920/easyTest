package cn.com.sinosoft.inf.PMS.reqDomains.sendPrpDengageIdReqPacket;

import cn.com.sinosoft.inf.dict.xmlmsg.common.RequestHeadSchema;
import cn.com.sinosoft.inf.dict.xmlmsg.common.SchemaNode;


public class SendPrpDengageIdReqPacket implements SchemaNode{

	private static final long serialVersionUID = 1L;

	public void validate() throws Exception {
		// TODO Auto-generated method stub
	}
	private RequestHeadSchema HEAD = new RequestHeadSchema();
	private SendPrpDengageIdReqBody BODY = new SendPrpDengageIdReqBody();

	public RequestHeadSchema getHEAD() {
		return HEAD;
	}
	public void setHEAD(RequestHeadSchema head) {
		HEAD = head;
	}
	public SendPrpDengageIdReqBody getBODY() {
		return BODY;
	}
	public void setBODY(SendPrpDengageIdReqBody body) {
		BODY = body;
	}

}
