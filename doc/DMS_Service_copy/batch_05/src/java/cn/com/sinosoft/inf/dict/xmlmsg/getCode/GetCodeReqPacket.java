package cn.com.sinosoft.inf.dict.xmlmsg.getCode;

import cn.com.sinosoft.inf.dict.xmlmsg.common.RequestHeadSchema;
import cn.com.sinosoft.inf.dict.xmlmsg.common.SchemaNode;

public class GetCodeReqPacket implements SchemaNode{
	private static final long serialVersionUID = 1L;
	public void validate() throws Exception {
		// TODO Auto-generated method stub
	}
	
	private RequestHeadSchema HEAD = new RequestHeadSchema();
	private GetCodeReqBody BODY = new GetCodeReqBody();
	public RequestHeadSchema getHEAD() {
		return HEAD;
	}
	public void setHEAD(RequestHeadSchema head) {
		HEAD = head;
	}
	public GetCodeReqBody getBODY() {
		return BODY;
	}
	public void setBODY(GetCodeReqBody body) {
		BODY = body;
	}

}
