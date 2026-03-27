package cn.com.sinosoft.inf.dict.xmlmsg.getprpdcompany;

import cn.com.sinosoft.inf.dict.xmlmsg.common.RequestHeadSchema;
import cn.com.sinosoft.inf.dict.xmlmsg.common.SchemaNode;

public class GetPrpDcompanyReqPacket implements SchemaNode {

	private static final long serialVersionUID = 1L;
	public void validate() throws Exception {
	}
	private RequestHeadSchema HEAD = new RequestHeadSchema();
	private GetPrpDcompanyReqBody BODY = new GetPrpDcompanyReqBody();
	public RequestHeadSchema getHEAD() {
		return HEAD;
	}
	public void setHEAD(RequestHeadSchema HEAD) {
		this.HEAD = HEAD;
	}
	public GetPrpDcompanyReqBody getBODY() {
		return BODY;
	}
	public void setBODY(GetPrpDcompanyReqBody BODY) {
		this.BODY = BODY;
	}
	
}
