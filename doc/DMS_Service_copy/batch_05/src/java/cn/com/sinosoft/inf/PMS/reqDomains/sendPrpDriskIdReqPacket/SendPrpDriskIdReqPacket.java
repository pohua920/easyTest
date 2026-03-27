package cn.com.sinosoft.inf.PMS.reqDomains.sendPrpDriskIdReqPacket;

import cn.com.sinosoft.inf.dict.xmlmsg.common.RequestHeadSchema;
import cn.com.sinosoft.inf.dict.xmlmsg.common.SchemaNode;


public class SendPrpDriskIdReqPacket implements SchemaNode{

	private static final long serialVersionUID = 1L;

	public void validate() throws Exception {
		// TODO Auto-generated method stub
	}
	private RequestHeadSchema HEAD = new RequestHeadSchema();
	private SendPrpDriskIdReqBody BODY = new SendPrpDriskIdReqBody();

	public RequestHeadSchema getHEAD() {
		return HEAD;
	}
	public void setHEAD(RequestHeadSchema head) {
		HEAD = head;
	}
	public SendPrpDriskIdReqBody getBODY() {
		return BODY;
	}
	public void setBODY(SendPrpDriskIdReqBody body) {
		BODY = body;
	}

}
