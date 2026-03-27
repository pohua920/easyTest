package cn.com.sinosoft.inf.PMS.resDomains.prpDframeListResPacket;

import cn.com.sinosoft.inf.dict.xmlmsg.common.ResponseHeadSchema;
import cn.com.sinosoft.inf.dict.xmlmsg.common.SchemaNode;


public class PrpDframeListResPacket implements SchemaNode{
	private static final long serialVersionUID = 1L;
	public void validate() throws Exception {
	}
	private ResponseHeadSchema HEAD = new ResponseHeadSchema();
	private PrpDframeListResBody BODY = new PrpDframeListResBody();
	
	public ResponseHeadSchema getHEAD() {
		return HEAD;
	}
	public void setHEAD(ResponseHeadSchema HEAD) {
		this.HEAD = HEAD;
	}
	public PrpDframeListResBody getBODY() {
		return BODY;
	}
	public void setBODY(PrpDframeListResBody BODY) {
		this.BODY = BODY;
	}

}
