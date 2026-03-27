package cn.com.sinosoft.inf.PMS.resDomains.prpDitemListResPacket;

import cn.com.sinosoft.inf.dict.xmlmsg.common.ResponseHeadSchema;
import cn.com.sinosoft.inf.dict.xmlmsg.common.SchemaNode;


public class PrpDitemListResPacket implements SchemaNode{
	private static final long serialVersionUID = 1L;
	public void validate() throws Exception {
	}
	private ResponseHeadSchema HEAD = new ResponseHeadSchema();
	private PrpDitemListResBody BODY = new PrpDitemListResBody();
	
	public ResponseHeadSchema getHEAD() {
		return HEAD;
	}
	public void setHEAD(ResponseHeadSchema HEAD) {
		this.HEAD = HEAD;
	}
	public PrpDitemListResBody getBODY() {
		return BODY;
	}
	public void setBODY(PrpDitemListResBody BODY) {
		this.BODY = BODY;
	}

}
