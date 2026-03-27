package cn.com.sinosoft.inf.dict.xmlmsg.getUserMsg;

import cn.com.sinosoft.inf.dict.xmlmsg.common.ResponseHeadSchema;
import cn.com.sinosoft.inf.dict.xmlmsg.common.SchemaNode;

public class GetUserMsgResPacket implements SchemaNode{

	private static final long serialVersionUID = 1L;

	public void validate() throws Exception {
	}
	
	private ResponseHeadSchema HEAD = new ResponseHeadSchema();
	private GetUserMsgResBody BODY = new GetUserMsgResBody();

	public ResponseHeadSchema getHEAD() {
		return HEAD;
	}
	public void setHEAD(ResponseHeadSchema HEAD) {
		this.HEAD = HEAD;
	}
	public GetUserMsgResBody getBODY() {
		return BODY;
	}
	public void setBODY(GetUserMsgResBody BODY) {
		this.BODY = BODY;
	}
	
}
