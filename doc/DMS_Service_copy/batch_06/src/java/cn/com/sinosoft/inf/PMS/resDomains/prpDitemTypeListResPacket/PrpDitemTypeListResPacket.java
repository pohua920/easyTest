package cn.com.sinosoft.inf.PMS.resDomains.prpDitemTypeListResPacket;

import cn.com.sinosoft.inf.dict.xmlmsg.common.ResponseHeadSchema;
import cn.com.sinosoft.inf.dict.xmlmsg.common.SchemaNode;


public class PrpDitemTypeListResPacket implements SchemaNode{
	private static final long serialVersionUID = 1L;
	public void validate() throws Exception {
	}
	private ResponseHeadSchema HEAD = new ResponseHeadSchema();
	private PrpDitemTypeListResBody BODY = new PrpDitemTypeListResBody();
	
	public ResponseHeadSchema getHEAD() {
		return HEAD;
	}
	public void setHEAD(ResponseHeadSchema HEAD) {
		this.HEAD = HEAD;
	}
	public PrpDitemTypeListResBody getBODY() {
		return BODY;
	}
	public void setBODY(PrpDitemTypeListResBody BODY) {
		this.BODY = BODY;
	}

}
