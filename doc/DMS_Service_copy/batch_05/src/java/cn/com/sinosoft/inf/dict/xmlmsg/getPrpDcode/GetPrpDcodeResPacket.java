package cn.com.sinosoft.inf.dict.xmlmsg.getPrpDcode;

import cn.com.sinosoft.inf.dict.xmlmsg.common.ResponseHeadSchema;
import cn.com.sinosoft.inf.dict.xmlmsg.common.SchemaNode;

public class GetPrpDcodeResPacket implements SchemaNode{
	private static final long serialVersionUID = 1L;

	public void validate() throws Exception {
	}

	private ResponseHeadSchema HEAD = new ResponseHeadSchema();
	private GetPrpDcodeResBody BODY = new GetPrpDcodeResBody();

	public ResponseHeadSchema getHEAD() {
		return HEAD;
	}

	public void setHEAD(ResponseHeadSchema HEAD) {
		this.HEAD = HEAD;
	}

	public GetPrpDcodeResBody getBODY() {
		return BODY;
	}

	public void setBODY(GetPrpDcodeResBody BODY) {
		this.BODY = BODY;
	}

}
