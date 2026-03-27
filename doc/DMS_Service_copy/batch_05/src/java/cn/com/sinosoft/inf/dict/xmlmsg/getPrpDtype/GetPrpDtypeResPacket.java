package cn.com.sinosoft.inf.dict.xmlmsg.getPrpDtype;

import cn.com.sinosoft.inf.dict.xmlmsg.common.ResponseHeadSchema;
import cn.com.sinosoft.inf.dict.xmlmsg.common.SchemaNode;


public class GetPrpDtypeResPacket implements SchemaNode{

	private static final long serialVersionUID = 1L;

	public void validate() throws Exception {
	}

	private ResponseHeadSchema HEAD = new ResponseHeadSchema();
	private GetPrpDtypeResBody BODY = new GetPrpDtypeResBody();

	public ResponseHeadSchema getHEAD() {
		return HEAD;
	}

	public void setHEAD(ResponseHeadSchema HEAD) {
		this.HEAD = HEAD;
	}

	public GetPrpDtypeResBody getBODY() {
		return BODY;
	}

	public void setBODY(GetPrpDtypeResBody BODY) {
		this.BODY = BODY;
	}

}
