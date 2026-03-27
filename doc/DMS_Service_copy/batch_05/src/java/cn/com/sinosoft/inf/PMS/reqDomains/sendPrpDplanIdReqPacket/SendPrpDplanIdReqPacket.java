package cn.com.sinosoft.inf.PMS.reqDomains.sendPrpDplanIdReqPacket;

import cn.com.sinosoft.inf.dict.xmlmsg.common.RequestHeadSchema;
import cn.com.sinosoft.inf.dict.xmlmsg.common.SchemaNode;


public class SendPrpDplanIdReqPacket implements SchemaNode{

	private static final long serialVersionUID = 1L;

	public void validate() throws Exception {
		// TODO Auto-generated method stub
	}
	private RequestHeadSchema HEAD = new RequestHeadSchema();
	private SendPrpDplanIdReqBody BODY = new SendPrpDplanIdReqBody();

	public RequestHeadSchema getHEAD() {
		return HEAD;
	}
	public void setHEAD(RequestHeadSchema head) {
		HEAD = head;
	}
	public SendPrpDplanIdReqBody getBODY() {
		return BODY;
	}
	public void setBODY(SendPrpDplanIdReqBody body) {
		BODY = body;
	}

}
