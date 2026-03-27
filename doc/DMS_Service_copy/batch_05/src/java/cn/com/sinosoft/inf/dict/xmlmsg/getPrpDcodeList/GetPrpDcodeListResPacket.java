package cn.com.sinosoft.inf.dict.xmlmsg.getPrpDcodeList;

import cn.com.sinosoft.inf.dict.xmlmsg.common.ResponseHeadSchema;
import cn.com.sinosoft.inf.dict.xmlmsg.common.SchemaNode;


public class GetPrpDcodeListResPacket implements SchemaNode{
	private static final long serialVersionUID = 1L;

	public void validate() throws Exception {
	}

	private ResponseHeadSchema HEAD = new ResponseHeadSchema();
	private GetPrpDcodeListResBody BODY = new GetPrpDcodeListResBody();

	public ResponseHeadSchema getHEAD() {
		return HEAD;
	}

	public void setHEAD(ResponseHeadSchema HEAD) {
		this.HEAD = HEAD;
	}

	public GetPrpDcodeListResBody getBODY() {
		return BODY;
	}

	public void setBODY(GetPrpDcodeListResBody BODY) {
		this.BODY = BODY;
	}

}
