package cn.com.sinosoft.inf.PMS.reqDomains.sendPrpDriskEngageIdReqPacket;

import cn.com.sinosoft.inf.dict.xmlmsg.common.RequestHeadSchema;
import cn.com.sinosoft.inf.dict.xmlmsg.common.SchemaNode;


public class SendPrpDriskEngageIdReqPacket implements SchemaNode{

	private static final long serialVersionUID = 1L;

	public void validate() throws Exception {
		// TODO Auto-generated method stub
	}
	private RequestHeadSchema HEAD = new RequestHeadSchema();
	private SendPrpDriskEngageIdReqBody BODY = new SendPrpDriskEngageIdReqBody();

	public RequestHeadSchema getHEAD() {
		return HEAD;
	}
	public void setHEAD(RequestHeadSchema head) {
		HEAD = head;
	}
	public SendPrpDriskEngageIdReqBody getBODY() {
		return BODY;
	}
	public void setBODY(SendPrpDriskEngageIdReqBody body) {
		BODY = body;
	}

}
