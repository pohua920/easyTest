package cn.com.sinosoft.inf.dict.xmlmsg.getPrpDcompanyList;

import cn.com.sinosoft.inf.dict.xmlmsg.common.ResponseHeadSchema;
import cn.com.sinosoft.inf.dict.xmlmsg.common.SchemaNode;

public class GetPrpDcompanyListResPacket implements SchemaNode{
	private static final long serialVersionUID = 1L;
	public void validate() throws Exception {
	}
	private ResponseHeadSchema HEAD = new ResponseHeadSchema();
	private GetPrpDcompanyListResBody BODY = new GetPrpDcompanyListResBody();
	
	public ResponseHeadSchema getHEAD() {
		return HEAD;
	}
	public void setHEAD(ResponseHeadSchema HEAD) {
		this.HEAD = HEAD;
	}
	public GetPrpDcompanyListResBody getBODY() {
		return BODY;
	}
	public void setBODY(GetPrpDcompanyListResBody BODY) {
		this.BODY = BODY;
	}

}
