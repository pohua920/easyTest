package cn.com.sinosoft.inf.dict.xmlmsg.getUpperCode;

import cn.com.sinosoft.inf.dict.xmlmsg.common.ResponseHeadSchema;
import cn.com.sinosoft.inf.dict.xmlmsg.common.SchemaNode;

public class GetUpperCodeResPacket implements SchemaNode{
	private static final long serialVersionUID = 1L;

	public void validate() throws Exception {
	}

	private ResponseHeadSchema HEAD = new ResponseHeadSchema();
	private GetUpperCodeResBody BODY = new GetUpperCodeResBody();

	public ResponseHeadSchema getHEAD() {
		return HEAD;
	}

	public void setHEAD(ResponseHeadSchema HEAD) {
		this.HEAD = HEAD;
	}

	public GetUpperCodeResBody getBODY() {
		return BODY;
	}

	public void setBODY(GetUpperCodeResBody BODY) {
		this.BODY = BODY;
	}

}
