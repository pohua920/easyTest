package cn.com.sinosoft.inf.PMS.reqDomains.sendPrpDriskLimitIdReqPacket;

import cn.com.sinosoft.inf.dict.xmlmsg.common.RequestHeadSchema;
import cn.com.sinosoft.inf.dict.xmlmsg.common.SchemaNode;


public class SendPrpDriskLimitIdReqPacket implements SchemaNode{

	private static final long serialVersionUID = 1L;

	public void validate() throws Exception {
		// TODO Auto-generated method stub
	}
	private RequestHeadSchema HEAD = new RequestHeadSchema();
	private SendPrpDriskLimitIdReqBody BODY = new SendPrpDriskLimitIdReqBody();

	public RequestHeadSchema getHEAD() {
		return HEAD;
	}
	public void setHEAD(RequestHeadSchema head) {
		HEAD = head;
	}
	public SendPrpDriskLimitIdReqBody getBODY() {
		return BODY;
	}
	public void setBODY(SendPrpDriskLimitIdReqBody body) {
		BODY = body;
	}

}
