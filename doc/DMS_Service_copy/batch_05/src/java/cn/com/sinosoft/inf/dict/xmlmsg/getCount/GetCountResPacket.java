package cn.com.sinosoft.inf.dict.xmlmsg.getCount;

import cn.com.sinosoft.inf.dict.xmlmsg.common.ResponseHeadSchema;
import cn.com.sinosoft.inf.dict.xmlmsg.common.SchemaNode;

public class GetCountResPacket implements SchemaNode{

	private static final long serialVersionUID = 1L;

	public void validate() throws Exception {
	}

	private ResponseHeadSchema HEAD = new ResponseHeadSchema();
	private GetCountResBody BODY = new GetCountResBody();

	public ResponseHeadSchema getHEAD() {
		return HEAD;
	}

	public void setHEAD(ResponseHeadSchema HEAD) {
		this.HEAD = HEAD;
	}

	public GetCountResBody getBODY() {
		return BODY;
	}

	public void setBODY(GetCountResBody body) {
		this.BODY = body;
	}

}
