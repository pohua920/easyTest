package cn.com.sinosoft.inf.PMS.resDomains.prpDengageListResPacket;

import cn.com.sinosoft.inf.dict.xmlmsg.common.ResponseHeadSchema;
import cn.com.sinosoft.inf.dict.xmlmsg.common.SchemaNode;


public class PrpDengageListResPacket implements SchemaNode{
	private static final long serialVersionUID = 1L;
	public void validate() throws Exception {
	}
	private ResponseHeadSchema HEAD = new ResponseHeadSchema();
	private PrpDengageListResBody BODY = new PrpDengageListResBody();
	
	public ResponseHeadSchema getHEAD() {
		return HEAD;
	}
	public void setHEAD(ResponseHeadSchema HEAD) {
		this.HEAD = HEAD;
	}
	public PrpDengageListResBody getBODY() {
		return BODY;
	}
	public void setBODY(PrpDengageListResBody BODY) {
		this.BODY = BODY;
	}

}
