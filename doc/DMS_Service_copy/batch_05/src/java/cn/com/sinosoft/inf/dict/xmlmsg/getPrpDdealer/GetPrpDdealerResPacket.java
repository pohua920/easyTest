package cn.com.sinosoft.inf.dict.xmlmsg.getPrpDdealer;

import cn.com.sinosoft.inf.dict.xmlmsg.common.ResponseHeadSchema;
import cn.com.sinosoft.inf.dict.xmlmsg.common.SchemaNode;

public class GetPrpDdealerResPacket implements SchemaNode{
	private static final long serialVersionUID = 1L;
	public void validate() throws Exception {
	}
	private ResponseHeadSchema HEAD = new ResponseHeadSchema();
	private GetPrpDdealerResBody BODY = new GetPrpDdealerResBody();
	public ResponseHeadSchema getHEAD() {
		return HEAD;
	}
	public void setHEAD(ResponseHeadSchema HEAD) {
		this.HEAD = HEAD;
	}
	public GetPrpDdealerResBody getBODY() {
		return BODY;
	}
	public void setBODY(GetPrpDdealerResBody BODY) {
		this.BODY = BODY;
	}
	
}
