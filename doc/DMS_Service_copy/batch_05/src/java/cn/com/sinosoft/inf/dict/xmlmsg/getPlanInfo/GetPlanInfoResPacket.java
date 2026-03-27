package cn.com.sinosoft.inf.dict.xmlmsg.getPlanInfo;

import cn.com.sinosoft.inf.dict.xmlmsg.common.ResponseHeadSchema;
import cn.com.sinosoft.inf.dict.xmlmsg.common.SchemaNode;
public class GetPlanInfoResPacket implements SchemaNode {

	private static final long serialVersionUID = 1L;

	public void validate() throws Exception {
	}

	private ResponseHeadSchema HEAD = new ResponseHeadSchema();
	private GetPlanInfoResBody BODY = new GetPlanInfoResBody();

	public ResponseHeadSchema getHEAD() {
		return HEAD;
	}

	public void setHEAD(ResponseHeadSchema HEAD) {
		this.HEAD = HEAD;
	}

	public GetPlanInfoResBody getBODY() {
		return BODY;
	}

	public void setBODY(GetPlanInfoResBody BODY) {
		this.BODY = BODY;
	}

}
