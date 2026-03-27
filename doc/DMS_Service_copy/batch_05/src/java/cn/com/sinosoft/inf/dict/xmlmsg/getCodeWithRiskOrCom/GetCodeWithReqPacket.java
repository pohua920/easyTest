package cn.com.sinosoft.inf.dict.xmlmsg.getCodeWithRiskOrCom;

import cn.com.sinosoft.inf.dict.xmlmsg.common.RequestHeadSchema;
import cn.com.sinosoft.inf.dict.xmlmsg.common.SchemaNode;

public class GetCodeWithReqPacket implements SchemaNode{
	private static final long serialVersionUID = 1L;
	public void validate() throws Exception {
	}
	
	private RequestHeadSchema HEAD = new RequestHeadSchema();
	private GetCodeWithReqBody BODY = new GetCodeWithReqBody();
	public RequestHeadSchema getHEAD() {
		return HEAD;
	}
	public void setHEAD(RequestHeadSchema head) {
		HEAD = head;
	}
	public GetCodeWithReqBody getBODY() {
		return BODY;
	}
	public void setBODY(GetCodeWithReqBody body) {
		BODY = body;
	}

}
