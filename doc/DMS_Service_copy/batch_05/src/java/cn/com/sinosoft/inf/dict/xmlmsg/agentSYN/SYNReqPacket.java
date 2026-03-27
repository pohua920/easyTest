package cn.com.sinosoft.inf.dict.xmlmsg.agentSYN;

import cn.com.sinosoft.inf.dict.xmlmsg.common.RequestHeadSchema;
import cn.com.sinosoft.inf.dict.xmlmsg.common.SchemaNode;



public class SYNReqPacket implements SchemaNode {

	private static final long serialVersionUID = 1L;

	public void validate() throws Exception {
	}
	private RequestHeadSchema HEAD = new RequestHeadSchema();
	private SYNReqBody BODY = new SYNReqBody();

	public RequestHeadSchema getHEAD() {
		return HEAD;
	}
	public void setHEAD(RequestHeadSchema head) {
		HEAD = head;
	}
	public SYNReqBody getBODY() {
		return BODY;
	}
	public void setBODY(SYNReqBody body) {
		BODY = body;
	}

}
