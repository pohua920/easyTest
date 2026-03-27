package cn.com.sinosoft.inf.PMS.reqDomains.sendPrpDlimitIdReqPacket;

import cn.com.sinosoft.inf.dict.xmlmsg.common.RequestHeadSchema;
import cn.com.sinosoft.inf.dict.xmlmsg.common.SchemaNode;


public class SendPrpDlimitIdReqPacket implements SchemaNode{

	private static final long serialVersionUID = 1L;

	public void validate() throws Exception {
		// TODO Auto-generated method stub
	}
	private RequestHeadSchema HEAD = new RequestHeadSchema();
	private SendPrpDlimitIdReqBody BODY = new SendPrpDlimitIdReqBody();

	public RequestHeadSchema getHEAD() {
		return HEAD;
	}
	public void setHEAD(RequestHeadSchema head) {
		HEAD = head;
	}
	public SendPrpDlimitIdReqBody getBODY() {
		return BODY;
	}
	public void setBODY(SendPrpDlimitIdReqBody body) {
		BODY = body;
	}

}
