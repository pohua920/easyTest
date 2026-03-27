package cn.com.sinosoft.inf.dict.xmlmsg.getUpperCode;

import cn.com.sinosoft.inf.dict.xmlmsg.common.RequestHeadSchema;
import cn.com.sinosoft.inf.dict.xmlmsg.common.SchemaNode;

public class GetUpperCodeReqPacket implements SchemaNode{

	private static final long serialVersionUID = 1L;

	public void validate() throws Exception {
	}

	private RequestHeadSchema HEAD = new RequestHeadSchema();
	private GetUpperCodeReqBody BODY = new GetUpperCodeReqBody();

	public RequestHeadSchema getHEAD() {
		return HEAD;
	}

	public void setHEAD(RequestHeadSchema HEAD) {
		this.HEAD = HEAD;
	}

	public GetUpperCodeReqBody getBODY() {
		return BODY;
	}

	public void setBODY(GetUpperCodeReqBody BODY) {
		this.BODY = BODY;
	}

}
