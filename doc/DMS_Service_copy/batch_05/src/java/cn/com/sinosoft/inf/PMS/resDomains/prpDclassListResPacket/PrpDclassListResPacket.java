package cn.com.sinosoft.inf.PMS.resDomains.prpDclassListResPacket;

import cn.com.sinosoft.inf.dict.xmlmsg.common.ResponseHeadSchema;
import cn.com.sinosoft.inf.dict.xmlmsg.common.SchemaNode;


public class PrpDclassListResPacket implements SchemaNode{
	private static final long serialVersionUID = 1L;
	public void validate() throws Exception {
	}
	private ResponseHeadSchema HEAD = new ResponseHeadSchema();
	private PrpDclassListResBody BODY = new PrpDclassListResBody();
	
	public ResponseHeadSchema getHEAD() {
		return HEAD;
	}
	public void setHEAD(ResponseHeadSchema HEAD) {
		this.HEAD = HEAD;
	}
	public PrpDclassListResBody getBODY() {
		return BODY;
	}
	public void setBODY(PrpDclassListResBody BODY) {
		this.BODY = BODY;
	}

}
