package cn.com.sinosoft.inf.dict.xmlmsg.getUserMsg;

import cn.com.sinosoft.inf.dict.xmlmsg.common.SchemaNode;
import cn.com.sinosoft.inf.dict.xmlmsg.common.UserMgrRequestHeadSchema;

public class GetUserMsgReqPacket implements SchemaNode{

	private static final long serialVersionUID = 1L;

	public void validate() throws Exception {
	}
	private UserMgrRequestHeadSchema HEAD = new UserMgrRequestHeadSchema();
	private GetUserMsgReqBody BODY = new GetUserMsgReqBody();

	public UserMgrRequestHeadSchema getHEAD() {
		return HEAD;
	}
	public void setHEAD(UserMgrRequestHeadSchema HEAD) {
		this.HEAD = HEAD;
	}
	public GetUserMsgReqBody getBODY() {
		return BODY;
	}
	public void setBODY(GetUserMsgReqBody BODY) {
		this.BODY = BODY;
	}
	
}
