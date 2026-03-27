package cn.com.sinosoft.inf.dict.xmlmsg.getPrpDexch;

import cn.com.sinosoft.inf.dict.xmlmsg.common.ResponseHeadSchema;
import cn.com.sinosoft.inf.dict.xmlmsg.common.SchemaNode;

public class GetPrpDexchResPacket implements SchemaNode {

	private static final long serialVersionUID = 1L;

	public void validate() throws Exception {
	}

	private ResponseHeadSchema HEAD = new ResponseHeadSchema();
	private GetPrpDexchResBody BODY = new GetPrpDexchResBody();

	public ResponseHeadSchema getHEAD() {
		return HEAD;
	}

	public void setHEAD(ResponseHeadSchema HEAD) {
		this.HEAD = HEAD;
	}

	public GetPrpDexchResBody getBODY() {
		return BODY;
	}

	public void setBODY(GetPrpDexchResBody BODY) {
		this.BODY = BODY;
	}

}
