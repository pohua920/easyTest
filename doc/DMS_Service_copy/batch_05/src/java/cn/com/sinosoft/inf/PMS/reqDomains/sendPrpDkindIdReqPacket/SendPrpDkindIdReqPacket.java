package cn.com.sinosoft.inf.PMS.reqDomains.sendPrpDkindIdReqPacket;

import cn.com.sinosoft.inf.dict.xmlmsg.common.RequestHeadSchema;
import cn.com.sinosoft.inf.dict.xmlmsg.common.SchemaNode;


public class SendPrpDkindIdReqPacket implements SchemaNode{

	private static final long serialVersionUID = 1L;

	public void validate() throws Exception {
		// TODO Auto-generated method stub
	}
	private RequestHeadSchema HEAD = new RequestHeadSchema();
	private SendPrpDkindIdReqBody BODY = new SendPrpDkindIdReqBody();

	public RequestHeadSchema getHEAD() {
		return HEAD;
	}
	public void setHEAD(RequestHeadSchema head) {
		HEAD = head;
	}
	public SendPrpDkindIdReqBody getBODY() {
		return BODY;
	}
	public void setBODY(SendPrpDkindIdReqBody body) {
		BODY = body;
	}

}
