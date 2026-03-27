package cn.com.sinosoft.inf.dict.xmlmsg.getPrpDexch;

import cn.com.sinosoft.inf.dict.xmlmsg.common.RequestHeadSchema;
import cn.com.sinosoft.inf.dict.xmlmsg.common.SchemaNode;

public class GetPrpDexchReqPacket implements SchemaNode {
	
	private static final long serialVersionUID = 1L;

	public void validate() throws Exception {
	}
	private RequestHeadSchema HEAD = new RequestHeadSchema();
	private GetPrpDexchReqBody BODY = new GetPrpDexchReqBody();

	public RequestHeadSchema getHEAD() {
		return HEAD;
	}
	public void setHEAD(RequestHeadSchema HEAD) {
		this.HEAD = HEAD;
	}
	public GetPrpDexchReqBody getBODY() {
		return BODY;
	}
	public void setBODY(GetPrpDexchReqBody BODY) {
		this.BODY = BODY;
	}
	

}
