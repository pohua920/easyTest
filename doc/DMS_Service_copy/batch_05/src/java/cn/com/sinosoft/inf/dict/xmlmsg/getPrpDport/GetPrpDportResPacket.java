package cn.com.sinosoft.inf.dict.xmlmsg.getPrpDport;

import cn.com.sinosoft.inf.dict.xmlmsg.common.ResponseHeadSchema;
import cn.com.sinosoft.inf.dict.xmlmsg.common.SchemaNode;

public class GetPrpDportResPacket implements SchemaNode{
	private static final long serialVersionUID = 1L;
	public void validate() throws Exception {
	}
	private ResponseHeadSchema HEAD = new ResponseHeadSchema();
	private GetPrpDportResBody BODY = new GetPrpDportResBody();
	public ResponseHeadSchema getHEAD() {
		return HEAD;
	}
	public void setHEAD(ResponseHeadSchema HEAD) {
		this.HEAD = HEAD;
	}
	public GetPrpDportResBody getBODY() {
		return BODY;
	}
	public void setBODY(GetPrpDportResBody BODY) {
		this.BODY = BODY;
	}
	
}
