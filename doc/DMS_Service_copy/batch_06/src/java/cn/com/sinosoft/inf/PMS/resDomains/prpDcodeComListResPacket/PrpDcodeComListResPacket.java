package cn.com.sinosoft.inf.PMS.resDomains.prpDcodeComListResPacket;

import cn.com.sinosoft.inf.dict.xmlmsg.common.ResponseHeadSchema;
import cn.com.sinosoft.inf.dict.xmlmsg.common.SchemaNode;


public class PrpDcodeComListResPacket implements SchemaNode{
	private static final long serialVersionUID = 1L;
	public void validate() throws Exception {
	}
	private ResponseHeadSchema HEAD = new ResponseHeadSchema();
	private PrpDcodeComListResBody BODY = new PrpDcodeComListResBody();
	
	public ResponseHeadSchema getHEAD() {
		return HEAD;
	}
	public void setHEAD(ResponseHeadSchema HEAD) {
		this.HEAD = HEAD;
	}
	public PrpDcodeComListResBody getBODY() {
		return BODY;
	}
	public void setBODY(PrpDcodeComListResBody BODY) {
		this.BODY = BODY;
	}

}
