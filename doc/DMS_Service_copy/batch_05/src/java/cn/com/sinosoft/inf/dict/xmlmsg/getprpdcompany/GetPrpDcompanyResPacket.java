package cn.com.sinosoft.inf.dict.xmlmsg.getprpdcompany;

import cn.com.sinosoft.inf.dict.xmlmsg.common.ResponseHeadSchema;
import cn.com.sinosoft.inf.dict.xmlmsg.common.SchemaNode;

public class GetPrpDcompanyResPacket implements SchemaNode{
	private static final long serialVersionUID = 1L;
	public void validate() throws Exception {
	}
	private ResponseHeadSchema HEAD = new ResponseHeadSchema();
	private GetPrpDcompanyResBody BODY = new GetPrpDcompanyResBody();
	
	public ResponseHeadSchema getHEAD() {
		return HEAD;
	}
	public void setHEAD(ResponseHeadSchema HEAD) {
		this.HEAD = HEAD;
	}
	public GetPrpDcompanyResBody getBODY() {
		return BODY;
	}
	public void setBODY(GetPrpDcompanyResBody BODY) {
		this.BODY = BODY;
	}

}
