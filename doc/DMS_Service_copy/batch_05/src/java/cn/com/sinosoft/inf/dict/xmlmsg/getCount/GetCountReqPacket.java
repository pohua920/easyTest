package cn.com.sinosoft.inf.dict.xmlmsg.getCount;

import cn.com.sinosoft.inf.dict.xmlmsg.common.RequestHeadSchema;
import cn.com.sinosoft.inf.dict.xmlmsg.common.SchemaNode;

public class GetCountReqPacket implements SchemaNode{

	private static final long serialVersionUID = 1L;

	public void validate() throws Exception {
	}

	private RequestHeadSchema HEAD = new RequestHeadSchema();
	private GetCountReqBody BODY = new GetCountReqBody();

	public RequestHeadSchema getHEAD() {
		return HEAD;
	}

	public void setHEAD(RequestHeadSchema HEAD) {
		this.HEAD = HEAD;
	}

	public GetCountReqBody getBODY() {
		return BODY;
	}

	public void setBODY(GetCountReqBody BODY) {
		this.BODY = BODY;
	}

}
