package cn.com.sinosoft.inf.dict.xmlmsg.getPrpDdriver;

import cn.com.sinosoft.inf.dict.xmlmsg.common.ResponseHeadSchema;
import cn.com.sinosoft.inf.dict.xmlmsg.common.SchemaNode;

public class GetPrpDdriverResPacket implements SchemaNode{
	private static final long serialVersionUID = 1L;
	public void validate() throws Exception {
	}
	private ResponseHeadSchema HEAD = new ResponseHeadSchema();
	private GetPrpDdriverResBody BODY = new GetPrpDdriverResBody();
	public ResponseHeadSchema getHEAD() {
		return HEAD;
	}
	public void setHEAD(ResponseHeadSchema HEAD) {
		this.HEAD = HEAD;
	}
	public GetPrpDdriverResBody getBODY() {
		return BODY;
	}
	public void setBODY(GetPrpDdriverResBody BODY) {
		this.BODY = BODY;
	}
	
	
}
