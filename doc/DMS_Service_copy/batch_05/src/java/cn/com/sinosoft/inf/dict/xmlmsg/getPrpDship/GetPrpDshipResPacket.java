package cn.com.sinosoft.inf.dict.xmlmsg.getPrpDship;

import cn.com.sinosoft.inf.dict.xmlmsg.common.ResponseHeadSchema;
import cn.com.sinosoft.inf.dict.xmlmsg.common.SchemaNode;

public class GetPrpDshipResPacket implements SchemaNode{
	private static final long serialVersionUID = 1L;
	public void validate() throws Exception {
	}
	private ResponseHeadSchema HEAD = new ResponseHeadSchema();
	private GetPrpDshipResBody BODY = new GetPrpDshipResBody();
	public ResponseHeadSchema getHEAD() {
		return HEAD;
	}
	public void setHEAD(ResponseHeadSchema HEAD) {
		this.HEAD = HEAD;
	}
	public GetPrpDshipResBody getBODY() {
		return BODY;
	}
	public void setBODY(GetPrpDshipResBody BODY) {
		this.BODY = BODY;
	}
}
