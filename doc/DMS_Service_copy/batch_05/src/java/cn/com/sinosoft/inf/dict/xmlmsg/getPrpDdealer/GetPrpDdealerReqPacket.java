package cn.com.sinosoft.inf.dict.xmlmsg.getPrpDdealer;

import cn.com.sinosoft.inf.dict.xmlmsg.common.RequestHeadSchema;
import cn.com.sinosoft.inf.dict.xmlmsg.common.SchemaNode;

public class GetPrpDdealerReqPacket implements SchemaNode {

	private static final long serialVersionUID = 1L;
	public void validate() throws Exception {
	}
	private RequestHeadSchema HEAD = new RequestHeadSchema();
	private GetPrpDdealerReqBody BODY = new GetPrpDdealerReqBody();
	public RequestHeadSchema getHEAD() {
		return HEAD;
	}
	public void setHEAD(RequestHeadSchema HEAD) {
		this.HEAD = HEAD;
	}
	public GetPrpDdealerReqBody getBODY() {
		return BODY;
	}
	public void setBODY(GetPrpDdealerReqBody BODY) {
		this.BODY = BODY;
	}
	
	
}
