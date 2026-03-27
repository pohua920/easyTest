package cn.com.sinosoft.inf.PMS.resDomains.prpDriskListResPacket;

import cn.com.sinosoft.inf.dict.xmlmsg.common.ResponseHeadSchema;
import cn.com.sinosoft.inf.dict.xmlmsg.common.SchemaNode;


public class PrpDriskListResPacket implements SchemaNode{
	private static final long serialVersionUID = 1L;
	public void validate() throws Exception {
	}
	private ResponseHeadSchema HEAD = new ResponseHeadSchema();
	private PrpDriskListResBody BODY = new PrpDriskListResBody();
	
	public ResponseHeadSchema getHEAD() {
		return HEAD;
	}
	public void setHEAD(ResponseHeadSchema HEAD) {
		this.HEAD = HEAD;
	}
	public PrpDriskListResBody getBODY() {
		return BODY;
	}
	public void setBODY(PrpDriskListResBody BODY) {
		this.BODY = BODY;
	}

}
