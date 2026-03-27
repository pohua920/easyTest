package cn.com.sinosoft.inf.PMS.resDomains.prpDplanListResPacket;

import cn.com.sinosoft.inf.dict.xmlmsg.common.ResponseHeadSchema;
import cn.com.sinosoft.inf.dict.xmlmsg.common.SchemaNode;


public class PrpDplanListResPacket implements SchemaNode{
	private static final long serialVersionUID = 1L;
	public void validate() throws Exception {
	}
	private ResponseHeadSchema HEAD = new ResponseHeadSchema();
	private PrpDplanListResBody BODY = new PrpDplanListResBody();
	
	public ResponseHeadSchema getHEAD() {
		return HEAD;
	}
	public void setHEAD(ResponseHeadSchema HEAD) {
		this.HEAD = HEAD;
	}
	public PrpDplanListResBody getBODY() {
		return BODY;
	}
	public void setBODY(PrpDplanListResBody BODY) {
		this.BODY = BODY;
	}

}
