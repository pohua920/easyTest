package cn.com.sinosoft.inf.PMS.reqDomains.sendPrpDriskShortRateIdReqPacket;

import cn.com.sinosoft.inf.dict.xmlmsg.common.RequestHeadSchema;
import cn.com.sinosoft.inf.dict.xmlmsg.common.SchemaNode;


public class SendPrpDriskShortRateIdReqPacket implements SchemaNode{

	private static final long serialVersionUID = 1L;

	public void validate() throws Exception {
		// TODO Auto-generated method stub
	}
	private RequestHeadSchema HEAD = new RequestHeadSchema();
	private SendPrpDriskShortRateIdReqBody BODY = new SendPrpDriskShortRateIdReqBody();

	public RequestHeadSchema getHEAD() {
		return HEAD;
	}
	public void setHEAD(RequestHeadSchema head) {
		HEAD = head;
	}
	public SendPrpDriskShortRateIdReqBody getBODY() {
		return BODY;
	}
	public void setBODY(SendPrpDriskShortRateIdReqBody body) {
		BODY = body;
	}

}
