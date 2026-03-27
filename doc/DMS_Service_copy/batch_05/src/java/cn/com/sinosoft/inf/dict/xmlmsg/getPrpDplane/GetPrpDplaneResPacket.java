package cn.com.sinosoft.inf.dict.xmlmsg.getPrpDplane;

import cn.com.sinosoft.inf.dict.xmlmsg.common.ResponseHeadSchema;
import cn.com.sinosoft.inf.dict.xmlmsg.common.SchemaNode;

public class GetPrpDplaneResPacket implements SchemaNode{
	private static final long serialVersionUID = 1L;
	public void validate() throws Exception {
	}
	private ResponseHeadSchema HEAD = new ResponseHeadSchema();
	private GetPrpDplaneResBody BODY = new GetPrpDplaneResBody();
	public ResponseHeadSchema getHEAD() {
		return HEAD;
	}
	public void setHEAD(ResponseHeadSchema HEAD) {
		this.HEAD = HEAD;
	}
	public GetPrpDplaneResBody getBODY() {
		return BODY;
	}
	public void setBODY(GetPrpDplaneResBody BODY) {
		this.BODY = BODY;
	}
	
}
