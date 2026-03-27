package cn.com.sinosoft.inf.PMS.reqDomains.sendPrpDriskItemIdReqPacket;

import cn.com.sinosoft.inf.dict.xmlmsg.common.RequestHeadSchema;
import cn.com.sinosoft.inf.dict.xmlmsg.common.SchemaNode;


public class SendPrpDriskItemIdReqPacket implements SchemaNode{

	private static final long serialVersionUID = 1L;

	public void validate() throws Exception {
		// TODO Auto-generated method stub
	}
	private RequestHeadSchema HEAD = new RequestHeadSchema();
	private SendPrpDriskItemIdReqBody BODY = new SendPrpDriskItemIdReqBody();

	public RequestHeadSchema getHEAD() {
		return HEAD;
	}
	public void setHEAD(RequestHeadSchema head) {
		HEAD = head;
	}
	public SendPrpDriskItemIdReqBody getBODY() {
		return BODY;
	}
	public void setBODY(SendPrpDriskItemIdReqBody body) {
		BODY = body;
	}

}
